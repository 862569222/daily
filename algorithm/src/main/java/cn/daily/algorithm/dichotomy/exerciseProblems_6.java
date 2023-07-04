package cn.daily.algorithm.dichotomy;

import sun.font.TrueTypeFont;

import java.util.Arrays;

/**
 * 项目名称：daily
 * 类 名 称：exerciseProblems_3
 * 类 描 述：二分法 - 局部最小值
 *
 *          有一个数组无序，且左右相邻的数不相等
 * 局部最小定义： arr[0] <arr[1]  认为0 位置局部最小
 *              arr[n-2] <arr[n-1] 认为n-1 位置局部最小
 *
 *
 *
 *
 * 创建时间：2023/6/19 11:11
 * 创 建 人：zhaibo
 */
public class exerciseProblems_6 {

    public static int find(int[] arr){
        if(arr == null || arr.length == 0){
            return -1;
        }

        int l = 0 ;
        int r = arr.length - 1;

        if(arr.length == 1 ||(arr.length >= 2 && arr[0] < arr[1])){
            return 0;
        }
        if(arr.length >= 2 && arr[arr.length-2] > arr[arr.length-1]){
            return arr.length-1;
        }
        while (l < r-1 ){

            int mid =  ( l + r ) / 2;

            if(arr[mid-1] > arr[mid] && arr[mid] < arr[mid+1]){
                return mid;
            }
            if(arr[mid] > arr[mid - 1]){
                r = mid - 1 ;
            }else if(arr[mid] > arr[mid + 1]){
                l = mid + 1 ;
            }
        }


        return arr[l] < arr[r] ? l : r;
    }

    public static boolean test(int[] arr, int minIndex){
        if(arr == null || arr.length == 0){
            return minIndex == -1;
        }

        int minIndexLeft = minIndex - 1;
        int minIndexRight = minIndex + 1;
        boolean left = minIndexLeft >= 0 ? arr[minIndexLeft] > arr[minIndex]  : true;
        boolean right = minIndexRight < arr.length ? arr[minIndex] < arr[minIndexRight] :true;
        return left && right;
    }

    public static int[] getRandomArray(int maxValue,int maxLen){
        int len ;
        do {
            len = (int)(Math.random()*maxLen);
        }while (len == 0);

        int[] arr = new int[len];
        arr[0] = (int)(Math.random()*maxValue);
        for (int i = 1; i < arr.length; i++) {
            do{
                arr[i] = (int)(Math.random()*maxValue);
            }while (arr[i-1] == arr[i]);
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
        int maxLen = 20;
        int times = 10000;
        for (int i = 0; i < times; i++) {
            int[] randomArray = getRandomArray(maxValue, maxLen);
//            printArray(randomArray);
            int index = find(randomArray);
            boolean testIndex = false;
            if(index !=-1){
                testIndex = test(randomArray,index );
            }
            if(!testIndex){
                printArray(randomArray);
                System.out.println("匹配错误:" + index + ":" + testIndex);
            }
            if(testIndex){
                printArray(randomArray);
                System.out.println("匹配:" + index + ":" + testIndex);
            }
        }
    }
}
