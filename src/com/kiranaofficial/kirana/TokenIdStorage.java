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
	
}
