package com.njh.rpc.RpcServer.Server.Registry;

import com.njh.rpc.RpcServer.Server.Framework.URL;

import java.io.*;
import java.util.*;

public class RemoteRegister {

    private static Map<String, List<URL>> map = new HashMap<>();

    //注册
    public static void register(String interfaceName, URL url){
        //本地非ZooKeeper等注册中心会报List为空的错
        //需要吧List持久化到本地
        List<URL> list = Collections.singletonList(url);
        map.put(interfaceName,list);

        saveFile();
    }

    //负载均衡--随机
    public static URL random(String interfaceName){
        map = getFile();

        List<URL> list = map.get(interfaceName);
        Random random = new Random();
        int n = random.nextInt(list.size());

        return list.get(n);
    }
    //持久化注册中心到本地
    private static void saveFile(){
        try {
            FileOutputStream fos = new FileOutputStream("/temp.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(map);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private static Map<String,List<URL>> getFile(){
        try {
            FileInputStream fis = new FileInputStream("/temp.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            return (Map<String,List<URL>>) ois.readObject();
        }catch (IOException e){
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }
}
