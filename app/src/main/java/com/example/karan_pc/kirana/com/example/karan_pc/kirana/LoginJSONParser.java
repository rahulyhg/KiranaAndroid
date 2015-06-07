package com.example.karan_pc.kirana.com.example.karan_pc.kirana;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Karan-PC on 30-05-2015.
 */
public class LoginJSONParser {
    public static User getLoginData(String serviceData) {

        try {
            JSONObject object = new JSONObject(serviceData);
            JSONObject parentObject = new JSONObject();
            parentObject = object.getJSONObject("object");
            User user = new User();
            user.setMajorCode(object.getInt("majorCode"));
            user.setId(parentObject.getInt("id"));
            user.setUserName(parentObject.getString("userName"));
            user.setUserToken(parentObject.getString("userToken"));
            user.setUserRole(parentObject.getString("userRole"));
            user.setEmail(parentObject.getString("email"));
            user.setPhone(parentObject.getString("phone"));
            user.setStreet(parentObject.getString("street"));
            user.setState(parentObject.getString("state"));

            JSONObject shopObj = null;
            if (!parentObject.isNull("shop")) {
                shopObj = parentObject.getJSONObject("shop");
            }
            JSONObject innerObject = null;
            if(shopObj != null)
                innerObject = new JSONObject(shopObj.toString());
            if(innerObject != null) {
                Shop shop = new Shop();
                shop.setId(innerObject.getInt("id"));
                shop.setName(innerObject.getString("name"));
                shop.setType(innerObject.getString("type"));
                shop.setAddress(innerObject.getString("address"));
                shop.setTin(innerObject.getString("tin"));
                shop.setServiceTax(innerObject.getDouble("serviceTax"));
                shop.setServiceCharge(innerObject.getDouble("serviceCharge"));
                shop.setVat(innerObject.getDouble("vat"));
                shop.setWebsite(innerObject.getString("website"));

                user.setShop(shop);
            }
            else
                user.setShop(null);

            return user;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
