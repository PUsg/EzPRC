package com.puc.server;


import com.puc.service.ServiceProvider;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolver;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import lombok.AllArgsConstructor;



/**
 * 初始化 主要负责序列化的编码解码，需要解决netty的粘包问题
 */
@AllArgsConstructor
public class NettyServerInitializer extends ChannelInitializer<SocketChannel> {

    private ServiceProvider serviceProvider;

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        // 消息格式 [长度][消息体]，解决粘包问题
        pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0,4,0,4));
        //计算当前待发送消息的长度，写入到前4个字节当中
        pipeline.addLast(new LengthFieldPrepender(4));

        // 这里使用java序列化方式， netty自带的编解码支持这种传输这种结构
        pipeline.addLast(new ObjectEncoder());
        pipeline.addLast(new ObjectDecoder(new ClassResolver() {
            @Override
            public Class<?> resolve(String s) throws ClassNotFoundException {
                return Class.forName(s);
            }
        }));

        pipeline.addLast(new NettyRPCServerHandler(serviceProvider));
    }
}
