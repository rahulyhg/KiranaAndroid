package com.kiranaofficial.kirana;

import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TableOrderJSONParser {
	public static TableOrder getOrderForTable(String serviceData) {
		TableOrder tableOrder = new TableOrder();
		JSONObject object = null;
		JSONObject tableOrderObject = null;
		JSONArray parentArray = new JSONArray();
		HashMap<String, Integer> orderListHashMap = null;
		try {
			object = new JSONObject(serviceData);
			orderListHashMap = new HashMap<String,Integer>();
			if(object != null) {
				parentArray = object.getJSONArray("data");
				tableOrderObject = parentArray.getJSONObject(0);
				
				tableOrder.setMajorCode(object.getInt("majorCode"));
				tableOrder.setId(tableOrderObject.getString("id"));
				tableOrder.setShopId(tableOrderObject.getInt("shopId"));
				tableOrder.setCreatedAt(tableOrderObject.getString("createdAt"));
				tableOrder.setUpdatedAt(tableOrderObject.getString("updatedAt"));
				JSONObject objOrderList = tableOrderObject.getJSONObject("orderList");
				
				Iterator<?> keys = objOrderList.keys();
				
				while(keys.hasNext()) {
				    String product = (String)keys.next();
				    int quantity = objOrderList.getInt(product);
				    orderListHashMap.put(product, quantity);
				}
				tableOrder.setOrderList(orderListHashMap);
				ExtraInfo extraInfo = new ExtraInfo();
				JSONObject objExtraInfo = tableOrderObject.getJSONObject("extraInfo");
				
				extraInfo.setIsBillPrinted(objExtraInfo.getString("IsBillPrinted"));
				extraInfo.setLocationCoordinates(objExtraInfo.getString("LocationCoordinates"));
				extraInfo.setTableNumber(objExtraInfo.getString("TableNumber"));
				
				tableOrder.setExtraInfo(extraInfo);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tableOrder;
	}
}
