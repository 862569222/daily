package cn.daily.algorithm.linkedList;

import lombok.Data;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 项目名称：daily
 * 类 名 称：LinkedListToQueue
 * 类 描 述：单链表实现队列
 * 创建时间：2023/6/21 09:55
 * 创 建 人：zhaibo
 */
public class LinkedListToQueue<T> {

    @Data
    public static class Node<T>{
        private T value;
        private Node next;
    }

    public static class Myqueue<T>{
        private Node<T> head;

        private Node<T> tail;

        private AtomicInteger size = new AtomicInteger();

        public T poll(){
            if(head == null){
                return null;
            }else {
                size.decrementAndGet();
                T value = head.value;
                head = head.next;
                return value;
            }
        }

        public void offer(T t ){
            Node<T> node = new Node<>();
            node.value = t;
            if(tail == null){
                head = tail = node;
            }else {
                tail.next = node;
                tail = node;
            }
            size.incrementAndGet();
        }
        public T peek(){
            T val = null ;
            if(head != null){
                val = head.value;
            }
            return val;
        }
        public int size(){
            return size.get();
        }
    }

    public static void main(String[] args) {
        Myqueue myqueue = new Myqueue();
        myqueue.offer(1);
        myqueue.offer(2);
        myqueue.offer(3);

        System.out.println(myqueue.poll());
        System.out.println("size:"+myqueue.size());
        System.out.println(myqueue.poll());
        System.out.println(myqueue.poll());
        System.out.println("size:"+myqueue.size());
        System.out.println(myqueue.poll());
        System.out.println("size:"+myqueue.size());
    }
}
