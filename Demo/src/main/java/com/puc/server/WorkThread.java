package com.puc.server;


import com.puc.common.request.RPCRequest;
import com.puc.common.response.RPCResponse;
import com.puc.service.ServiceProvider;
import lombok.AllArgsConstructor;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.Socket;

@AllArgsConstructor
public class WorkThread implements Runnable{

    private Socket socket;
    private ServiceProvider serviceProvider;

    @Override
    public void run() {
        try{
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            RPCRequest request = (RPCRequest) ois.readObject();
            RPCResponse response = getResponse(request);
            oos.writeObject(response);
            oos.flush();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("从IO中读取数据错误");
        }
    }

    private RPCResponse getResponse(RPCRequest request) {
        // 得到服务名
        String interfaceName = request.getInterfaceName();
        // 得到服务端相应的服务类
        Object service = serviceProvider.getService(interfaceName);
        // 反射调用方法
        Method method = null;
        try{
            method = service.getClass().getMethod(request.getMethodName(), request.getParamsTypes());
            Object invoke = method.invoke(service, request.getParams());
            return RPCResponse.success(invoke);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("方法执行错误");
            return RPCResponse.fail();
        }

    }

}
