package com.megaease.jjl.stocksocket;
/**
 * 启动Socket的客户端
 * @author Administrator
 * @date 2020-2-6
 * @version 
 * @description
 */
public class StartClient {
	public static void main(String[] args) {
		Client client = new Client("localhost", 1003);
		
		client.request();
	}

}

