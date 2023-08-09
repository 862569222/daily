package cn.daily.zookeeper.config2;

import cn.daily.zookeeper.config.ZKUtils;
import org.apache.zookeeper.ZooKeeper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @Author: zhaibo
 * @CreateTime: 2023-08-08
 * @Description: TODO
 */
public class TestConfig {

    ZooKeeper zk;

    @Before
    public void getZk(){
        zk =  ZKUtils.getZK();
    }

    @After
    public void close(){
        try {
            zk.close();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void test() throws InterruptedException {
        MyConf myconf = new MyConf();
        WatchCallBack watchCallBack = new WatchCallBack(zk, myconf);
        watchCallBack.await();

        while (true){
            if(myconf.getConf() == null ){
                System.out.println("conf is null");
                watchCallBack.await();
            }else {
                System.out.println(myconf.getConf());
            }
            Thread.sleep(500);
        }
    }
}
