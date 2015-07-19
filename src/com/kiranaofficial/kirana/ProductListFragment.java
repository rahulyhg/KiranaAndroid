package com.kiranaofficial.kirana;

import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.kiranaofficial.kirana.adapters.ProductListAdapter;

public class ProductListFragment extends Fragment {
	private RecyclerView rcvwOfferProducts;
	Button btnUploadCSV;
	List<ProductUpload> products = null;
	TokenIdStorage storage;
	Context context;
	ProgressDialog progress;
    public ProductListFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_product_recycler_list, container, false);
        rcvwOfferProducts = (RecyclerView)rootView.findViewById(R.id.rcvwProducts);
        //ibtOk = (ImageButton)rootView.findViewById(R.id.ibtOk);
        btnUploadCSV = (Button)rootView.findViewById(R.id.btnUploadCSV);
        
        storage = (TokenIdStorage) getActivity().getApplicationContext();
        
        MyLinearLayoutManager linearLayoutManager = new MyLinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rcvwOfferProducts.setLayoutManager(linearLayoutManager);
        rcvwOfferProducts.setHasFixedSize(true);
        
        initializeData();
        
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

    private class BackgroundTask extends AsyncTask<String, String, List<ProductUpload>> {

        @Override
        protected void onPreExecute() {
            publishProgress("Called the login web service");
            progress = ProgressDialog.show(getActivity(), "Kirana", "Loading Product list");
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
            	btnUploadCSV.setVisibility(View.GONE);
            	if(products.size() != 0) {
	            	int code = products.get(0).getMajorCode();
	            	if(code == 200) {
	            		if(products.size()>0) {
		            		ProductListAdapter adapter = new ProductListAdapter(products);
		                    rcvwOfferProducts.setAdapter(adapter);
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
            	} else {
        			btnUploadCSV.setVisibility(View.VISIBLE);
        			btnUploadCSV.setOnClickListener(new View.OnClickListener() {
        	            @Override
        	            public void onClick(View v) {
        	            	getFragmentManager()
        	                .beginTransaction()
        	                .add(R.id.homeDrawerFrame, new UploadProductListFragment())
        	                .commit();
        	            }
        	        });
        		}
            }  else {
            	String serviceMsg = "Web service error";
            	Common.ShowWebServiceResponse(context, serviceMsg);
            }
        }
    }

}
