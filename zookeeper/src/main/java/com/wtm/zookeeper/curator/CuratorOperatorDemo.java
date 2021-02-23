package com.wtm.zookeeper.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.transaction.CuratorTransactionResult;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class CuratorOperatorDemo {

    public static void main(String[] args) throws InterruptedException {
        CuratorFramework curatorFramework=CuratorClientUtils.getInstance();
        System.out.println("连接成功.........");

        //fluent风格

        /**
         * 创建节点
         */

        /*try {
            //创建多个节点
           // String result=curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).
          //          forPath("/curator0/curator1/curator11","123".getBytes());
            //System.out.println(result);

            //返回创建路径
            String s = curatorFramework.create().forPath("/curator", "test".getBytes());
            System.out.println(s);
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        /**
         * 删除节点
         */
//        try {
//            //默认情况下，version为-1
//            //删除多个节点
//            curatorFramework.delete().deletingChildrenIfNeeded().forPath("/curator0");
//            //删除单个
//            curatorFramework.delete().forPath("/curator");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        /**
         * 查询
         */
       /* Stat stat=new Stat();
        try {
            byte[] bytes=curatorFramework.getData().storingStatIn(stat).forPath("/curator");
            System.out.println(new String(bytes)+"-->stat:"+stat);
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        /**
         * 更新
         */

        /*try {
            Stat stat=curatorFramework.setData().forPath("/curator","123".getBytes());
            System.out.println(stat);
        } catch (Exception e) {
            e.printStackTrace();
        }*/


        /**
         * 异步操作
         * 异步获取结果 inBackground
         * 像一个线程池的一个接口类ExecutorService
         */
/*        ExecutorService service= Executors.newFixedThreadPool(1);
        final CountDownLatch countDownLatch=new CountDownLatch(1);
        try {
            curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).
                    inBackground(new BackgroundCallback() {
                        public void processResult(CuratorFramework curatorFramework, CuratorEvent curatorEvent) throws Exception {
                            System.out.println(Thread.currentThread().getName()+"->resultCode:"+curatorEvent.getResultCode()+"->"
                                    +curatorEvent.getType());
                            countDownLatch.countDown();
                        }
                    },service).forPath("/curator0","deer".getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("阻塞");
        countDownLatch.await();
        service.shutdown();*/

        /**
         * 事务操作（curator独有的）
         */
        try {
            Collection<CuratorTransactionResult> resultCollections=curatorFramework.inTransaction().create().forPath("/curator1","222".getBytes()).and().
                    setData().forPath("/curator","222".getBytes()).and().commit();
            for (CuratorTransactionResult result:resultCollections){
                System.out.println(result.getForPath()+"->"+result.getType());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
