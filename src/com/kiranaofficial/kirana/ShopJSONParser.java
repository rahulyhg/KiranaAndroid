package com.kiranaofficial.kirana;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ShopJSONParser {
	public static List<Shop> getLoginData(String serviceData) {
		Shop shop = new Shop();
		List<Shop> shops = new ArrayList<Shop>();
		JSONObject object = null;
		
	    try {
	        object = new JSONObject(serviceData);
	        shop.setMajorCode(object.getInt("majorCode"));
	    } catch (JSONException e) {
	        e.printStackTrace();
	    }
	    
	    JSONArray parentArray = new JSONArray();
	    try {
			parentArray = object.getJSONArray("data");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    for(int i=0;i<parentArray.length();i++) {
	    	try {
	    		shop.setId(parentArray.getJSONObject(i).getInt("id"));
                shop.setName(parentArray.getJSONObject(i).getString("name"));
                shop.setType(parentArray.getJSONObject(i).getString("type"));
                shop.setAddress(parentArray.getJSONObject(i).getString("address"));
                shop.setTin(parentArray.getJSONObject(i).getString("tin"));
                shop.setServiceTax(parentArray.getJSONObject(i).getDouble("serviceTax"));
                shop.setServiceCharge(parentArray.getJSONObject(i).getDouble("serviceCharge"));
                shop.setVat(parentArray.getJSONObject(i).getDouble("vat"));
                shop.setWebsite(parentArray.getJSONObject(i).getString("website"));
		    	
		    	shops.add(shop);
	    	} catch(JSONException e) {
	    		
	    	}
	    }
	    
		return shops;
	}
}
