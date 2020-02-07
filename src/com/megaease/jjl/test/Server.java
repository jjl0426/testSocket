package com.megaease.jjl.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public static int number=0;
	private ServerSocket serverSocket;
	int port;
	public Server(int port) {
		this.port=port;
		try {
			this.serverSocket=new ServerSocket(port);
		} catch (Exception e) {
			System.out.println("服务器端异常："+e.getMessage());
		}
	}
	
	public void work() {
		System.out.println("Server...");
		Socket client=null;
		try {
			while (true) {
				
				//从请求对列中获取连接
				client=serverSocket.accept();//阻塞式等待连接
				//处理连接
				new Thread(new response(client)).start();
				
			}
		} catch (Exception e) {
			System.out.println("服务器端异常："+e.getMessage());
		}
	}
	
	private class response implements Runnable{
		private Socket socket;
		public response(Socket client) {
			this.socket=client;
		}
		@Override
		public void run() {
			
			synchronized (this) {
			
			try {
				//读取客户端数据
				BufferedReader reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String client_data=reader.readLine();
				System.out.println(client_data);
				String client_threadname=client_data.split(",")[0];
				number++;
				//向客户端回复数据
				PrintStream out=new PrintStream(socket.getOutputStream());
				System.out.println(client_threadname+",number值："+number);
				out.println(client_threadname+",number值："+number);
				out.close();
				reader.close();
			} catch (Exception e) {
				System.out.println("服务器端异常："+e.getMessage());
			}finally{
				if (socket!=null) {
					try {
						socket.close();
					} catch (IOException e) {
						socket=null;
						System.out.println("服务器端finally异常："+e.getMessage());
					}
				}
			}
			}
		}
	}
	
	
}
