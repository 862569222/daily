package cn.daily.algorithm.problems;

import org.junit.Test;

/**
 * 项目名称：daily
 * 类 名 称：exerciseProblems
 * 类 描 述：TODO
 * 创建时间：2023/6/10 07:50
 * 创 建 人：zhaibo
 */
public class exerciseProblems_2 {

    /**
     * @name:
     * @description: 有一个函数0 1不是等概率输出，请基于此函数得到0 1等概率输出
     * 思路：
     * @return:
     * @date: 2023/6/10 07:50
     *
    **/

    public int f2(){
        return Math.random()< 0.8 ? 0:1;
    }



    /**
     * 将f3函数改为等概率输出0 1
     *     0   1
     *     p  1-p    概率
     *
     * 组合数  00      01      10          11   四种组合，扣除 00 和 11 的
     *        pp    p(1-p)   (1-p)p   (1-p)(1-p)
     *
     * @return
     */
    public int f3(){
        int num ;
        do {
            num = f2();
        }while (num == f2());
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
            nums[num]++;
        }
        System.out.println( (double) count/(double)total);
        for (int i = 0; i < nums.length; i++) {
            System.out.println(nums[i]);
        }
        System.out.println("=============");
        nums = new int[2];
        for (int i = 0; i < total; i++) {
            int num = f3();

            nums[num]++;
        }
        for (int i = 0; i < nums.length; i++) {
            System.out.println("第"+i+"个："+nums[i]);
        }

    }
}
