package cn.daily.completable;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

/**
 * @author zhaibo
 * @date 2023/8/22
 */
public class AllOf {
    /**
     * allOf的方式是让内部编写多个CompletableFuture的任务，多个任务都执行完后，才会继续执行你后续拼接的任务
     *
     * allOf返回的CompletableFuture是Void，没有返回结果
     *
     * @param args arg游戏
     * @throws IOException ioexception
     */
    public static void main(String[] args) throws IOException {
        CompletableFuture.allOf(
                CompletableFuture.runAsync(() -> {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("任务A");
                }),
                CompletableFuture.runAsync(() -> {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("任务B");
                }),
                CompletableFuture.runAsync(() -> {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("任务C");
                })
        ).thenRun(() -> {
            System.out.println("任务D");
        });

        System.in.read();
    }
}
