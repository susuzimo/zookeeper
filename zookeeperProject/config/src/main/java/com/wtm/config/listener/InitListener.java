package com.wtm.config.listener;


import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.springframework.context.ApplicationContext;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class InitListener  implements ServletContextListener{
    private static  final String URL="/db/url";
    private static  final String PASSWORD="/db/password";
    private static  final String USERNAME="/db/username";
    private static  final String DRIVER="/db/driver";
    private  ApplicationContext context;

    ZkClient zkClient=new ZkClient("10.80.16.183:2181",5000,5000,new MyZkSerializer());

    private void init(){
        zkClient.subscribeDataChanges(URL, new IZkDataListener() {
            @Override
            public void handleDataChange(String s, Object o) throws Exception {
               /* EnjoyDataSource enjoyDataSource=(EnjoyDataSource)context.getBean("dataSource");
                enjoyDataSource.setUrl(o.toString());
                enjoyDataSource.changeDataSource();*/
               //去刷新配置文件
            }

            @Override
            public void handleDataDeleted(String s) throws Exception {

            }
        });
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        init();
    }
}
