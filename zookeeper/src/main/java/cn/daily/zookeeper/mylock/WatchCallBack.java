package cn.daily.zookeeper.mylock;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @Author: zhaibo
 * @CreateTime: 2023-08-09
 * @Description:
 */
public class WatchCallBack implements AsyncCallback.StringCallback, AsyncCallback.ChildrenCallback, AsyncCallback.StatCallback,Watcher {
    ZooKeeper zooKeeper;
    String threadName;
    String pathName;
    CountDownLatch countDownLatch = new CountDownLatch(1);
    public String getPathName() {
        return pathName;
    }

    public void setPathName(String pathName) {
        this.pathName = pathName;
    }

    public WatchCallBack(ZooKeeper zooKeeper, String threadName) {
        this.zooKeeper = zooKeeper;
        this.threadName = threadName;
    }

    public void tryLock() {

        try {
            zooKeeper.create("/lock",threadName.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL,this,"123");
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void unLock() {
        try {
            zooKeeper.delete(pathName,-1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (KeeperException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void processResult(int rc, String path, Object ctx, String name) {
        if(StringUtils.hasText(name)){
            pathName = name;
            zooKeeper.getChildren("/", false, this, "123");
        }
    }

    @Override
    public void processResult(int rc, String path, Object ctx, List<String> children) {
        Collections.sort(children);
        int i = children.indexOf(pathName.substring(1));

        if(i == 0 ){
            System.out.println(threadName +" i am first....");
            countDownLatch.countDown();
        }else {
            zooKeeper.exists("/"+children.get(i-1),this,this,"233");
        }
    }

    @Override
    public void processResult(int rc, String path, Object ctx, Stat stat) {

    }

    @Override
    public void process(WatchedEvent event) {
        switch (event.getType()) {
            case None:
                break;
            case NodeCreated:
                break;
            case NodeDeleted:
                zooKeeper.getChildren("/", false, this, "123");
                break;
            case NodeDataChanged:
                break;
            case NodeChildrenChanged:
                break;
        }
    }
}
