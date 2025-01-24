package com.daily.io.testreactor.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;
import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class MyNetty {

    @Test
    public void myBytebuf() {
        ByteBuf buf = PooledByteBufAllocator.DEFAULT.heapBuffer(8, 20);
        print(buf);
        buf.writeBytes(new byte[]{1, 2, 3, 4});
        print(buf);
        buf.writeBytes(new byte[]{1, 2, 3, 4});
        print(buf);
        buf.writeBytes(new byte[]{1, 2, 3, 4});
        print(buf);
    }

    public static void print(ByteBuf buf) {
        System.out.println("buf.isReadable()    :" + buf.isReadable());
        System.out.println("buf.readerIndex()   :" + buf.readerIndex());
        System.out.println("buf.readableBytes() " + buf.readableBytes());
        System.out.println("buf.isWritable()    :" + buf.isWritable());
        System.out.println("buf.writerIndex()   :" + buf.writerIndex());
        System.out.println("buf.writableBytes() :" + buf.writableBytes());
        System.out.println("buf.capacity()  :" + buf.capacity());
        System.out.println("buf.maxCapacity()   :" + buf.maxCapacity());
        System.out.println("buf.isDirect()  :" + buf.isDirect());
        System.out.println("--------------");
    }


    @Test
    public void loopExecutor() throws IOException {
        // 1. 创建一个单线程的 EventLoopGroup
        NioEventLoopGroup group = new NioEventLoopGroup(2);
        group.execute(() -> {
            for (; ; ) {
                System.out.println("hello world001");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        group.execute(() -> {
            for (; ; ) {
                System.out.println("hello world002");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        System.in.read();

    }


    @Test
    public void clientMode() throws Exception {
        NioEventLoopGroup group = new NioEventLoopGroup(2);

        NioSocketChannel client = new NioSocketChannel();
        group.register(client);

        ChannelFuture connect = client.connect(new InetSocketAddress("127.0.0.1", 19090));

        ChannelFuture sync = connect.sync();

        ByteBuf byteBuf = Unpooled.copiedBuffer("hello, server".getBytes(StandardCharsets.UTF_8));
        ChannelFuture send = client.writeAndFlush(byteBuf);

        send.sync();

        sync.channel().closeFuture().sync();
        System.out.println("client is over...");
    }

    @Test
    public void serverMode() throws Exception {
        NioEventLoopGroup group = new NioEventLoopGroup(2);
        NioServerSocketChannel server = new NioServerSocketChannel();
        group.register(server);

        ChannelPipeline pipeline = server.pipeline().addLast(new MyAcceptHandler(group, new ChannelInit()));
        ChannelFuture bind = server.bind(new InetSocketAddress("127.0.0.1", 19090));

        bind.sync().channel().closeFuture().sync();
        System.out.println("server is over...");
    }


    @Test
    public void nettyClient() throws Exception {
        NioEventLoopGroup group = new NioEventLoopGroup(1);

        Bootstrap bs = new Bootstrap();
        ChannelFuture channelFuture = bs.group(group)
                .channel(NioSocketChannel.class)
//                .handler(new ChannelInit())
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline p = ch.pipeline();
                        p.addLast(new ChannelInit());
                    }
                })
                .connect(new InetSocketAddress("127.0.0.1", 19090));

        Channel client = channelFuture.sync().channel();
        ByteBuf byteBuf = Unpooled.copiedBuffer("hello, server".getBytes(StandardCharsets.UTF_8));
        ChannelFuture send = client.writeAndFlush(byteBuf);
        send.sync();
        client.closeFuture().sync();

    }

    @Test
    public void nettyServer() throws Exception {
        NioEventLoopGroup group = new NioEventLoopGroup(2);
        ServerBootstrap bs = new ServerBootstrap();
        ChannelFuture bind = bs.group(group, group)
                .channel(NioServerSocketChannel.class)
//                .childHandler(new ChannelInit())
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ChannelPipeline p = ch.pipeline();
                        p.addLast(new MyInHandler());
                    }
                })
                .bind(new InetSocketAddress("127.0.0.1", 19090));

        bind.sync().channel().closeFuture().sync();

    }

}

class MyAcceptHandler extends ChannelInboundHandlerAdapter {

    private final EventLoopGroup selector;
    private final ChannelHandler handler;

    public MyAcceptHandler(EventLoopGroup thread, ChannelHandler myInHandler) {
        this.selector = thread;
        this.handler = myInHandler;  //ChannelInit
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelRegistered");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelActive");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //  listen  socket   accept    client
        //  socket           R/W
        SocketChannel client = (SocketChannel) msg;

        // 响应式 handler
        ChannelPipeline pipeline = client.pipeline();
        pipeline.addLast(handler);

        // 注册到 selector
        selector.register(client);
    }

}

@ChannelHandler.Sharable
class ChannelInit extends ChannelInboundHandlerAdapter {

        @Override
        public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
            Channel client = ctx.channel();
            ChannelPipeline p = client.pipeline();
            p.addLast(new MyInHandler());
            p.remove(this);
        }
}

/*
    * 为什么要有一个 ChannelInit，可以没有，但是 MyInHandler 就得设计成单例
 */
//@ChannelHandler.Sharable
class MyInHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
//        CharSequence str = byteBuf.readCharSequence(byteBuf.readableBytes(), CharsetUtil.UTF_8);
        CharSequence str = byteBuf.getCharSequence(0, byteBuf.readableBytes(), CharsetUtil.UTF_8);
        System.out.println(str);
        ctx.writeAndFlush(byteBuf);
    }
}
