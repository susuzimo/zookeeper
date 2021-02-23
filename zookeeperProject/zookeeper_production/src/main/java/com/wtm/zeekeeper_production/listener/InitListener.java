package com.wtm.zeekeeper_production.listener;


import com.wtm.zeekeeper_production.zk.ServiceRegister;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Properties;

/*
*ServletContextListener
* 它能够监听 ServletContext 对象的生命周期，实际上就是监听 Web 应用的生命周期。
 */
public class InitListener implements ServletContextListener {
    @Override
    //容器初始化的时候会调用这个类
    public void contextInitialized(ServletContextEvent sce) {
        Properties properties = new Properties();
        try {
            properties.load(InitListener.class.getClassLoader().getResourceAsStream("application.properties"));
            String hostAddress = InetAddress.getLocalHost().getHostAddress();
            Integer port = Integer.valueOf(properties.getProperty("server.port"));
            ServiceRegister.register(hostAddress,port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
