package com.wtm.zookeper_lock.zk;

import com.wtm.zookeper_lock.utils.OrderNumGenerator;

public class OrderService implements Runnable {
    private OrderNumGenerator orderNumGenerator=new OrderNumGenerator();

    private Lock lock=new ZookeeperDistrbuteLock();

    //private Lock lock=new ZookeeperDistrbuteLock2();

    @Override
    public void run() {
        getNumber();
    }
    public void getNumber(){
        try {
            lock.getLock();
            String number=orderNumGenerator.getNumber();
            System.out.println(Thread.currentThread().getName()+",生成订单ID:"+number);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unLock();
        }
    }

    public static void main(String[] args) {
        System.out.println("生成唯一订单号");
        for (int i=0;i<10;i++){
            new Thread(new OrderService()).start();
        }
    }
}
