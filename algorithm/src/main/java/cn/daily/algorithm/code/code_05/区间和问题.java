package cn.daily.algorithm.code.code_05;

/**
 * @author zhaibo
 * @date 2023/12/20
 */
public class 区间和问题 {
    /**
     * 测试地址：
     *      https://leetcode.com/problems/count-of-range-sum/
     *给你一个整数数组 nums 以及两个整数 lower 和 upper 。求数组中，值位于范围 [lower, upper] （包含 lower 和 upper）之内的 区间和的个数 。
     *
     * 区间和 S(i, j) 表示在 nums 中，位置从 i 到 j 的元素之和，包含 i 和 j (i ≤ j)。
     *
     *
     *
     * 示例 1：
     * 输入：nums = [-2,5,-1], lower = -2, upper = 2
     * 输出：3
     * 解释：存在三个区间：[0,0]、[2,2] 和 [0,2] ，对应的区间和分别是：-2 、-1 、2 。
     * 示例 2：
     *
     * 输入：nums = [0], lower = 0, upper = 0
     * 输出：1
     *
     * 提示：
     *
     * 1 <= nums.length <= 105
     * -231 <= nums[i] <= 231 - 1
     * -105 <= lower <= upper <= 105
     * 题目数据保证答案是一个 32 位 的整数
     */

    public static int countRangeSum(int[] nums, int lower, int upper) {
        if(nums == null || nums.length  == 0 ){
            return 0;
        }
        long[] sum = new long[nums.length];
        sum[0] = nums[0];
        for(int i = 1 ; i < nums.length ; i++){
            sum[i] = sum[i-1] + nums[i];
        }

        return process(sum,0,nums.length - 1 ,lower,upper);

    }

    private static int process(long[] sum, int l, int r ,int lower, int upper) {
        if(l == r ){
            if(sum[l] >=lower && sum[l] <= upper){
                return 1;
            }else {
                return 0;
            }
        }
        //l 和 r 不相等 ，此时小和数组有多个数
        int mid = l + ((r - l) >> 1);
        return process(sum,l,mid,lower,upper)+
                process(sum,mid + 1 ,r,lower,upper )+
                merge(sum,l,mid ,r,lower,upper);

    }
    // 范围 [2,5]
    //0-j小和数组 1 3 4 4 5 6 7       4 4    5    6     8     9
    //i-j范围                    [-1,2]  [0,3] [1,4] [3,6] [4,7]
    private static int merge(long[] sum, int l, int mid, int r, int lower, int upper) {
        int ans  = 0;
        int windowL = l;
        int windowR = l;

        for (int i = mid + 1; i <= r; i++) {
            long min = sum[i] - upper;
            long max = sum[i] - lower;
            //只有小于min 的时候windowL才右移 等于的时候右移会漏掉满足条件的区间和
            while (windowL <= mid && sum[windowL] < min ){
                windowL++;
            }
            //同理 等于windowR的时候就要右移 ，且windowR位置的数还不满足区间和条件
            while (windowR <= mid && sum[windowR] <= max){
                windowR++;
            }
            //所以此处计算数量的时候并没有 +1
            ans += windowR - windowL ;
        }

        long[] help = new long[r - l + 1];
        int index = 0;
        int p1 = l;
        int p2 = mid + 1;
        while (p1 <= mid && p2 <= r ){
            help[index++] = sum[p1] > sum[p2] ? sum[p2++] : sum[p1++];
        }

        while (p1 <= mid){
            help[index++] = sum[p1++];
        }
        while(p2 <= r){
            help[index++] = sum[p2++];
        }

        for (int i = 0; i < help.length; i++) {
            sum[l+i] = help[i];
        }

        return ans;
    }

}
