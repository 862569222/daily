package com.daily.io.testreactor.singlethread;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.Channel;
import java.nio.channels.ServerSocketChannel;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: zhaibo
 * @CreateTime: 2025-01-18
 * @Description: TODO
 */
public class SelectorThreadGroup {

    SelectorThread[] selectorThreads;
    ServerSocketChannel serverSocketChannel;
    AtomicInteger ct = new AtomicInteger(0);

    public SelectorThreadGroup(int threadCount) {
        selectorThreads = new SelectorThread[threadCount];
        for (int i = 0; i < selectorThreads.length; i++) {
            selectorThreads[i] = new SelectorThread();
            new Thread(selectorThreads[i]).start();
        }
    }

    public void bind(int port) {

        try {
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.bind(new InetSocketAddress(port));
            register(serverSocketChannel);
//            serverSocketChannel.register(nextSelectorThread().selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void register(Channel channel) throws InterruptedException {
        SelectorThread selectorThread = nextSelectorThread();
        //1,通过队列传递数据 消息
        selectorThread.queue.put(channel);
        //2,通过打断阻塞，让对应的线程去自己在打断后完成注册selector
        selectorThread.selector.wakeup();

    }

    public SelectorThread nextSelectorThread() {
        return selectorThreads[ct.incrementAndGet() % selectorThreads.length];
    }
}
