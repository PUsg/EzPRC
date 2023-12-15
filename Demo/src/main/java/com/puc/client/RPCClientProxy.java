package com.puc.client;

import com.puc.client.IOClient;
import com.puc.client.RPCClient;
import com.puc.common.request.RPCRequest;
import com.puc.common.response.RPCResponse;
import lombok.AllArgsConstructor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


@AllArgsConstructor
public class RPCClientProxy implements InvocationHandler {

    private String host;

    private int port;

    private RPCClient client;

    public RPCClientProxy(RPCClient rpcClient) {
        this.client = rpcClient;
    }

    // jdk动态代理, 每一次代理对象调用方法， 都会经过此方法增强， 反射获取request对象， socket发送到客户端
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RPCRequest request = RPCRequest.builder().interfaceName(method.getDeclaringClass().getName()).methodName(method.getName())
                .params(args).paramsTypes(method.getParameterTypes()).build();

        //数据传输
        RPCResponse response = client.sendRequest(request);
        return response.getData();
    }

    <T>T getProxy(Class<T> clazz){
        Object o = Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, this);
        return (T)o;
    }

}
