package com.kiranaofficial.kirana;

import java.util.HashMap;

public class TableOrder {
	int majorCode;
	String id;
	int shopId;
	String createdAt;
	String updatedAt;
	
	HashMap<String,Integer> orderList;
	ExtraInfo extraInfo;
	
	public int getMajorCode() {
		return majorCode;
	}
	public void setMajorCode(int majorCode) {
		this.majorCode = majorCode;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getShopId() {
		return shopId;
	}
	public void setShopId(int shopId) {
		this.shopId = shopId;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public String getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}
	public HashMap<String,Integer> getOrderList() {
		return orderList;
	}
	public void setOrderList(HashMap<String,Integer> orderListHashMap) {
		this.orderList = orderListHashMap;
	}
	public ExtraInfo getExtraInfo() {
		return extraInfo;
	}
	public void setExtraInfo(ExtraInfo extraInfo) {
		this.extraInfo = extraInfo;
	}
}

class OrderList {
	HashMap<String, Integer> orderListHashMap;
}

class ExtraInfo {
	String tableNumber;
	String locationCoordinates;
	String isBillPrinted;
	
	public String getTableNumber() {
		return tableNumber;
	}
	public void setTableNumber(String tableNumber) {
		this.tableNumber = tableNumber;
	}
	public String getLocationCoordinates() {
		return locationCoordinates;
	}
	public void setLocationCoordinates(String locationCoordinates) {
		this.locationCoordinates = locationCoordinates;
	}
	public String getIsBillPrinted() {
		return isBillPrinted;
	}
	public void setIsBillPrinted(String isBillPrinted) {
		this.isBillPrinted = isBillPrinted;
	}
}
