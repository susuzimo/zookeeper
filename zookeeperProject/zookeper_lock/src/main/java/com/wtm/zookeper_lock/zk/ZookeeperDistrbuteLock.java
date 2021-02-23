package com.wtm.zookeper_lock.zk;

import org.I0Itec.zkclient.IZkDataListener;

import java.util.concurrent.CountDownLatch;


public class ZookeeperDistrbuteLock  extends ZookeeperAbstractLock{
    private CountDownLatch countDownLatch=null;

    @Override
    //尝试获取锁
    public boolean tryLock() {
        try{
            zkClient.createEphemeral(PATH);
            return  true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
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
      //注册事件
      zkClient.subscribeDataChanges(PATH,iZkDataListener);
      //如果节点
      if(zkClient.exists(PATH)){
         countDownLatch=new CountDownLatch(1);
         try {
             //等待，一直等到接受到事件通知
             countDownLatch.await();
         }catch (Exception e){
             e.printStackTrace();
         }
         //删除监听
          zkClient.unsubscribeDataChanges(PATH,iZkDataListener);
      }
    }

    @Override
    public void unLock() {
        //释放锁
        if(zkClient!=null){
            zkClient.delete(PATH);
            zkClient.close();
            System.out.println("释放资源");
        }
    }
}
