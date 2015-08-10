package com.kiranaofficial.kirana;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.kiranaofficial.kirana.adapters.OrderSummaryAdapter;

public class OrderSummaryFragment extends Fragment implements LocationListener{

	ListView lvwOrderSummary;
	Button btnAddMore, btnSave, btnPrintBill;
	OrderSummaryAdapter orderSummaryAdapter;
	ProgressDialog progress;
	Context context;
	TokenIdStorage storage;
	String userToken = null, createdDateandTime, updatedDateandTime, tableNumber, provider, isBillPrinted;
	TableOrder tableOrder = null;
	List<ProductUpload> productMenu;
	HashMap<String, Integer> menuHashMap;
	HashMap<String, String> extraInfoHashMap;
	ProductUpload summaryProduct;
	int PRODUCT_QUANTITY = 31;
	double latitude = 0f, longitude = 0f;
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.fragment_order_summary, container, false);
		
		lvwOrderSummary = (ListView)rootView.findViewById(R.id.lvwOrderSummary);
		btnAddMore = (Button)rootView.findViewById(R.id.btnAddMore);
		btnSave = (Button)rootView.findViewById(R.id.btnSave);
		btnAddMore = (Button)rootView.findViewById(R.id.btnAddMore);
		
		context = getActivity();
		storage = (TokenIdStorage) getActivity().getApplicationContext();
        userToken = storage.getUserToken();
        final String tableId = getArguments().getString("TableId");
		
		BackgroundTask task = new BackgroundTask();
		final String summaryUrl = "http://54.169.108.240:8080/KiranaService/v1/order/" + tableId + "?userToken=" + userToken;
		task.execute(summaryUrl);
		
		btnSave.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(productMenu != null && productMenu.size() > 0) {
					menuHashMap = new HashMap<String, Integer>();
					extraInfoHashMap = new HashMap<String, String>();
					for(int i = 0;i<productMenu.size();i++) {
						String pdtName = productMenu.get(i).getProductId();
						int quantity = productMenu.get(i).getQuantity();
						//double totalCost = Double.parseDouble(summaryProducts.get(i).getProductTotalCost());
						menuHashMap.put(pdtName, quantity);
					}
					SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
					updatedDateandTime = dateFormatter.format(new Date());
					
					extraInfoHashMap.put("TableNumber", tableNumber);
					extraInfoHashMap.put("LocationCoordinates", latitude + "_" + longitude);
					extraInfoHashMap.put("IsBillPrinted", isBillPrinted);
					
					String uploadMenuUrl = "http://54.169.108.240:8080/KiranaService/v1/order/update?userToken=" + userToken + "&orderId=" + tableId;
					UpdateBackgroundTask task = new UpdateBackgroundTask();
			        if(Common.IsOnline(context)) {
			        	task.execute(uploadMenuUrl);
			        } else {
			        	Common.ShowNoNetworkToast(context);
			        }
				}
			}
		});
		
		btnAddMore.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				navigateToMenuOrderUpdate();
			}
		});
		
		return rootView;
	}
	
	private void navigateToMenuOrderUpdate() {
		MenuOrderFragment menuOrderFragment = new MenuOrderFragment();
		Bundle tableBundle = new Bundle();
		storage.setUpdateOrderSummary(true);
		storage.setProductMenu(productMenu);
		//tableBundle.putString("TableId", tableId);
		menuOrderFragment.setArguments(tableBundle);
		
    	android.support.v4.app.FragmentManager manager = getActivity().getSupportFragmentManager();
    	android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
    	transaction.replace(R.id.homeDrawerFrame, menuOrderFragment);
    	transaction.commit();
	}
	
	private class BackgroundTask extends AsyncTask<String, String, TableOrder> {

        @Override
        protected void onPreExecute() {
            publishProgress("Called the login web service");
            progress = ProgressDialog.show(getActivity(), "Kirana", "Loading Order Summary");
        }

        @Override
        protected TableOrder doInBackground(String... params) {
            String serviceContent = HttpManagerProductGet.GetServiceData(params[0]);
            if(serviceContent != null)
            	tableOrder = TableOrderJSONParser.getOrderForTable(serviceContent);
            return tableOrder;
        }

        @Override
        protected void onPostExecute(TableOrder tableOrder) {
        	progress.dismiss();
            if(tableOrder != null) {
            	productMenu = new ArrayList<ProductUpload>();
            	menuHashMap = tableOrder.getOrderList();
            	tableNumber = tableOrder.getExtraInfo().getTableNumber();
            	isBillPrinted = tableOrder.getExtraInfo().getIsBillPrinted();
            	createdDateandTime = tableOrder.getCreatedAt();
            	storage.setCreatedDateTime(createdDateandTime);
            	
            	for(String key:menuHashMap.keySet()) {
            		summaryProduct = new ProductUpload();
            		summaryProduct.setProductId(key);
            		summaryProduct.setQuantity(menuHashMap.get(key));
            		productMenu.add(summaryProduct);
            	}
            	orderSummaryAdapter = new OrderSummaryAdapter(getActivity(), productMenu, new IOrderSummary(){

					@Override
					public void editOrderItem(int position) {
						// TODO Auto-generated method stub
						Intent ProductQuantity = new Intent(context,OrderQuantityActivity.class);
						ProductQuantity.putExtra("ProductName",productMenu.get(position).getProductId());
						ProductQuantity.putExtra("ProductPrice", productMenu.get(position).getPrice() + "");
						ProductQuantity.putExtra("ProductEdtQty", productMenu.get(position).getQuantity());
						
						startActivityForResult(ProductQuantity, PRODUCT_QUANTITY);
					}

					@Override
					public void deleteOrderItem(int position) {
						// TODO Auto-generated method stub
						productMenu.remove(position);
						orderSummaryAdapter.notifyDataSetChanged();
					}
                });
            	lvwOrderSummary.setAdapter(orderSummaryAdapter);
            }  else {
            	String serviceMsg = "Web service error";
            	Common.ShowWebServiceResponse(context, serviceMsg);
            }
        }
    }
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == Activity.RESULT_OK) {
			ProductUpload selectedProduct;
			String productNameSelected = data.getStringExtra("ProductNameSelected");
			int qty = data.getIntExtra("ProductQty", 0);
			//double productPrice = data.getDoubleExtra("ProductPrice", 0.0);
			for(int i = 0;i<productMenu.size();i++) {
				if(productNameSelected.equalsIgnoreCase(productMenu.get(i).getProductId())) {
					
					selectedProduct = new ProductUpload();
					selectedProduct.setProductId(productMenu.get(i).getProductId());
					selectedProduct.setQuantity(qty);
					selectedProduct.setPrice(productMenu.get(i).getPrice());
					
					productMenu.set(i, selectedProduct);
					orderSummaryAdapter.notifyDataSetChanged();
					break;
				}
			}
		}
	}
	
	private class UpdateBackgroundTask extends AsyncTask<String, String, Integer> {

        @Override
        protected void onPreExecute() {
            publishProgress("Called the login web service");
            progress = ProgressDialog.show(getActivity(), "Kirana", "Updating Order Summary");
        }

        @Override
        protected Integer doInBackground(String... params) {
        	JSONObject obj = new JSONObject();
			try {
				
				String strMenu = menuHashMap.toString().replace('=', ':');
				String strExtraInfo = extraInfoHashMap.toString().replace('=', ':');
				
				JSONObject menuObject = new JSONObject(strMenu);
				JSONObject extraInfoObject = new JSONObject(strExtraInfo);
				
				obj.put("createdAt", createdDateandTime);
				obj.put("updatedAt", updatedDateandTime);
				obj.put("orderList", menuObject);
				obj.put("extraInfo", extraInfoObject);
				
				Integer responseCode = null;
				String objString = obj.toString();
				
				URL url = new URL(params[0]);
                HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
                urlConnection.setRequestMethod("PUT");
                urlConnection.setRequestProperty("Content-Type","application/json");
                try {
                    urlConnection.setDoOutput(true);
                    urlConnection.setChunkedStreamingMode(0);

                    OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
                    out.write(objString.getBytes());
                    out.flush();
                    out.close();

                    InputStream is = new BufferedInputStream(urlConnection.getInputStream());
                    BufferedReader in = new BufferedReader(new InputStreamReader(is));
                    //while ((inputLine = in.readLine()) != null)
                        //System.out.println(inputLine);
                    String strResponseAfterOrder = in.readLine();
                    is.close();
                }catch(Exception e) {
                    e.printStackTrace();
                }
                finally {
                    responseCode = urlConnection.getResponseCode();
                    String responseString = urlConnection.getResponseMessage();
                    
                    urlConnection.disconnect();
                }
                return responseCode;
            } catch(Exception e) {

            }
			return null;
        }

        @Override
        protected void onPostExecute(Integer responseCode) {
        	progress.dismiss();
        }
    }

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		latitude = location.getLatitude();
	    longitude = location.getLongitude();
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

}
