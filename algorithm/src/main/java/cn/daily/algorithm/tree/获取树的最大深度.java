package cn.daily.algorithm.tree;

/**
 * 测试链接：https://leetcode.com/problems/maximum-depth-of-binary-tree
 * @author zhaibo
 * @date 2023/9/5
 */
public class 获取树的最大深度 {
    public static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;
    }


    /**
     * 以root为头的树，最大高度是多少返回！
     * @param root
     * @return int
     */
    public static int maxDepth(TreeNode root) {
        if(root == null){
            return 0;
        }
        return Math.max(maxDepth(root.left),maxDepth(root.right)) + 1;
    }

    public static int maxDepth2(TreeNode root) {
        if(root == null){
            return 0;
        }
        return Math.max(maxDepth2(root.left),maxDepth2(root.right)) + 1;
    }
}
