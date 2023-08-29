package cn.daily.algorithm.linkedList;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * @Author: zhaibo
 * @CreateTime: 2023-08-29
 * @Description: 测试链接：https://leetcode.com/problems/merge-k-sorted-lists/
 */
public class K个有序链表合并 {
    public static class ListNode {
        public int val;
        public ListNode next;
    }

    public static ListNode mergeKLists(ListNode[] lists) {
        if(lists == null ){
            return null;
        }
        PriorityQueue<ListNode> queue = new PriorityQueue<ListNode> ((Comparator<ListNode>) (o1, o2) -> o1.val - o2.val);
        for (ListNode node :lists){
            if(node != null){
                queue.add(node);
            }
        }
        if(queue.isEmpty()){
            return null;
        }
        ListNode head = queue.poll();
        ListNode pos = head;
        if(pos.next != null){
            queue.add(pos.next);
        }
        while (!queue.isEmpty()){
            ListNode node = queue.poll();
            pos.next = node;
            pos = node;
            if(node.next != null){
                queue.add(node.next);
            }
        }


        return head;
    }

}
