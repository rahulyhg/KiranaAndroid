package com.kiranaofficial.kirana.adapters;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kiranaofficial.kirana.MyOrders;
import com.kiranaofficial.kirana.R;

public class MyOrdersAdapter extends BaseAdapter{
	
	List<MyOrders> products;
	Context context;
	LayoutInflater inflater;
	int PRODUCT_QUANTITY = 1989;
	public MyOrdersAdapter(Context context, List<MyOrders> products) {
		this.context = context;
		this.products = products;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		int size = products.size();
		return products.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		if(convertView == null) {
			inflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.lvw_item_my_orders, null);
		}
		
		TextView txtTableNumber = (TextView) convertView.findViewById(R.id.txtTableNumber);
		TextView txtGrandTotal = (TextView) convertView.findViewById(R.id.txtGrandTotal);
		TextView txtIsBilled = (TextView) convertView.findViewById(R.id.txtIsBilled);
		TextView txtUpdatedTime = (TextView) convertView.findViewById(R.id.txtUpdatedTime);
        
        final double price = Double.parseDouble(products.get(position).getGrandTotal());

        txtTableNumber.setText(products.get(position).getTableNumber());
        txtGrandTotal.setText(price + "");
        if(products.get(position).getIsBilled().equalsIgnoreCase("NO")) {
        	txtIsBilled.setText("NOT BILLED");
        	txtIsBilled.setTextColor(Color.RED);
        }else if(products.get(position).getIsBilled().equalsIgnoreCase("YES")){
        	txtIsBilled.setText("BILLED");
        	txtIsBilled.setTextColor(Color.rgb(0, 100, 0));
        }
        txtUpdatedTime.setText(products.get(position).getUpdatedTime());
        
        /*convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			}
		});*/

        return convertView;
	}

}
