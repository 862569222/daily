package cn.daily.algorithm.code.code_06;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * @Author: zhaibo
 * @CreateTime: 2023-12-23
 * @Description: TODO
 */
public class 堆排 {

    public static void heapSort(int[] arr){
        if(arr == null || arr.length < 2){
            return;
        }
        //O(N*logN)
//        for (int i = 0; i < arr.length; i++) {
//            heapInsert(arr,i);
//        }
        //O(N)
        for (int i = arr.length - 1 ; i >= 0; i--) {
            heapify(arr,i,arr.length);
        }

        int size = arr.length;
        swap(arr,0,--size);
        // O(N*logN)
        while (size > 0){// O(N)
            heapify(arr,0,size);// O(logN)
            swap(arr,0,--size);// O(1)
        }
    }

    private static void heapify(int[] arr,int index,int size) {
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

    private static void heapInsert(int[] arr, int index) {
        while ((index - 1)/2 >= 0 && arr[index] > arr[(index-1)/2]){
            if(arr[index] > arr[(index - 1)/2]){
                swap(arr,index,(index - 1)/2);
            }
            index = (index - 1)/2;
        }
    }

    private static void swap(int[] arr, int index, int i) {
        int temp = arr[index];
        arr[index] = arr[i];
        arr[i] = temp;
    }

    // for test
    public static void comparator(int[] arr) {
        Arrays.sort(arr);
    }

    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
        }
        return arr;
    }

    // for test
    public static int[] copyArray(int[] arr) {
        if (arr == null) {
            return null;
        }
        int[] res = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            res[i] = arr[i];
        }
        return res;
    }

    // for test
    public static boolean isEqual(int[] arr1, int[] arr2) {
        if ((arr1 == null && arr2 != null) || (arr1 != null && arr2 == null)) {
            return false;
        }
        if (arr1 == null && arr2 == null) {
            return true;
        }
        if (arr1.length != arr2.length) {
            return false;
        }
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }
        return true;
    }

    // for test
    public static void printArray(int[] arr) {
        if (arr == null) {
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    // for test
    public static void main(String[] args) {

        int testTime = 500000;
        int maxSize = 10;
        int maxValue = 100;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            heapSort(arr1);
            comparator(arr2);
            if (!isEqual(arr1, arr2)) {
                succeed = false;
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Fucking fucked!");

        int[] arr = generateRandomArray(maxSize, maxValue);
        printArray(arr);
        heapSort(arr);
        printArray(arr);
    }

}
