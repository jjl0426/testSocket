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
	//交易限度
	private int size=5;
	//卖家
	//private Map<String, Stock> sellStock=new HashMap<String, Stock>();
	private Queue sellQueue=new Queue(size);
	//买家
	//private Map<String, Stock> buyStock=new HashMap<String, Stock>();
	private Queue buyQueue=new Queue(size);
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
				//String stockName=clientInputStr.trim().split(":")[1];
				//Integer stockNumber=Integer.parseInt(clientInputStr.trim().split(":")[2]);
				//股价
				Integer stockPrice=Integer.parseInt(clientInputStr.trim().split(":")[1]);
				if (clientInputStr.trim().indexOf("Buy")!=-1) {					
					//System.out.println("买入["+stockName+"]股，数量："+stockNumber+",股价："+stockPrice+"元");
					System.out.println("买家所出股价："+stockPrice+"元");
					/*if (buyStock.containsKey(stockName.trim())) {
						Integer oldNumber=buyStock.get(stockName.trim()).getStockNumber();
						buyStock.get(stockNumber).setStockNumber(oldNumber+stockNumber);
					}else {
						buyStock.put(stockName, new Stock(stockNumber, stockPrice));
					}*/
					buyQueue.add(stockPrice);
					//升序
					buyQueue.bigSort();
					System.out.println("买"+buyQueue.toString());
				}else if (clientInputStr.trim().indexOf("Sell")!=-1) {
					//System.out.println("卖出["+stockName+"]股，数量："+stockNumber+",股价："+stockPrice+"元");
					System.out.println("卖家所出股价："+stockPrice+"元");
					/*if (sellStock.containsKey(stockName.trim())) {
						Integer oldNumber=sellStock.get(stockName.trim()).getStockNumber();
						sellStock.get(stockNumber).setStockNumber(oldNumber+stockNumber);
						
					}else {
						sellStock.put(stockName, new Stock(stockNumber, stockPrice));
					}*/
					sellQueue.add(stockPrice);
					//降序
					sellQueue.smallSort();
				}else {
					valid=false;
					System.out.println("命令格式有误！");
				}
				//向客户端回复数据
				out=new PrintStream(socket.getOutputStream());
				if (!valid) {
					out.println("Failed,命令有误,交易失败");
					return;
				}
				if (sellQueue.isEmpty() || buyQueue.isEmpty() || clientInputStr.trim().indexOf("Sell")!=-1) {
					out.println("等待卖家或买家进行交易");
					return;
				}
				
				if (sellQueue.isFull() || buyQueue.isFull()) {
					out.println("超过交易限度");
					return;
				}
				int[] buy=buyQueue.getQueueArray();
				int[] sell=sellQueue.getQueueArray();
				System.out.println(sellQueue.toString());
				for (int i = 0; i <buy.length; i++) {
					for (int j = 0; j <sell.length; j++) {
						if (buy[i]>=sell[j]) {
							
							out.println("Success,交易成功,买家以"+sell[j]+"元，买入股票");
							buyQueue.remove();
							sellQueue.remove();
							break;
						}
						if (i==buy.length-1 && j==sell.length-1) {
							out.println("等待卖家或买家进行交易");
							break;
						}
					}
				}
				
				
				
				/*//成交的情况，即是买家，库存充足，价格合理
				Integer oldNumber=sellStock.get(stockName).getStockNumber();
				sellStock.get(stockName).setStockNumber(oldNumber-stockNumber);
				
				out.println("Success,成交,成交股票为："+stockName+",买入数量为："+stockNumber+",成交额为："+(sellStock.get(stockName).getStockPrice()*stockNumber));
				//成交之后，从买家对列中移除买家信息
				buyStock.remove(stockName);
				if (sellStock.get(stockName).getStockNumber()==0) {
					//当股票卖出完毕，则从卖家对列中移除
					sellStock.remove(stockName);
				}*/
				
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
