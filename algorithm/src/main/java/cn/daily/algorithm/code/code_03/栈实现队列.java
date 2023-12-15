package cn.daily.algorithm.code.code_03;

import java.util.Stack;

/**
 * @author zhaibo
 * @date 2023/12/13
 */
public class 栈实现队列 {

    public static class TwoStackQueue<T>{
        private Stack<T> pushStack = new Stack<>();
        private Stack<T> popStack = new Stack<>();


        public void offer(T t ){
            pushStack.push(t);
            pushToPop();
        }
        public T poll(){
            if(popStack.isEmpty() && pushStack.isEmpty()){
                return null;
            }
            T pop = popStack.pop();
            pushToPop();

            return pop;
        }
        public void pushToPop(){
            if (popStack.isEmpty()){
                while (!pushStack.isEmpty()){
                    popStack.push(pushStack.pop());
                }
            }
        }
        public T peek(){
            if(popStack.isEmpty() && pushStack.isEmpty()){
                return null;
            }
            pushToPop();
            return popStack.peek();
        }
    }

    public static void main(String[] args) {
        TwoStackQueue<Integer> test = new TwoStackQueue<Integer>();
        test.offer(1);
        test.offer(2);
        test.offer(3);
        System.out.println(test.peek());
        System.out.println(test.poll());
        System.out.println(test.peek());
        System.out.println(test.poll());
        System.out.println(test.peek());
        System.out.println(test.poll());
    }

}
