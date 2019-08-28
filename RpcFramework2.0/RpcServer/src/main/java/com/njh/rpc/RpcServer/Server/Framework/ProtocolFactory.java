package com.njh.rpc.RpcServer.Server.Framework;

import com.njh.rpc.RpcServer.Server.Protocol.Dubbo.DubboProtocol;
import com.njh.rpc.RpcServer.Server.Protocol.Http.HttpProtocol;

/**
 * ClassName: ProtocolFactory
 * Description: TODO
 * Author: njh
 * Time: ,11:23
 * Version: V1.0
 **/
public class ProtocolFactory{
    public static Protocol getProtocol(){
        //工厂模式默认是http请求
        String name  = System.getProperty("protocolName");
        if(name == null || name.equals("")) name =" http";
        //新增协议可以在这里加
        switch(name){
            case "http":
                return new HttpProtocol();
            case "dubbo":
                return new DubboProtocol();
            default:
                break;
        }
        return new HttpProtocol();
    }
}
