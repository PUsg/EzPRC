package com.puc.client;
import com.puc.pojo.blog.Blog;
import com.puc.proxy.ClientProxy;
import com.puc.service.BlogService;
import com.puc.service.UserService;
import com.puc.pojo.user.User;

public class RPCClient {
    public static void main(String[] args) {
        ClientProxy clientProxy = new ClientProxy("127.0.0.1", 8899);
        UserService proxy = clientProxy.getProxy(UserService.class);

        //服务的方法1
        User userById = proxy.getUserById(10);
        System.out.println("从服务端得到的user" + userById);

        //服务的方法2
        User user = User.builder().userName("张三").id(100).sex(true).build();
        Integer integer = proxy.insertUserId(user);
        System.out.println("向服务端插入数据："+integer);

        BlogService blogService = clientProxy.getProxy(BlogService.class);
        Blog blogById = blogService.getBlogById(10000);
        System.out.println("从服务端得到的blog为：" + blogById);
    }
}
