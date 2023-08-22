package cn.daily.completable;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

/**
 * @author zhaibo
 * @date 2023/8/22
 */
public class ApplyToEither {
    /**
     * 比如有任务A，任务B，任务C。任务A和任务B并行执行，只要任务A或者任务B执行完毕，开始执行任务C
     *
     * A or B ----- C
     *
     * applyToEither，acceptEither，runAfterEither三个方法拼接任务的方式都是一样的
     *
     * 区别依然是，可以接收结果并且返回结果，可以接收结果没有返回结果，不接收结果也没返回结果
     *
     * @param args arg游戏
     * @throws IOException ioexception
     */
    public static void main(String[] args) throws IOException {
        CompletableFuture<Integer> taskC = CompletableFuture.supplyAsync(() -> {
            System.out.println("任务A");
            return 78;
        }).applyToEither(CompletableFuture.supplyAsync(() -> {
            System.out.println("任务B");
            return 66;
        }), resultFirst -> {
            System.out.println("任务C");
            return resultFirst;
        });

        System.out.println(taskC.join());
        System.in.read();
    }
}
