package com.njh.rpc.RpcServer.Server.Protocol.Dubbo;

import com.njh.rpc.RpcServer.Server.Framework.Invocation;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class NettyClient<T> {

    private NettyClientHandler clientHandler = null;
    //使用线程池来提高并发效率
    private static ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public void start(String hostname, int port){
        clientHandler = new NettyClientHandler();

        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap()
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .group(eventLoopGroup)
                .remoteAddress(hostname, port)
                .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline()//设置编码器、解码器与handler
                                    .addLast("decoder",new ObjectDecoder(ClassResolvers
                                            .weakCachingConcurrentResolver(this.getClass()
                                                                            .getClassLoader())))
                                    .addLast("encoder",new ObjectEncoder())
                                    .addLast("handler",clientHandler);
                        }
            });
            //连接服务器
           bootstrap.connect(hostname,port).sync();
        }catch(InterruptedException e){
           e.printStackTrace();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }
    }
    //向服务器发送请求
    public String send(String hostname, int port , Invocation invocation){
        if(clientHandler == null){
            start(hostname,port);
        }
        //设置消息类
        clientHandler.setInvocation(invocation);
        try{
            return (String) executorService.submit(clientHandler).get();   //异步获取结果Future
        }catch(InterruptedException e){
            e.printStackTrace();
        }catch(ExecutionException e){
            e.printStackTrace();
        }
        return null;
    }

}
