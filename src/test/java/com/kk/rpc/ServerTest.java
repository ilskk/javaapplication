package com.kk.rpc;

import com.kk.rpc.server.HelloService;
import com.kk.rpc.server.HelloServiceImpl;
import com.kk.rpc.server.ServiceCenter;
import com.kk.rpc.server.ServiceCenterImpl;

/*
RPC:远程过程调用
    角色1:客户端-反射
    角色2:发布服务的接口-socket-动态代理
    角色3:服务的注册中心

    调用流程:
        1,客户端通过socket请求客户端,并通过字符串(或Class)形式将需要请求的接口发送给服务端(动态代理发送类的结构);
        2,服务端将可供接口注册到服务中心(通过map保存,key:接口,value:接口实现类);
        3,服务端接收到客户端请求后,通过请求的接口名在服务中心的map中寻找对应的接口实现类;
        找到之后?
            解析刚才客户端发送来的类结构,解析完成后,通过反射技术将该方法执行,
            执行完毕后再将该方法的返回值返回给客户端;

 */
public class ServerTest {

    public static void main(String[] args){
        //开启一个线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                //服务中心
                ServiceCenter service=new ServiceCenterImpl(9999);
                //将接口和实现类注册到服务中心
                service.register(HelloService.class, HelloServiceImpl.class);
                service.start();
            }
        }).start();

    }
}
