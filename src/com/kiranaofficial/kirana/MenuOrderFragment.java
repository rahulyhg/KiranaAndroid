package com.kiranaofficial.kirana;

import java.util.ArrayList;
import java.util.List;

import com.kiranaofficial.kirana.adapters.MenuOrderAdapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MenuOrderFragment extends android.support.v4.app.Fragment{
	
	ListView lvwMenuOrder;
	Button btnMenuOrder;
	Context context;
	TokenIdStorage storage;
	ProgressDialog progress;
	String userToken = null;
	EditText searchProducts;
	MenuOrderAdapter adapter;
	int PRODUCT_QUANTITY = 30;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_menu_order, container, false);
        
        lvwMenuOrder = (ListView)rootView.findViewById(R.id.lvwMenuOrder);
        btnMenuOrder = (Button)rootView.findViewById(R.id.btnMenuOrder);
        searchProducts = (EditText)rootView.findViewById(R.id.searchProducts);
        
        storage = (TokenIdStorage) getActivity().getApplicationContext();
        userToken = storage.getUserToken();
        
        initializeData();
        
        searchProducts.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				String queryProduct = searchProducts.getText().toString().toLowerCase();
				adapter.filter(queryProduct);
			}
		});
        
        return rootView;
	}

	private void initializeData(){
    	context = getActivity();
    	String userToken = storage.getUserToken();
    	final String productsUrl = "http://54.169.108.240:8080/KiranaService/v1/product/own?userToken=" + userToken;
        BackgroundTask task = new BackgroundTask();
        if(Common.IsOnline(context)) {
        	task.execute(productsUrl);
        } else {
        	Common.ShowNoNetworkToast(context);
        }
    }
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == Activity.RESULT_OK) {
			int qty = data.getIntExtra("ProductQty", 0);
			String productNameSelected = data.getStringExtra("ProductNameSelected");
		}
	}
	
	private class BackgroundTask extends AsyncTask<String, String, List<ProductUpload>> {

        @Override
        protected void onPreExecute() {
            publishProgress("Called the login web service");
            progress = ProgressDialog.show(getActivity(), "Kirana", "Loading Menu");
        }

        @Override
        protected List<ProductUpload> doInBackground(String... params) {
            String serviceContent = HttpManagerProductGet.GetServiceData(params[0]);
            List<ProductUpload> products = new ArrayList<ProductUpload>();
            if(serviceContent != null)
            	products = ProductJSONParser.getLoginData(serviceContent);
            return products;
        }

        @Override
        protected void onPostExecute(List<ProductUpload> products) {
        	progress.dismiss();
            if(products != null) {
            	if(products.size() != 0) {
	            	int code = products.get(0).getMajorCode();
	            	if(code == 200) {
	            		if(products.size()>0) {
		            		adapter = new MenuOrderAdapter(getActivity(),products, new IOrderQuantity() {

								@Override
								public void getOrderQuantity(String strProductName,String strProductPrice) {
									// TODO Auto-generated method stub
									Intent ProductQuantity = new Intent(context,OrderQuantityActivity.class);
									ProductQuantity.putExtra("ProductName",strProductName);
									ProductQuantity.putExtra("ProductPrice", strProductPrice);
									startActivityForResult(ProductQuantity, PRODUCT_QUANTITY);
								}
		            		});
		            		lvwMenuOrder.setAdapter(adapter);
	            		} 
	            	} else if(code == 401) {
	                	String serviceMsg = "Unauthorized user";
	                	Common.ShowWebServiceResponse(context, serviceMsg);
	                } else if(code == 403){
	                	String serviceMsg = "Service forbidden";
	                	Common.ShowWebServiceResponse(context, serviceMsg);
	                } else if(code == 500){
	                	String serviceMsg = "500 Web service error";
	                	Common.ShowWebServiceResponse(context, serviceMsg);
	                }
            	}
            }  else {
            	String serviceMsg = "Web service error";
            	Common.ShowWebServiceResponse(context, serviceMsg);
            }
        }
    }
}
