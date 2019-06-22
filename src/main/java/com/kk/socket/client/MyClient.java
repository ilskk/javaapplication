package com.kk.socket.client;

import com.kk.socket.server.MyServer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class MyClient {

	private static Socket client=null;
	private static int length=1024*1024*10;

	static Socket connection()throws Exception{
		return new Socket("127.0.0.1",6666);
	}

	public static void request()throws Exception {
		//客户端连接服务端发布的服务
		client=connection();
		
		//接收服务端发送的消息(通过流)
		InputStream in=client.getInputStream();
		byte[] buf=new byte[length];
		int lne=in.read(buf);
		System.out.println("Client接收Server的消息:"+new String(buf,0,lne));

//		向服务端回馈消息
		OutputStream out=client.getOutputStream();
		out.write("world!!!".getBytes());

		in.close();
		out.close();
		client.close();
	}

	public static void receiveFile()throws Exception{
		client=connection();

		//接收服务端发送的文件并保存到硬盘中
		InputStream in=client.getInputStream();
		byte[] buf=new byte[length];
		int len=in.read(buf);
		File file=new File("src\\main\\resources\\socket\\被复制的文件-"+(int)(Math.random()*100));
		FileOutputStream fos=new FileOutputStream(file);
		fos.write(buf,0,len);
		System.out.println("已接收并保存文件...");

		fos.close();
		in.close();
		client.close();
	}

}
