package cn.daily.threadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @BelongsProject: daily
 * @BelongsPackage: cn.daily.threadPool
 * @Author: zhaibo
 * @CreateTime: 2023-07-09
 * @Description: TODO
 */
public class CachedThreadPool {
    /**
     * newCachedThreadPool 最大线程数是Integer.MAX_VALUE 所以无论来多少任务都会执行
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 1; i <= 200; i++) {
            final int j = i;
            executorService.execute(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + ":" + j);
            });
        }
        executorService.shutdown();
    }

}
