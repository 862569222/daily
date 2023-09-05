package cn.daily.algorithm.tree;

/**
 * 测试链接：https://leetcode.com/problems/same-tree
 * @author zhaibo
 * @date 2023/8/30
 */
public class 比较两个二叉树是否相同 {

    public static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;
    }
    public static boolean isSameTree(TreeNode p, TreeNode q) {

        if(p == null ^ q ==null ){
            return false;
        }

        if(p == null && q == null){
            return true;
        }
        return q.val == p.val && isSameTree(p.left,q.left) && isSameTree(p.right,q.right);
    }


    public static boolean isSameTree2(TreeNode p, TreeNode q) {
        if(p == null ^ q == null){
            return false;
        }
        if(p == null && q == null){
            return true;
        }

        return p.val == q.val && isSameTree2(p.left,q.left) && isSameTree2(p.right,q.right);
    }
}
