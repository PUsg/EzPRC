package com.puc.server.impl;

import com.puc.server.RPCServer;
import com.puc.service.provider.ServiceProvider;
import com.puc.thread.WorkThread;
import jdk.nashorn.internal.runtime.regexp.joni.ast.StringNode;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

public class SimpleRPCRPCServer implements RPCServer {

    private ServiceProvider serviceProvider;

    public SimpleRPCRPCServer(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    @Override
    public void start(int port) {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("服务端启动了");
            // BIO的方式监听Socket
            while (true){
                Socket socket = serverSocket.accept();
                // 开启一个新线程去处理
                new Thread(new WorkThread(socket, serviceProvider)).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {

    }
}
