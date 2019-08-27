package com.njh.rpc.RpcServer.Server.Protocol.Http;

import com.njh.rpc.RpcServer.Server.Framework.Invocation;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpClient {
    public String send(String hostname, int port, Invocation invocation){
        try{
            //net的URL
            URL url = new URL("http",hostname,port,"/");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            //设置请求方法为POST
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            //序列化写入相关的invocation信息类
            OutputStream outputStream = httpURLConnection.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(outputStream);

            oos.writeObject(invocation);
            oos.flush();
            oos.close();

            //反序列化接收
            InputStream inputStream = httpURLConnection.getInputStream();
            String result = IOUtils.toString(inputStream);

            return result;
        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}
