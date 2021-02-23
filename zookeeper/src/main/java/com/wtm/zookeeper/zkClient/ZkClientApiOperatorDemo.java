package com.wtm.zookeeper.zkClient;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

import java.util.List;
import java.util.concurrent.TimeUnit;


public class ZkClientApiOperatorDemo {

    private final static String CONNECTSTRING="10.80.16.183:2181";

    private static ZkClient  getInstance(){
        return new ZkClient(CONNECTSTRING,10000);
    }

    public static void main(String[] args) throws InterruptedException {
        ZkClient zkClient=getInstance();
        //zkclient 提供递归创建父节点的功能   true代表可以递归创建字节点
        //zkClient.createPersistent("/zkclient/zkclient1/zkclient1-1/zkclient1-1-1",true);
        //System.out.println("success");

        //删除节点
        //zkClient.deleteRecursive("/zkclient");



        //获取子节点
        //List<String> list=zkClient.getChildren("/zkclient");
        //System.out.println(list);

        //watcher
        /*
        zkClient.subscribeDataChanges("/zkclient", new IZkDataListener() {

            public void handleDataChange(String s, Object o) throws Exception {
                System.out.println("节点名称："+s+"->节点修改后的值"+o);
            }

            public void handleDataDeleted(String s) throws Exception {

            }
        });
        */
        //zkClient.writeData("/zkclient","node");
        //TimeUnit.SECONDS.sleep(2);
        //zkClient.writeData("/zkclient","node1");
        //TimeUnit.SECONDS.sleep(2);


        zkClient.subscribeChildChanges("/zkclient", new IZkChildListener() {

            public void handleChildChange(String s, List<String> list) throws Exception {
                System.out.println("节点名称："+s+"->"+"当前的节点列表："+list);
            }
        });
        zkClient.delete("/zkclient/zkclient1/zkclient1-1/zkclient1-1-1");
        TimeUnit.SECONDS.sleep(2);
    }
}
