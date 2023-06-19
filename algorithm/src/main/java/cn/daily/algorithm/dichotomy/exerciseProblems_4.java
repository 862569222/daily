package cn.daily.algorithm.dichotomy;

import java.util.Arrays;

/**
 * 项目名称：daily
 * 类 名 称：exerciseProblems_3
 * 类 描 述：二分法 - 有序数组找到>=num最左的位置
 * 创建时间：2023/6/19 11:11
 * 创 建 人：zhaibo
 */
public class exerciseProblems_4 {

    public static int find(int[] arr, int num){
        if(arr == null || arr.length == 0){
            return -1;
        }
        int l = 0 ;
        int r = arr.length - 1;
        int index = -1;
        while (l <= r ){
            int mid = ( l + r) / 2;
            if(arr[mid] == num){
                index = mid;
                r = mid - 1;
            }
            if(arr[mid] > num){
                r = mid -1;
            }
            if(arr[mid] < num){
                l = mid + 1;
            }
        }
        return index;
    }

    public static int test(int[] arr, int num){
        if(arr == null || arr.length == 0){
            return -1;
        }
        for (int i = 0; i < arr.length; i++) {
            if(arr[i] == num){
                return i;
            }
        }
        return -1;
    }

    public static int[] getRandomArray(int maxValue,int maxLen){
        int len = (int)(Math.random()*maxLen);
        int[] arr = new int[len];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int)(Math.random()*maxValue);
        }
        return arr;
    }
    public static void printArray(int[] arr){
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + ",");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int maxValue = 30 ;
        int maxLen = 10;
        int times = 10000;
        for (int i = 0; i < times; i++) {
            int[] randomArray = getRandomArray(maxValue, maxLen);

            Arrays.sort(randomArray);
            printArray(randomArray);
            int index = find(randomArray, 4);
            int testIndex = test(randomArray, 4);
            if(index != testIndex){
                System.out.println("匹配错误:" + index + ":" + testIndex);
            }
        }
    }
}
