package com.kk.rpc.server.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Client {

    /**
     * 获取代表服务端接口的动态代理对象
     * @param serviceInterface 请求的接口
     * @param address 待请求服务端的ip:端口
     * @param <T> 动态代理对象
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getRemoteProxyObject(Class serviceInterface, InetSocketAddress address){

       /*
       Proxy.newProxyInstance(a,b,c)
            a:类加载器,需要代理哪个类,就需要将该类的类加载器传入
            b:需要代理对象,具备哪些方法-接口(单继承,多实现)
            c:
        */
        return (T)Proxy.newProxyInstance(serviceInterface.getClassLoader(),
                new Class[]{serviceInterface}, new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args){
                    //proxy:代理对象,method:哪个方法,args:参数列表

                        ObjectInputStream in=null;
                        ObjectOutput out=null;

                        //客户端向服务端发送请求,请求某一个具体的接口
                        Socket client=null;
                        try {
                            client=new Socket();
                            client.connect(address);

                            out=new ObjectOutputStream(client.getOutputStream()); //发送序列化对象(对象流)
                            //接口名,方法名:writeUTF()
                            out.writeUTF(serviceInterface.getName());
                            out.writeUTF(method.getName());
                            //方法参数的类型,方法参数:writeObject()
                            out.writeObject(method.getParameterTypes());
                            out.writeObject(args);

                            //等待服务端处理...
                            //接收服务端处理后的返回值
                            in=new ObjectInputStream(client.getInputStream());
                            return in.readObject(); //客户端-服务端->返回值
                        } catch (Exception e) {
                            e.printStackTrace();
                            return null;
                        } finally {
                            try {
                                if(out!=null){
                                    out.close();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            try {
                                if(in!=null){
                                    in.close();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }
}
