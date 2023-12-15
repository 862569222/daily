package cn.daily.algorithm.code.code_04;

/**
 * @author zhaibo
 * @date 2023/12/14
 */
public class 归并排序 {

    public static void mergeSort(int[] arr){
        if(arr == null || arr.length < 2){
            return ;
        }
        process(arr,0,arr.length - 1);
    }

    private static void process(int[] arr, int L, int R) {
        if(L == R){
            return ;
        }
        int mid = L + ((R-L)>>1);
        process(arr,L,mid);
        process(arr,mid+1,R);
        merge(arr,L,mid,R);
    }

    private static void merge(int[] arr, int l, int mid, int r) {
        int[] help = new int[r - l + 1];
        int i = 0;
        int p1 = l;
        int p2 = mid + 1;
        while (p1 <= mid && p2 <= r){
            help[i++] = arr[p1] >= arr[p2] ? arr[p2++] : arr[p1++];
        }
        while (p1 <= mid){
            help[i++] = arr[p1++];
        }
        while (p2 <= r){
            help[i++] = arr[p2++];
        }

        for (int j = 0; j <help.length ; j++) {
            arr[l+j] = help[j];
        }
    }

    //非递归方式实现
    public static void mergeSort2(int[] arr){
        if(arr == null || arr.length < 2){
            return ;
        }
        //步长
        int megerSize = 1;
        int N = arr.length;
        while (megerSize < N){
            //定义第一个左组下标位置
            int L = 0;
            while (L < N){
                if(L + megerSize > N){
                    break;
                }
                int M = L + megerSize - 1;
                int R = M + megerSize > N-1 ? N - 1 :  M + megerSize;
                merge(arr,L,M,R);
                L = R + 1;
            }
            //防止溢出
            if(megerSize > N/2){
                break;
            }
            megerSize <<= 1;
        }
    }

    public static int[] getRandomArray(int size,int range){
        int[] array = new int[(int)((size )* Math.random() + 1)];
        for (int i = 0; i < array.length; i++) {
            array[i] = (int)(Math.random()*range + 1);
        }
        return array;
    }
    public static boolean isEqual(int[] arr , int[] arr2){
        if(arr.length != arr2.length){
            return false;
        }
        for (int i = 0; i < arr.length; i++) {
            if(arr[i] != arr2[i]){
                return false;
            }
        }
        return true;
    }

    public static int[] copyArray(int[] arr){
        int[] newArray = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            newArray[i] = arr[i];
        }
        return newArray;
    }

    public static void main(String[] args) {
        System.out.println("验证开始");
        int size = 10;
        int times = 1000000;
        int range = 13;
        for (int i = 0; i < times; i++) {
            int[] array = getRandomArray(size, range);
            int[] copyArray = copyArray(array);
            mergeSort(array);
            mergeSort2(copyArray);
            if(!isEqual(array,copyArray)){
                System.out.println("出错了");
            }
        }
        System.out.println("验证结束");
    }


}
