package com.kiranaofficial.kirana;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class OrderQuantityActivity extends Activity {
	
	EditText edtProductQuantity;
	Button btnCancelProduct, btnAddProduct;
	String productName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_quantity);
		
		productName = getIntent().getStringExtra("ProductName");
		getIntent().getStringExtra("ProductPrice");
		
		edtProductQuantity = (EditText)findViewById(R.id.edtProductQuantity);
		btnCancelProduct = (Button)findViewById(R.id.btnCancelProduct);
		btnAddProduct = (Button)findViewById(R.id.btnAddProduct);
		
		btnAddProduct.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent returnToMenuOrder = new Intent();
				Bundle orderBundle = new Bundle();
				int qty = Integer.parseInt(edtProductQuantity.getText().toString().trim());
				orderBundle.putInt("ProductQty", qty);
				orderBundle.putString("ProductNameSelected", productName);
				setResult(RESULT_OK, returnToMenuOrder);
				finish();
			}
		});
	}

}
