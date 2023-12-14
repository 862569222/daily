package cn.daily.algorithm.code.code_04;

/**
 * @author zhaibo
 * @date 2023/12/13
 */
public class 数组实现队列 {


    public static class MyQueue{
        private int pushIndex;
        private int pollIndex;
        private int size ;
        private int[] arr;

        public MyQueue(int limit) {
            arr = new int[limit];
        }

        public void offer(int val){
            if(size == arr.length){
                throw new RuntimeException("队列满了，不能插入");
            }
            arr[pushIndex++] = val;
            size++;
            pushIndex = pushIndex == arr.length  ? 0 : pushIndex;

        }
        public int poll(){
            if(size == 0){
                throw new RuntimeException("队列为空");
            }
            int val = arr[pollIndex++];
            size--;
            pollIndex = pollIndex== arr.length ? 0: pollIndex;
            return val;
        }
    }

    public static void main(String[] args) {
        MyQueue queue = new MyQueue(5);
        queue.offer(1);
        queue.offer(2);
        queue.offer(3);
        queue.offer(4);
        queue.offer(5);
        System.out.println(queue.poll());
        queue.offer(6);
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
    }
}
