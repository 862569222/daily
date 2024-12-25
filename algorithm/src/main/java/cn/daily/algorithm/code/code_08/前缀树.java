package cn.daily.algorithm.code.code_08;

import org.omg.CORBA.StringHolder;

import java.util.HashMap;

/**
 * @Author: zhaibo
 * @CreateTime: 2024-02-26
 * @Description:
 * 1）单个字符串中，字符从前到后的加到一棵多叉树上
 * 2）字符放在路上，节点上有专属的数据项（常见的是pass和end值）
 * 3）所有样本都这样添加，如果没有路就新建，如有路就复用
 * 4）沿途节点的pass值增加1，每个字符串结束时来到的节点end值增加1
 * 设计一种结构。用户可以：
 * 1）void insert(String str)            添加某个字符串，可以重复添加，每次算1个
 * 2）int search(String str)             查询某个字符串在结构中还有几个
 * 3)  void delete(String str)           删掉某个字符串，可以重复删除，每次算1个
 * 4）int prefixNumber(String str)  查询有多少个字符串，是以str做前缀的
 * 1）固定数组实现
 *
 * 2）哈希表实现
 */
public class 前缀树 {
    public static class Node1{
        public int pass;
        public int end;

        public Node1[] next;

        public Node1() {
            pass = 0;
            end = 0;
            next = new Node1[26];
        }
    }

    public static class  Trie1{
        private Node1 root;

        public Trie1(){
            root = new Node1();
        }

        public void insert(String word){
            if(word == null){
                return;
            }
            char[] chars = word.toCharArray();
            Node1 node = root;
            node.pass++;
            int path = 0 ;
            // 从左往右遍历字符
            for (int i = 0; i < chars.length; i++) {
                // 由字符，对应成走向哪条路
                path = chars[i] - 'a';
                if(node.next[path] == null){
                    node.next[path] = new Node1();
                }
                node = node.next[path];
                node.pass++;
            }
            node.end++;
        }
        // word这个单词之前加入过几次
        public int search(String word){
            if(word == null){
                return 0;
            }
            char[] str = word.toCharArray();
            Node1 node = root;
            int path = 0;
            for (int i = 0; i < str.length; i++) {
                path = str[i] - 'a';
                if(node.next[path] == null){
                    return 0;
                }
                node = node.next[path];
            }
            return node.end;
        }

        //所有加入的字符串中，有几个是以pre这个字符串作为前缀的
        public int prefixNumber(String pre){
            if(pre == null){
                return 0;
            }
            char[] str = pre.toCharArray();
            Node1 node = root;
            int path = 0;
            for (int i = 0; i < str.length; i++) {
                path = str[i] - 'a';
                if(node.next[path] == null){
                    return 0;
                }
                node = node.next[path];
            }
            return node.pass;
        }

        public void delete(String word){
            if(word == null ){
                return;
            }

            if(search(word) > 0){
                char[] str = word.toCharArray();
                Node1 node = root;
                node.pass--;
                int path = 0;
                for (int i = 0; i < str.length; i++) {
                    path = str[i] - 'a';
                    if(--node.next[path].pass == 0){
                        node.next[path] = null;
                        return;
                    }
                    node = node.next[path];
                }
                node.end--;
            }
        }
    }


    public static class Node2{
        public int pass;
        public int end;
        public HashMap<Integer,Node2> next ;

        public Node2() {
            this.pass = 0;
            this.end = 0;
            next = new HashMap<>();
        }
    }

    public static class Trie2{
        private Node2 root;

        public Trie2() {
            this.root = new Node2();
        }

        public void insert(String word){
            if(word == null){
                return;
            }
            char[] str = word.toCharArray();
            Node2 node = root;
            int path = 0 ;
            for (int i = 0; i < str.length; i++) {
                path = str[i];
                if(!node.next.containsKey(path)){
                    node.next.put(path,new Node2());
                }
                node = node.next.get(path);
                node.pass++;
            }
            node.end++;

        }

        public void delete(String word) {
            if (search(word) != 0) {
                char[] chs = word.toCharArray();
                Node2 node = root;
                node.pass--;
                int index = 0;
                for (int i = 0; i < chs.length; i++) {
                    index = (int) chs[i];
                    if (--node.next.get(index).pass == 0) {
                        node.next.remove(index);
                        return;
                    }
                    node = node.next.get(index);
                }
                node.end--;
            }
        }

        // word这个单词之前加入过几次
        public int search(String word) {
            if (word == null) {
                return 0;
            }
            char[] chs = word.toCharArray();
            Node2 node = root;
            int index = 0;
            for (int i = 0; i < chs.length; i++) {
                index = (int) chs[i];
                if (!node.next.containsKey(index)) {
                    return 0;
                }
                node = node.next.get(index);
            }
            return node.end;
        }

        // 所有加入的字符串中，有几个是以pre这个字符串作为前缀的
        public int prefixNumber(String pre) {
            if (pre == null) {
                return 0;
            }
            char[] chs = pre.toCharArray();
            Node2 node = root;
            int index = 0;
            for (int i = 0; i < chs.length; i++) {
                index = (int) chs[i];
                if (!node.next.containsKey(index)) {
                    return 0;
                }
                node = node.next.get(index);
            }
            return node.pass;
        }

    }


    public static class Right {

        private HashMap<String, Integer> box;

        public Right() {
            box = new HashMap<>();
        }

        public void insert(String word) {
            if (!box.containsKey(word)) {
                box.put(word, 1);
            } else {
                box.put(word, box.get(word) + 1);
            }
        }

        public void delete(String word) {
            if (box.containsKey(word)) {
                if (box.get(word) == 1) {
                    box.remove(word);
                } else {
                    box.put(word, box.get(word) - 1);
                }
            }
        }

        public int search(String word) {
            if (!box.containsKey(word)) {
                return 0;
            } else {
                return box.get(word);
            }
        }

        public int prefixNumber(String pre) {
            int count = 0;
            for (String cur : box.keySet()) {
                if (cur.startsWith(pre)) {
                    count += box.get(cur);
                }
            }
            return count;
        }
    }

    // for test
    public static String generateRandomString(int strLen) {
        char[] ans = new char[(int) (Math.random() * strLen) + 1];
        for (int i = 0; i < ans.length; i++) {
            int value = (int) (Math.random() * 6);
            ans[i] = (char) (97 + value);
        }
        return String.valueOf(ans);
    }

    // for test
    public static String[] generateRandomStringArray(int arrLen, int strLen) {
        String[] ans = new String[(int) (Math.random() * arrLen) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = generateRandomString(strLen);
        }
        return ans;
    }

    public static void main(String[] args) {
        int arrLen = 100;
        int strLen = 20;
        int testTimes = 100000;
        for (int i = 0; i < testTimes; i++) {
            String[] arr = generateRandomStringArray(arrLen, strLen);
            Trie1 trie1 = new Trie1();
            Trie2 trie2 = new Trie2();
            Right right = new Right();
            for (int j = 0; j < arr.length; j++) {
                double decide = Math.random();
                if (decide < 0.25) {
                    trie1.insert(arr[j]);
                    trie2.insert(arr[j]);
                    right.insert(arr[j]);
                } else if (decide < 0.5) {
                    trie1.delete(arr[j]);
                    trie2.delete(arr[j]);
                    right.delete(arr[j]);
                } else if (decide < 0.75) {
                    int ans1 = trie1.search(arr[j]);
                    int ans2 = trie2.search(arr[j]);
                    int ans3 = right.search(arr[j]);
                    if (ans1 != ans2 || ans2 != ans3) {
                        System.out.println("Oops!");
                    }
                } else {
                    int ans1 = trie1.prefixNumber(arr[j]);
                    int ans2 = trie2.prefixNumber(arr[j]);
                    int ans3 = right.prefixNumber(arr[j]);
                    if (ans1 != ans2 || ans2 != ans3) {
                        System.out.println("Oops!");
                    }
                }
            }
        }
        System.out.println("finish!");

    }

}












