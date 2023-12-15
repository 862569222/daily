package cn.daily.algorithm.code.code_03;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * @author zhaibo
 * @date 2023/12/14
 */
public class 队列实现栈 {

    public static class TwoQueueStack<T>{
        private Queue<T> queue = new LinkedList<>();
        private Queue<T> help = new LinkedList<>();

        public void push(T t){
            queue.offer(t);
        }

        public T poll(){
            if(queue.isEmpty()){
               throw new RuntimeException("queue is empty");
            }
            while (queue.size()>1){
                help.offer(queue.poll());
            }
            T ans = queue.poll();
            Queue<T> temp = help;
            help = queue;
            queue = temp;

            return ans;

        }

        public T peek(){
            if(queue.isEmpty()){
                throw new RuntimeException("queue is empty");
            }
            while (queue.size() > 1){
                help.offer(queue.poll());
            }
            T ans = queue.poll();
            help.offer(ans);
            Queue<T> tmp = queue;
            queue = help;
            help = tmp;
            return ans;
        }
        public boolean isEmpty() {
            return queue.isEmpty();
        }

    }

    public static void main(String[] args) {
        System.out.println("test begin");
        TwoQueueStack<Integer> myStack = new TwoQueueStack<>();
        Stack<Integer> test = new Stack<>();
        int testTime = 1000000;
        int max = 1000000;
        for (int i = 0; i < testTime; i++) {
            if (myStack.isEmpty()) {
                if (!test.isEmpty()) {
                    System.out.println("Oops");
                }
                int num = (int) (Math.random() * max);
                myStack.push(num);
                test.push(num);
            } else {
                if (Math.random() < 0.25) {
                    int num = (int) (Math.random() * max);
                    myStack.push(num);
                    test.push(num);
                } else if (Math.random() < 0.5) {
                    if (!myStack.peek().equals(test.peek())) {
                        System.out.println("Oops");
                    }
                } else if (Math.random() < 0.75) {
                    if (!myStack.poll().equals(test.pop())) {
                        System.out.println("Oops");
                    }
                } else {
                    if (myStack.isEmpty() != test.isEmpty()) {
                        System.out.println("Oops");
                    }
                }
            }
        }

        System.out.println("test finish!");

    }

}
