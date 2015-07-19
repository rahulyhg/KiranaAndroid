package com.kiranaofficial.kirana;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

public class CSVEntryDialogActivity extends Activity{
	EditText txtNameValue, txtQtyValue, txtPriceValue, txtDiscountValue, txtTaxValue, txtExtra1Value, txtExtra2Value, txtExtra3Value, txtExtra4Value;
	TextView txtImageValue;
	Button btnCancel, btnOk;
	String strName, strQty, strPrice, strDiscount, strTax, strExtra1, strExtra2, strExtra3, strExtra4, strImagePath, strCurFileName;
	List<String> csvEntries;
	static CSVProduct csvProduct;
	int selectedPosition;
	ImageButton ibtPopupImage;
	Context context;
	private static final int GALLERY_REQUEST_PATH = 0, FOLDER_REQUEST_PATH = 1, CAMERA_REQUEST_PATH = 2;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_csv_entry_dialog);
        
        context = this;
        this.setFinishOnTouchOutside(false);
        
        txtNameValue = (EditText)findViewById(R.id.txtNameValue);
        txtQtyValue = (EditText)findViewById(R.id.txtQtyValue);
        txtPriceValue = (EditText)findViewById(R.id.txtPriceValue);
        txtDiscountValue = (EditText)findViewById(R.id.txtDiscountValue);
        txtTaxValue = (EditText)findViewById(R.id.txtTaxValue);
        txtExtra1Value = (EditText)findViewById(R.id.txtExtra1Value);
        txtExtra2Value = (EditText)findViewById(R.id.txtExtra2Value);
        txtExtra3Value = (EditText)findViewById(R.id.txtExtra3Value);
        txtExtra4Value = (EditText)findViewById(R.id.txtExtra4Value);
        
        txtImageValue = (TextView)findViewById(R.id.txtImageValue);
        btnCancel = (Button)findViewById(R.id.btnCancel);
        btnOk = (Button)findViewById(R.id.btnOk);
        
        ibtPopupImage = (ImageButton)findViewById(R.id.ibtPopupImage);
        
        ibtPopupImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(context, v);
                popup.getMenuInflater().inflate(R.menu.csv_product_image_options, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getItemId() == R.id.popGallery) {
                        	Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        	startActivityForResult(Intent.createChooser(galleryIntent, "Select Product Image"), GALLERY_REQUEST_PATH);
                        } else if (item.getItemId() == R.id.popFolders) {
                        	Intent fileExplorer = new Intent(CSVEntryDialogActivity.this, FileExplorerActivity.class);
                            startActivityForResult(fileExplorer, FOLDER_REQUEST_PATH);
                        } else if(item.getItemId() == R.id.popTakePic) {
                        	Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                        	startActivityForResult(intent, CAMERA_REQUEST_PATH);
                        }
                        return true;
                    }
                });
                popup.show();
            }
        });
        
        //edit Product
        
        Intent intent = getIntent();
        final CSVProduct editProduct = (CSVProduct)intent.getSerializableExtra("SelectedProduct");
        selectedPosition = intent.getIntExtra("SelectedProductPos", 0);
        if(editProduct != null) {
        	txtNameValue.setText(editProduct.getProductName());
        	txtQtyValue.setText(editProduct.getProductQty());
        	txtPriceValue.setText(editProduct.getProductPrice());
        	txtDiscountValue.setText(editProduct.getProductDiscount());
        	txtTaxValue.setText(editProduct.getProductTax());
        	
        	if(editProduct.getProductImagePath() == "" || editProduct.getProductImagePath() == null)
        		txtImageValue.setText("No Image Selected");
        	else 
        		txtImageValue.setText(editProduct.getProductImagePath());
        	
        	txtExtra1Value.setText(editProduct.getProductExtra1());
        	txtExtra2Value.setText(editProduct.getProductExtra2());
        	txtExtra3Value.setText(editProduct.getProductExtra3());
        	txtExtra4Value.setText(editProduct.getProductExtra4());
        }
        
        //end edit Product
        
        csvProduct = new CSVProduct();
        
        btnOk.setOnClickListener(new View.OnClickListener() { 
        	@Override
            public void onClick(View v) {
        		strName = txtNameValue.getText().toString().trim();
	            strQty = txtQtyValue.getText().toString().trim();
	            strPrice = txtPriceValue.getText().toString().trim();
	            strDiscount = txtDiscountValue.getText().toString().trim();
	            strTax = txtTaxValue.getText().toString().trim();
	            strExtra1 = txtExtra1Value.getText().toString().trim();
	            strExtra2 = txtExtra2Value.getText().toString().trim();
	            strExtra3 = txtExtra3Value.getText().toString().trim();
	            strExtra4 = txtExtra4Value.getText().toString().trim();
	            if(strName.isEmpty()) {
	            	txtNameValue.setError("Please enter Product Name");
	            } else {
	            	
	            	csvProduct.setProductName(strName);
	            	csvProduct.setProductQty(strQty);
	            	csvProduct.setProductPrice(strPrice);
	            	csvProduct.setProductDiscount(strDiscount);
	            	csvProduct.setProductTax(strTax);
	            	csvProduct.setProductImagePath(txtImageValue.getText().toString().trim());
	            	csvProduct.setProductExtra1(strExtra1);
	            	csvProduct.setProductExtra2(strExtra2);
	            	csvProduct.setProductExtra3(strExtra3);
	            	csvProduct.setProductExtra4(strExtra4);
	            	
	            	Intent returnToProductList = new Intent();
	            	Bundle productBundle = new Bundle();
	            	if(editProduct == null)
	            		productBundle.putSerializable("NewProduct", csvProduct);
	            	else {
	            		productBundle.putSerializable("NewProduct", null);
	            		productBundle.putSerializable("EditedProduct", csvProduct);
	            		productBundle.putInt("EditedPos", selectedPosition);
	            	}
                    returnToProductList.putExtras(productBundle);
                    setResult(RESULT_OK, returnToProductList);
                    finish();
	            }
        	}
        });
        
        btnCancel.setOnClickListener(new View.OnClickListener() { 
        	@Override
            public void onClick(View v) {
        		finish();
        	}
        });
    }
	
	public void onActivityResult(int requestCode, int resultCode, Intent data){
		if(requestCode == GALLERY_REQUEST_PATH) {
			if(resultCode == Activity.RESULT_OK) {
				
                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();
 
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String realPath = cursor.getString(columnIndex);
                cursor.close();

                strImagePath = realPath.toString();
				txtImageValue.setText(strImagePath);
				csvProduct.setProductImagePath(strImagePath);
			} else {
				
			}
		} else if(requestCode == FOLDER_REQUEST_PATH) {
            if (resultCode == Activity.RESULT_OK) {
                strCurFileName = data.getStringExtra("GetFileName");
                strImagePath = strCurFileName;
                txtImageValue.setText(strImagePath);
                csvProduct.setProductImagePath(strImagePath);
            } else {
            	
			}
        } else if(requestCode == CAMERA_REQUEST_PATH) {
        	if(resultCode == Activity.RESULT_OK) {
	        	Bitmap photo = (Bitmap)data.getExtras().get("data"); 
	            //imageView.setImageBitmap(photo);
	            Uri tempUri = getImageUri(getApplicationContext(), photo);
	            strImagePath = getRealPathFromURI(tempUri);
	            txtImageValue.setText(strImagePath);
	            csvProduct.setProductImagePath(strImagePath);
	            File finalFile = new File(strImagePath);
        	} else {
        		
			}
        }
    }
	
	private Uri getImageUri(Context inContext, Bitmap inImage) {
	    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
	    inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
	    String path = Images.Media.insertImage(inContext.getContentResolver(), inImage, "Product Image", null);
	    return Uri.parse(path);
	}

	private String getRealPathFromURI(Uri uri) {
	    Cursor cursor = getContentResolver().query(uri, null, null, null, null); 
	    cursor.moveToFirst(); 
	    int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA); 
	    return cursor.getString(idx); 
	}
}
