package com.kiranaofficial.kirana;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karan-PC on 30-05-2015.
 */
public class LoginJSONParser {
    public static User getLoginData(String serviceData) {

            User user = new User();
            JSONObject object = null;
            try {
                object = new JSONObject(serviceData);
                if(object != null)
                	user.setMajorCode(object.getInt("majorCode"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JSONArray parentArray = new JSONArray();
            List<UserList.OneUser> userList = new ArrayList<UserList.OneUser>();
            try {
            	if(object != null)
            		parentArray = object.getJSONArray("data");
            } catch (JSONException e) {
                e.printStackTrace();
            }
                JSONObject users = new JSONObject();
                try {
                    users = parentArray.getJSONObject(0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    user.setId(users.getInt("id"));
                    user.setUserName(users.getString("userName"));
                    user.setUserToken(users.getString("userToken"));
                    user.setUserRole(users.getString("userRole"));
                    user.setEmail(users.getString("email"));
                    user.setPhone(users.getString("phone"));
                    user.setStreet(users.getString("street"));
                    user.setState(users.getString("state"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JSONObject shopObj = null;
        //array of shops if requirement changes
                /*try {
                    JSONArray shopArray = new JSONArray();
                    shopArray = object.getJSONArray("shop");
                    for(int i = 0;i< shopArray.length();i++) {
                        JSONObject shops = new JSONObject();
                        shops = parentArray.getJSONObject(i);
                    }
                }catch (Exception e) {

                }*/

        //end
                if (!users.isNull("shop")) {
                    try {
                        shopObj = users.getJSONObject("shop");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                JSONObject innerObject = null;
                if (shopObj != null)
                    try {
                        innerObject = new JSONObject(shopObj.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                if (innerObject != null) {
                    Shop shop = new Shop();
                    try {
                        shop.setId(innerObject.getInt("id"));
                        shop.setName(innerObject.getString("name"));
                        shop.setType(innerObject.getString("type"));
                        shop.setAddress(innerObject.getString("address"));
                        shop.setTin(innerObject.getString("tin"));
                        shop.setServiceTax(innerObject.getDouble("serviceTax"));
                        shop.setServiceCharge(innerObject.getDouble("serviceCharge"));
                        shop.setVat(innerObject.getDouble("vat"));
                        shop.setWebsite(innerObject.getString("website"));
                    } catch (Exception e) {

                    }
                    user.setShop(shop);
                }

        //}
        return user;
    }
}
