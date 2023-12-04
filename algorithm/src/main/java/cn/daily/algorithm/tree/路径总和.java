package cn.daily.algorithm.tree;

/**
 * @Author: zhaibo
 * @CreateTime: 2023-12-04
 * @Description: https://leetcode.cn/problems/path-sum/
 */
public class 路径总和 {
    public static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;

        TreeNode(int val) {
            this.val = val;
        }
    }

    public static boolean isSum = false;

    public boolean hasPathSum(TreeNode root, int targetSum) {
        if(root == null){
            return false;
        }
        isSum = false;
        process(root,0,targetSum);
        return isSum;
    }

    public static void process(TreeNode x,int preSum,int targetSum){
        //x是叶子节点
        if(x.left == null && x.right == null ){
            if(x.val + preSum == targetSum){
                isSum = true;
                return;
            }
        }
        preSum += x.val;
        //x非叶子节点
        if(x.left != null){
            process(x.left,preSum,targetSum);
        }
        if (x.right != null) {
            process(x.right,preSum,targetSum);
        }
    }
}
