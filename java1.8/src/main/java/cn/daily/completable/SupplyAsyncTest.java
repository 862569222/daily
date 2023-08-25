package cn.daily.completable;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @date：2023/8/8
 * @author：zhaibo
 */
public class SupplyAsyncTest {
    /**
     * CompletableFuture如果不提供线程池的话，默认使用的ForkJoinPool，而ForkJoinPool内部是守护线程，
     * 如果main线程结束了，守护线程会跟着一起结束。
     */
    public static void main(String[] args)  {
        // 生产者，可以指定返回结果
        CompletableFuture<String> firstTask = CompletableFuture.supplyAsync(() -> {
            System.out.println("异步任务开始执行");
            System.out.println("异步任务执行结束");
            return "返回结果";
        });

        String result1 = firstTask.join();
        String result2 = null;
        try {
            result2 = firstTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println(result1 + "," + result2);
    }
}
