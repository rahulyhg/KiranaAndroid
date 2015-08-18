package com.kiranaofficial.kirana;

import java.util.List;

import com.kiranaofficial.kirana.adapters.MyOrdersAdapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MyOrdersFragment extends Fragment{
	
	Context context;
	TokenIdStorage storage;
	String userToken;
	ListView lvwMyOrders;
	ProgressDialog progress;
	MyOrdersAdapter myOrdersAdapter;
	
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.fragment_my_orders, container, false);
		
		lvwMyOrders = (ListView)rootView.findViewById(R.id.lvwMyOrders);
		
		context = getActivity();
		storage = (TokenIdStorage) getActivity().getApplicationContext();
        userToken = storage.getUserToken();
        
		String uploadMenuUrl = "http://54.169.108.240:8080/KiranaService/v1/order/own?userToken=" + userToken;
		GetMyOrdersBackgroundTask task = new GetMyOrdersBackgroundTask();
        if(Common.IsOnline(context)) {
        	task.execute(uploadMenuUrl);
        } else {
        	Common.ShowNoNetworkToast(context);
        }
		
		return rootView;
	}
	
	private class GetMyOrdersBackgroundTask extends AsyncTask<String, String, List<MyOrders>> {

        @Override
        protected void onPreExecute() {
            publishProgress("Called the login web service");
            progress = ProgressDialog.show(getActivity(), "Kirana", "Loading My Orders");
        }

        @Override
        protected List<MyOrders> doInBackground(String... params) {
            String serviceContent = HttpManagerProductGet.GetServiceData(params[0]);
            List<MyOrders> myOrders = MyOrdersJSONParser.getMyOrders(serviceContent);
            return myOrders;
        }

        @Override
        protected void onPostExecute(final List<MyOrders> myOrders) {
        	progress.dismiss();
        	myOrdersAdapter = new MyOrdersAdapter(getActivity(), myOrders);
        	lvwMyOrders.setAdapter(myOrdersAdapter);
        	myOrdersAdapter.notifyDataSetChanged();
        	
        	lvwMyOrders.setOnItemClickListener(new OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view,
                        int position, long id) {
                	
                	if(!myOrders.get(position).getIsBilled().isEmpty() && !myOrders.get(position).getTableNumber().isEmpty()) {
	                	storage.setTableId(myOrders.get(position).getTableId());
	                	
	                	OrderSummaryFragment orderSummaryFragment = new OrderSummaryFragment();
	    				android.support.v4.app.FragmentManager manager = getActivity().getSupportFragmentManager();
	    		    	android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
	    		    	transaction.replace(R.id.homeDrawerFrame, orderSummaryFragment);
	    		    	transaction.commit();
                	}
                }
            });
        }   
    }
}
