package com.puc.server.impl;

import com.puc.server.RPCServer;
import com.puc.service.provider.ServiceProvider;
import com.puc.thread.WorkThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolRPCRPCServer implements RPCServer {

    private final ThreadPoolExecutor threadPool;
    private ServiceProvider serviceProvide;

    public ThreadPoolRPCRPCServer(ServiceProvider serviceProvide) {
        threadPool =  new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), 1000, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(100));
        this.serviceProvide = serviceProvide;
    }

    public ThreadPoolRPCRPCServer(ServiceProvider serviceProvide, int corePoolSize,
                                  int maximumPoolSize,
                                  long keepAliveTime,
                                  TimeUnit unit,
                                  BlockingQueue<Runnable> workQueue){

        threadPool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
        this.serviceProvide = serviceProvide;
    }

    @Override
    public void start(int port) {
        System.out.println("线程池版服务器启动了");
        try{
            ServerSocket serverSocket = new ServerSocket(port);
            while(true) {
                Socket socket = serverSocket.accept();
                threadPool.execute(new WorkThread(socket, serviceProvide));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {

    }
}
