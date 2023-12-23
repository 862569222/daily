package cn.daily.algorithm.code.code_04;

import java.text.Format;

/**
 * @Author: zhaibo
 * @CreateTime: 2023-12-16
 * @Description: TODO
 */
public class 小和问题 {
    /**
     *  [1,4,3,6,5,3,2,7,9,4]
     *  获取每一个数的左边有多少个数比当前数小，并把总和返回
     *  转换思维：
     *  通过归并排序 左组 右组有序的特性
     *  获取右边多少个数比当前数大
     */
    public static int getSmallSum(int[] arr){
        if(arr == null || arr.length < 2){
            return 0;
        }
        return process(arr,0,arr.length-1);
    }

    private static int process(int[] arr, int l, int r) {
        if(l == r){
            return 0;
        }

        int mid = l + ((r - l)>>1 );
        return  process(arr,l ,mid) +
                process(arr,mid + 1 ,r) +
                merger(arr,l,mid,r);
    }

    private static int merger(int[] arr, int l, int mid, int r) {
        int[] help = new int[r - l + 1];
        int index = 0;
        int p1 = l;
        int p2 = mid + 1;
        int ans = 0;
        while (p1 <= mid && p2 <= r){
            ans += arr[p1] < arr[p2] ? (r - p2 + 1) * arr[p1] : 0;
            help[index++] = arr[p1] < arr[p2] ? arr[p1++] : arr[p2++];
        }
        while (p1 <= mid){
            help[index++] = arr[p1++];
        }

        while (p2 <= r){
            help[index++] = arr[p2++];
        }

        for (int i = 0; i < help.length; i++) {
            arr[l+i] = help[i];
        }

        return ans;
    }

    public static int comparator(int[] arr){
        if(arr == null || arr.length < 2){
            return 0;
        }
        int ans = 0;
        for (int i = 1; i < arr.length; i++) {
            for (int j = 0; j < i ; j++) {
                ans += arr[j] < arr[i] ? arr[j] : 0;
            }
        }
        return ans;
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
        int maxSize = 100;
        int maxValue = 100;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            if (getSmallSum(arr1) != comparator(arr2)) {
                succeed = false;
                printArray(arr1);
                printArray(arr2);
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Fucking fucked!");
    }
}
