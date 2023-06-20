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

public class ReverseList {

    @Data
    public static class Node{
        private Integer value;
        private Node next;

    }

    public static Node reverse(Node head){
        if(head == null){
            return null;
        }
        Node pre = null;
        Node pos = null;
        while (head != null){
            pos = head.next;
            head.next = pre;
            pre = head;
            head = pos;

        }

        return pre;
    }

    public static Node getRandomLinkedList(int maxValue ,int maxLen){
        int len = (int)(Math.random()*maxLen);
        if(len == 0){
            return null;
        }
        len -- ;
        Node head = new Node();
        head.setValue((int) (Math.random() * maxValue));
        Node p = head ;
        while (len != 0){
            Node next = new Node();
            next.setValue((int) (Math.random() * maxValue));
            p.setNext(next);
            p = next;
            len --;
        }
        return head;
    }

    public static List<Integer> toList (Node node){
        ArrayList<Integer> objects = new ArrayList<>();

        while (node != null){
            objects.add(node.value);
            node = node.next;
        }
        return objects;
    }

    public static boolean checkLinkedListReverse(Node node,List<Integer> list){
        for (int i = list.size() - 1; i >= 0 ; i--) {
            while (node != null){
                if(!list.get(i).equals(node.value) ){
                    return false;
                }
                node = node.next;
            }
        }
        return true;
    }

    public static void printNode(Node node){
        while (node != null){
            System.out.print(node.value +",");
            node = node.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int maxValue = 30 ;
        int maxLen = 6;
        int times = 100;
        for (int i = 0; i < times; i++) {
            Node randomLinkedList = getRandomLinkedList(maxValue, maxLen);
            List<Integer> objects = toList(randomLinkedList);
            String collect = objects.stream().map(integer -> integer.toString()).collect(Collectors.joining(","));
            System.out.println(collect);
            Node reverse = reverse(randomLinkedList);
            printNode(reverse);
            boolean b = checkLinkedListReverse(reverse, objects);
            System.out.println("");
        }

    }
}
