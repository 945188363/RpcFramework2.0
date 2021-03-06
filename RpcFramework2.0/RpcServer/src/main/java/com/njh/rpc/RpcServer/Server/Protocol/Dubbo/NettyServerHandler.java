package com.njh.rpc.RpcServer.Server.Protocol.Dubbo;

import com.njh.rpc.RpcServer.Server.Framework.Invocation;
import com.njh.rpc.RpcServer.Server.Registry.LocalRegister;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.lang.reflect.Method;

public class NettyServerHandler extends ChannelInboundHandlerAdapter{  //ChannelInboundHandlerAdapter不会在channelRead结束时释放资源
    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object msg) throws Exception{
        //do something with msg, for example
        Invocation invocation = (Invocation)msg;
        //创建接口类
        Class serviceImpl = LocalRegister.get(invocation.getInterfaceName());
        //获取方法参数、方法名等
        Method method = serviceImpl.getMethod(invocation.getMethodNmae(),invocation.getParamTypes());
        //执行方法
        Object result = method.invoke(serviceImpl.newInstance(),invocation.getParams());
        //写出到客户端
        System.out.println("Netty------"+result.toString());
        channelHandlerContext.writeAndFlush(result);

    }

}