package com.wtm.config.listener;

import org.I0Itec.zkclient.exception.ZkMarshallingError;
import org.I0Itec.zkclient.serialize.ZkSerializer;

import java.io.UnsupportedEncodingException;

//序列化及反序列化介绍
public class MyZkSerializer  implements ZkSerializer{
    @Override
    public Object deserialize(byte[] bytes) throws ZkMarshallingError {
        try {
            new java.lang.String(bytes,"utf-8");
        }catch (UnsupportedEncodingException e){

        }
        return  "";
    }

    @Override
    public byte[] serialize(Object o) throws ZkMarshallingError {
        try{
            return java.lang.String.valueOf(o).getBytes("utf-8");
        }catch (Exception e){

        }
        return null;
    }
}
