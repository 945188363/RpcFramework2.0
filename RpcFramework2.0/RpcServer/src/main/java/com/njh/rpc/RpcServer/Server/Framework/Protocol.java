package com.njh.rpc.RpcServer.Server.Framework;
/**
 * @Author njh
 * @Description 协议接口
 * @Date 13:29,
 * @Param
 * @return
 **/
public interface Protocol{
    //抽象出启动服务端
    void start(URL url);
    //抽象客户端发送
    String send(URL url ,Invocation invocation);
}
