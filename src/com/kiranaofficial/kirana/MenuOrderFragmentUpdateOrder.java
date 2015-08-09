package com.kiranaofficial.kirana;

import java.util.HashMap;
import java.util.List;

import com.kiranaofficial.kirana.adapters.MenuOrderAdapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class MenuOrderFragmentUpdateOrder extends Fragment{
	
	ListView lvwMenuOrder;
	Button btnMenuOrder;
	Context context;
	TokenIdStorage storage;
	ProgressDialog progress;
	String userToken = null, tableNumber, provider, currentDateandTime;
	EditText searchProducts;
	MenuOrderAdapter adapter;
	int PRODUCT_QUANTITY = 30, edtProductQty = 0;
	List<ProductOrderSummary> summaryProducts;
	HashMap<String, Integer> menuHashMap;
	HashMap<String, String> extraInfoHashMap;
	TextView txtCartQty;
	LocationManager locationManager;
	double latitude = 0f, longitude = 0f;
	String strResponseAfterOrder = null;
	TableOrder tableOrder = null;

	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_menu_order, container, false);
        
        lvwMenuOrder = (ListView)rootView.findViewById(R.id.lvwMenuOrder);
        btnMenuOrder = (Button)rootView.findViewById(R.id.btnMenuOrder);
        searchProducts = (EditText)rootView.findViewById(R.id.searchProducts);
        txtCartQty = (TextView)rootView.findViewById(R.id.txtCartQty);
        
		return rootView;
	}

}
