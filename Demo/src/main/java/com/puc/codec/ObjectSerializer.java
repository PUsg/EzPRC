package com.puc.codec;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ObjectSerializer implements Serializer{

    // 利用java IO 对象 -> 字节数组
    @Override
    public byte[] serialize(Object obj) {
        byte[] bytes = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try{
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            bytes = bos.toByteArray();
            oos.close();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new byte[0];
    }
    // 字节数组 -> 对象
    @Override
    public Object deserialize(byte[] bytes, int messageType) {
        Object obj = null;
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        try{
            ObjectInputStream ois = new ObjectInputStream(bis);
            obj = ois.readObject();
            ois.close();
            bis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    @Override
    public int getType() {
        return 0;
    }
}
