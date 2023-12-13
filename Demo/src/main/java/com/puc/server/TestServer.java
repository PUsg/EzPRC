package com.puc.server;

import com.puc.server.impl.SimpleRPCRPCServer;
import com.puc.server.impl.ThreadPoolRPCRPCServer;
import com.puc.service.BlogService;
import com.puc.service.UserService;
import com.puc.service.impl.BlogServiceImpl;
import com.puc.service.impl.UserServiceImpl;
import com.puc.service.provider.ServiceProvider;

import java.util.HashMap;
import java.util.Map;

public class TestServer {

    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        BlogService blogService = new BlogServiceImpl();

        ServiceProvider serviceProvider = new ServiceProvider();
        serviceProvider.provideServiceInterface(userService);
        serviceProvider.provideServiceInterface(blogService);

        RPCServer RPCServer = new ThreadPoolRPCRPCServer(serviceProvider);
        RPCServer.start(8899);
    }
}
