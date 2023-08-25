package cn.daily.completable;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

/**
 *
 * @author zhaibo
 * @date 2023/8/22
 */
public class ThenRun {
    /**
     * 套路和thenApply，thenAccept一样，都是任务A和任务B的拼接
     * 前置任务没有返回结果，后置任务不接收前置任务结果，后置任务也会有返回结果
     *
     * @param args arg游戏
     * @throws IOException ioexception
     */
    public static void main(String[] args) throws IOException {
        CompletableFuture.runAsync(() -> {
            System.out.println("任务A！！");
        }).thenRun(() -> {
            System.out.println("任务B！！");
        });
        System.in.read();
    }

}
