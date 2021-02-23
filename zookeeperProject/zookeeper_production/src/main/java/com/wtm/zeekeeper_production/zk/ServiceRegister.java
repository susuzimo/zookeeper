package com.wtm.zeekeeper_production.zk;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class ServiceRegister {

    private static  final String BASE_SERVICES="/services";

    private static  final String SERVICE_NAME="/products";

    public static void register(String address,int port){
        String path=BASE_SERVICES+SERVICE_NAME;
        try {
            ZooKeeper zooKeeper = new ZooKeeper("10.80.16.183:2181", 5000, (watchedEvent) -> { });
            Stat exists = zooKeeper.exists(BASE_SERVICES + SERVICE_NAME, false);
            System.out.println(exists);
            //先判断服务器根路径是否存在
            if(exists==null){
                zooKeeper.create(path,"".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
            String server_path=address+":"+port;
            zooKeeper.create(path+"/child",server_path.getBytes(),ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.EPHEMERAL_SEQUENTIAL);
            System.out.println("产品注册成功");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
