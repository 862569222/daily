package cn.daily.completable;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

/**
 * @author zhaibo
 * @date 2023/8/22
 */
public class Exceptionally {
    /**
     * exceptionally
     *
     * 这个也是拼接任务的方式，但是只有前面业务执行时出现异常了，才会执行当前方法来处理
     *
     * 只有异常出现时，CompletableFuture的编排任务没有处理完时，才会触发
     *
     * thenCompose，handle
     *
     * 这两个也是异常处理的套路，可以根据方法描述发现，他的功能方向比exceptionally要更加丰富
     *
     * thenCompose可以拿到返回结果同时也可以拿到出现的异常信息，但是thenCompose本身是Consumer不能返回结果。无法帮你捕获异常，但是可以拿到异常返回的结果。
     *
     * handle可以拿到返回结果同时也可以拿到出现的异常信息，并且也可以指定返回托底数据。可以捕获异常的，异常不会抛出去。
     *
     * @param args arg游戏
     * @throws IOException ioexception
     */
    public static void main(String[] args) throws IOException {
        CompletableFuture<Integer> taskC = CompletableFuture.supplyAsync(() -> {
            System.out.println("任务A");
//            int i = 1 / 0;
            return 78;
        }).applyToEither(CompletableFuture.supplyAsync(() -> {
            System.out.println("任务B");
            return 66;
        }), resultFirst -> {
            System.out.println("任务C");
            return resultFirst;
        }).handle((r,ex) -> {
            System.out.println("handle:" + r);
            System.out.println("handle:" + ex);
            return -1;
        });
        /*.exceptionally(ex -> {
            System.out.println("exceptionally:" + ex);
            return -1;
        });*/
        /*.whenComplete((r,ex) -> {
            System.out.println("whenComplete:" + r);
            System.out.println("whenComplete:" + ex);
        });*/


        System.out.println(taskC.join());
        System.in.read();
    }
}
