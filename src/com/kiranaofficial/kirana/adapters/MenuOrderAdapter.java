package com.kiranaofficial.kirana.adapters;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.sax.StartElementListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kiranaofficial.kirana.IOrderQuantity;
import com.kiranaofficial.kirana.OrderQuantityActivity;
import com.kiranaofficial.kirana.ProductUpload;
import com.kiranaofficial.kirana.R;

public class MenuOrderAdapter extends BaseAdapter{
	
	List<ProductUpload> products, tempProducts;
	Context context;
	LayoutInflater inflater;
	int PRODUCT_QUANTITY = 30;
	IOrderQuantity orderQuantity;
	public MenuOrderAdapter(Context context, List<ProductUpload> products, IOrderQuantity orderQuantity) {
		this.context = context;
		this.products = new ArrayList<ProductUpload>();
		this.tempProducts = products;
		this.products.addAll(tempProducts);
		this.orderQuantity = orderQuantity;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return tempProducts.size();
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
		
		final ViewHolder holder;
		if(convertView == null) {
			holder = new ViewHolder();
			inflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.lvw_item_menu, null);
			
			holder.imgProduct = (ImageView) convertView.findViewById(R.id.imgProduct);
			holder.txtProductName = (TextView) convertView.findViewById(R.id.txtProductName);
			holder.txtProductPrice = (TextView) convertView.findViewById(R.id.txtProductPrice);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
        
        final double price = tempProducts.get(position).getPrice();

        holder.txtProductName.setText(tempProducts.get(position).getProductId());
        holder.txtProductPrice.setText(price + "");
        
        convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				orderQuantity.getOrderQuantity(tempProducts.get(position).getProductId(), price + "");
			}
		});

        return convertView;
	}
	
	// Filter Class
	public void filter(String charText) {
		tempProducts.clear();
		if (charText.length() == 0) {
			tempProducts.addAll(products);
		} else {
			for (ProductUpload product : products) {
				if (product.getProductId().toLowerCase().contains(charText)) {
					tempProducts.add(product);
				}
			}
		}
		notifyDataSetChanged();
	}
	
	private class ViewHolder {
		ImageView imgProduct;
		TextView txtProductName;
		TextView txtProductPrice;
	}

}
