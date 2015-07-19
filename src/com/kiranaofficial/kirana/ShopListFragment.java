package com.kiranaofficial.kirana;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import com.kiranaofficial.kirana.adapters.ShopListAdapter;

/**
 * Created by Karan-PC on 21-06-2015.
 */
public class ShopListFragment extends Fragment {
    ListView lvwShops;
    ShopListAdapter shopListAdapter;
    Shop shop = null;
    Context context;
    User user = null;
    TokenIdStorage storage;
    List<Shop> shopList, shops;
    ProgressDialog progress;
    public ShopListFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_shop_details, container, false);
        context = getActivity();
        storage = (TokenIdStorage) getActivity().getApplicationContext();
        
        Intent intent = getActivity().getIntent();
        shopList = new ArrayList<Shop>();
        if(intent != null) {
        	user = (User)intent.getSerializableExtra("loggedInUser");
        	if(user != null) {
            	shop = user.getShop();
            	if(shop != null)
            		shopList.add(0,shop);
            	else
            		getShopForUser();
            } else {
            	Common.ShowWebServiceResponse(getActivity(), "No User");
            }
        }
        lvwShops = (ListView)rootView.findViewById(R.id.lvwShops);
        shopListAdapter = new ShopListAdapter(getActivity(), shopList, new IDeleteShop(){

            @Override
            public void refreshListView(int position) {
                shopList.remove(position);
                shopListAdapter.notifyDataSetChanged();
            }
        });
        lvwShops.setAdapter(shopListAdapter);

        return rootView;
    }

	private void getShopForUser() {
		String userToken = storage.getUserToken();
    	final String productsUrl = "http://52.0.139.50:8080/KiranaService/v1/shop/own?userToken=" + userToken;
        BackgroundTask task = new BackgroundTask();
        if(Common.IsOnline(context)) {
        	task.execute(productsUrl);
        } else {
        	Common.ShowNoNetworkToast(context);
        }
	}
	
	private class BackgroundTask extends AsyncTask<String, String, List<Shop>> {

        @Override
        protected void onPreExecute() {
            publishProgress("Called the login web service");
            progress = ProgressDialog.show(getActivity(), "Kirana", "Loading shops");
        }

        @Override
        protected List<Shop> doInBackground(String... params) {
            String serviceContent = HttpManagerProductGet.GetServiceData(params[0]);
            shops = new ArrayList<Shop>();
            if(serviceContent != null)
            	shops = ShopJSONParser.getLoginData(serviceContent);
            return shops;
        }

        @Override
        protected void onPostExecute(List<Shop> products) {
        	progress.dismiss();
            if(products != null) {
            	int code = products.get(0).getMajorCode();
            	if(code == 200) {
            		Shop shop = new Shop();
            		for(int i = 0;i<products.size();i++) {
	            		shop.setId(products.get(i).getId());
	                    shop.setName(products.get(i).getName());
	                    shop.setType(products.get(i).getType());
	                    shop.setAddress(products.get(i).getAddress());
	                    shop.setTin(products.get(i).getTin());
	                    shop.setServiceTax(products.get(i).getServiceTax());
	                    shop.setServiceCharge(products.get(i).getServiceCharge());
	                    shop.setVat(products.get(i).getVat());
	                    shop.setWebsite(products.get(i).getWebsite());
	                    shopList.add(shop);
            		}
            		shopListAdapter.notifyDataSetChanged();
            	} else if(code == 401) {
                	String serviceMsg = "Unauthorized user";
                	Common.ShowWebServiceResponse(context, serviceMsg);
                } else if(code == 403){
                	String serviceMsg = "Service forbidden";
                	Common.ShowWebServiceResponse(context, serviceMsg);
                } else {
                	String serviceMsg = "Web service error";
                	Common.ShowWebServiceResponse(context, serviceMsg);
                }
            } 
        }
    }

}
