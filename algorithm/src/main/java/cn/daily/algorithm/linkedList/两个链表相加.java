package cn.daily.algorithm.linkedList;

import lombok.Data;

/**
 * 项目名称：daily
 * 类 名 称：两个链表相加
 * 类 描 述：// 测试链接：https://leetcode.com/problems/add-two-numbers/
 * 创建时间：2023/7/4 22:42
 * @author zhaibo
 */
public class 两个链表相加 {

    @Data
    public static class ListNode{
        private Integer val;
        private ListNode next;

        public ListNode(Integer val) {
            this.val = val;
        }
    }

    public ListNode addTwoNumbers(ListNode head1,ListNode head2){
        int len1 = getLength(head1);
        int len2 = getLength(head2);
        ListNode l = len1 >= len2 ? head1 : head2;
        ListNode s = l == head1 ? head2 : head1;
        ListNode curL = l;
        ListNode curS = s;
        ListNode last = curL;
        int carry = 0;
        int curNum = 0;
        while (curS != null) {
            curNum = curL.val + curS.val + carry;
            curL.val = (curNum % 10);
            carry = curNum / 10;
            last = curL;
            curL = curL.next;
            curS = curS.next;
        }
        while (curL != null) {
            curNum = curL.val + carry;
            curL.val = (curNum % 10);
            carry = curNum / 10;
            last = curL;
            curL = curL.next;
        }
        if (carry != 0) {
            last.next = new ListNode(1);
        }
        return l;
    }

    public int getLength(ListNode node){
        int size = 0;
        while(node != null){
            size++;
            node = node.next;
        }
        return size;
    }

    public static void main(String[] args) {
        System.out.println(9/10);
    }
}
