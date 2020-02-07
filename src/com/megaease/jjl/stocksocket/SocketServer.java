package com.megaease.jjl.stocksocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;


/**
 * 股票服务端
 * @author Administrator
 * @date 2020-2-6
 * @version 
 * @description
 */
public class SocketServer {
	
	//创建serverSocket
	private ServerSocket serverSocket;
	//端口
	int port;
	//带参构造器
	public SocketServer(int port){
		this.port=port;
		try {
			this.serverSocket=new ServerSocket(port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("服务端异常："+e.getMessage());
		}
	}
	
	
	//服务器对客户端的响应
	public void work() {
		System.out.println("SocketServer...");
		Socket client=null;
		try {
			while (true) {
				//从请求对列中获取连接
				client=serverSocket.accept();//阻塞式等待连接
				//处理连接
				new Thread(new response(client)).start();
			}
		} catch (Exception e) {
			System.err.println("服务端异常："+e.getMessage());
		}
	}
	
	/**
	 * Server的响应封装为一个类，方便多线程
	 * @author Administrator
	 * @date 2020-2-6
	 * @version 
	 * @description
	 */
	//卖家
	private Map<String, Integer> sellStock=new HashMap<String, Integer>(); 
	//买家
	private Map<String, Integer> buyStock=new HashMap<String, Integer>();
	private class response implements Runnable{
		
		private Socket socket;
		public response(Socket client) {
			this.socket=client;
		}
		@Override
		public void run() {
			BufferedReader reader=null;
			PrintStream out=null;
			try {
				boolean valid=true;
				//读取客户端数据
				reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String clientInputStr=reader.readLine();
				if (clientInputStr.trim().indexOf("Buy")!=-1) {					
					System.out.println("读取到买家数据是："+clientInputStr);
					buyStock.put(clientInputStr.split(":")[1], Integer.parseInt(clientInputStr.split(":")[2]));
					System.out.println("买入股价："+Integer.parseInt(clientInputStr.split(":")[2]));
				}else if (clientInputStr.trim().indexOf("Sell")!=-1) {
					System.out.println("读取到卖家数据是："+clientInputStr);
					sellStock.put(clientInputStr.split(":")[1], Integer.parseInt(clientInputStr.split(":")[2]));
					System.out.println("卖出股价："+Integer.parseInt(clientInputStr.split(":")[2]));
				}else {
					valid=false;
					System.out.println("命令格式有误！");
				}
				//向客户端回复数据
				out=new PrintStream(socket.getOutputStream());
				if (!valid) {
					out.println("Failed,暂未出现卖家或命令有误,交易失败");
					return;
				}
				if (sellStock.size()==0 || buyStock.size()==0) {
					out.println("Failed,暂未出现买家或者暂未出现卖家,交易失败");
					return;
				}
				if (!sellStock.containsKey(clientInputStr.split(":")[1]) || !buyStock.containsKey(clientInputStr.split(":")[1])) {
					out.println("Failed,暂未出现想要买入的股票,交易失败");
				}
				if (buyStock.get(clientInputStr.split(":")[1])<sellStock.get(clientInputStr.split(":")[1])) {
					out.println("Failed,买卖家所出股价不合理,交易失败");
					return;
				}
				out.println("Success,成交,成交额为："+sellStock.get(clientInputStr.split(":")[1]));
				buyStock.remove(clientInputStr.split(":")[1]);
				sellStock.remove(clientInputStr.split(":")[1]);
				
			} catch (Exception e) {
				System.out.println("服务器run异常："+e.getMessage());
			}finally{
				try {
					reader.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				try {
					out.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				if (socket!=null) {
					try {
						socket.close();
						
					} catch (Exception e) {
						socket=null;
						System.out.println("服务器finally异常"+e.getMessage());
					}
				}
			}
			
		}
		
	}
	
	
}
