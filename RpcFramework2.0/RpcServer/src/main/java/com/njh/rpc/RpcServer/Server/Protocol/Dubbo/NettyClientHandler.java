package com.njh.rpc.RpcServer.Server.Protocol.Dubbo;

import com.njh.rpc.RpcServer.Server.Framework.Invocation;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.Callable;

public class NettyClientHandler  extends ChannelInboundHandlerAdapter implements Callable{ //ChannelInboundHandlerAdapter不会在channelRead结束时释放资源
    private ChannelHandlerContext context;
    private String result;
    private Invocation invocation;

    public Invocation getInvocation(){
        return invocation;
    }

    public void setInvocation(Invocation invocation){
        this.invocation = invocation;
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception{
        context = ctx;
    }

    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object msg) throws Exception {
        //do something with msg, for example
        result = (String) msg;
    }

    @Override
    public Object call() throws Exception{
        //写回invocation类
        context.writeAndFlush(invocation);
        //返回结果
        return result;
    }
}

