package cn.daily.algorithm;

import org.junit.Test;

/**
 * 项目名称：daily
 * 类 名 称：exerciseProblems
 * 类 描 述：TODO
 * 创建时间：2023/6/10 07:50
 * 创 建 人：zhaibo
 */
public class exerciseProblems {

    /**
     * @name:
     * @description: 有一个函数1-5等概率输出，请基于此函数得到1-7等概率输出
     * 思路：
     * @return:
     * @date: 2023/6/10 07:50
     *
    **/

    //该函数 等概率输出1-5
    public int f2(){
        return (int)(Math.random()*5 +1);
    }

    //将f2函数改为等概率输出0 1
    public int f3(){
        int num ;
        do {
            num = f2();
        }while (num == 3);
        num = num < 3 ? 0:1;
        return num;
    }
    // 等概率输出 0 - 7
    public int f4(){
        return (f3() << 2) + (f3() << 1) + f3();
    }
    //等于 0 的时候重做
    public int f5(){
        int num ;
        do {
            num = f4() ;
        }while (num == 0);
        return num;
    }



    @Test
    public void t1(){
        int count = 0;
        int total = 1000000;
        int[] nums = new int[5];
        for (int i = 0; i < total; i++) {
            int num = f2();
            if(num < 3){
                count++;
            }
            nums[num-1]++;
        }
        System.out.println( (double) count/(double)total);
        for (int i = 0; i < nums.length; i++) {
            System.out.println(nums[i]);
        }
        System.out.println("=============");
        nums = new int[8];
        for (int i = 0; i < total; i++) {
            int num = f5();

            nums[num]++;
        }
        for (int i = 0; i < nums.length; i++) {
            System.out.println("第"+i+"个："+nums[i]);
        }

    }


    /**
     * @name:
     * @description: 练习 15-67等概率输出
     *          00 0000
     *          11 1111
     * @return:
     * @date: 2023/6/10 09:49
     *
    **/
    // 等概率输出  15-67
    public int f6(){
        return (f3() << 5) + (f3() << 4)+(f3() << 3) + (f3() << 2) + (f3() << 1) + f3();
    }

    public int f7(){
        int num ;
        do {
            num = f6();
        }while (num < 15 || num > 67);
        return num;
    }

    @Test
    public void t2(){
        int count = 0;
        int total = 1000000;
        int[] nums = new int[5];
        for (int i = 0; i < total; i++) {
            int num = f2();
            if(num < 3){
                count++;
            }
            nums[num-1]++;
        }
        System.out.println( (double) count/(double)total);
        for (int i = 0; i < nums.length; i++) {
            System.out.println(nums[i]);
        }
        System.out.println("=============");
        nums = new int[68];
        for (int i = 0; i < total; i++) {
            int num = f7();

            nums[num]++;
        }
        for (int i = 0; i < nums.length; i++) {
            System.out.println("第"+i+"个："+nums[i]);
        }
    }
}
