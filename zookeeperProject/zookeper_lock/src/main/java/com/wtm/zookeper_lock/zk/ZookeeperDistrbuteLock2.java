package com.wtm.zookeper_lock.zk;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;


public class ZookeeperDistrbuteLock2 extends ZookeeperAbstractLock{
    private CountDownLatch countDownLatch=null;
    private String beforePath;   //当前请求节点的前一个节点
    private String currentPath;  //当前请求的节点


    public  ZookeeperDistrbuteLock2(){
        if(!this.zkClient.exists(PATH2)){
            this.zkClient.createPersistent(PATH2);
        }
    }



    @Override
    //尝试获取锁
    public boolean tryLock() {
        //如果currentPath为空则为第一次尝试加锁，第一次加锁赋值currentPath
        if(currentPath==null || currentPath.length()<=0){
            //创建一个临时顺序节点  /lock2/
            currentPath=this.zkClient.createEphemeralSequential(PATH2+"/","lock");
        }
        //获取所有临时节点并排序，临时节点名称为自增长的字符串如 0000000000
        List<String> children = this.zkClient.getChildren(PATH2);
        Collections.sort(children);
        //如果当前节点在所有节点中排名第一则获取锁成功
        if(currentPath.equals(PATH2+"/"+children.get(0))){
            return true;
        }else{
            //如果不是第一名，则获取前面的节点名称赋值给beforePath
            int wz=Collections.binarySearch(children,currentPath.substring(7));
            beforePath=PATH2+"/"+children.get(wz-1);
        }
        return false;
    }

    @Override
    public void waitLock() {
      IZkDataListener iZkDataListener=new IZkDataListener(){
          @Override
          public void handleDataDeleted(String path) throws Exception {
              //唤醒等待的线程
              if(countDownLatch!=null){
                  countDownLatch.countDown();
              }
          }
          @Override
          public void handleDataChange(String s, Object o) throws Exception {

          }
      };
      //注册事件 给排在前面的节点增加数据删除的watcher，本质是启动另外一个线程去前置节点
      zkClient.subscribeDataChanges(beforePath,iZkDataListener);
      //如果节点
      if(zkClient.exists(beforePath)){
         countDownLatch=new CountDownLatch(1);
         try {
             //等待，一直等到接受到事件通知
             countDownLatch.await();
         }catch (Exception e){

         }
         //删除监听
          zkClient.unsubscribeDataChanges(beforePath,iZkDataListener);
      }
    }

    @Override
    public void unLock() {
        //释放锁
        if(zkClient!=null){
            zkClient.delete(currentPath);
            zkClient.close();
            System.out.println("释放资源");
        }
    }
}
