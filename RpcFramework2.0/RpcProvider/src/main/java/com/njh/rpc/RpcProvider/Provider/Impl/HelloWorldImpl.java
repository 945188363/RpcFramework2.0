package com.njh.rpc.RpcProvider.Provider.Impl;

import com.njh.rpc.RpcApi.API.HelloWorld;

/**
 * ClassName: HelloWorldImpl
 * Description: TODO
 * Author: njh
 * Time: ,15:19
 * Version: V1.0
 **/
public class HelloWorldImpl implements HelloWorld{
    @Override
    public String helloWord(){
        return "Hello,World!";
    }

    @Override
    public String sayHi(String name){
        return "Hi," + name;
    }
}
