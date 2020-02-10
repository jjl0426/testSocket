package com.megaease.jjl.testcomparator;

import java.util.Arrays;


//Student类创建时实现Comparable接口，覆写compareTo()方法
//成绩按从高到低排序，成绩相等按年龄从小到大排序
public class Student_comparable implements Comparable<Student_comparable>{//指定类型为Student

	private String name;
	private int age;
	private float score;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public float getScore() {
		return score;
	}
	public void setScore(float score) {
		this.score = score;
	}
	public Student_comparable(String name,int age,float score){
		this.age=age;
		this.score=score;
		this.name=name;
	}
	public String toString() {
		return "Student{name:"+name+",age:"+age+",score:"+score+"}";
	}
	
	public int compareTo(Student_comparable stu) {//覆写compareTo()方法，实现排序规则的应用
		if (this.score>stu.score) {
			return -1;
		}else if (this.score<stu.score) {
			return 1;
		}else {
			if (this.age>stu.age) {
				return 1;
			}else if (this.age<stu.age) {
				return -1;
			}else {
				return 0;
			}
		}
	}
	public static void main(String[] args) {
		Student_comparable stu[] = {new Student_comparable("张三",20,90.0f),
	            new Student_comparable("李四",22,90.0f),new Student_comparable("王五",20,99.0f),
	            new Student_comparable("赵六",20,70.0f),new Student_comparable("孙七",22,100.0f)} ;
		Arrays.sort(stu);
		for (int i = 0; i < stu.length; i++) {
			System.out.println(stu[i]);
		}
	}
}
