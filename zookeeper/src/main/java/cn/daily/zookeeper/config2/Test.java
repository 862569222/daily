package cn.daily.zookeeper.config2;

import cn.daily.zookeeper.config.DefaultWatch;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @Author: zhaibo
 * @CreateTime: 2023-08-09
 * @Description: TODO
 */
public class Test {
    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        ZooKeeper zk = new ZooKeeper("192.168.100.3:2181/test", 3000, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                Event.EventType type = watchedEvent.getType();
                Event.KeeperState state = watchedEvent.getState();
                switch (type) {
                    case None:
                        break;
                    case NodeCreated:
                        break;
                    case NodeDeleted:
                        break;
                    case NodeDataChanged:
                        break;
                    case NodeChildrenChanged:
                        break;
                }

                switch (state) {
                    case Unknown:
                        break;
                    case Disconnected:
                        break;
                    case NoSyncConnected:
                        break;
                    case SyncConnected:
                        countDownLatch.countDown();
                        break;
                    case AuthFailed:
                        break;
                    case ConnectedReadOnly:
                        break;
                    case SaslAuthenticated:
                        break;
                    case Expired:
                        break;
                }
            }
        });
        countDownLatch.await();
        ZooKeeper.States state = zk.getState();
        System.out.println(state.toString());
        zk.create("/user", "zhangsan".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL, new AsyncCallback.StringCallback() {
            @Override
            public void processResult(int rc, String path, Object ctx, String name) {
                System.out.println(name);
                System.out.println(path);
            }
        },"124");
        Stat stat = new Stat();
//        byte[] data = zk.getData("/user", false, stat);
//        System.out.println(new String(data));
//        zk.getData("/user", new Watcher() {
//            @Override
//            public void process(WatchedEvent watchedEvent) {
//                switch (watchedEvent.getType()) {
//                    case None:
//                        break;
//                    case NodeCreated:
//                        zk.getData("/user", new Watcher() {
//                            @Override
//                            public void process(WatchedEvent watchedEvent) {
//
//                            }
//                        }, (i, s1, o, bytes, stat1) -> System.out.println("新增值：" + new String(bytes)), "");
//                        break;
//                    case NodeDeleted:
//                        zk.getData("/user", new DefaultWatch(), new AsyncCallback.DataCallback() {
//                            @Override
//                            public void processResult(int i, String s, Object o, byte[] bytes, Stat stat) {
//                                System.out.println("删除值");
//
//                            }
//                        },"");
//                        break;
//                    case NodeDataChanged:
//                        zk.getData("/user", new DefaultWatch(), (i, s1, o, bytes, stat1) -> System.out.println("修改值2："+new String(bytes)),"");
//                        break;
//                    case NodeChildrenChanged:
//                        break;
//                }
//            }
//        }, new AsyncCallback.DataCallback() {
//            @Override
//            public void processResult(int i, String s, Object o, byte[] bytes, Stat stat) {
//                System.out.println("修改值："+new String(bytes));
//            }
//        },"123");
        System.in.read();
        zk.close();
    }
}
