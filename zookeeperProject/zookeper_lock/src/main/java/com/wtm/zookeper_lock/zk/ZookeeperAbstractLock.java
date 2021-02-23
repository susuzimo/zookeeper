package com.wtm.zookeper_lock.zk;

import org.I0Itec.zkclient.ZkClient;

//将重复代码写入子类
public abstract  class ZookeeperAbstractLock  extends AbstractLock{
    //zk连接地址
    private static final String CONNECTSTRING="10.80.16.183:2181";
    //创建zk连接
    protected ZkClient zkClient= new ZkClient(CONNECTSTRING);
    protected static final String PATH="/lock";
    protected static final String PATH2="/lock2";

}
