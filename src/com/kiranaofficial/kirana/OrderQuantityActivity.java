package com.kiranaofficial.kirana;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class OrderQuantityActivity extends Activity {
	
	EditText edtProductQuantity;
	TextView txtOrderProduct, txtProductPrice, txtTotalPrice;
	Button btnCancelProduct, btnAddProduct;
	String productName, productEdtQty;
	Double productPrice;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_quantity);
		
		txtOrderProduct = (TextView)findViewById(R.id.txtOrderProduct);
		txtProductPrice = (TextView)findViewById(R.id.txtProductPrice);
		txtTotalPrice = (TextView)findViewById(R.id.txtTotalPrice);
		edtProductQuantity = (EditText)findViewById(R.id.edtProductQuantity);
		btnCancelProduct = (Button)findViewById(R.id.btnCancelProduct);
		btnAddProduct = (Button)findViewById(R.id.btnAddProduct);
		
		productName = getIntent().getStringExtra("ProductName");
		productPrice = Double.parseDouble(getIntent().getStringExtra("ProductPrice"));
		productEdtQty = getIntent().getIntExtra("ProductEdtQty", 0) + "";
		
		txtOrderProduct.setText(productName);
		txtProductPrice.setText(productPrice + "");
		edtProductQuantity.setText(productEdtQty);
		
		btnAddProduct.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent returnToMenuOrder = new Intent();
				Bundle orderBundle = new Bundle();
				int qty = Integer.parseInt(edtProductQuantity.getText().toString().trim());
				orderBundle.putInt("ProductQty", qty);
				orderBundle.putString("ProductNameSelected", productName);
				/*
				 * Use the following 
					1. Multiply individual order with its tax percentage 
					2. Add all the orders
					3. Multiply total with additional tax bracket I. e. VAT
				 * 
				 */
				//orderBundle.putDouble("ProductPrice", productPrice * qty);
				returnToMenuOrder.putExtras(orderBundle);
				setResult(RESULT_OK, returnToMenuOrder);
				finish();
			}
		});
	}

}
