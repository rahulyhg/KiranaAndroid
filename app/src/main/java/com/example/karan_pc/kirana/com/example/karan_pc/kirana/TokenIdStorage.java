package com.example.karan_pc.kirana.com.example.karan_pc.kirana;

import android.app.Application;

/**
 * Created by Karan-PC on 21-06-2015.
 */
public class TokenIdStorage extends Application {

    int id;
    String userToken;

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
}
