package com.kk.socket.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServer {

	private static ServerSocket server=null;
	private static int length=1024*1024*10;

	static void success(){System.out.println("客户端连接成功!");}

	static {
		try {
			//绑定服务的端口,ip:为本机id
			//暴露一个服务,服务地址,该服务的地址:本机ip:6666
			server=new ServerSocket(6666);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void response()throws Exception{
		Socket client= server.accept();
		success();
		//服务端向客户端发送消息(通过流)
		OutputStream out=client.getOutputStream();
		out.write("hello...".getBytes());

		//接收客户端消息
		InputStream in=client.getInputStream();
		byte[] buf=new byte[length];
		int len=in.read(buf);
		System.out.println("Server接收Client的回馈消息:"+new String(buf,0,len));

		in.close();
		out.close();
		client.close();
		server.close();
	}

	public static void sendFile()throws Exception {
		Socket client=server.accept();
		success();
		//发送文件
		File file=new File("src\\main\\resources\\socket\\result.txt");
		OutputStream output=client.getOutputStream();
		//把文件从硬盘读入到内存
		FileInputStream fis=new FileInputStream(file);
		byte[] bytes=new byte[length];
		int lne=fis.read(bytes);
		output.write(bytes,0,lne);
		System.out.println("Server已发送文件...");

		fis.close();
		output.close();
		client.close();
		server.close();
	}

	public static void thread()throws Exception{
		while (true){
			Socket client=server.accept();
			new Thread(new T(client)).start();
		}
	}

	private static class T implements Runnable{
		private Socket client;

		public T(Socket client) {
			this.client = client;
		}

		@Override
		public void run() {
			OutputStream output= null;
			FileInputStream fis= null;
			try {
				success();
				File file=new File("src\\main\\resources\\socket\\result.txt");
				output = client.getOutputStream();
				fis = new FileInputStream(file);
				byte[] bytes=new byte[length];
				int lne=fis.read(bytes);
				output.write(bytes,0,lne);
				System.out.println("Server已发送文件...");
				fis.close();
				output.close();
				client.close();
//				server.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}
	
}
