package cn.daily.algorithm;

/**
 * @author zhaibo
 * @title: zb
 * @projectName eureka
 * @description: TODO
 * @date 2021/2/1 7:15
 */
public class RecursionSort {
    public static void main(String[] args) {
        int[] arr = {4,9,2,67,8};
        System.out.println(getmax2(arr,0,arr.length-1));
    }

    private static int getmax(int[] arr, int L, int R) {
        if(L == R){
            return arr[L] ;
        }

        int mid = (L+R)/2;
        int leftMax = getmax(arr, L, mid);
        int rightMax = getmax(arr, mid+1, R);
        return Math.max(leftMax,rightMax);
    }

    private static int getmax2(int[] arr , int l ,int r){
        if(l == r){
            return arr[r];
        }

        int mid = l + ((r-l)>>1);
        int leftmax = getmax2(arr, l, mid);
        int rightmax = getmax2(arr, mid + 1 ,r);
        return  Math.max(leftmax,rightmax);
    }
}
