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
     * @return:
     * @date: 2023/6/10 07:50
     *
    **/
    public int f1(){

        return 0;
    }
    //该函数 等概率输出1-5
    public int f2(){
        return (int)(Math.random()*5 +1);
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
    }
}
