package com.kk.rpc.server;

public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHi(String name) {
        return "hi..."+name;
    }
}
