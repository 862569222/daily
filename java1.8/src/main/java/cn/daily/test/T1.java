package cn.daily.test;

import cn.daily.juc.container.Student;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zhaibo
 * @title: zb
 * @projectName dailyWork
 * @description: TODO
 * @date 2021/7/5 14:46
 */
@Slf4j
public class T1 {
    @Test
    public void t1(){
        Integer a = 128;
        Integer b = 128;
        System.out.println(a==b);
    }

    @Test
    public void t2(){
        Class<Student> studentClass = Student.class;
        Method[] declaredMethods = studentClass.getDeclaredMethods();
        Arrays.stream(declaredMethods).forEach(o->{
            System.out.println(o);
        });

    }


    @Test
    public void t3() throws InterruptedException {
        Thread.currentThread().interrupt();
        System.out.println(Thread.currentThread().isInterrupted());
        //Thread.interrupted() 判断线程是否被中断，并在第二次清除标记未置为false
        System.out.println(Thread.interrupted());
        System.out.println(Thread.interrupted());
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        lock.lock();
        System.out.println();
        System.out.println("main 线程拿到锁");
        Thread thread = new Thread(() -> {
            System.out.println("子线程尝试获取锁");
            lock.lock();
            System.out.println("子线程获取到锁");
            try {
                Thread.sleep(1000);
//                lock.wait();
            } catch (InterruptedException e) {
                System.out.println("线程中断退出");
            }
            lock.unlock();
        });
        thread.start();
        try {
            Thread.sleep(5000);
        }catch (InterruptedException e){
        }
        System.out.println("main 线程释放锁");
        lock.unlock();
        lock.unlock();
        System.out.println("等待子线程执行完毕");
        thread.join();
        System.out.println("子线程执行完毕");
    }

    @Test
    public void t4() throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();

        Thread thread = new Thread(() -> {
            System.out.println("子线程尝试获取锁");
            lock.lock();
            System.out.println("子线程获取到锁");
            try {
                condition.await();
                System.out.println("子线程被唤醒");
                lock.lock();
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("线程中断退出");
            }
            lock.unlock();
            System.out.println("子线程释放锁");
        });
        thread.start();
        Thread.sleep(1000);
        lock.lock();
        condition.signal();
        lock.unlock();
        System.out.println("main 线程释放锁");
        thread.join();

    }
}
