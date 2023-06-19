package cn.daily.algorithm;

import java.util.Arrays;

/**
 * 项目名称：daily
 * 类 名 称：exerciseProblems_3
 * 类 描 述：二分法 - 有序数组找到num位置
 * 创建时间：2023/6/19 11:11
 * 创 建 人：zhaibo
 */
public class exerciseProblems_3 {

    public static boolean find(int[] arr, int num){
        if(arr == null || arr.length == 0){
            return false;
        }
        int l = 0 ;
        int r = arr.length;
        while (l <= r){
            int mid = (l + r) / 2;
            if(arr[mid] == num){
                return true;
            }
            if (arr[mid] > num){
                r = mid - 1;
            }
            if(arr[mid] < num){
                l = mid +1;
            }
        }
        return false;
    }

    public static boolean test(int[] arr, int num){
        if(arr == null || arr.length == 0){
            return false;
        }
        for (int i = 0; i < arr.length; i++) {
            if(arr[i] == num){
                return true;
            }
        }
        return false;
    }

    public static int[] getRandomArray(int maxValue,int maxLen){
        int len = (int)Math.random()*maxLen;
        int[] arr = new int[len];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int)Math.random()*maxValue;
        }
        return arr;
    }

    public static void main(String[] args) {
        int maxValue = 30 ;
        int maxLen = 20;
        int times = 100000000;
        for (int i = 0; i < times; i++) {
            int[] randomArray = getRandomArray(maxValue, maxLen);
            Arrays.sort(randomArray);
            if(find(randomArray,5) != test(randomArray,5)){
                System.out.println("匹配错误");
            }
        }
    }
}
