package com.megaease.jjl.stocksocket;
/**
 * 股票Stock类，包含股票信息[买卖股票价格，股票数量]
 * @author Administrator
 * @date 2020-2-7
 * @version 
 * @description
 */
public class Stock {
	
	private Integer stockPrice;
	private Integer stockNumber;
	public Integer getStockPrice() {
		return stockPrice;
	}
	public void setStockPrice(Integer stockPrice) {
		this.stockPrice = stockPrice;
	}
	public Integer getStockNumber() {
		return stockNumber;
	}
	public void setStockNumber(Integer stockNumber) {
		this.stockNumber = stockNumber;
	}
	
	public Stock() {
		
	}
	public Stock(Integer stockNumber,Integer stockPrice){
		this.stockNumber=stockNumber;
		this.stockPrice=stockPrice;
	}
}
