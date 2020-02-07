package com.megaease.jjl.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	String ip;
	int serverport;
	Socket client;
	public Client(String ip,int serverport) {
		this.ip=ip;
		this.serverport=serverport;
		try {
			this.client=new Socket(ip,serverport);
		} catch (UnknownHostException e) {
			System.out.println("客户端异常："+e.getMessage());
		} catch (IOException e) {
			System.out.println("客户端异常："+e.getMessage());
		}
	}
	
	class MyClient implements Runnable{

		
		@Override
		public void run() {
			int count=1;
			
			while (true) {
				
					if (count>10) {
						break;
					}
				Socket socket=null;
				try {
					socket=new Socket(ip,serverport);
					//向服务端发送数据
					PrintStream out=new PrintStream(socket.getOutputStream());
					String data=Thread.currentThread().getName()+",发的第"+count+"次";
					out.println(data);
					//读取服务器端的数据
					BufferedReader reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
					String result=reader.readLine();
					System.out.println("客户端："+result);
					reader.close();
					out.close();
				} catch (Exception e) {
					System.out.println("客户端异常："+e.getMessage());
				}finally{
					if (socket!=null) {
						try {
							socket.close();
						} catch (IOException e) {
							System.out.println("客户端finally异常："+e.getMessage());
						}
					}
				}
				count++;
				
			}
			
		}
		
	}
	
}
