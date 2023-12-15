package cn.daily.algorithm.code.code_03;

import lombok.Data;

/**
 * @author zhaibo
 * @date 2023/12/08
 */
public class 删除单链表中的某个值 {

    @Data
    public static class Node{
        private Integer value;
        private Node next;
    }

    /**
     * 1->2->3->4->5->6->7->8
     * @param head
     * @param val
     * @return {@link Node}
     */
    public static Node removeValue(Node head, int val){

        //判断头节点是不是要删除的值，拿到要返回的头节点
        while(head != null){
            if(head.value != val){
                break;
            }
            head = head.next;
        }
        Node pre = head;
        Node cur = head ;
        while (cur != null){
            if(cur.value == val){
                pre.next = cur.next;
            }else {
                pre = cur;
            }
            cur = cur.next;
        }
        return head;
    }

}
