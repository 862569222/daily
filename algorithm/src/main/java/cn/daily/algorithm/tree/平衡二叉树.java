package cn.daily.algorithm.tree;

/**
 * https://leetcode.com/problems/balanced-binary-tree
 * @author zhaibo
 * @date 2023/12/01
 */
public class 平衡二叉树 {

    public static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;

        TreeNode(int val) {
            this.val = val;
        }
    }
    public static class Info {
        public boolean isBalanced;
        public int height;

        public Info(boolean i, int h) {
            isBalanced = i;
            height = h;
        }
    }

    public boolean isBalanced(TreeNode root) {
        return process(root).isBalanced;
    }

    public Info process(TreeNode root){
        if(root == null ){
            return new Info(true,0);
        }
        Info left = process(root.left);
        Info right = process(root.right);
        int height = Math.max(left.height,right.height) + 1;
        boolean isBalance = left.isBalanced && right.isBalanced && (Math.abs(left.height - right.height) < 2);
        return new Info(isBalance, height);
    }
}
