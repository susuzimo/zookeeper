package com.wtm.zookeeper.api;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


public class CreateSessionDemo  {
    private final static String CONNECTSTRING="10.80.16.183:2181";
    private static CountDownLatch countDownLatch=new CountDownLatch(1);
    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        ZooKeeper zooKeeper=new ZooKeeper(CONNECTSTRING, 5000, new Watcher() {
            public void process(WatchedEvent watchedEvent) {
                //如果当前的连接状态是连接成功的，那么通过计数器去控制
                if(watchedEvent.getState()==Event.KeeperState.SyncConnected){
                    countDownLatch.countDown();
                    System.out.println(watchedEvent.getState());
                }
                if(watchedEvent.getType()== Event.EventType.NodeDataChanged){
                    System.out.println("数据变更触发路径："+watchedEvent.getPath());
                }
                if(watchedEvent.getType()== Event.EventType.NodeCreated){
                    System.out.println("创建节点："+watchedEvent.getPath());
                }
                /*
                if(watchedEvent.getType()== Event.EventType.NodeDataChanged){
                    try {
                        System.out.println("数据变更触发路径："+watchedEvent.getPath()+"->改变后的值："+
                                new String(zookeeper.getData(watchedEvent.getPath(),true,stat)));
                    } catch (KeeperException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }else if(watchedEvent.getType()== Event.EventType.NodeChildrenChanged){//子节点的数据变化会触发
                    try {
                        System.out.println("子节点数据变更路径："+watchedEvent.getPath()+"->节点的值："+
                                zookeeper.getData(watchedEvent.getPath(),true,stat));
                    } catch (KeeperException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }else if(watchedEvent.getType()== Event.EventType.NodeCreated){//创建子节点的时候会触发
                    try {
                        System.out.println("节点创建路径："+watchedEvent.getPath()+"->节点的值："+
                                zookeeper.getData(watchedEvent.getPath(),true,stat));
                    } catch (KeeperException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }else if(watchedEvent.getType()== Event.EventType.NodeDeleted){//子节点删除会触发
                    System.out.println("节点删除路径："+watchedEvent.getPath());
                }
                */

            }
        });
        countDownLatch.await();
        System.out.println(zooKeeper.getState());
        String path="/z1";
        zooKeeper.exists(path,true);
        zooKeeper.create(path,"123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);


        //创建节点  ZooDefs.Ids.OPEN_ACL_UNSAFE==acl    CreateMode.PERSISTENT 创建节点类型
        //zooKeeper.create("/test-1","123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);


        //获取数据
        //Stat stat=new Stat();
        //byte[] data = zooKeeper.getData("/test", true, stat);
        //System.out.println(new String(data));
        //stat记录的是节点信息
        //System.out.println(stat);


        //修改数据  version -1 不做版本控制  监听 watch只起一次作用
        //Stat stat=new Stat();
        //zooKeeper.getData("/test-1", true, stat);
        //zooKeeper.setData("/test-1","test".getBytes(),-1);
        //删除节点
        //zooKeeper.delete("/test",-1);



        //获取指定节点下的子节点
        //List<String> childrens=zooKeeper.getChildren("/test-1",true);
        //System.out.println(childrens);

        // 创建节点和子节点
        /*String path="/node11";
        zooKeeper.create(path,"123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
        TimeUnit.SECONDS.sleep(1);
        Stat stat=zooKeeper.exists(path+"/node1",true);
        if(stat==null){//表示节点不存在
            zooKeeper.create(path+"/node1","123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
            TimeUnit.SECONDS.sleep(1);
        }
        //修改子路径
        zooKeeper.setData(path+"/node1","deer".getBytes(),-1);
        TimeUnit.SECONDS.sleep(1);*/
    }
}
