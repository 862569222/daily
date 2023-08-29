package cn.daily.algorithm.linkedList;


import java.util.Currency;
import java.util.Deque;
import java.util.LinkedList;

/**
 * 双向链表实现队列
 * @date: 2023/7/4
 * @author：zhaibo
**/
public class 双向链表实现队列 {

    public static class DoubleNode<T>{
        private T value;
        private DoubleNode last;
        private DoubleNode next;

        public DoubleNode(T value) {
            this.value = value;
        }
    }


    /**
     *
     * o -> o ->o -> o ->o -> o ->
     * tail                   head
     * @param <T>
     */
    public static class MyQueue<T>{
        DoubleNode<T> head ;
        DoubleNode<T> tail ;
        private int size;

        public boolean isEmpty(){
            return size == 0;
        }
        public int size() {
            return size;
        }

        public void offerHead(T value){
            DoubleNode curNode = new DoubleNode(value);
            if(head == null){
                head = curNode;
                tail = curNode;
            }else {
                head.next = curNode;
                curNode.last = head;
                head = curNode;
            }
            size++;
        }
        public T pollHead(){

            if(head == null){
                return null;
            }
            size--;
            T value = head.value;
            if(head == tail){
                head = null;
                tail = null;
            } else {
                head = head.last;
                head.next = null;
            }
            return value;
        }

        public void offerTail( T value){
            DoubleNode<T> node = new DoubleNode<>(value);
            if(tail == null ){
               tail = node;
               head = node;
            }else {
                node.next = tail;
                tail.last = node;
                tail = node;
            }
            size++;
        }

        public T pollTail(){

            if(tail == null){
                return null;
            }

            size--;
            T value = tail.value;
            if(tail == head){
                tail = null;
                head = null;
            } else {
                tail = tail.next;
                tail.last = null;
            }
            return value;
        }

        public T peekHead(){
            if(head != null){
                return head.value;
            }
            return null;
        }

        public T peekTail(){
            if(tail != null){
                return tail.value;
            }
            return null;
        }
    }

    public static void testDeque() {
        MyQueue<Integer> myDeque = new MyQueue<>();
        Deque<Integer> test = new LinkedList<>();
        int testTime = 50000;
        int maxValue = 200000000;
        System.out.println("测试开始！");
        for (int i = 0; i < testTime; i++) {
            if (myDeque.isEmpty() != test.isEmpty()) {
                System.out.println("Oops!");
            }
            if (myDeque.size() != test.size()) {
                System.out.println("Oops!");
            }
            double decide = Math.random();
            if (decide < 0.33) {
                int num = (int) (Math.random() * maxValue);
                if (Math.random() < 0.5) {
                    myDeque.offerHead(num);
                    test.addFirst(num);
                } else {
                    myDeque.offerTail(num);
                    test.addLast(num);
                }
            } else if (decide < 0.66) {
                if (!myDeque.isEmpty()) {
                    int num1 = 0;
                    int num2 = 0;
                    if (Math.random() < 0.5) {
                        num1 = myDeque.pollHead();
                        num2 = test.pollFirst();
                    } else {
                        num1 = myDeque.pollTail();
                        num2 = test.pollLast();
                    }
                    if (num1 != num2) {
                        System.out.println("Oops!");
                    }
                }
            } else {
                if (!myDeque.isEmpty()) {
                    int num1 = 0;
                    int num2 = 0;
                    if (Math.random() < 0.5) {
                        num1 = myDeque.peekHead();
                        num2 = test.peekFirst();
                    } else {
                        num1 = myDeque.peekTail();
                        num2 = test.peekLast();
                    }
                    if (num1 != num2) {
                        System.out.println("Oops!");
                    }
                }
            }
        }
        if (myDeque.size() != test.size()) {
            System.out.println("Oops!");
        }
        while (!myDeque.isEmpty()) {
            int num1 = myDeque.pollHead();
            int num2 = test.pollFirst();
            if (num1 != num2) {
                System.out.println("Oops!");
            }
        }
        System.out.println("测试结束！");
    }

    public static void main(String[] args) {
        testDeque();
    }

}
