package com.puc.server;

import com.puc.service.BlogService;
import com.puc.service.UserService;
import com.puc.service.impl.BlogServiceImpl;
import com.puc.service.impl.UserServiceImpl;
import com.puc.service.ServiceProvider;

public class TestServer {

    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        BlogService blogService = new BlogServiceImpl();

        ServiceProvider serviceProvider = new ServiceProvider("127.0.0.1", 8899);
        serviceProvider.provideServiceInterface(userService);
        serviceProvider.provideServiceInterface(blogService);

        RPCServer RPCServer = new NettyRPCServer(serviceProvider);
        RPCServer.start(8899);
    }
}
