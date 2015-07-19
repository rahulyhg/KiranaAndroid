package com.kiranaofficial.kirana;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.net.URLCodec;


import android.app.Activity;
import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.kiranaofficial.kirana.AndroidMultiPartEntity.ProgressListener;
import com.kiranaofficial.kirana.adapters.CSVProductListAdapter;

public class CSVProductListFragment extends Fragment{
	View rootView;
	private RecyclerView lvwCSVProducts;
	ImageButton ibtAddCSVProduct, ibtUpload;
	private static final int REQUEST_PATH = 2;
	Intent csvEditor;
	private List<CSVProduct> products;
	CSVProductListAdapter csvAdapter;
	private TextView txtHelp;
	String curFileName;
	long totalSize = 0;
	TokenIdStorage storage;
	String userToken = null, userName = null, fileName = null;
	File folder = null;
	
	//Delimiter used in CSV file
	private static final String COMMA_DELIMITER = ",";
	private static final String NEW_LINE_SEPARATOR = "\n";
	
	//CSV file header
	private static final String FILE_HEADER = "product_id,quantity,price,discount,tax_bracket,image_path";
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_csv_product_list, container, false);
        lvwCSVProducts = (RecyclerView)rootView.findViewById(R.id.lvwCSVProducts);
        ibtAddCSVProduct = (ImageButton)rootView.findViewById(R.id.ibtAddCSVProduct);
        ibtUpload =  (ImageButton)rootView.findViewById(R.id.ibtUpload);
        txtHelp = (TextView)rootView.findViewById(R.id.txtHelp);
        
        products = new ArrayList<CSVProduct>();
        storage = (TokenIdStorage) getActivity().getApplicationContext();
        userToken = storage.getUserToken();
        userName = storage.getUserName();
        
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        lvwCSVProducts.setLayoutManager(linearLayoutManager);
        lvwCSVProducts.setHasFixedSize(true);
        
        csvAdapter = new CSVProductListAdapter(products);
        lvwCSVProducts.setAdapter(csvAdapter);
        csvAdapter.notifyDataSetChanged();
        
        registerForContextMenu(lvwCSVProducts);
        
        ibtAddCSVProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                csvEditor = new Intent(getActivity(), CSVEntryDialogActivity.class);
                startActivityForResult(csvEditor, REQUEST_PATH);
            }
        });
        
        ibtUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	
            	Random randomGenerator = new Random();
            	int randomInt = randomGenerator.nextInt(100);
            	folder = new File(Environment.getExternalStorageDirectory() + "/Kirana CSV Files");
            	boolean success = true;
            	if (!folder.exists()) {
            	    success = folder.mkdir();
            	}
            	if (success) {
            	    // Do something on success
            		fileName = folder + "/" + userName + ".csv";
            	} else {
            	    // Do something else on failure 
            	}
            	
            	//start	
            		
        		FileWriter fileWriter = null;
        				
        		try {
        			fileWriter = new FileWriter(fileName);

        			//Write the CSV file header
        			fileWriter.append(FILE_HEADER.toString());
        			
        			//Add a new line separator after the header
        			fileWriter.append(NEW_LINE_SEPARATOR);
        			
        			//Write a new student object list to the CSV file
        			for (CSVProduct product : products) {
        				fileWriter.append(String.valueOf(product.getProductName()));
        				fileWriter.append(COMMA_DELIMITER);
        				fileWriter.append(product.getProductQty());
        				fileWriter.append(COMMA_DELIMITER);
        				fileWriter.append(product.getProductPrice());
        				fileWriter.append(COMMA_DELIMITER);
        				fileWriter.append(product.getProductDiscount());
        				fileWriter.append(COMMA_DELIMITER);
        				fileWriter.append(String.valueOf(product.getProductTax()));
        				fileWriter.append(COMMA_DELIMITER);
        				fileWriter.append(String.valueOf(product.getProductImagePath()));
        				fileWriter.append(COMMA_DELIMITER);
        				fileWriter.append(String.valueOf(product.getProductExtra1()));
        				fileWriter.append(COMMA_DELIMITER);
        				fileWriter.append(String.valueOf(product.getProductExtra2()));
        				fileWriter.append(COMMA_DELIMITER);
        				fileWriter.append(String.valueOf(product.getProductExtra2()));
        				fileWriter.append(COMMA_DELIMITER);
        				fileWriter.append(String.valueOf(product.getProductExtra2()));
        				fileWriter.append(NEW_LINE_SEPARATOR);
        			}
        			//success
        			Toast.makeText(getActivity(), "CSV file created in storage!", Toast.LENGTH_LONG).show();
        			PhotoUploadBackgroundTask task = new PhotoUploadBackgroundTask();
                    if(Common.IsOnline(getActivity())) {
                    	task.execute("Upload Image");
                    } else {
    	            	Common.ShowNoNetworkToast(getActivity());
    	            }
        		} catch (Exception e) {
        			//failure
        			e.printStackTrace();
        			Toast.makeText(getActivity(), "CSV file creation failed", Toast.LENGTH_LONG).show();
        		} finally {
        			
        			try {
        				fileWriter.flush();
        				fileWriter.close();
        			} catch (IOException e) {
        				Toast.makeText(getActivity(), "CSV file creation failed", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
        			}
        			
        		}
            	
            	//end
            	
            }
        });
        
        return rootView;
    }
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		CSVProductListAdapter adapter = new CSVProductListAdapter();
		int position = -1;
	    try {
	        position = csvAdapter.getPosition();
	    } catch (Exception e) {
	        return super.onContextItemSelected(item);
	    }
	    switch (item.getTitle().toString()) {
	        case "Edit Product":
	        	Bundle bundle = new Bundle();
                bundle.putSerializable("SelectedProduct", products.get(position));
                bundle.putInt("SelectedProductPos", position);
                csvEditor = new Intent(getActivity(), CSVEntryDialogActivity.class);
                csvEditor.putExtras(bundle);
                startActivityForResult(csvEditor, REQUEST_PATH);
	            break;
	        case "Delete Product":
	        	products.remove(position);
	        	csvAdapter.notifyDataSetChanged();
	        	if(products.size() == 0) {
                	txtHelp.setText("No Products to display");
                	ibtUpload.setVisibility(View.GONE);
                }
	            break;
	    }
		return super.onContextItemSelected(item);
	}


	// Listen for results.
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        // See which child activity is calling us back.
        if (requestCode == REQUEST_PATH){
            if (resultCode == Activity.RESULT_OK) {
                CSVProduct csvProduct = null;
            	csvProduct = (CSVProduct)data.getSerializableExtra("NewProduct");
            	if(csvProduct != null) {
            		products.add(csvProduct);
                }
                else {
                	CSVProduct editedProduct = (CSVProduct)data.getSerializableExtra("EditedProduct");
                	int editedPosition = data.getIntExtra("EditedPos", 0);               	
                	csvProduct = new CSVProduct();
                	csvProduct.setProductName(editedProduct.getProductName());
                	csvProduct.setProductQty(editedProduct.getProductQty());
                	csvProduct.setProductPrice(editedProduct.getProductPrice());
                	csvProduct.setProductDiscount(editedProduct.getProductDiscount());
                	csvProduct.setProductTax(editedProduct.getProductTax());
                	csvProduct.setProductImagePath(editedProduct.getProductImagePath());
                	csvProduct.setProductExtra1(editedProduct.getProductExtra1());
                	csvProduct.setProductExtra2(editedProduct.getProductExtra2());
                	csvProduct.setProductExtra3(editedProduct.getProductExtra3());
                	csvProduct.setProductExtra4(editedProduct.getProductExtra4());
                	
                	products.set(editedPosition, csvProduct);
                }
                csvAdapter.notifyDataSetChanged();
                
                if(products.size() == 0) {
                	txtHelp.setText("No Products to display");
                	ibtUpload.setVisibility(View.GONE);
                }
                else {
                	txtHelp.setText("Long press on product for more options");
                	ibtUpload.setVisibility(View.VISIBLE);
                }
            }
        }
    }
    
    private class PhotoUploadBackgroundTask extends AsyncTask<String, Integer, Integer> {

        @Override
        protected void onPreExecute() {
        	super.onPreExecute();
        }

		@Override
        protected Integer doInBackground(String... params) {
			for(int i = 0;i<products.size();i++) {
				String url = null;
				String responseString = null;
	        	Integer statusCode = null;
	        	File sourceFile = null;
	            HttpClient httpclient = new DefaultHttpClient();
	            URLCodec codec = new URLCodec();
	            String encodedUrl = null;
	            try {
					url = "http://54.169.108.240:8080/KiranaService/v1/product/image/upload?userToken=" + userToken + "&productCode=" + codec.encode(products.get(i).getProductName());
				} catch (EncoderException e2) {
					e2.printStackTrace();
				}
	            HttpPost httppost = new HttpPost(url);
	 
	            try {
	                AndroidMultiPartEntity entity = new AndroidMultiPartEntity(new ProgressListener() {
		 
	                    @Override
	                    public void transferred(long num) {
	                        publishProgress((int) ((num / (float) totalSize) * 100));
	                    }
	                });
	                curFileName = products.get(i).getProductImagePath();
	                if(curFileName != null || !curFileName.isEmpty())
	                	sourceFile = new File(curFileName);//new
	                entity.addPart("file", new FileBody(sourceFile));//new
	                totalSize = entity.getContentLength();
	                httppost.setEntity(entity);
	                HttpResponse response = httpclient.execute(httppost);
	                HttpEntity r_entity = response.getEntity();
	 
	                statusCode = response.getStatusLine().getStatusCode();
	                if (statusCode == 200) {
	                    responseString = EntityUtils.toString(r_entity);
	                } else {
	                    responseString = statusCode + "";
	                }
	            } catch (ClientProtocolException e) {
	                responseString = e.toString();
	            } catch (IOException e) {
	                responseString = e.toString();
	            }
        	}
			return 200;
        }

        @Override
        protected void onPostExecute(Integer statusCode) {
        	if(statusCode == 200) {
        		CSVBackgroundTask task = new CSVBackgroundTask();
        		String uploadUrl = "http://54.169.108.240:8080/KiranaService/v1/product/register?userToken=" + userToken;
        		task.execute("CSV Upload");
        	}
        }
    }
    
    private class CSVBackgroundTask extends AsyncTask<String, Integer, Integer> {
    	
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected Integer doInBackground(String... params) {
			String responseString = null;
        	Integer statusCode = null;
        	File sourceFile = null;
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(params[0]);
 
            try {
                AndroidMultiPartEntity entity = new AndroidMultiPartEntity(new ProgressListener() {
	 
                    @Override
                    public void transferred(long num) {
                        publishProgress((int) ((num / (float) totalSize) * 100));
                    }
                });
                if(fileName != null || !fileName.isEmpty())
                	sourceFile = new File(fileName);//new
                entity.addPart("file", new FileBody(sourceFile));//new
                totalSize = entity.getContentLength();
                httppost.setEntity(entity);
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity r_entity = response.getEntity();
 
                statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    responseString = EntityUtils.toString(r_entity);
                } else {
                    responseString = statusCode + "";
                }
            } catch (ClientProtocolException e) {
                responseString = e.toString();
            } catch (IOException e) {
                responseString = e.toString();
            }
            return statusCode;
		}
		
		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
		}
    }
}
