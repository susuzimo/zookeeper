package com.wtm.zookeeper_customer.listener;

import com.wtm.zookeeper_customer.utils.LoadBalance;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.ArrayList;
import java.util.List;

public class InitListener implements ServletContextListener {

    private static  final String BASE_SERVICES="/services";

    private static  final String SERVICE_NAME="/products";

    private ZooKeeper zooKeeper;

    private void init(){
        try {
            //连接上zk,获得列表信息
            zooKeeper=new ZooKeeper("10.80.16.183:2181",5000,(watchedEvent) ->{
                if(watchedEvent.getType()== Watcher.Event.EventType.NodeChildrenChanged && watchedEvent.getPath().equals(BASE_SERVICES+SERVICE_NAME)){
                    updateServiceList();
                }
            });
            //第一次连接的时候要获得列表信息
            updateServiceList();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void updateServiceList() {
        List<String> newServerList=new ArrayList<>();
        try{
            //监听多次
            List<String> children = zooKeeper.getChildren(BASE_SERVICES + SERVICE_NAME, true);
            for(String subNode:children){
                byte[] data = zooKeeper.getData(BASE_SERVICES + SERVICE_NAME + "/" + subNode, false, null);
                String host=new String(data,"utf-8");
                System.out.println("host:"+host);
                newServerList.add(host);
            }
            LoadBalance.SERVICE_LIST=newServerList;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        init();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
