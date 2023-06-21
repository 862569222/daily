package cn.daily.algorithm.linkedList;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 项目名称：daily
 * 类 名 称：ReverseList
 * 类 描 述：单链表反转
 * 创建时间：2023/6/20 09:49
 * 创 建 人：zhaibo
 */

public class ReverseDoubleList {


    @Data
    public static class DoubleNode{
        private Integer value;
        private DoubleNode last;
        private DoubleNode next;
    }

    public static DoubleNode reverseDoubleNode(DoubleNode head){
        if(head == null){
            return null;
        }
        DoubleNode pre = null;
        DoubleNode pos = null;
        while (head != null){
            pos = head.next;
            head.last = head.next;
            head.next = pre;
            pre = head;
            head = pos;
        }
        return pre;
    }

    public static DoubleNode getRandomDoubleList(int maxValue ,int maxLen){
        int len = (int)(Math.random()*maxLen);
        if(len == 0){
            return null;
        }
        len -- ;
        DoubleNode head = new DoubleNode();
        head.setValue((int) (Math.random() * maxValue));
        DoubleNode pre = head;
        while (len != 0){
            DoubleNode next = new DoubleNode();
            next.setValue((int) (Math.random() * maxValue));
            next.setLast(pre);
            pre.next = next;
            pre = next;
            len -- ;
        }
        return head;
    }

    public static List<Integer> doubleNodeToList (DoubleNode node){
        ArrayList<Integer> objects = new ArrayList<>();

        while (node != null){
            objects.add(node.value);
            node = node.next;
        }
        return objects;
    }


    public static boolean checkDoubleListReverse(DoubleNode head,List<Integer> origin) {
        DoubleNode end = null;
        for (int i = origin.size() - 1; i >= 0; i--) {
            if (!origin.get(i).equals(head.value)) {
                return false;
            }
            end = head;
            head = head.next;
        }
        for (int i = 0; i < origin.size(); i++) {
            if (!origin.get(i).equals(end.value)) {
                return false;
            }
            end = end.last;
        }
        return true;
    }



    public static void printNode(DoubleNode node){
        while (node != null){
            System.out.print(node.value +",");
            node = node.next;
        }
        System.out.println();
    }
    public static void main(String[] args) {
        int maxValue = 10 ;
        int maxLen = 6;
        int times = 100;

        for (int i = 0; i < times; i++) {
            DoubleNode randomLinkedList = getRandomDoubleList(maxValue, maxLen);
            List<Integer> objects = doubleNodeToList(randomLinkedList);
            String collect = objects.stream().map(integer -> integer.toString()).collect(Collectors.joining(","));
            System.out.println(collect);
            DoubleNode reverse = reverseDoubleNode(randomLinkedList);
            printNode(reverse);
            boolean b = checkDoubleListReverse(reverse, objects);
            System.out.println("");
        }

    }
}
