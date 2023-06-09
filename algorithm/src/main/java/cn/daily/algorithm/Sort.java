package cn.daily.algorithm;


/**
 * 项目名称：dailyWork
 * 类 名 称：Sort
 * 类 描 述：TODO
 * 创建时间：2023/6/1 21:27
 * 创 建 人：zhaibo
 */
public class Sort {

    /**
     * @name: selectionSort
     * @description: 选择排序
     * @return:
     * @date: 2023/6/1 21:29
     *
    **/
    public static void selectionSort(int[] arr){
        if(arr == null || arr.length < 2){
            return;
        }
        //从左到右遍历 选择一个最小值和最小值交换位置
        int N = arr.length;
        for(int i = 0; i < arr.length ; i++){
            int minIndex = i;
            for(int j = i+1 ; j < arr.length  ; j++ ){
                minIndex = arr[minIndex] > arr[j] ? j : minIndex;
            }
            swap(arr,minIndex, i);
        }
    }

    public static void bubbleSort(int[] arr){
        if(arr == null || arr.length < 2){
            return;
        }
        // 0 1  1 2  2 3  3 4  n n-1
        //两两比较最大值放最后
        int N = arr.length;
        for (int end = N  ; end > 0 ; end--) {

            for (int j = 1; j < end  ; j++) {
                if(arr[j-1] > arr[j]){
                    swap(arr,j-1,j);
                }
            }
        }
    }

    public static void insertSort(int[] arr){
        if(arr == null || arr.length < 2){
            return;
        }

        //扑克牌插入
        int n = arr.length;
        for (int i = 1; i < n; i++) {
            int newNumIndex = i;
            while (newNumIndex-1 >= 0 && arr[newNumIndex-1] > arr[newNumIndex] ){
                swap(arr,newNumIndex-1,newNumIndex);
                newNumIndex-- ;
            }
        }
    }

    public static void insertSort2(int[] arr){
        if(arr == null || arr.length < 2){
            return;
        }

        //扑克牌插入
        int n = arr.length;
        for (int end = 1; end < n; end++) {

            for(int pre = end -1 ; pre >=0 && arr[pre]> arr[pre +1 ] ; pre--){
                swap(arr,pre,pre +1);
            }
        }
    }

    public static void swap (int arr[],int now,int next){
        int temp = arr[next];
        arr[next] = arr[now];
        arr[now] = temp;
    }
    public static void printSort(int[] arr){
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]+" ");
        }
        System.out.println();
    }
    public static void main(String[] args) {

        int[] arr = {4,6,6,3,8,9,5,3,2,5,7,8,6,543,9,65,4};
        printSort(arr);
        insertSort3(arr);
        printSort(arr);
        double random = Math.random();
        System.out.println(random);
        double random1 = Math.random();
        System.out.println(random1);
        System.out.println(Math.min(random, random1));
    }

    public static void insertSort3(int[] arr){
        if(arr == null || arr.length < 2){
            return;
        }

        int n = arr.length;
        for (int i = n; i > 0 ; i--) {
            for (int j = 1; j < i; j++) {
                if(arr[j-1] > arr[j]){
                    swap(arr,j-1,j);
                }
            }
        }
    }


}
