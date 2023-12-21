package cn.daily.algorithm.code.code_05;

import java.util.Stack;

/**
 * @author zhaibo
 * @date 2023/12/20
 */
public class 快排 {
    /**
     * 快排1。0
     * @param arr
     */
    public static void quickSort(int[] arr){
        if(arr == null || arr.length < 2){
            return;
        }
        process(arr,0,arr.length - 1);
    }

    public static void quickSort2(int[] arr){
        if(arr == null || arr.length < 2){
            return;
        }

        process2(arr,0,arr.length - 1 );
    }

    private static void process(int[] arr, int l, int r) {
        if(l >= r){
            return;
        }
        int partition = partition(arr, l, r);
        process(arr,l ,partition - 1 );
        process(arr ,partition + 1,r);
    }

    private static void process2(int[] arr,int l,int r){
        if(l >= r){
            return;
        }
        int[] ints = netherlandsFlag(arr, l, r);
        process2(arr,l,ints[0] - 1);
        process2(arr,ints[1] + 1,r);
    }

    /**
     * 随机快排 3。0
     * @param arr
     */
    public static void quickSort3(int[] arr){
        if(arr == null || arr.length < 2){
            return;
        }

        process3(arr,0,arr.length - 1 );
    }

    private static void process3(int[] arr, int l, int r) {
        if(l >= r){
            return;
        }
        swap(arr,l + (int)(Math.random()*(r - l + 1) ),r);
        int[] ints = netherlandsFlag(arr, l, r);
        process3(arr,l,ints[0] - 1);
        process3(arr,ints[1] + 1,r);
    }

    //取arr[r]的值为划分值，最后在和左边界的只交换,返回划分区域坐标
    // 2 0 1 4 2 8 2
    public static int partition(int[] arr,int l ,int r){

        if(l == r){
            return l;
        }
        int left = l - 1 ;
        int index = l;
        while(index < r){

            if(arr[index] <= arr[r]){
                swap(arr,++left,index);
            }
            index++;
        }
        swap(arr,++left,r);
        return left;
    }

    //荷兰国旗问题
    //取arr[r]的值为划分值 <arr[r]  =arr[r]  >arr[r]，返回划分区域坐标
    // 2 0 1 4 2 8 2
    public static int[] netherlandsFlag(int[] arr ,int l ,int r ){
        if(l > r ){
            return new int[]{-1,-1};
        }
        if(l == r){
            return new int[]{l,l};
        }
        int left = l - 1;
        int right = r;
        int index = l;
        while (index < right){
            if(arr[index] == arr[r]){
                index++;
            }else if(arr[index] < arr[r]){
                swap(arr,++left,index++);
            } else if(arr[index] > arr[r]){
                swap(arr,--right,index);
            }
        }
        swap(arr,right,r );
        return new int[]{left + 1,right};
    }

    /**
     * 非递归方式实现快排
     */
    public static class Op {
        public int l;
        public int r;

        public Op(int left, int right) {
            l = left;
            r = right;
        }
    }
    // 快排3.0 非递归版本 用栈来执行
    public static void quickSort4(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        int N = arr.length;
        swap(arr, (int) (Math.random() * N), N - 1);
        int[] equalArea = netherlandsFlag(arr, 0, N - 1);
        int el = equalArea[0];
        int er = equalArea[1];
        Stack<Op> stack = new Stack<>();
        stack.push(new Op(0, el - 1));
        stack.push(new Op(er + 1, N - 1));
        while (!stack.isEmpty()) {
            // op.l ... op.r
            Op op = stack.pop();
            if (op.l < op.r) {
                swap(arr, op.l + (int) (Math.random() * (op.r - op.l + 1)), op.r);
                equalArea = netherlandsFlag(arr, op.l, op.r);
                el = equalArea[0];
                er = equalArea[1];
                stack.push(new Op(op.l, el - 1));
                stack.push(new Op(er + 1, op.r));
            }
        }
    }
 

    public static void swap(int[] arr ,int l,int r){
        int temp = arr[l];
        arr[l] = arr[r];
        arr[r] = temp;
    }

    public static int[] getRamdomArray(int size,int range){
        int[] array = new int[(int)(Math.random()*size + 1)];
        for (int i = 0; i < array.length; i++) {
            array[i] = (int)(Math.random()*range + 1);
        }
        return array;
    }

    public static boolean isEqual(int[] target ,int[] source){
        if(target.length != source.length){
            return false;
        }
        for (int i = 0; i < target.length; i++) {
            if(target[i] != source[i]){
                return false;
            }
        }
        return true;
    }

    public static int[] copyArray(int[] arr){
        if(arr == null || arr.length == 0){
            return arr;
        }
        int[] array = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            array[i] = arr[i];
        }
        return array;
    }

    public static void sort(int[] arr){
        if(arr == null || arr.length < 2){
            return;
        }
        int N = arr.length - 1;
        for (int i = N; i >= 0  ; i--) {
            for (int j = 0; j < i ; j++) {
                if (arr[j] > arr[j+1]){
                    swap(arr,j,j+1);
                }
            }
        }
    }

    public static void main(String[] args) {
        int times = 1000000;
        int size = 10;
        int range = 30;
        System.out.println("开始");
        for (int i = 0; i < times; i++) {
            int[] array = getRamdomArray(size, range);
            int[] copyArray = copyArray(array);
            quickSort4(array);
            sort(copyArray);
            if(!isEqual(array,copyArray)){
                System.out.println("出错了");
                break;
            }
        }
        System.out.println("结束");
    }
}
