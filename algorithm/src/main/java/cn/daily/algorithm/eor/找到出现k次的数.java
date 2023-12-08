package cn.daily.algorithm.eor;

import javax.lang.model.element.VariableElement;
import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;

/**
 * 一个数组中有一种数出现k次，其他数都出现了M次，
 *  M > 1 ,K < M ,找到出现了K 次的数。 要求，额外空间复杂度O（1） ，时间复杂度O（N）
 *
 * @author zhaibo
 * @date 2023/12/07
 */
public class 找到出现k次的数 {

    public static int km(int[] arr,int k ,int m){
        if(arr == null){
            return -1;
        }
        //int 32位，定义一个32长度的数组，存储所有出现k次，m次的数的32位累加信息，记录每一位出现的次数总和
        // 12 的二进制 00000000 00000000 00000000 00001100
        int[] array = new int[32];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < 32; j++) {
                array[j] += ((arr[i] >> j ) & 1);
            }
        }
        // 每一位的累加和如果是M次的整数倍，代表出现 K 次的数在该位上是 0
        int ans = 0;
        for (int i = 0; i < array.length ; i++) {
            if(array[i] % m != 0){
                ans |= (1 << i);
            }
        }
        return ans;

    }

    public static int getKtimesNum(int[] arr ,int k ,int m ){
        HashMap<Integer, Integer> map = new HashMap<>();
        for(int num : arr){
            if(map.containsKey(num)){
                map.put(num ,map.get(num)+1);
            }else {
                map.put(num ,1);
            }
        }
        for(Integer num :map.keySet()){
            if (map.get(num) == k) {
                return num;
            }
        }
        return -1 ;
    }

    /**
     * @param range  数值范围区间
     * @param numKinds 数字种类的个数
     * @param k 数字出现的次数
     * @param m 数字出现的次数
     * @return {@link int[]}
     */
    public static int[] getRandomArray(int range,int numKinds ,int k,int m){
        //定义一个出现k次的数
        int ktimsNum = getRangeNum(range);

        numKinds -- ;
        int[] arr = new int[k + (numKinds * m)];
        //填充出现k次的数
        for (int i = 0; i < k; i++) {
            arr[i] = ktimsNum;
        }
        //填充出现m次的数
        HashSet set = new HashSet();
        set.add(ktimsNum);
        int t = k;
        for (; 0 < numKinds; numKinds--) {
            int mtimsNum;
            do{
                mtimsNum = getRangeNum(range);
            }while (set.contains(mtimsNum));
            set.add(mtimsNum);
            for (int j = 0; j < m; j++) {
                arr[t++] = mtimsNum;
            }
        }
        //arr填好了，在将数值随机打散
        for (int i = 0; i < arr.length; i++) {
            int index = (int)(Math.random()*arr.length);
            int tmp = arr[i];
            arr[i] = arr[index];
            arr[index] = tmp;
        }
        return arr;

    }

    /**
     * [-range ,range] 区间
     * @param range
     * @return int
     */
    public static int getRangeNum(int range){
        return (int)( Math.random()*range +1) - (int)( Math.random()*range +1);
    }

    public static void main(String[] args) {
        int[] arr = new int[]{1,2,3,4,6,5,3,2,6,2,4,5,1,6};
        int range = 8;
        int numKinds = 4;
        int k = 3 ;
        int m = 5;
        int times = 10000000;
        System.out.println("测试开始");
        for (int i = 0; i < times; i++) {
            int[] array = getRandomArray(range, numKinds, k, m);
            int ktimesNum = getKtimesNum(array, k, m);
            int km = km(array, k, m);
            if(ktimesNum != km){
                System.out.println("出错了");
            }
        }
        System.out.println("测试结束");
    }
}
