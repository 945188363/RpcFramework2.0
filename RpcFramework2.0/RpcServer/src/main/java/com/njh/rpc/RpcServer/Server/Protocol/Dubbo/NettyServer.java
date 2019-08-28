package com.njh.rpc.RpcServer.Server.Protocol.Dubbo;


import com.njh.rpc.RpcServer.Server.Framework.URL;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

public class NettyServer {
    public void start(URL url){
        int process = Runtime.getRuntime().availableProcessors();
        EventLoopGroup worker = new NioEventLoopGroup();
        EventLoopGroup boss = new NioEventLoopGroup(process);
        try {
            ServerBootstrap serverBootstrap= new ServerBootstrap();
            serverBootstrap.group(worker,boss)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline()
                                    .addLast("decoder",new ObjectDecoder(ClassResolvers
                                            .weakCachingConcurrentResolver(this.getClass()
                                                    .getClassLoader())))
                                    .addLast("encoder",new ObjectEncoder())
                                    .addLast("inboundAggregator",new HttpObjectAggregator(50*1024*1024))
                                    .addLast("handler",new NettyServerHandler());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG,1024)
                    .childOption(ChannelOption.SO_KEEPALIVE,true);
            //绑定ip,端口
            ChannelFuture future = serverBootstrap.bind(url.getHostname(),url.getPort()).sync();
            //启动
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //释放资源
            worker.shutdownGracefully();
            boss.shutdownGracefully();
        }

    }

}
