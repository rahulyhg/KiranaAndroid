package com.kiranaofficial.kirana;

import java.util.List;

public class MyOrders {
	String tableNumber;
	String tableId;
	String grandTotal;
	String isBilled;
	String updatedTime;
	List<ProductUpload> products;
	
	public String getTableNumber() {
		return tableNumber;
	}
	public void setTableNumber(String tableNumber) {
		this.tableNumber = tableNumber;
	}
	public String getTableId() {
		return tableId;
	}
	public void setTableId(String tableId) {
		this.tableId = tableId;
	}
	public String getGrandTotal() {
		return grandTotal;
	}
	public void setGrandTotal(String grandTotal) {
		this.grandTotal = grandTotal;
	}
	public String getIsBilled() {
		return isBilled;
	}
	public void setIsBilled(String isBilled) {
		this.isBilled = isBilled;
	}
	public String getUpdatedTime() {
		return updatedTime;
	}
	public void setUpdatedTime(String updatedTime) {
		this.updatedTime = updatedTime;
	}
	public List<ProductUpload> getProducts() {
		return products;
	}
	public void setProducts(List<ProductUpload> products) {
		this.products = products;
	}
	
}
