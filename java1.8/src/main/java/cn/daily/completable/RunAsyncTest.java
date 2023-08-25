package cn.daily.completable;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

/**
 * @date：2023/8/8
 * @author：zhaibo
 */
public class RunAsyncTest {
    /**
     * 当前方式既不会接收参数，也不会返回任何结果，非常基础的任务编排方式
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        CompletableFuture.runAsync(() -> {
            System.out.println("任务go");
            System.out.println("任务done");
        });

        System.in.read();
    }
}
