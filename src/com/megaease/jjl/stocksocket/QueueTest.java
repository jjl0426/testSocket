package com.megaease.jjl.stocksocket;

import org.junit.Assert;
import org.junit.Test;


public class QueueTest {
	@Test
	public void name() {
		Queue queue = new Queue(5);
		queue.add(10);
		queue.add(30);
		queue.add(40);
		queue.add(20);
		queue.add(10);
		int[] queues=queue.bigSort();
		for (int i = 1; i < queues.length; i++) {
			Assert.assertTrue(queues[i-1]<=queues[i]);
		}
	}
	public static void main(String[] args) {
		Queue queue = new Queue(5);
		queue.add(10);
		queue.add(30);
		queue.add(40);
		queue.add(20);
		queue.add(10);
		queue.bigSort();
		
		System.out.println(queue.toString());
		queue.smallSort();
		System.out.println(queue.toString());
		
	}
}
