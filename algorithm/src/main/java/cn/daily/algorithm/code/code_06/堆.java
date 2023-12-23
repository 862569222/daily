package cn.daily.algorithm.code.code_06;

/**
 * 1）堆结构就是用数组实现的完全二叉树结构
 * 2）完全二叉树中如果每棵子树的最大值都在顶部就是大根堆
 * 3）完全二叉树中如果每棵子树的最小值都在顶部就是小根堆
 * 4）堆结构的heapInsert与heapify操作
 * 5）堆结构的增大和减少
 * 6）优先级队列结构，就是堆结构
 * @author zhaibo
 * @date 2023/12/22
 */
public class 堆 {

    /**
     * 大根堆
     */
    public static class MyMaxHeap{
        private int[] arr;
        private int limit;
        private int size;

        public MyMaxHeap(int limit) {
            arr = new int[limit];
            this.limit = limit;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public boolean isFull() {
            return size == limit;
        }

        public void push(int num){
            if(limit == size){
                throw new RuntimeException("heap is full");
            }
            arr[size] = num;
            heapInsert(arr,size++);
        }

        public int pop(){
            if(size == 0){
                throw new RuntimeException("heap is empty");
            }
            int ans = arr[0];
            arr[0] = arr[--size];
            heapify(arr);

            return ans;
        }

        /**
         * 重构堆结构
         *
         * @param arr
         * @param size
         */
        private void heapify(int[] arr) {
            int head = 0;
            int left = head * 2 + 1;
            while (left < size){
                //证明存在左孩子
                //获取左右孩子中最大孩子的下标
                int largestIndex = left + 1 < size && arr[left + 1] > arr[left] ? left + 1 : left;
                largestIndex = arr[largestIndex] > arr[head] ? largestIndex : head;
                if(head == largestIndex){
                    break;
                }
                swap(arr,head,largestIndex);
                head = largestIndex;
                left = largestIndex * 2 + 1;
            }
        }

        private void heapInsert(int[] arr, int index) {
            while ( arr[index] > arr[(index - 1)/2]){
                swap(arr,index,(index - 1)/2);
                index = (index - 1)/2;
            }
        }

        private void swap(int[] arr, int index, int i) {
            int temp = arr[index];
            arr[index] = arr[i];
            arr[i] = temp;
        }

    }

    public static class RightMaxHeap{
        private int[] arr;
        private int limit;
        private int size;

        public RightMaxHeap(int limit) {
            arr = new int[limit];
            this.limit = limit;
        }
        public boolean isEmpty() {
            return size == 0;
        }

        public boolean isFull() {
            return size == limit;
        }

        public void push(int num){
            if(size == limit){
                throw new RuntimeException("heap is full");
            }
            arr[size++] = num;
        }

        public int pop(){
            int maxIndex = 0;
            for (int i = 1; i < size; i++) {
                if(arr[i] > arr[maxIndex] ){
                    maxIndex = i;
                }
            }
            int ans = arr[maxIndex];
            arr[maxIndex] = arr[--size];
            return ans;
        }

    }

    public static void main(String[] args) {
        int value = 1000;
        int limit = 100;
        int times = 1000000;

        for (int i = 0; i < times; i++) {
            int curLimit = (int)(Math.random() * limit)+ 1;
            Heap maxHeap = new Heap(curLimit);
            RightMaxHeap rightMaxHeap = new RightMaxHeap(curLimit);
            int curOptimes = (int)(Math.random() * limit);
            for (int j = 0; j < curOptimes; j++) {
                if (maxHeap.isEmpty() != rightMaxHeap.isEmpty()) {
                    System.out.println("Oops1!");
                }
                if (maxHeap.isFull() != rightMaxHeap.isFull()) {
                    System.out.println("Oops2!");
                }
                if(maxHeap.isEmpty()){
                    int curValue = (int) (Math.random() * value);
                    maxHeap.push(curValue);
                    rightMaxHeap.push(curValue);
                }else if(maxHeap.isFull()){
                    if(maxHeap.pop() != rightMaxHeap.pop()){
                        System.out.println("Oops3!");
                    }
                }else {
                    if(Math.random() < 0.5){
                        int curValue = (int) (Math.random() * value);
                        maxHeap.push(curValue);
                        rightMaxHeap.push(curValue);
                    }else {
                        if(maxHeap.pop() != rightMaxHeap.pop()){
                            System.out.println("Oops4!");
                        }
                    }
                }
            }

        }
        System.out.println("finish!");
    }


    /**
     * =========================================================================
     */

    public static class Heap{
        public int[] arr;
        public int limit;
        public int size;

        public Heap(int limit) {
            arr = new int[limit];
            this.limit = limit;
        }

        public boolean isEmpty(){
            return size==0;
        }
        public boolean isFull(){
            return size == limit;
        }

        public void push(int num){
            if(size == limit){
                throw new RuntimeException("heap is full");
            }

            arr[size] = num;
            heapInsert2(arr,size++);
        }

        public int pop(){
            int ans = arr[0];

            arr[0] = arr[--size];
            heapify(arr);

            return ans;
        }

        private void heapify(int[] arr) {
            int index = 0;
            int left = index*2 +1;
            while (left < size){
                //获取最大子孩子
                int largestIndex = left + 1 < size && arr[left + 1] > arr[left] ? left + 1 : left;
                largestIndex = arr[largestIndex] > arr[index] ? largestIndex : index;
                if(largestIndex == index){
                    break;
                }
                swap(arr,largestIndex,index);
                index = largestIndex;
                left = index*2 + 1;
            }
        }

        private void heapInsert2(int[] arr, int index) {
            while ((index - 1)/2 >= 0 && arr[index] > arr[(index-1)/2]){
                if(arr[index] > arr[(index - 1)/2]){
                    swap(arr,index,(index - 1)/2);
                }
                index = (index - 1)/2;
            }
        }

        private void swap(int[] arr, int index, int i) {
            int temp = arr[index];
            arr[index] = arr[i];
            arr[i] = temp;
        }
    }
}
