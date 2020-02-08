package com.megaease.jjl.stocksocket;

import java.util.Arrays;

/**
 * 队列类
 * @author Administrator
 * @date 2020-2-8
 * @version 
 * @description
 */
public class Queue {
	//队列默认大小
	private int DEFAULT_SIZE=10;
	//队列最大容量
	private int maxsize;
	//队列数组
	private int[]queueArray;
	//队列起始元素
	private int start;
	//队列结束元素
	private int end;
	//队列总元素个数
	private int items;
	
	public Queue(int size){
		this.maxsize=size;
		this.queueArray=new int[size];
		this.start=0;
		this.end=-1;
		this.items=0;
	}
	
	/**
	 * 获取队列数组
	 * @return
	 */
	public int[] getQueueArray() {
		return queueArray;
	}
	
	/**
	 * 向队列中插入元素
	 * @param x
	 * @return
	 */
	public boolean add(int x) {
		if(this.isFull()){
			return false;
		}
		end=(end+1)%maxsize;
		queueArray[end]=x;
		items++;
		return true;
	}

	/**
	 * 移除队列中的第一个元素
	 * @return
	 */
	public int remove() {
		if (this.isEmpty()) {
			return -1;
		}
		int temp=queueArray[start];
		start=(start+1)%maxsize;
		items--;
		return temp;
	}
	
	/**
	 * 判断队列是否为空,空则返回true
	 * @return
	 */
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return items==0;
	}

	/**
	 * 判断队列是否已达储存上限,达到上限则返回true
	 * @return
	 */
	public boolean isFull() {
		// TODO Auto-generated method stub
		return items==maxsize;
	}
	
	/**
	 * 由小到大排序，升序
	 * @return
	 */
	public int[] bigSort() {
		Arrays.sort(this.getQueueArray());
		return this.getQueueArray();
	}
	
	/**
	 * 由大到小，降序
	 */
	public int[] smallSort() {
		int[]temp=this.getQueueArray();
		for (int i = 0; i < temp.length-1; i++) {
			for (int j = 0; j < temp.length-1-i; j++) {
				if (temp[j]<temp[j+1]) {
					int middle=temp[j];
					temp[j]=temp[j+1];
					temp[j+1]=middle;
				}
			}
		}
		return temp;
	}
	
	
	public String toString() {
		StringBuilder sb=new StringBuilder();
		sb.append("[");
		for (int i = start;;i= ++i % maxsize) {
			sb.append(queueArray[i]).append(",");
			if (i==end) {
				break;
			}
		}
		sb.replace(sb.length()-1,sb.length(),"]");
		return sb.toString();
	}
	
	
	
}
