package com.puc.client;


import com.puc.client.RPCClient;
import com.puc.common.request.RPCRequest;
import com.puc.common.response.RPCResponse;
import com.puc.register.ServiceRegister;
import lombok.AllArgsConstructor;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

@AllArgsConstructor
public class SimpleRPCClient implements RPCClient {

    private String host;
    private int port;
    private ServiceRegister serviceRegister;


    /**
     * 客户端发起一次请求， Socket建立哦连接， 发起请求Request 得到响应response
     * 这里的request是封装好的 不同的service需要进行不同的封装， 客户端只知道Service接口，需要一层动态代理根据反射封装不同的Service
     * @param request 请求
     * @return 响应
     */
    @Override
    public RPCResponse sendRequest(RPCRequest request) {
        // 从注册中心获取host， post
        InetSocketAddress address = serviceRegister.serviceDiscovery(request.getInterfaceName());
        host = address.getHostName();
        port = address.getPort();

        try{
            Socket socket = new Socket(host, port);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

            System.out.println(request);
            oos.writeObject(request);
            oos.flush();
            RPCResponse response = (RPCResponse) ois.readObject();
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
