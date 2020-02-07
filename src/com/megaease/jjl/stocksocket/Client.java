package com.megaease.jjl.stocksocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * 买股票的客户端
 * @author Administrator
 * @date 2020-2-6
 * @version 
 * @description
 */
public class Client {

//	public static final int port=1001;
//	public static final String host="localhost";
	//IP
	private String ip;
	//端口
	private int serverPort;
	//Socket
	private Socket client;
	private Scanner scanner = new Scanner(System.in);
	public Client(String ip,int serverPort){
		this.ip=ip;
		this.serverPort=serverPort;
		try {
			this.client=new Socket(ip, serverPort);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			System.err.println("客户端异常："+e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("客户端异常："+e.getMessage());
		}
	}
	
	/**
	 * 向服务器端发送请求
	 * @param ip
	 * @param serverPort
	 */
	public void request(){
		System.out.println("Client Start...");
		while (true) {
			Socket socket=null;
			try {
				socket=new Socket(this.ip,this.serverPort);
				//向服务端发送数据
				PrintStream out=new PrintStream(socket.getOutputStream());
				System.out.println("请输入想要成交的股名：");
				String stockName=scanner.next();
				System.out.println("请输入想要成交的股价：");
				int price=scanner.nextInt();
				System.out.println("请输入命令[eg:Buy/Sell]:");
				String data=scanner.next();
				data+=":"+stockName+":"+price;
				//要发送的数据
				System.out.println("客户端发送的数据是："+data);
				out.println(data);
				//读取服务端数据
				BufferedReader reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String serverInputStr=reader.readLine();
				System.out.println("读取到服务端数据是："+serverInputStr);
				reader.close();
				out.close();
				
			} catch (Exception e) {
				System.err.println("客户端异常："+e.getMessage());
			}finally{
				if (socket!=null) {
					try {
						socket.close();
					} catch (IOException e) {
						socket=null;
						System.err.println("客户端finally异常："+e.getMessage());
					}
				}
			}
		}
	}
	
	
	
}
