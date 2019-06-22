package com.kk.socket;

import com.kk.socket.client.MyClient;
import com.kk.socket.server.MyServer;

class Server {

    public static void main(String[] args)throws Exception{
//        MyServer.response();
//        MyServer.sendFile();
        MyServer.thread();
    }

}
class Client{

    public static void main(String[] args)throws Exception{
//        MyClient.request();
        MyClient.receiveFile();
    }
}