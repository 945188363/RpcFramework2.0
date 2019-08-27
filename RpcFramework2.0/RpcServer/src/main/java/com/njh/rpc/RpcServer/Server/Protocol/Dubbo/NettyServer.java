package com.njh.rpc.RpcServer.Server.Protocol.Dubbo;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

public class NettyServer {
    private int port;
    private String ip;

    public NettyServer(String ip, int port){
        this.ip=ip;
        this.port=port;
        start();
    }

    public void start(){
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
                                    .addLast("inboundDecoder",new HttpRequestDecoder())
                                    .addLast("outboundEncoder",new HttpResponseEncoder())
                                    .addLast("inboundAggregator",new HttpObjectAggregator(50*1024*1024))
                                    .addLast("inboundHandler",new NettyServerHandler());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG,1024)
                    .childOption(ChannelOption.SO_KEEPALIVE,true);

            ChannelFuture future = serverBootstrap.bind(ip,port).sync();
            System.out.println(("Server started in ip: " + ip + "  port: " + port + "."));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            worker.shutdownGracefully();
            boss.shutdownGracefully();
        }

    }

    public static void main(String[] args) {
        new NettyServer("localhost", 6688);
    }
}
