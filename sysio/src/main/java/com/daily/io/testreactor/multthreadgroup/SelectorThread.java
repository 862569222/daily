package com.daily.io.testreactor.multthreadgroup;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Author: zhaibo
 * @CreateTime: 2025-01-18
 * @Description: TODO
 */
public class SelectorThread implements Runnable {
    // 每线程对应一个selector，
    // 多线程情况下，该主机，该程序的并发客户端被分配到多个selector上
    //注意，每个客户端，只绑定到其中一个selector
    //其实不会有交互问题
    Selector selector = null;
    LinkedBlockingQueue<Channel> queue = new LinkedBlockingQueue<>();
    SelectorThreadGroup threadGroup;
    public SelectorThread(SelectorThreadGroup selectorThreadGroup) {

        try {
            this.threadGroup = selectorThreadGroup;
            selector = Selector.open();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {

        while (true) {

            try {
                //1,select()
                int nums = selector.select();
                System.out.println("nums: " + nums);
                //2,处理keys
                if (nums > 0) {
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        iterator.remove();
                        if (key.isAcceptable()) {
                            acceptHandler(key);
                        }
                        if (key.isReadable()) {
                            readHander(key);
                        }
                        if (key.isWritable()) {

                        }

                    }
                }

                //3,处理队列注册消息
                if (queue.size() > 0) {
                    Channel channel = queue.take();
                    if (channel instanceof ServerSocketChannel) {
                        ServerSocketChannel socketChannel = (ServerSocketChannel) channel;
                        socketChannel.register(selector, SelectionKey.OP_ACCEPT);
                        System.out.println(Thread.currentThread().getName() + " register listen");
                    }
                    if (channel instanceof SocketChannel) {
                        SocketChannel socketChannel = (SocketChannel) channel;
                        ByteBuffer buffer = ByteBuffer.allocate(4096);
                        socketChannel.register(selector, SelectionKey.OP_READ, buffer);
                        System.out.println(Thread.currentThread().getName() + " register client: " + socketChannel.getRemoteAddress());
                    }
                }
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }

        }

    }

    private void readHander(SelectionKey key) {
        System.out.println(Thread.currentThread().getName() + " read......");
        ByteBuffer buffer = (ByteBuffer) key.attachment();
        SocketChannel client = (SocketChannel) key.channel();
        buffer.clear();
        while (true) {
            try {
                int num = client.read(buffer);
                if (num > 0) {
                    buffer.flip();  //将读到的内容翻转，然后直接写出
                    while (buffer.hasRemaining()) {
                        client.write(buffer);
                    }
                    buffer.clear();
                } else if (num == 0) {
                    break;
                } else if (num < 0) {
                    //客户端断开了
                    System.out.println("client: " + client.getRemoteAddress() + "closed......");
                    key.cancel();
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void acceptHandler(SelectionKey key) {
        System.out.println(Thread.currentThread().getName() + "   acceptHandler......");
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
        try {
            SocketChannel socketChannel = serverSocketChannel.accept();
            socketChannel.configureBlocking(false);
            //注册到那个selector上呢？
            //这个版本区分了boss worker
            register(socketChannel);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void register(Channel channel) {
        if (channel instanceof ServerSocketChannel) {
            SelectorThread selectorThread = threadGroup.nextSelectorThread();
            selectorThread.queue.add(channel);
            selectorThread.selector.wakeup();
        }else if (channel instanceof SocketChannel) {
            SelectorThread workerSelectorThread = threadGroup.nextWorkerSelectorThread();
            workerSelectorThread.queue.add(channel);
            workerSelectorThread.selector.wakeup();
        }
    }
}
