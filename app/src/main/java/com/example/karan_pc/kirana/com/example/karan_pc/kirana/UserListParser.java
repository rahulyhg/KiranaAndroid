package com.example.karan_pc.kirana.com.example.karan_pc.kirana;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karan-PC on 07-06-2015.
 */
public class UserListParser {
    public static UserList getUserList(String serviceData) {
        try {
            JSONObject object = new JSONObject(serviceData);
            JSONArray parentArray = new JSONArray();
            List<UserList.OneUser> userList = new ArrayList<UserList.OneUser>();
            parentArray = object.getJSONArray("object");
            UserList user = new UserList();
            for(int i = 0;i<parentArray.length();i++) {
                JSONObject users = new JSONObject();
                users = parentArray.getJSONObject(i);
                UserList.OneUser oneUser = new UserList().new OneUser();
                oneUser.setId(users.getInt("id"));
                oneUser.setUserName(users.getString("userName"));
                oneUser.setUserToken(users.getString("userToken"));
                oneUser.setUserRole(users.getString("userRole"));
                oneUser.setEmail(users.getString("email"));
                oneUser.setPhone(users.getString("phone"));
                oneUser.setStreet(users.getString("street"));
                oneUser.setState(users.getString("state"));
                JSONObject shopObj = null;
                if (!users.isNull("shop")) {
                    shopObj = users.getJSONObject("shop");
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

                    oneUser.setShop(shop);
                    userList.add(oneUser);
                }
                else {
                    oneUser.setShop(null);
                    userList.add(oneUser);
                }
            }
            user.setOneUser(userList);
            user.setMajorCode(object.getInt("majorCode"));
            return user;
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
