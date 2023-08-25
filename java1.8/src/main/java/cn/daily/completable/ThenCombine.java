package cn.daily.completable;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

/**
 * @author zhaibo
 * @date 2023/8/22
 */
public class ThenCombine {
    /**
     * 比如有任务A，任务B，任务C。任务A和任务B并行执行，等到任务A和任务B全部执行完毕后，再执行任务C。
     *
     * A+B ------ C
     *
     * 基于前面thenApply，thenAccept，thenRun知道了一般情况三种任务的概念
     *
     * thenCombine以及thenAcceptBoth还有runAfterBoth的区别是一样的。
     *
     * @param args arg游戏
     * @throws IOException ioexception
     */
    public static void main(String[] args) throws IOException {
        CompletableFuture<Integer> taskC = CompletableFuture.supplyAsync(() -> {
            System.out.println("任务A");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 78;
        }).thenCombine(CompletableFuture.supplyAsync(() -> {
            System.out.println("任务B");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 66;
        }), (resultA, resultB) -> {
            System.out.println("任务C");
            int resultC = resultA + resultB;
            return resultC;
        });

        System.out.println(taskC.join());
        System.in.read();
    }
}
