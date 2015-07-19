package com.kiranaofficial.kirana.adapters;

import java.util.ArrayList;

import com.kiranaofficial.kirana.CSVProduct;
import com.kiranaofficial.kirana.R;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;

public class CSVEditorAdapter extends BaseAdapter {
	  private ArrayList<CSVProduct> productList;
	  private Context context;
	   
	  public CSVEditorAdapter(Context context, int textViewResourceId, 
		   ArrayList<CSVProduct> productList) {
		   super();
		   this.context = context;
		   this.productList = new ArrayList<CSVProduct>();
		   this.productList.addAll(productList);
	  }

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
	   final ViewHolder holder;
	   CSVProduct product = productList.get(position);
	   if (convertView == null) {
		   
		   holder = new ViewHolder();
		   LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		   convertView = vi.inflate(R.layout.lvw_item_csv_editor, null);
		   holder.edtProductId = (EditText)convertView.findViewById(R.id.edtProductId);
		   convertView.setTag(holder);
		    /*LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		    convertView = vi.inflate(R.layout.lvw_item_csv_editor, null);
		    EditText edtProductId = (EditText)convertView.findViewById(R.id.edtProductId);
		    edtProductId.addTextChangedListener(new MyTextWatcher(convertView));*/
	   } else {
           holder = (ViewHolder)convertView.getTag();
       }
	   
	   /*EditText edtProductId = (EditText)convertView.findViewById(R.id.edtProductId);
	   edtProductId.setTag(product);
	   if(product.getProductName() != ""){
		   edtProductId.setText(String.valueOf(product.getProductName()));
	   }
	   else {
		   edtProductId.setText("");
	   }*/
	   holder.heldPos = position;
	   holder.edtProductId.setText(productList.get(position).getProductName());
	   holder.edtProductId.addTextChangedListener(new MyTextWatcher(convertView,holder));
	   return convertView;
	}
	
	private class MyTextWatcher implements TextWatcher {
		
		private View view;
		private ViewHolder holder;
		private MyTextWatcher(View view,ViewHolder holder) {
		   this.view = view;
		   this.holder = holder;
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			String productId = s.toString().trim();
			//EditText edtProductId = (EditText)view.findViewById(R.id.edtProductId);
			//CSVProduct product = (CSVProduct)edtProductId.getTag();
			//product.setProductName(productId);
			productList.get(holder.heldPos).setProductName(productId);
		}
		
	}
	
	private class ViewHolder {
		EditText edtProductId;
		int heldPos;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return productList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return productList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
}
