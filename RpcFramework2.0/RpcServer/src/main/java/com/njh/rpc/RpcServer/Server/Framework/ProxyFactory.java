package com.njh.rpc.RpcServer.Server.Framework;

import com.njh.rpc.RpcServer.Server.Protocol.Http.HttpClient;
import com.njh.rpc.RpcServer.Server.Registry.RemoteRegister;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyFactory {
    //代理类工厂
    public static <T> T getProxy(final Class interfaceClass){
        return  (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class<?>[] {interfaceClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] arguments) throws Throwable {
                HttpClient httpClient = new HttpClient();
                //实现可用性封装
                Invocation invocation = new Invocation(interfaceClass.getName(),method.getName(),method.getParameterTypes(),arguments);
                URL url = RemoteRegister.random(interfaceClass.getName());

                String result = httpClient.send(url.getHostname(),url.getPort(),invocation);

                return result;
            }
        });
    }
}
