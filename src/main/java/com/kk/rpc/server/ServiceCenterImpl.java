package com.kk.rpc.server;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//服务中心具体事项
public class ServiceCenterImpl implements ServiceCenter {

    //服务端的所有接口可供客户端访问的接口,都注册到map中
    //key:接口名,value:接口具体实现
    private static HashMap<String,Class> serviceRegisterMap =new HashMap<>();
    private static Integer port;
    //连接池
    private static ExecutorService executor= Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    private static boolean isRuning;

    public ServiceCenterImpl(Integer port) {
        this.port=port;
    }

    private static class ServerTask implements Runnable{

        private Socket socket;

        public ServerTask() {
        }

        public ServerTask(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            ObjectInputStream in=null;
            ObjectOutput out=null;
            try {

                //接收客户端连接以及请求,处理该请求
                in=new ObjectInputStream(socket.getInputStream());
                //因为ObjectInputStream对发送数据的顺序严格要求,因此需要参照发送的顺序逐个接收
                String serviceName=in.readUTF();
                String methodName=in.readUTF();
                Class[] parameterTypes=(Class[])in.readObject();
                Object[] args=(Object[]) in.readObject();
                //根据客户端请求,到map中找到与之对应的具体接口
                Class serverClass = serviceRegisterMap.get(serviceName);
                Method method = serverClass.getMethod(methodName, parameterTypes);
                Object result = method.invoke(serverClass.newInstance(), args);

                //向客户端将执行完毕的返回值传给客户端
                out=new ObjectOutputStream(socket.getOutputStream());
                out.writeObject(result);
            } catch (Exception e) {
                e.printStackTrace();
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
    }

    //开启服务端服务
    @Override
    public void start(){

        ServerSocket server= null;
        try {
            server = server=new ServerSocket();
            server.bind(new InetSocketAddress(port));
        } catch (IOException e) {
            e.printStackTrace();
        }

        isRuning=true; //服务已经开启

        while (true){

            System.out.println("start server...");

            Socket socket=null;
            //客户端每次请求一次连接(发一次请求),则从服务端从连接池中获取一个线程对象去处理
            try {
               socket = server.accept();//等待客户端连接
            } catch (IOException e) {
                e.printStackTrace();
            }
            executor.execute(new ServerTask(socket));
        }
    }

    @Override
    public void stop() {
        isRuning=false;
        executor.shutdown();
    }

    @Override
    public void register(Class serverName, Class serviceImpl) {
        serviceRegisterMap.put(serverName.getName(),serviceImpl);
    }
}
