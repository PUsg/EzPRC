package com.puc.client;

import com.puc.common.request.RPCRequest;
import com.puc.common.response.RPCResponse;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class IOClient {
    /**
     *  负责底层与服务端的通信 发送req 接收 resp
     *  这里的req是封装好的。 不同的service需要进行不同的封装，客户端只知道Service接口， 需要一层动态代理根据反射封装不同的service
     * @param host
     * @param port
     * @param rpcRequest
     * @return
     */

    public static RPCResponse sendRequest(String host, int port, RPCRequest rpcRequest) {

        try{
            Socket socket = new Socket(host, port);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            System.out.println(rpcRequest);

            oos.writeObject(rpcRequest);
            oos.flush();

            RPCResponse response = (RPCResponse)ois.readObject();
            return response;

        }catch (Exception e) {
            e.printStackTrace();
            System.out.println();
            return null;
        }
    }
}
