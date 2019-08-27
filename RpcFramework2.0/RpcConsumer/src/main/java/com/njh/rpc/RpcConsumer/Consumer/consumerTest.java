package com.njh.rpc.RpcConsumer.Consumer;

import com.njh.rpc.RpcApi.API.HelloWorld;
import com.njh.rpc.RpcServer.Server.Framework.Invocation;
import com.njh.rpc.RpcServer.Server.Framework.ProxyFactory;
import com.njh.rpc.RpcServer.Server.Protocol.Http.HttpClient;

/**
 * ClassName: consumerTest
 * Description: TODO
 * Author: njh
 * Time: ,15:22
 * Version: V1.0
 **/
public class consumerTest{
    public static void main(String[] args) throws Exception{
        //http协议调用-代理工厂类
        HelloWorld helloWorld = ProxyFactory.getProxy(HelloWorld.class);

        System.out.println(helloWorld.sayHi("njh"));

    }
}
