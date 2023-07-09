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
public class SingleThreadExecutor {
    public static void main(String[] args) throws Exception {
        ExecutorService threadPool = Executors.newSingleThreadExecutor();

        threadPool.execute(() -> {
            System.out.println(Thread.currentThread().getName() + "," + "111");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        threadPool.execute(() -> {
            System.out.println(Thread.currentThread().getName() + "," + "222");
        });
        threadPool.execute(() -> {
            System.out.println(Thread.currentThread().getName() + "," + "333");
        });
        threadPool.execute(() -> {
            System.out.println(Thread.currentThread().getName() + "," + "444");
        });

        threadPool.shutdown();
    }

}
