package com.megaease.jjl.testpriorityqueue;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
/**
 * 对列遵循先进先出(First-In-First-Out)模式
 * PriorityQueue是基于优先堆的一个无界对列
 * PriorityQueue中的元素可以默认自然排序或通过提供的Comparator在对列实例化的时候排序
 * PriorityQueue不允许空值，不支持non-comparable(不可比较)的对象，且在排序时会按照优先级处理元素
 * PriorityQueue的头是基于自然排序或者Comparator排序的最小元素
 * PriorityQueue的大小不受限制，但在创建时可以指定初始大小。向PriorityQueue增加元素时，大小会自动增加
 * PriorityQueue是非线程安全的，所以Java提供了PrioritBlockingyQueue(实现BlockingQueue接口)用于Java多线程环境
 * @author Administrator
 * @date 2020-2-10
 * @version 
 * @description
 */
public class PriorityQueueExample {
	
	//匿名Comparator实现
	public static Comparator<Customer>idComparator=new Comparator<Customer>() {
		public int compare(Customer c1, Customer c2) {
			return (int)(c1.getId()-c2.getId());
		}
	};
	
	//用于往队列增加数据的通用方法
	private static void addDataToQueue(Queue<Customer>customersprQueue) {
		Random random=new Random();
		for (int i = 0; i < 7; i++) {
			int id=random.nextInt(100);
			customersprQueue.add(new Customer(id, "Pankaj"+id));
		}
	}
	
	//用于从队列中去数据的通用方法
	private static void pollDataFromQueue(Queue<Customer>customersPriQueue) {
		while (true) {
			Customer customer = customersPriQueue.poll();
			if (customer==null)break;
			System.out.println("Processing customer with ID="+customer.getId());
		}
	}
	
	public static void main(String[] args) {
		
		//优先队列自然排序示例
		Queue<Integer>integerPQueue=new PriorityQueue<>(7);
		Random random=new Random();
		for (int i = 0; i <7; i++) {
			integerPQueue.add(new Integer(random.nextInt(100)));
		}
		for (int i = 0; i < 7; i++) {
			Integer integer=integerPQueue.poll();
			System.out.println("Processing Integer:"+integer);
		}
		//优先队列使用实例
		Queue<Customer>customerPrQueue=new PriorityQueue<>(7,idComparator);
		addDataToQueue(customerPrQueue);
		//实现Comparator，最小的元素在对列的头部因而最先被取出
		//如不实现Comparator，在建立customerPrQueue时会抛出ClassCastExcetion
		pollDataFromQueue(customerPrQueue);
	}
	
}
