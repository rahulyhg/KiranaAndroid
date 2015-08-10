package com.kiranaofficial.kirana;

import java.util.List;

import android.app.Application;

/**
 * Created by Karan-PC on 21-06-2015.
 */
public class TokenIdStorage extends Application {

    int id;
    String userToken;
    String userName;
    List<ProductUpload> products;
    Shop shop;
    boolean isUpdateOrderSummary = false;
    List<ProductUpload> productMenu;
    String tableNumber;
    String createdDateTime;
    String tableId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}
	
	public List<ProductUpload> getProducts() {
		return products;
	}

	public void setProducts(List<ProductUpload> products) {
		this.products = products;
	}

	public boolean isUpdateOrderSummary() {
		return isUpdateOrderSummary;
	}

	public void setUpdateOrderSummary(boolean isUpdateOrderSummary) {
		this.isUpdateOrderSummary = isUpdateOrderSummary;
	}

	public List<ProductUpload> getProductMenu() {
		return productMenu;
	}

	public void setProductMenu(List<ProductUpload> productMenu) {
		this.productMenu = productMenu;
	}

	public String getTableId() {
		return tableId;
	}

	public void setTableId(String tableId) {
		this.tableId = tableId;
	}

	public String getTableNumber() {
		return tableNumber;
	}

	public void setTableNumber(String tableNumber) {
		this.tableNumber = tableNumber;
	}

	public String getCreatedDateTime() {
		return createdDateTime;
	}

	public void setCreatedDateTime(String createdDateTime) {
		this.createdDateTime = createdDateTime;
	}
	
}
