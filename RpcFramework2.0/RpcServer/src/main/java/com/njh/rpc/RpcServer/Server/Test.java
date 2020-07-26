package com.njh.rpc.RpcServer.Server;

import java.util.HashMap;

/**
 * @ClassName:Test
 * @Author:njh
 * @Description:TODO
 */
public class Test {
    public static void main(String[] args) {
        JavaBean jb = new JavaBean();
        HashMap<String,String> params = new HashMap<>();
        params.put("a","1");
        params.put("b","2");

        System.out.println(UrlEncode("www.baidu.com/",params));

    }
    public static String UrlEncode(String url , HashMap<String,String> params){

        StringBuffer sb = new StringBuffer(url + "?");

        params.forEach((k,v)-> sb.append(k+"="+v+"&"));

        return sb.substring(0,sb.length()-1);
    }



}
