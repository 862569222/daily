package cn.daily.algorithm.linkedList;

import lombok.Data;

/**
 * 项目名称：daily
 * 类 名 称：两个链表相加
 * 类 描 述：// 测试链接：https://leetcode.com/problems/add-two-numbers/
 * 创建时间：2023/7/4 22:42
 * @author zhaibo
 */
public class 两个链表相加_2 {

    @Data
    public static class ListNode{
        private Integer val;
        private ListNode next;

        public ListNode(Integer val) {
            this.val = val;
        }
    }

    public ListNode addTwoNumbers(ListNode head1,ListNode head2){
        int head1Length = getLength(head1);
        int head2length = getLength(head2);
        ListNode maxNode = head1Length >= head2length ? head1 : head2;
        ListNode minNode = maxNode == head1 ? head2 : head1;
        ListNode cur = maxNode;
        ListNode index = cur;
        ListNode lastNode = maxNode;
        int carry = 0;
        while (minNode!= null){
            int sum = minNode.val + maxNode.val + carry;
            carry = sum / 10;
            cur.val = sum % 10;
            index = cur;
            cur = cur.next;
            minNode = minNode.next;
            maxNode = maxNode.next;
        }

        while (maxNode!= null){
            int sum = maxNode.val + carry;
            carry = sum / 10;
            cur.val = sum % 10;
            index = cur;
            cur = cur.next;
            maxNode = maxNode.next;
        }

        if(carry!=0){
            index.next = new ListNode(1);
        }

        return lastNode;
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
