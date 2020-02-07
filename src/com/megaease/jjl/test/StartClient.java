package com.megaease.jjl.test;

import com.megaease.jjl.test.Client.MyClient;

public class StartClient {
	public static void main(String[] args) {
		Client client = new Client("localhost", 10000);
		MyClient myClient= client.new MyClient();
		for (int i = 0; i < 5; i++) {
			new Thread(myClient).start();
		}
	}
}
