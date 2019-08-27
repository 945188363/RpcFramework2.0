package com.njh.rpc.RpcServer.Server.Protocol.Http;

import org.apache.catalina.*;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.core.StandardEngine;
import org.apache.catalina.core.StandardHost;
import org.apache.catalina.startup.Tomcat;

public class HttpServer {
    public void start(String hostname,int port){
        Tomcat tomcat = new Tomcat();
        //创建一个Tomcat服务器，具体层级架构可百度
        Server server = tomcat.getServer();
        Service service = server.findService("Tomcat");

        Connector connector = new Connector();
        connector.setPort(port);

        Engine engine = new StandardEngine();
        engine.setDefaultHost(hostname);

        Host host = new StandardHost();
        host.setName(hostname);

        String contextPath = "";
        Context context = new StandardContext();
        context.setPath(contextPath);
        context.addLifecycleListener(new Tomcat.DefaultWebXmlListener());

        host.addChild(context);
        engine.addChild(host);

        service.setContainer(engine);
        service.addConnector(connector);
        //servlet过滤路由
        tomcat.addServlet(contextPath,"dispatcher",new DispatcherServlet());
        context.addServletMappingDecoded("/*","dispatcher");

        try{
            tomcat.start();
            tomcat.getServer().await();
        }catch (LifecycleException e){
            e.printStackTrace();
        }
    }
}
