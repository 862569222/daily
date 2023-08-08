package cn.daily.blockingqueue;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 项目名称：daily
 * 类 名 称：LinkedBlockingQueueTest
 * 类 描 述：TODO
 * 创建时间：2023/7/8 10:45
 *
 * @author：zhaibo
 */
public class LinkedBlockingQueueTest {
    public static void main(String[] args) throws InterruptedException {
        LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>();
        queue.add(1);
        queue.offer(2);
        queue.offer(3,1, TimeUnit.MILLISECONDS);
        queue.put(4);


        System.out.println(queue.poll());
        System.out.println(queue.poll(1, TimeUnit.MILLISECONDS));
        System.out.println(queue.take());
        System.out.println(queue.peek());
    }
}
