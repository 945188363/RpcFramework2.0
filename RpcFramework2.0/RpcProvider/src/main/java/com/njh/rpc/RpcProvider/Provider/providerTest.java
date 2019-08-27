package com.njh.rpc.RpcProvider.Provider;

import com.njh.rpc.RpcApi.API.HelloWorld;
import com.njh.rpc.RpcProvider.Provider.Impl.HelloWorldImpl;
import com.njh.rpc.RpcServer.Server.Framework.URL;
import com.njh.rpc.RpcServer.Server.Protocol.Http.HttpServer;
import com.njh.rpc.RpcServer.Server.Registry.LocalRegister;
import com.njh.rpc.RpcServer.Server.Registry.RemoteRegister;

/**
 * ClassName: providerTest
 * Description: TODO
 * Author: njh
 * Time: ,15:27
 * Version: V1.0
 **/
public class providerTest{
    public static void main(String[] args) throws Exception{
        //1.本地注册{服务名，实现类}
        LocalRegister.register(HelloWorld.class.getName(),HelloWorldImpl.class);

        //2.远程注册{服务名，List<URL>}
        URL url = new URL("localhost",8080);
        RemoteRegister.register(HelloWorld.class.getName(),url);

        //3.开启Tomcat的http服务器
        HttpServer httpServer = new HttpServer();
        httpServer.start("localhost",8080);

    }
}
