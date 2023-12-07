package cn.daily.algorithm.eor;

/**
 * 数组中其他数出现偶数次，只有两个数出现奇数次，找到并输出这两个数
 * @author zhaibo
 * @date 2023/12/07
 */
public class 找到两个出现奇数次的数 {

    public static void getNum(int[] arr){
        if(arr == null){
            return;
        }
        int eor = 0;
        for (int i = 0; i < arr.length; i++) {
            eor ^= arr[i];
        }
        //假设两个数是a,b 那么获取到的num=a^b
        //找到eor 二进制数最右侧的 1  ， eor & ((~eor)+1) 和 eor &(-eor) 等效
        // eor  000010101011000   eor是异或的结果，所以为1的代表两个数不相同异或才为1，由此可以区分出a,b 两个数
        //eor`  000000000001000
        //eor`
        int onlyone = eor &(-eor);
        int a= 0;
        for (int i = 0; i < arr.length; i++) {
            //假设a的第三位是1， 000010101011000  
            //                000000000001000
            if((arr[i] & onlyone) != 0 ){
                a ^=arr[i];
            }
        }
        int b = a ^ onlyone;
        String out = String.format("数组中出现奇数次的两个数：a=%s,b=%s",a,b);
        System.out.println(out);
    }

    public static void main(String[] args) {
        int[] arr = new int[]{1,2,3,4,6,5,3,2,6,2,4,5,1,6};
        getNum(arr);
    }
}
