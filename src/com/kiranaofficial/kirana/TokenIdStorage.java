package com.kiranaofficial.kirana;

import android.app.Application;

/**
 * Created by Karan-PC on 21-06-2015.
 */
public class TokenIdStorage extends Application {

    int id;
    String userToken;
    String userName;

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
}
