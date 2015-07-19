package com.kiranaofficial.kirana;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProductJSONParser {
	public static List<ProductUpload> getLoginData(String serviceData) {
		ProductUpload product = new ProductUpload();
		List<ProductUpload> products = new ArrayList<ProductUpload>();
		JSONObject object = null;
		
	    try {
	        object = new JSONObject(serviceData);
	        product.setMajorCode(object.getInt("majorCode"));
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
		    	product.setId(parentArray.getJSONObject(i).getInt("id"));
		    	product.setProductId(parentArray.getJSONObject(i).getString("productCode"));
		    	product.setQuantity(parentArray.getJSONObject(i).getInt("quantity"));
		    	product.setPrice(parentArray.getJSONObject(i).getDouble("price"));
		    	product.setDiscount(parentArray.getJSONObject(i).getDouble("discount"));
		    	product.setTaxBracket(parentArray.getJSONObject(i).getDouble("taxBracket"));
		    	product.setCreatedAt(parentArray.getJSONObject(i).getString("created_at"));
		    	product.setUpdatedAt(parentArray.getJSONObject(i).getString("updated_at"));
		    	
		    	products.add(product);
		    	product = new ProductUpload();
	    	} catch(JSONException e) {
	    		
	    	}
	    }
	    
		return products;
	}
}
