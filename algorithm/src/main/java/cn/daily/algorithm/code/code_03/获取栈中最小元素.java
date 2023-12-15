package cn.daily.algorithm.code.code_03;

import java.util.Stack;

/**
 *
 * @author zhaibo
 * @date 2023/12/13
 */
public class 获取栈中最小元素 {
    /**
     * 实现一个特殊的栈，在基本功能的基础上，再实现返回栈中最小元素的功能
     *
     * 1）pop、push、getMin操作的时间复杂度都是 O(1)。
     *
     * 2）设计的栈类型可以使用现成的栈结构。
     *
     */
    public static class MyStack{
        private Stack<Integer> dataStack = new Stack<>();
        private Stack<Integer> minStack = new Stack<>();

        public void push(Integer val){
            dataStack.push(val);
            if(minStack.isEmpty() || val <= getMin()){
                minStack.push(val);
            }

        }
        public Integer pop(){
            if(dataStack.isEmpty()){
                throw new RuntimeException("stack is empty");
            }
            Integer pop = dataStack.pop();
            if(pop.equals(getMin())){
                minStack.pop();
            }
            return pop;
        }

        public Integer getMin(){
            if(minStack == null || minStack.isEmpty()){
                throw new RuntimeException("stack is empty");
            }
            return minStack.peek();
        }
    }
    public static void main(String[] args) {
        MyStack stack1 = new MyStack();
        stack1.push(3);
        System.out.println(stack1.getMin());
        stack1.push(4);
        System.out.println(stack1.getMin());
        stack1.push(1);
        System.out.println(stack1.getMin());
        System.out.println(stack1.pop());
        System.out.println(stack1.getMin());


    }
}
