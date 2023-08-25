package cn.daily.completable;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

/**
 * @author zhaibo
 * @date 2023/8/22
 */
public class AnyOf {
    /**
     * anyOf是基于多个CompletableFuture的任务，只要有一个任务执行完毕就继续执行后续，最先执行完的任务做作为返回结果的入参
     *
     *
     * @param args arg游戏
     * @throws IOException ioexception
     */
    public static void main(String[] args) throws IOException {
        CompletableFuture.anyOf(
                CompletableFuture.supplyAsync(() -> {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("任务A");
                    return "A";
                }),
                CompletableFuture.supplyAsync(() -> {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("任务B");
                    return "B";
                }),
                CompletableFuture.supplyAsync(() -> {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("任务C");
                    return "C";
                })
        ).thenAccept(r -> {
            System.out.println("任务D执行，" + r + "先执行完毕的");
        });

        System.in.read();
    }
}
