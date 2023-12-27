package cn.daily.algorithm.code.code_07;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

/**
 * （用堆的实现）
 * @author zhaibo
 * @date 2023/12/27
 */
public class 最大线段重合问题 {

    /**
     *
     * 给定很多线段，每个线段都有两个数[start, end]，
     * 表示线段开始位置和结束位置，左右都是闭区间
     * 规定：
     * 1）线段的开始和结束位置一定都是整数值
     * 2）线段重合区域的长度必须>=1
     * 返回线段最多重合区域中，包含了几条线段
     */

    public static int maxCover1(int[][] lines) {

        int max = lines[0][1];
        int min = lines[0][0];
        for (int i = 1; i < lines.length; i++) {
            max = Math.max(max,lines[i][1]);
            min = Math.min(min,lines[i][0]);
        }
        int cover = 0;
        for (double i = min + 0.5; i < max; i++) {
            int nums = 0;
            for (int j = 0; j < lines.length; j++) {
                if(lines[j][0] < i && i < lines[j][1]){
                    nums++;
                }
            }
            cover = Math.max(cover,nums);
        }

        return cover;
    }

    public static int maxCover2(int[][] lines){
        Line[] sortArray = new Line[lines.length];
        for (int i = 0; i < sortArray.length; i++) {
            sortArray[i] = new Line(lines[i][0],lines[i][1]);
        }
        Arrays.sort(sortArray,(a,b)-> a.start - b.start);

        int max = 0;
        PriorityQueue<Integer> queue = new PriorityQueue();
        for (int i = 0; i < sortArray.length; i++) {
            while (!queue.isEmpty() && queue.peek() <= sortArray[i].start){
                queue.poll();
            }
            queue.offer(sortArray[i].end);
            max = Math.max(max,queue.size());
        }
        return max;
    }

    public static class Line{
        private int start;
        private int end;

        public Line(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    public static int[][] getLines(int length,int start ,int end){
        int[][] arrays = new int[(int)(Math.random()*length ) + 1][2];
        for (int i = 0; i < arrays.length; i++) {
            int l = 0 , r = 0;
            do {
                l = (int)(Math.random()*start) + 1;
                r = (int)(Math.random()*end) + 1;
            }while (l >= r);
            arrays[i][0] = l;
            arrays[i][1] = r;
        }
        return arrays;
    }

    public static void main(String[] args) {
        int times = 1000000;
        int length = 20;
        int start = 20;
        int end = 40;
        System.out.println("begin...");
        for (int i = 0; i < times; i++) {
            int[][] lines = getLines(length, start, end);
            int maxCover1 = maxCover1(lines);
            int maxCover2 = maxCover2(lines);
            if(maxCover1 != maxCover2){
                System.out.println("Oops");

                break;
            }
        }
        System.out.println("end...");
    }
}
