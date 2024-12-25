package cn.daily.algorithm.code.code_10;

/**
 * @Author: zhaibo
 * @CreateTime: 2024-11-27
 * @Description:
 *  给定两个可能有环也可能无环的单链表，头节点head1和head2。请实现一个函数，如果两个链表相交，请返回相交的 第一个节点。如果不相交，返回null
 * 【要求】
 *  如果两个链表长度之和为N，时间复杂度请达到O(N)，额外空间复杂度 请达到O(1)。
 */
public class 链表常见面试题_1 {

    public static class Node{
        public int value;
        public Node next;

        public Node(int value) {
            this.value = value;
        }
    }


    /**
     * 1. 判断链表是否有环，找到第一个入环节点，如果没有返回null
     *   采用快慢指针的方案，慢指针每次走一步，快指针每次走两步。
     *   当两个指针相遇，快指针回到头节点每次走一步，慢指针继续每次走一步，两个指针再次相遇的点就是第一个入环节点
     */
    public static Node getLoopNode(Node head ){
        if(head == null || head.next == null || head.next.next == null ){
            return null;
        }
        Node slow = head.next;
        Node fast = head.next.next;
        while (slow != fast){
            if(fast.next == null || fast.next.next == null){
                return null;
            }
            slow = slow.next;
            fast = fast.next.next;
        }
        fast = head;
        while (fast != slow){
            slow = slow.next;
            fast = fast.next;
        }

        return slow;
    }

    /**
     * 2. 判断两个链表相交的第一个节点，三种情况
     *    》 两个链表都没有环
     *    》 两个链表都有环，但是两者不相交
     *    》 两个链表都有环，但是共有一个环，且是同一个入环节点
     *    》 两个链表都有环，但是共有一个环，但是是两个不同的入环节点
     *    ps：两个链表一个有环，另一个无环 这种情况不存在！！！！！
     */
    //两个链表都没有环: 判断是否相交就看尾节点是否相同,相同返回第一个交点。不相同返回null
    public static Node getNoLoop(Node head1,Node head2){
        if(head1 == null || head2 == null ){
            return null;
        }
        int len = 0;
        Node n1 = head1;
        while (n1.next != null){
            n1 = n1.next;
            len++;
        }
        Node n2 = head2;
        while (n2.next != null){
            n2 = n2.next;
            len--;
        }

        if(n1 != n2){
            return null;
        }

        n1 = len > 0 ? head1:head2;
        n2 = len < 0 ? head1:head2;

        int abs = Math.abs(len);

        while (abs > 0){
            n1 = n1.next;
            abs--;
        }

        while (n1 != n2){
            n1 = n1.next;
            n2 = n2.next;
        }

        return n1;

    }

    // 两个有环链表，返回第一个相交节点，如果不想交返回null
    public static Node getBothLoop(Node head1,Node loop1,Node head2,Node loop2){
        /**
         * 两个链表都有环，但是共有一个环，且是同一个入环节点
         */
        if(loop1 ==loop2){
            Node n1 = head1;
            int len = 0;
            while (n1.next != null){
                len++;
                n1 = n1.next;
                if(n1 == loop1){
                    break;
                }
            }
            Node n2 = head2;
            while (n2.next != null){
                len--;
                n2 = n2.next;
                if(n2 == loop1){
                    break;
                }
            }

            n1 = len > 0 ? head1:head2;
            n2 = len < 0 ? head1:head2;

            int abs = Math.abs(len);

            while (abs > 0){
                n1 = n1.next;
                abs--;
            }
            while (n1 != n2){
                n1 = n1.next;
                n2 = n2.next;
            }
            return n1;

        }else {
            /**
             * 两个链表都有环，但是两者不相交
             * 两个链表都有环，但是共有一个环，但是是两个不同的入环节点
             */
            Node node = loop1.next;
            while (node != loop1){
                if(node == loop2){
                    return node;
                }
                node = node.next;
            }

            return null;

        }

    }


    public static Node getIntersectNode(Node node1 ,Node node2){
        if(node1 == null || node2 == null){
            return null;
        }

        Node loopNode = getLoopNode(node1);
        Node loopNode2 = getLoopNode(node2);
        if(loopNode == null && loopNode2 == null){
            return getNoLoop(node1, node2);
        }

        if (loopNode != null && loopNode2 != null) {
            return getBothLoop(node1, loopNode, node2, loopNode2);
        }

        return null;
    }

    public static void main(String[] args) {
        // 1->2->3->4->5->6->7->null
        Node head1 = new Node(1);
        head1.next = new Node(2);
        head1.next.next = new Node(3);
        head1.next.next.next = new Node(4);
        head1.next.next.next.next = new Node(5);
        head1.next.next.next.next.next = new Node(6);
        head1.next.next.next.next.next.next = new Node(7);

        // 0->9->8->6->7->null
        Node head2 = new Node(0);
        head2.next = new Node(9);
        head2.next.next = new Node(8);
        head2.next.next.next = head1.next.next.next.next.next; // 8->6
        System.out.println(getIntersectNode(head1, head2).value);

        // 1->2->3->4->5->6->7->4...
        head1 = new Node(1);
        head1.next = new Node(2);
        head1.next.next = new Node(3);
        head1.next.next.next = new Node(4);
        head1.next.next.next.next = new Node(5);
        head1.next.next.next.next.next = new Node(6);
        head1.next.next.next.next.next.next = new Node(7);
        head1.next.next.next.next.next.next = head1.next.next.next; // 7->4

        // 0->9->8->2...
        head2 = new Node(0);
        head2.next = new Node(9);
        head2.next.next = new Node(8);
        head2.next.next.next = head1.next; // 8->2
        System.out.println(getIntersectNode(head1, head2).value);

        // 0->9->8->6->4->5->6..
        head2 = new Node(0);
        head2.next = new Node(9);
        head2.next.next = new Node(8);
        head2.next.next.next = head1.next.next.next.next.next; // 8->6
        System.out.println(getIntersectNode(head1, head2).value);

    }



}
