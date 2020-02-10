package com.megaease.jjl.testcomparator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class OrderBean implements Comparable<OrderBean>{
	
	//编号
	private int id;
	//创建时间
	private String cdate;
	//产品名称
	private String product;
	//重量
	private int weight;
	//价格
	private long price;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCdate() {
		return cdate;
	}
	public void setCdate(String cdate) {
		this.cdate = cdate;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public long getPrice() {
		return price;
	}
	public void setPrice(long price) {
		this.price = price;
	}
	public OrderBean(int id, String cdate, String product, int weight,
			long price) {
		super();
		this.id = id;
		this.cdate = cdate;
		this.product = product;
		this.weight = weight;
		this.price = price;
	}
	@Override
	public String toString() {
		return "\nOrder_"+id+": ["
                + "cdate="+cdate+", "
                + "product="+product+", "
                + "weight="+weight+" KG, "
                + "price="+price+" RMB, "
                + "]";
	}
	
	/**
	 * 按照weight升序
	 * sort的话，默认调用
	 */
	public int compareTo(OrderBean o) {
		return weight - o.getWeight();
	}
	
	/**
     * @author wujn
     * @Description:按条件分组
     * @param datas
     * @param c
     * 是否为同一组的判断标准 0 为同一组，1为不同组
     * @return
     */
    public static <T> List<List<T>> divider(Collection<T> datas, Comparator<? super T> c) {
        List<List<T>> result = new ArrayList<List<T>>();
        for (T t : datas) {
            boolean isSameGroup = false;
            for (int j = 0; j < result.size(); j++) {
                if (c.compare(t, result.get(j).get(0)) == 0) {
                    isSameGroup = true;
                    result.get(j).add(t);
                    break;
                }
            }
            if (!isSameGroup) {
                // 创建
                List<T> innerList = new ArrayList<T>();
                result.add(innerList);
                innerList.add(t);
            }
        }
        return result;
    }
	
	
	//测试比较器Comparable
	public static void testComparable(List<OrderBean> list) {
		System.out.println("testComparable===============");
		//调用Collections.sort方法，默认按照weight升序排列
        Collections.sort(list);
        System.out.println("按照订单的weight升序排序："+list);
	}
	
	//测试比较器Comparator
	public static void testComparator(List<OrderBean>list) {
		System.out.println("testComparator===============");
		/**
		 * ----------排列----------
		 */
		//id降序排列
		Collections.sort(list,new Comparator<OrderBean>() {
			public int compare(OrderBean o1, OrderBean o2) {
				return o2.getId() - o1.getId();
			}
		});
		System.out.println("按照订单的id降序排列："+list);
		//单价升序排列
		Collections.sort(list,new Comparator<OrderBean>() {
			public int compare(OrderBean o1, OrderBean o2) {
				return (int)(o1.getPrice()/o1.getWeight() - o2.getPrice()/o2.getWeight());
			}
		});
		System.out.println("按照订单的单价升序排列:"+list);
		System.out.println("===============================================");
		
		/**
		 * ---------分组----------
		 * 等于，不等于
		 */
		List<List<OrderBean>> byDate=divider(list, new Comparator<OrderBean>() {
			public int compare(OrderBean o1, OrderBean o2) {
				return o1.getCdate().equals(o2.getCdate()) ? 0:1;
			}
		});
		for (int i = 0; i < byDate.size(); i++) {
			System.out.println("按照订单的cdate分组["+i+"]:"+byDate.get(i));
		}
		
	}
	
	
	public static void main(String[] args) {
		
		OrderBean order1 = new OrderBean(1,"2018-01-01","牛肉",10,300);
        OrderBean order2 = new OrderBean(5,"2018-01-01","怪兽肉",80,400);
        OrderBean order3 = new OrderBean(2,"2018-02-01","牛肉",100,300);
        OrderBean order4 = new OrderBean(9,"2018-03-01","唐僧肉",2,600);
        List<OrderBean> list = new ArrayList<OrderBean>();
        list.add(order1);
        list.add(order2);
        list.add(order3);
        list.add(order4);
        
        OrderBean.testComparable(list);
        OrderBean.testComparator(list);
        
	}
}
