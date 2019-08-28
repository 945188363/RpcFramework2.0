package com.njh.rpc.RpcServer.Server.Protocol.Http;

import com.njh.rpc.RpcServer.Server.Framework.Invocation;
import com.njh.rpc.RpcServer.Server.Framework.Protocol;
import com.njh.rpc.RpcServer.Server.Framework.URL;

/**
 * ClassName: DubboProtocol
 * Description: TODO
 * Author: njh
 * Time: ,11:14
 * Version: V1.0
 **/
public class HttpProtocol implements Protocol{
    @Override
    public void start(URL url){
        new HttpServer().start(url.getHostname(),url.getPort());
    }

    @Override
    public String send(URL url, Invocation invocation){
        return new HttpClient().send(url.getHostname(),url.getPort(),invocation);
    }
}
