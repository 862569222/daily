package cn.daily.algorithm.code.code_05;

/**
 * @author zhaibo
 * @date 2023/12/20
 */
public class 快排 {

    //取arr[r]的值为划分值，最后在和左边界的只交换,返回划分区域坐标
    // 2 0 1 4 2 8 2
    public int partition(int[] arr,int l ,int r){
        if(l > r){
            return -1;
        }
        if(l == r){
            return l;
        }
        int left = l - 1 ;
        int index = l;
        while(index < r){

            if(arr[index] <= arr[r]){
                swap(arr,++left,index);
            }
            index++;
        }
        swap(arr,++left,r);
        return left;
    }

    //荷兰国旗问题
    //取arr[r]的值为划分值 <arr[r]  =arr[r]  >arr[r]，返回划分区域坐标
    // 2 0 1 4 2 8 2
    public static int[] netherlandsFlag(int[] arr ,int l ,int r ){
        if(l > r ){
            return new int[]{-1,-1};
        }
        if(l == r){
            return new int[]{l,l};
        }
        int left = l - 1;
        int right = r;
        while (left < right){

        }


        return new int[]{left + 1,right};
    }


    public static void swap(int[] arr ,int l,int r){
        int temp = arr[l];
        arr[l] = arr[r];
        arr[r] = temp;
    }


}
