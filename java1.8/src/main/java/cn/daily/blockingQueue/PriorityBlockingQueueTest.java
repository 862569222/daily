package cn.daily.blockingQueue;

import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * 项目名称：daily
 * 类 名 称：PriorityBlockingQueueTest
 * 类 描 述：TODO
 * 创建时间：2023/7/8 10:46
 *
 * @author：zhaibo
 */
public class PriorityBlockingQueueTest {
    public static void main(String[] args) {
        PriorityBlockingQueue<Node> priorityBlockingQueue = new PriorityBlockingQueue<Node>(4, Comparator.comparingInt(o -> o.age));

        System.out.println(priorityBlockingQueue.offer(new Node("zhangsan", 19)));
        System.out.println(priorityBlockingQueue.offer(new Node("lisi", 26)));
        System.out.println(priorityBlockingQueue.offer(new Node("lili", 35)));
        System.out.println(priorityBlockingQueue.offer(new Node("xiaomei", 14)));

        System.out.println(priorityBlockingQueue.poll());
        System.out.println(priorityBlockingQueue.poll());
        System.out.println(priorityBlockingQueue.poll());
        System.out.println(priorityBlockingQueue.poll());

    }
    public static class Node{
        private String name;
        private int age;

        public Node(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }
}
