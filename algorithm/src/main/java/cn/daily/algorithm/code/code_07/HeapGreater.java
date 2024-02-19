package cn.daily.algorithm.code.code_07;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * @Author: zhaibo
 * @CreateTime: 2024-02-18
 * @Description: 加强堆
 */
public class HeapGreater<T> {
    private ArrayList<T> heap;
    private HashMap<T,Integer> indexMap;
    private int heapSize;
    private Comparator<? super T> comparator;

    public HeapGreater(Comparator<? super T> comparator) {
        this.heapSize = 0;
        this.heap = new ArrayList<>();
        this.indexMap = new HashMap<>();
        this.comparator = comparator;
    }

    public boolean isEmpty(){
        return heapSize == 0;
    }

    public int size(){
        return heapSize;
    }

    public boolean contains(T obj){
        return indexMap.containsKey(obj);
    }

    public T peek(){
        return heap.get(0);
    }

    public void push(T obj){
        heap.add(obj);
        indexMap.put(obj,heapSize);
        heapInsert(heapSize++);

    }

    public T pop(){
        T obj = heap.get(0);
        swap(0,heapSize - 1);
        indexMap.remove(obj);
        heap.remove(--heapSize);
        heapify(0);
        return obj;
    }

    public void remove(T obj){
        T replace = heap.get(heapSize - 1);
        int index = indexMap.get(obj);
        indexMap.remove(obj);
        heap.remove(--heapSize);
        if(obj != replace){
            heap.set(index,replace);
            indexMap.put(replace,index);
            resign(replace);
        }
    }

    public void resign(T replace) {
        heapInsert(indexMap.get(replace));
        heapify(indexMap.get(replace));
    }

    public List<T> getAllElements() {
        List<T> ans = new ArrayList<>();
        for (T c : heap) {
            ans.add(c);
        }
        return ans;
    }
    private void heapify(int index) {
        int left = index * 2 + 1;
        while (left < heapSize){
            int best = left + 1 < heapSize &&
                    comparator.compare(heap.get(left + 1),heap.get(left)) < 0 ? left + 1 :left;
            best = comparator.compare(heap.get(best),heap.get(index)) < 0 ? best: index;
            if (best == index){
                break;
            }
            swap(best,index);
            index = best;
            left = index * 2 + 1;
        }
    }

    private void heapInsert(int index) {
        while(comparator.compare(heap.get(index),heap.get((index - 1)/2)) < 0 ){
            swap(index,(index - 1)/2);
            index = (index - 1)/2;
        }
    }

    private void swap(int i, int j) {
        T o1 = heap.get(i);
        T o2 = heap.get(j);
        heap.set(i, o2);
        heap.set(j, o1);
        indexMap.put(o2, i);
        indexMap.put(o1, j);
    }


}
