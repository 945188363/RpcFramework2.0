package com.njh.rpc.RpcServer.Server.Protocol.Dubbo;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;


public class NettyClient {
    private int port;
    private String ip;
    private SocketChannel socketChannel;

    public NettyClient(int port, String ip) throws InterruptedException {
        this.port = port;
        this.ip = ip;
        start();
    }

    private void start() throws InterruptedException {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap()
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .group(eventLoopGroup)
                .remoteAddress(ip, port)
                .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline()
                                    .addLast("inboundDecoder",new HttpRequestDecoder())
                                    .addLast("outboundEncoder",new HttpResponseEncoder())
                                    .addLast(new NettyClientHandler());
                        }
            });
            ChannelFuture future = bootstrap.bind(ip, port).sync();
            if (future.isSuccess()) {
                socketChannel = (SocketChannel) future.channel();
                System.out.println("------connect server success------");
            }
            future.channel().closeFuture().sync();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new NettyClient(6688, "localhost");
    }

}
