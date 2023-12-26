package com.puc.service;


import com.puc.register.ServiceRegister;
import com.puc.register.ZkServiceRegister;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * 存放服务接口名与服务端对应的实现类
 * 服务启动时候要暴露其相关的实现类
 * 根据req中的interface调用服务端中相关的实现类
 */
public class ServiceProvider {

    private Map<String, Object> interfaceProvider;
    private ServiceRegister serviceRegister;
    private String host;
    private int port;
    public ServiceProvider(String host, int port){
        // 需要传入服务端自身的服务的网络地址
        this.host = host;
        this.port = port;
        this.interfaceProvider = new HashMap<>();
        this.serviceRegister = new ZkServiceRegister();
    }

    public void provideServiceInterface(Object service) {
        Class<?>[] interfaces = service.getClass().getInterfaces();
        for (Class<?> clazz : interfaces) {
            interfaceProvider.put(clazz.getName(), service);
            serviceRegister.register(clazz.getName(), new InetSocketAddress(host, port));
        }
    }

    public Object getService(String interfaceName){
        return interfaceProvider.get(interfaceName);
    }

}
