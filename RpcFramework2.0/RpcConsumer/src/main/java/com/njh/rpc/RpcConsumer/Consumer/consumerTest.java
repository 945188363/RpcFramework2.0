package com.njh.rpc.RpcConsumer.Consumer;

import com.njh.rpc.RpcApi.API.HelloWorld;
import com.njh.rpc.RpcServer.Server.Framework.ProxyFactory;

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
        HelloWorld helloWorld = ProxyFactory.getProxy(HelloWorld.class,"http");//httpp协议
        //HelloWorld helloWorld1 = ProxyFactory.getProxy(HelloWorld.class,"dubbo");//dubbo协议

        System.out.println(helloWorld.sayHi("njh"));

    }
}
