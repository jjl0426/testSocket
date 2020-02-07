package com.megaease.jjl.testthread;
/**
 * 创建一个实现Runnable接口的类
 * 多线程累计计数到1000
 * @author Administrator
 * @date 2020-1-16
 * @version 
 * @description
 */
public class TestThread{
	//公共变量
	int count=0;
	public static void main(String[] args) {
		//创建一个实现Runable的类
		TestThread testThread= new TestThread();
		//创建一个任务
		MyRunnable myRunnable1 = testThread.new MyRunnable();
		//MyRunnable myRunnable2 = testThread.new MyRunnable();
		//MyRunnable myRunnable3 = testThread.new MyRunnable();
		//MyRunnable myRunnable4 = testThread.new MyRunnable();
		//MyRunnable myRunnable5 = testThread.new MyRunnable();
		//创建5个线程
		for (int i = 0; i < 5; i++) {
			new Thread(myRunnable1).start();
		}
		//new Thread(myRunnable1).start();
		//new Thread(myRunnable2).start();
		//new Thread(myRunnable3).start();
		//new Thread(myRunnable4).start();
		//new Thread(myRunnable5).start();
		
	}




class MyRunnable implements Runnable{

	@Override
	public void run() {
		while (true) {
			//锁住的是同一对象
			synchronized (this) {
				if (count>=1000) {
					break;
				}
				System.out.println(Thread.currentThread().getName()+":count:"+(++count));
				//测试时，线程更容易切换
				Thread.yield();
			}
		}
		
	}

}
}