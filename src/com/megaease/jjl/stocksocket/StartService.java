package com.megaease.jjl.stocksocket;
/**
 * 启动Socket的服务端
 * @author Administrator
 * @date 2020-2-6
 * @version 
 * @description
 */
public class StartService {

	public static void main(String[] args) {
		new SocketServer(1003).work();
	}
}
