package cn.daily.algorithm.linkedList;

import java.util.HashMap;

/**
 * 项目名称：daily
 * 类 名 称：K个节点组内逆序调整
 * 类 描 述：// 测试链接：https://leetcode.com/problems/reverse-nodes-in-k-group/
 * 1->2->3->4->5->6->7->8
 * k=3 ，3个节点为一组分组，然后小组内进行逆序调整,不足三个的原样输出
 * 3->2->1->6->5->4->7->8
 * @date：2023/7/13
 * @author：zhaibo
 */
public class K个节点组内逆序调整 {

    // 不要提交这个类
    public static class ListNode {
        public int val;
        public ListNode next;
    }

    public static ListNode reverseKGroup(ListNode head, int k) {
        ListNode start = head;
        ListNode lastNode = getLastNode(head, k);
        if (lastNode == null){
            return head;
        }
        head = lastNode;
        reverse(start, lastNode);
        ListNode lastEnd = start;
        while (lastEnd.next != null){
            start = lastEnd.next;
            lastNode = getLastNode(start, k);
            if (lastNode == null){
                return head;
            }
            reverse(start, lastNode);
            lastEnd.next = lastNode;
            lastEnd = start;
        }
        return head;

    }

    private static void reverse(ListNode start, ListNode lastNode) {
        lastNode = lastNode.next;
        ListNode cur = start;
        ListNode pre = null;
        ListNode next = null;
        while (cur != lastNode){
            next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;

        }
        start.next = lastNode;
    }

    public static ListNode getLastNode(ListNode start ,int k){
        while (--k != 0 && start != null ){
            start = start.next;
        }
        return start;
    }


}
