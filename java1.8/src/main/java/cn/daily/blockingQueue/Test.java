package cn.daily.blockingQueue;

import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * 项目名称：daily
 * 类 名 称：Test
 * 类 描 述：TODO
 * 创建时间：2023/6/16 15:30
 * 创 建 人：zhaibo
 */
public class Test {

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

    public static void main(String[] args) {
        PriorityBlockingQueue<Node> queue =  new PriorityBlockingQueue(5, (o1,o2)-> {
            Node o11 = (Node)o1;
            Node o22 = (Node)o2;
            return o11.getAge() - o22.getAge() ;
        });
        Node node = new Node("zhangsan",18);
        Node node2 = new Node("zhangsan",9);
        Node node3= new Node("zhangsan",1);
        Node node4 = new Node("zhangsan",23);
        Node node5 = new Node("zhangsan",6);
        queue.offer(node);
        queue.offer(node2);
        queue.offer(node3);
        queue.offer(node4);
        queue.offer(node5);

        System.out.println("==============");
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());

    }
}
