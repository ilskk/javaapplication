package com.kk.rpc;

import com.kk.rpc.server.HelloService;
import com.kk.rpc.client.Client;

import java.net.InetSocketAddress;

public class ClientTest {

    public static void main(String[] args)throws Exception{
        HelloService service=
            Client.getRemoteProxyObject(Class.forName("com.kk.rpc.server.HelloService"),
                    new InetSocketAddress("127.0.0.1",9999));
        System.out.println( service.sayHi("什么鬼"));
    }
}
