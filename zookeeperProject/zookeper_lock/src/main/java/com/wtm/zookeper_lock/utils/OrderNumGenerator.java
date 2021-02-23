package com.wtm.zookeper_lock.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderNumGenerator {
    //全局订单id
    public static int COUNT=0;
    public static  Object lock=new Object();
    public  String getNumber(){
        synchronized (lock){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
            return simpleDateFormat.format(new Date())+"-"+ ++COUNT;
        }
    }
}
