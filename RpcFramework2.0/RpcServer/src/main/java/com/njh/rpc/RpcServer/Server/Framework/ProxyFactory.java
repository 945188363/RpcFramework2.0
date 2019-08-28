package com.njh.rpc.RpcServer.Server.Framework;

import com.njh.rpc.RpcServer.Server.Registry.RemoteRegister;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyFactory {
    //代理类工厂
    public static <T> T getProxy(final Class interfaceClass/*,final String protocol*/){
        return  (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class<?>[] {interfaceClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] arguments) throws Throwable {
                String result = null;
                //实现可用性封装
                Invocation invocation = new Invocation(interfaceClass.getName(),method.getName(),method.getParameterTypes(),arguments);
                URL url = RemoteRegister.random(interfaceClass.getName());
                //使用工厂模式重构
                Protocol protocol = ProtocolFactory.getProtocol();
                result = protocol.send(url,invocation);
//                if(protocol.equals("http")){
//                    HttpClient httpClient = new HttpClient();
//                    result = httpClient.send(url.getHostname(),url.getPort(),invocation);
//                }else if(protocol.equals("dubbo")){
//                    NettyClient nettyClient = new NettyClient();
//                    result = nettyClient.send(url.getHostname(),url.getPort(),invocation);
//                }
                return result;
            }
        });
    }
}
