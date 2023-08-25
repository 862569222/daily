package cn.daily.completable;


import java.io.IOException;
import java.util.concurrent.CompletableFuture;

/**
 * 然后接受
 *
 * @author zhaibo
 * @date 2023/08/22
 */
public class ThenAccept {
    /**
     * 套路和thenApply一样，都是任务A和任务B的拼接
     *
     * 前置任务需要有返回结果，后置任务会接收前置任务的结果，返回后置任务没有返回值
     *
     * @param args arg游戏
     * @throws IOException ioexception
     */
    public static void main(String[] args) throws  IOException {
        CompletableFuture.supplyAsync(() -> {
            System.out.println("任务A");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "abcdefg";
        }).thenAccept(result -> {
            System.out.println("任务b，拿到结果处理：" + result);
        });

        System.in.read();
    }
}
