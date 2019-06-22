package com.kk.rpc.server;

//服务中心
public interface ServiceCenter {

    void start();
    void stop();
    //注册服务
    void register(Class serverName,Class serviceImpl);

}
