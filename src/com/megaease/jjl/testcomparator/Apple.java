package com.megaease.jjl.testcomparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Apple implements Comparable<Apple>{

	//编号
	private int id;
	//价格
	private double price;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Apple(int id,double price){
		this.id=id;
		this.price=price;
	}
	
	@Override
	public int compareTo(Apple o) {
		//return
		Double.compare(this.getPrice(), o.getPrice());
		if (Math.abs(this.price-o.price)<0.01) {
			return 0;
		}else {
			return (o.price-this.price)>0?-1:1;
		}
	}
	
	@Override
	public String toString() {
		
		return "Apple{id="+id+",price="+price+"}";
	}
	
	
	//测试Comparator 和 Comparable
	public static void main(String[] args) {
		Apple apple1 = new Apple(1, 4.8);
		Apple apple2 = new Apple(2, 5.9);
		Apple apple3 = new Apple(3, 8.5);
		List<Apple> list = new ArrayList<Apple>();
		list.add(apple1);
		list.add(apple3);
		list.add(apple2);
		System.out.println("Comparable=====");
		System.out.printf("This list of apples:%s\n",list);
		Collections.sort(list);
		System.out.printf("This list of apples:%s\n",list);
		System.out.println("Comparator=====");
		System.out.printf("this list of apples: %s\n",list);
        Collections.sort(list,new DESComparator());
        System.out.printf("this list of apples: %s\n",list);
        Collections.sort(list,new AESComparator());
        System.out.printf("this list of apples: %s\n",list);
		
		
		
		
	}
}
//升序
	class AESComparator implements Comparator<Apple>{

		public int compare(Apple o1, Apple o2) {
	        if (Math.abs(o1.getPrice()-o2.getPrice())<0.001)
	            return 0;
	        else{
	            return (o1.getPrice()-o2.getPrice())>0?1:-1;
	        }
	    }
		
	}
	//降序
	class DESComparator implements Comparator<Apple>{
		@Override
		public int compare(Apple o1, Apple o2) {
			if (Math.abs(o1.getPrice()-o2.getPrice())<0.001)
	            return 0;
	        else{
	            return (o1.getPrice()-o2.getPrice())>0?-1:1;
	        }
		}
	}
