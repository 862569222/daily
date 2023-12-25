package cn.daily.algorithm.code.code_06;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * @Author: zhaibo
 * @CreateTime: 2023-12-25
 * @Description: TODO
 */
public class 堆排序_移动范围小于K {
    // 1 2 3 4 5 6 7 8 9
    public static void sortArrayLessK(int[] arr ,int k){

        Queue<Integer> queue = new PriorityQueue();
        // 1 2 3 4  k =2
        for (int i = 0; i <= k; i++) {
            queue.offer(arr[i]);
        }
        int index = 0 ;

        while(index+k+1< arr.length){
            arr[index] = queue.poll();
            queue.offer(arr[index+k+1]);
            index++;
        }

        while (!queue.isEmpty()){
            arr[index++] = queue.poll();
        }
    }

    public static void sortArrayLessK2(int[] arr ,int k){

        Queue<Integer> heap = new PriorityQueue();
        int index = 0;
        for (; index <= k ; index++) {
            heap.offer(arr[index]);
        }
        int i = 0;
        while (index < arr.length ){
            arr[i++] = heap.poll();
            heap.offer(arr[index++]);
         }
        while (!heap.isEmpty()){
            arr[i++] = heap.poll();
        }
    }

    public static void comparator(int[] arr, int k) {
        Arrays.sort(arr);
    }

    // for test
    public static int[] randomArrayNoMoveMoreK(int maxSize, int maxValue, int K) {
        int size = 0;
        do {
            size = (int) ((maxValue + 1) * Math.random());
        }while (size <= K);
        int[] arr = new int[size];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
        }
        // 先排个序
        Arrays.sort(arr);
        // 然后开始随意交换，但是保证每个数距离不超过K
        // swap[i] == true, 表示i位置已经参与过交换
        // swap[i] == false, 表示i位置没有参与过交换
        boolean[] isSwap = new boolean[arr.length];
        for (int i = 0; i < arr.length; i++) {
            int j = Math.min(i + (int) (Math.random() * (K + 1)), arr.length - 1);
            if (!isSwap[i] && !isSwap[j]) {
                isSwap[i] = true;
                isSwap[j] = true;
                int tmp = arr[i];
                arr[i] = arr[j];
                arr[j] = tmp;
            }
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
        System.out.println("test begin");
        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 150;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int k = (int) (Math.random() * maxSize) + 1;
            int[] arr = randomArrayNoMoveMoreK(maxSize, maxValue, k);
            int[] arr1 = copyArray(arr);
            int[] arr2 = copyArray(arr);
            sortArrayLessK2(arr1, k);
            comparator(arr2, k);
            if (!isEqual(arr1, arr2)) {
                succeed = false;
                System.out.println("K : " + k);
                printArray(arr);
                printArray(arr1);
                printArray(arr2);
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Fucking fucked!");
    }

}
