package com.njh.rpc.RpcServer.Server.Framework;

public interface Protocol{
    void start(URL url);

    String send(URL url ,Invocation invocation);
}
