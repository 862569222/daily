package cn.daily.blockingqueue;

import lombok.Data;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * 项目名称：daily
 * 类 名 称：DelayQueueTest
 * 类 描 述：TODO
 * 创建时间：2023/7/8 10:59
 *
 * @author：zhaibo
 */
public class DelayQueueTest {
    public static void main(String[] args) throws InterruptedException {
        DelayQueue<Task> delayQueue = new DelayQueue<Task>();
        delayQueue.put(new Task("任务A", 3000L));
        delayQueue.put(new Task("任务B", 9000L));
        delayQueue.put(new Task("任务C", 6000L));
        delayQueue.put(new Task("任务D", 4000L));

        System.out.println(delayQueue.take());
        System.out.println(delayQueue.take());
        System.out.println(delayQueue.take());
        System.out.println(delayQueue.take());

    }

    @Data
    static class Task implements Delayed {
        private String name;
        private Long time;

        public Task(String name, Long delay) {
            this.name = name;
            this.time = delay + System.currentTimeMillis();
        }

        /**
         * 判断什么时候出延迟队列
         * @param unit the time unit
         */
        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(time - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }

        @Override
        public int compareTo(Delayed o) {
            return (int) (this.time - ((Task) o).getTime());
        }
    }
}
