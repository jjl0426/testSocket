package com.megaease.jjl.testcomparator;

import java.util.Arrays;
import java.util.Comparator;

//Student_comparator类原先没有比较器，类完成之后构建一个比较器StudentComparator类
//按年龄从大到小排序
public class Student_comparator {
	private String name;
	private int age;
	public Student_comparator(String name,int age){
		this.name=name;
		this.age=age;
	}
	
	
	public String toString() {
		return "Student_comparator [name=" + name + ", age=" + age + "]";
	}


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


	public boolean equals(Object obj) {//覆写equals
		if (this==obj) {
			return true;
		}
		if (!(obj instanceof Student_comparator)) {
			return false;
		}
		Student_comparator stu=(Student_comparator)obj;
		if (stu.name.equals(this.name)&&stu.age==this.age) {
			return true;
		}else {
			return false;
		}
	}
	
	public static void main(String[] args) {
		Student_comparator stu[] = {new Student_comparator("张三",20),
	            new Student_comparator("李四",22),new Student_comparator("王五",20),
	            new Student_comparator("赵六",20),new Student_comparator("孙七",22)} ;
	        Arrays.sort(stu,new StudentComparator());// 进行排序操作
	        for(int i=0;i<stu.length;i++){// 循环输出数组中的内容
	            System.out.println(stu[i]) ;
	        }
	}
	
}

class StudentComparator implements Comparator<Student_comparator>{//实现比较器

	//因为Object类中本身已经有了equals()方法
	public int compare(Student_comparator s1, Student_comparator s2) {
		if (s1.equals(s2)) {
			return 0;
		}else if (s1.getAge()<s2.getAge()) {//按年龄比较
			return 1;
		}else {
			return -1;
		}
	}
}



