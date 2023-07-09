package cn.daily.blockingQueue;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * @BelongsProject: daily
 * @BelongsPackage: cn.daily.blockingQueue
 * @Author: zhaibo
 * @CreateTime: 2023-07-08
 * @Description: TODO
 */
public class SynchronousQueueTest {
    public static void main(String[] args) throws InterruptedException {
        SynchronousQueue queue = new SynchronousQueue();

        new Thread(()->{
            try {
                System.out.println(queue.poll(3000L, TimeUnit.MILLISECONDS));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();

        Thread.sleep(1100);

        new Thread(()->{
            boolean offer = false;
            try {
                offer = queue.offer(1, 1000L, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(offer);
        }).start();
    }
}
