package cn.daily.algorithm.linkedList;

import lombok.Data;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 单链表实现栈
 * 项目名称：daily
 * 类 名 称：LinkedListToQueue
 * 类 描 述：单链表实现栈
 *
 * @author zhaibo
 * @date 2023/08/29
 */
public class 单链表实现栈<T> {

    @Data
    public static class Node<T>{
        private T value;
        private Node next;
    }

    public static class Mystack<T>{

        private Node<T> head;

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
            if(head == null){
               head = node;
            }else {
                node.next = head;
                head = node;
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
        Mystack myqueue = new Mystack();
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
