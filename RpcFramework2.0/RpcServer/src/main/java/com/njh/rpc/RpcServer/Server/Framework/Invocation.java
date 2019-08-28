package com.njh.rpc.RpcServer.Server.Framework;

import java.io.Serializable;
/**
 * @Author njh
 * @Description 消息类
 * @Date 13:32,
 * @Param
 * @return
 **/
public class Invocation implements Serializable {
    private String interfaceName;
    private String methodNmae;
    private Class[] paramTypes;
    private Object[] params;


    public Invocation(String interfaceName, String methodNmae, Class[] paramTypes, Object[] params) {
        this.interfaceName = interfaceName;
        this.methodNmae = methodNmae;
        this.paramTypes = paramTypes;
        this.params = params;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getMethodNmae() {
        return methodNmae;
    }

    public void setMethodNmae(String methodNmae) {
        this.methodNmae = methodNmae;
    }

    public Class[] getParamTypes() {
        return paramTypes;
    }

    public void setParamTypes(Class[] paramTypes) {
        this.paramTypes = paramTypes;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }
}
