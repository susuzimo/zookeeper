package com.wtm.zookeeper.zkClient;

import org.I0Itec.zkclient.ZkClient;


public class SessionDemo {

    private final static String CONNECTSTRING="10.80.16.183:2181";

    public static void main(String[] args) {
        ZkClient zkClient=new ZkClient(CONNECTSTRING,4000);

        System.out.println(zkClient+" - > success");
    }
}
