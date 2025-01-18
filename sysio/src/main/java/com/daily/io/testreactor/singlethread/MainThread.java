package com.daily.io.testreactor.singlethread;

/**
 * @Author: zhaibo
 * @CreateTime: 2025-01-18
 * @Description: 理解netty模型
 */
public class MainThread {

    public static void main(String[] args) {
        // 1. 创建IO线程,考虑封装的思想通过一个类管理IO线程，该类可以对io线程进行创建初始化
        SelectorThreadGroup threadGroup = new SelectorThreadGroup(1);
        // 2. 我应该把 监听（9999）的  server  注册到某一个 selector上
        threadGroup.bind(9999);
    }

}
