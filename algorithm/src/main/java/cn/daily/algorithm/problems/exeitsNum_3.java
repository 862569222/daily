package cn.daily.algorithm.problems;

import java.util.Arrays;

/**
 * 项目名称：daily
 * 类 名 称：exerciseProblems_3
 * 类 描 述：二分法查询一个数
 * 创建时间：2023/6/13 22:27
 * 创 建 人：zhaibo
 */
public class exeitsNum_3 {

    public static boolean find(int[] arr ,int num ){
        if(arr == null || arr.length == 0){
            return false;
        }

        int l = 0;
        int r = arr.length - 1 ;

        while (l <= r ){
            int mid = (l + r )/2;
            if(arr[mid] == num){
                return true;
            }
            if(arr[mid] > num){
                r = mid - 1;
            }else {
                l = mid + 1;
            }
        }
        return false;
    }

    public static boolean test(int[] arr ,int num){
        for (int i = 0; i <arr.length ; i++) {
            if (arr[i] == num){
                return true;
            }
        }
        return false;
    }

    public static int[] getRandomArray(int maxLen,int maxValue){
        int len = (int)(Math.random()*maxLen);
        int[] arr = new int[len];
        for (int i = 0; i <arr.length ; i++) {
            arr[i] = (int)(Math.random()*maxValue);
        }
        return arr;
    }


    public static void main(String[] args) {
        int maxlen = 30;
        int maxValue = 20;
        int times = 1000000;
        for (int i = 0; i < times; i++) {
            int[] randomArray = getRandomArray(maxlen, maxValue);
            Arrays.sort(randomArray);
            boolean b = find(randomArray, 5);
            boolean test = test(randomArray, 5);
            if(b != test){
                System.out.println("出错了");

            }
        }
    }
}
