package com.kiranaofficial.kirana;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class MyOrdersJSONParser {
	public static List<MyOrders> getMyOrders(String serviceData) {
		MyOrders myOrder = null;
		List<MyOrders> myOrders = new ArrayList<MyOrders>();
		JSONObject object = null;
		JSONArray parentArray = new JSONArray();
		JSONObject extraInfoObject = null;
		try {
			object = new JSONObject(serviceData);
			if(object != null) {
				parentArray = object.getJSONArray("data");
				for(int i = 0;i<parentArray.length();i++) {
					myOrder = new MyOrders();
					myOrder.setTableId(parentArray.getJSONObject(i).getString("id"));
					myOrder.setUpdatedTime(parentArray.getJSONObject(i).getString("updatedAt"));
					String extraInfoString = parentArray.getJSONObject(i).getString("extraInfo");
					extraInfoObject = new JSONObject(extraInfoString);
					
					if(extraInfoObject.has("TableNumber"))
						myOrder.setTableNumber(extraInfoObject.getString("TableNumber"));
					else 
						myOrder.setTableNumber("");
					
					if(extraInfoObject.has("IsBillPrinted"))
						myOrder.setIsBilled(extraInfoObject.getString("IsBillPrinted"));
					else 
						myOrder.setIsBilled("");
					
					if(extraInfoObject.has("GrandTotal"))
						myOrder.setGrandTotal(extraInfoObject.getString("GrandTotal"));
					else 
						myOrder.setGrandTotal("0.0");
					
					myOrders.add(myOrder);
				}
			}
		} catch(Exception e) {
			e.getMessage();
		}
		return myOrders;
	}
}
