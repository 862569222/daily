package com.daily.io.testreactor.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import javax.lang.model.element.VariableElement;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;


/*
    1，先假设一个需求，写一个RPC
    2，来回通信，连接数量，拆包？
    3，动态代理呀，序列化，协议封装
    4，连接池
    5，就像调用本地方法一样去调用远程的方法，面向java中就是所谓的 面向interface开发
 */
public class MyRPCTest {

    @Test
    public void startServer(){

        NioEventLoopGroup boss = new NioEventLoopGroup(1);
        NioEventLoopGroup worker = boss;
        ServerBootstrap b = new ServerBootstrap();
        ChannelFuture f = b.group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ChannelPipeline p = ch.pipeline();
                        p.addLast(new ServerRequestHandler());
                    }
                })
                .bind(new InetSocketAddress("127.0.0.1",19090));
        try {
            f.sync().channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void get(){


        //动态代理 1. 生成代理类  2. 代理类调用方法
        Car car = MyProxy.proxyGet(Car.class);



    }

}

class MyProxy{
    public static <T> T proxyGet(Class<T> clazz ){
        ClassLoader classLoader = clazz.getClassLoader();
        Class<?>[] interfaces = new Class[]{clazz};

        return (T) Proxy.newProxyInstance(classLoader,interfaces,(proxy, method, args)->{
            //1. 连接池，初始化连接
            MyPoolFactory factory = MyPoolFactory.getFactory();
            NioSocketChannel client = factory.getClient(new InetSocketAddress("127.0.0.1", 19090));
            //2. 序列化
            String name = clazz.getName();
            String methodName = method.getName();
            Class<?>[] parameterTypes = method.getParameterTypes();

            MyContent content = new MyContent();
            content.setArgs(args);
            content.setName(name);
            content.setMethodName(methodName);
            content.setParameterTypes(parameterTypes);

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(content);
            byte[] msgBody = bos.toByteArray();

            //2，requestID+message  ，本地要缓存
            //协议：【header<>】【msgBody】
            MyHeader header = createHeader(msgBody);
            bos.reset();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(header);
            byte[] headerByte = bos.toByteArray();

            CountDownLatch countDownLatch = new CountDownLatch(1);
            ResponseCallback.addCallback(header.getRequestID(),()->{
                countDownLatch.countDown();
            });

            //4，发送--> 走IO  out -->走Netty（event 驱动）
            ByteBuf byteBuf = PooledByteBufAllocator.DEFAULT.directBuffer(headerByte.length + msgBody.length);
            byteBuf.writeBytes(headerByte);
            byteBuf.writeBytes(msgBody);
            ChannelFuture channelFuture = client.writeAndFlush(byteBuf);
            channelFuture.sync();  //io是双向的，你看似有个sync，她仅代表out

            countDownLatch.await();
            //3. 协议封装
            //4. 远程调用
            //5. 解包
            //6. 反序列化
            //7. 返回
            return null;
        });
    }

    private static MyHeader createHeader(byte[] msgBody) {
        MyHeader header = new MyHeader();
        int flag = 0x14141414;
        long requestID = Math.abs(UUID.randomUUID().getLeastSignificantBits());
        int dataLen = msgBody.length;

        header.setFlag(flag);
        header.setRequestID(requestID);
        header.setDataLen(dataLen);

        return header;
    }
}

class ResponseCallback{
    static final ConcurrentHashMap<Long, Runnable> mapping = new ConcurrentHashMap<>();

    public static void  addCallback(long requestID, Runnable runnable){
        mapping.put(requestID,runnable);
    }
    public static void runCallback(long requestID){
        Runnable runnable = mapping.get(requestID);
        runnable.run();
        removeCallback(requestID);
    }

    private static void removeCallback(long requestID) {
        mapping.remove(requestID);
    }
}

/**
 * 定义一个连接池工厂，用于管理连接池，分配连接
 */
class MyPoolFactory {
    int poolSize = 1;
    //工厂是单例的
    private static MyPoolFactory factory = new MyPoolFactory();

    private MyPoolFactory() {
    }

    public static MyPoolFactory getFactory() {
        return factory;
    }

    Random r = new Random();

    ConcurrentHashMap<InetSocketAddress, MyPool> pools = new ConcurrentHashMap<>();

    //获取连接 一个consumer可以连接多个provider，每个provider都有自己的连接池
    public synchronized NioSocketChannel getClient(InetSocketAddress address) {
        int index = r.nextInt(poolSize);
        //1. 从连接池中获取连接
        MyPool myPool = pools.get(address);
        if (myPool == null) {
            myPool = new MyPool(poolSize);
            pools.putIfAbsent(address, myPool);
        }
        //存在连接，并且是有效的，直接返回
        if (myPool.clients[index] != null && myPool.clients[index].isActive()) {
            return myPool.clients[index];
        }

        //2. 如果没有连接，创建连接
        synchronized (myPool.lock[index]) {
            //3. 返回连接
            return myPool.clients[index] = create(address);
        }
    }

    private NioSocketChannel create(InetSocketAddress address) {
        //创建连接
        NioEventLoopGroup group = new NioEventLoopGroup(1);
        Bootstrap bs = new Bootstrap();
        ChannelFuture connect = bs.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        ChannelPipeline p = nioSocketChannel.pipeline();
                        p.addLast(new ClientResponse());
                    }
                })
                .connect(address);

        try {
            NioSocketChannel client = (NioSocketChannel) connect.sync().channel();
            return client;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}


class ServerRequestHandler extends ChannelInboundHandlerAdapter{

    //provider:
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        ByteBuf buf = (ByteBuf) msg;
        ByteBuf sendBuf = buf.copy();

        if(buf.readableBytes() >= 110){
            byte[] bytes = new byte[110];
            buf.readBytes(bytes);
            ByteArrayInputStream in = new ByteArrayInputStream(bytes);
            ObjectInputStream oin = new ObjectInputStream(in);
            MyHeader header = (MyHeader) oin.readObject();
            System.out.println("server response @ id: "+ header.getRequestID());


            if(buf.readableBytes() >= header.getDataLen()){

                byte[] data = new byte[(int)header.getDataLen()];
                buf.readBytes(data);
                ByteArrayInputStream din = new ByteArrayInputStream(data);
                ObjectInputStream doin = new ObjectInputStream(din);
                MyContent content = (MyContent) doin.readObject();
                System.out.println(content.getName());
            }


        }


        ChannelFuture channelFuture = ctx.writeAndFlush(sendBuf);
        channelFuture.sync();
    }

}


class ClientResponse extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("client receive msg:" + msg);
        ByteBuf byteBuf = (ByteBuf) msg;

        if(byteBuf.readableBytes() >=110){
            byte[] bytes = new byte[110];
            byteBuf.readBytes(bytes);
            ByteArrayInputStream is = new ByteArrayInputStream(bytes);
            ObjectInputStream os = new ObjectInputStream(is);
            MyHeader myHeader = (MyHeader) os.readObject();
            System.out.println("client response @ id: "+ myHeader.getRequestID());
            //TODO 接收到的响应应该通知谁

            if(byteBuf.readableBytes() >= myHeader.getDataLen()){

                byte[] data = new byte[(int)myHeader.getDataLen()];
                byteBuf.readBytes(data);
                ByteArrayInputStream din = new ByteArrayInputStream(data);
                ObjectInputStream doin = new ObjectInputStream(din);
                MyContent content = (MyContent) doin.readObject();
                System.out.println(content.getName());
            }
        }


    }

}

/**
 * 定义连接池
 */
class MyPool{
    NioSocketChannel[] clients;
    Object[] lock;

    public MyPool(int poolSize) {
        //初次建立连接都是空的，随用随建立
        clients = new NioSocketChannel[poolSize];
        lock = new Object[poolSize];
        for (int i = 0; i < poolSize; i++) {
            lock[i] = new Object();
        }
    }
}

class MyContent{
    String name;
    String methodName;
    Object[] args;
    Class<?>[] parameterTypes;
    //TODO 为什么要有这个字段
    long requestID;


    public String getName() {
        return name;
    }

    public String getMethodName() {
        return methodName;
    }

    public Object[] getArgs() {
        return args;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public long getRequestID() {
        return requestID;
    }

    public void setRequestID(long requestID) {
        this.requestID = requestID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public void setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }
}


class MyHeader{
    int flag; //标识
    long requestID; //请求ID
    long dataLen; //数据长度

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public long getRequestID() {
        return requestID;
    }

    public void setRequestID(long requestID) {
        this.requestID = requestID;
    }

    public long getDataLen() {
        return dataLen;
    }

    public void setDataLen(long dataLen) {
        this.dataLen = dataLen;
    }
}

interface Car{
    String move(String msg);
}
