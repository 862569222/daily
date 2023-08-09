package cn.daily.zookeeper.config2;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.springframework.util.StringUtils;

import java.util.concurrent.CountDownLatch;

/**
 * @Author: zhaibo
 * @CreateTime: 2023-08-09
 * @Description: 实现配置监控
 */
public class WatchCallBack implements Watcher, AsyncCallback.StatCallback, AsyncCallback.DataCallback {
    ZooKeeper zooKeeper;
    MyConf myConf;
    String confName = "/conf";
    CountDownLatch countDownLatch = new CountDownLatch(1);
    public WatchCallBack(ZooKeeper zooKeeper, MyConf myConf) {
        this.zooKeeper = zooKeeper;
        this.myConf = myConf;
    }
    public void await(){
        zooKeeper.exists(confName, this,this,"123");
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        switch (watchedEvent.getType()) {
            case None:
                break;
            case NodeCreated:
                zooKeeper.getData(confName,this,this,"123");
                break;
            case NodeDeleted:
                zooKeeper.getData(confName,this,this,"123");
                countDownLatch.countDown();
                countDownLatch = new CountDownLatch(1);
                myConf.setConf(null);
                break;
            case NodeDataChanged:
                zooKeeper.getData(confName,this,this,"123");
                break;
            case NodeChildrenChanged:
                break;
        }
    }

    @Override
    public void processResult(int i, String s, Object o, Stat stat) {
        if(stat != null){
            zooKeeper.getData(confName,this,this,"123");
        }
    }

    @Override
    public void processResult(int i, String s, Object o, byte[] bytes, Stat stat) {

        if(bytes != null){
            myConf.setConf(new String(bytes));
            countDownLatch.countDown();
        }

    }
}
