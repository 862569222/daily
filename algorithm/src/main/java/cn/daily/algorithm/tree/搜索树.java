package cn.daily.algorithm.tree;

/**
 * @Author: zhaibo
 * @CreateTime: 2023-12-04
 * @Description: https://leetcode.cn/problems/validate-binary-search-tree/submissions/
 */
public class 搜索树 {
    public static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;

        TreeNode(int val) {
            this.val = val;
        }
    }

    public static class Info{
        boolean isBST;
        int max;
        int min;

        public Info(boolean isBST, int max, int min) {
            this.isBST = isBST;
            this.max = max;
            this.min = min;
        }
    }

    public boolean isValidBST(TreeNode root) {
        return process(root).isBST;
    }

    public Info process(TreeNode x){
        if(x == null){
            return null;
        }
        Info leftNode = process(x.left);
        Info rightNode = process(x.right);
        int max = x.val;
        int min = x.val;
        if(leftNode != null){
            max = Math.max(max,leftNode.max);
            min = Math.min(min,leftNode.min);
        }
        if(rightNode != null ){
            max = Math.max(max, rightNode.max);
            min = Math.min(min, rightNode.min);
        }
        boolean isBST  = true;

        if(leftNode != null && !leftNode.isBST ){
            isBST = false;
        }
        if(rightNode != null && !rightNode.isBST){
            isBST = false;
        }
        boolean leftIsBST = leftNode == null ? true : (leftNode.max < x.val );
        boolean rightIsBST = rightNode == null ? true : (rightNode.min > x.val);
        if(!(leftIsBST && rightIsBST)){
            isBST = false;
        }

        return new Info(isBST,max,min);

    }


    public Info process2(TreeNode x){
        if(x == null){
            return null;
        }
        Info leftNode = process2(x.left);
        Info rightNode = process2(x.right);
        int max = x.val;
        int min = x.val;
        if(leftNode != null){
            max = Math.max(max,leftNode.max);
            min = Math.min(min,leftNode.min);
        }
        if(rightNode != null ){
            max = Math.max(max, rightNode.max);
            min = Math.min(min, rightNode.min);
        }
        boolean isBST  = false;
        boolean leftIsBST = leftNode == null? true : leftNode.isBST;
        boolean rightIsBST = rightNode == null ? true : rightNode.isBST;
        boolean leftMaxLessX = leftNode == null ? true : (leftNode.max < x.val );
        boolean rightMinMoreX = rightNode == null ? true : (rightNode.min > x.val);
        if(leftIsBST && rightIsBST && rightMinMoreX && leftMaxLessX){
            isBST = true;
        }

        return new Info(isBST,max,min);

    }
}
