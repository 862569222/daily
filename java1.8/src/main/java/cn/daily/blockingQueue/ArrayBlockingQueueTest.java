package cn.daily.blockingQueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 项目名称：daily
 * 类 名 称：ArrayBlockingQueue
 * 类 描 述：TODO
 * 创建时间：2023/7/8 10:36
 *
 * @author：zhaibo
 */
public class ArrayBlockingQueueTest {
    public static void main(String[] args) throws InterruptedException {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(5);

        queue.add(1);
        queue.offer(2);
        queue.offer(3, 1, TimeUnit.MILLISECONDS);
        queue.put(4);


        System.out.println(queue.poll());
        System.out.println(queue.poll(1, TimeUnit.MILLISECONDS));
        System.out.println(queue.take());
        System.out.println(queue.peek());

    }
}
