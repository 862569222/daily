package cn.daily.algorithm.eor;

/**
 * 数组中其他数出现偶数次，只有一个数出现奇数次，找到并输出这个数
 * @author zhaibo
 * @date 2023/12/07
 */
public class 找到唯一出现奇数次的数 {

    public static void getNum(int[] arr){
        if(arr == null){
            return;
        }
        int num = 0;
        for (int i = 0; i < arr.length; i++) {
            num ^= arr[i];
        }
        System.out.println(num);
    }

    public static void main(String[] args) {
        int[] arr = new int[]{1,2,3,4,5,3,2,2,4,5,1};
        getNum(arr);
    }
}
