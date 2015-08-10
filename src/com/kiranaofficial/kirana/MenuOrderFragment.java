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
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.kiranaofficial.kirana.adapters.MenuOrderAdapter;

public class MenuOrderFragment extends android.support.v4.app.Fragment implements LocationListener{
	
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
	boolean isUpdateOrderSummary;
	
	//update summary order fields
	
	List<ProductUpload> productMenu;
	String updatedDateTime;
	ProductUpload summaryProductUpdate;
	
	//end
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_menu_order, container, false);
        
        lvwMenuOrder = (ListView)rootView.findViewById(R.id.lvwMenuOrder);
        btnMenuOrder = (Button)rootView.findViewById(R.id.btnMenuOrder);
        searchProducts = (EditText)rootView.findViewById(R.id.searchProducts);
        txtCartQty = (TextView)rootView.findViewById(R.id.txtCartQty);
        
        summaryProducts = new ArrayList<ProductOrderSummary>();
        
        storage = (TokenIdStorage) getActivity().getApplicationContext();
        userToken = storage.getUserToken();
        tableNumber = getArguments().getString("TableNumber");
        context = getActivity();
        
        //update order summary
        isUpdateOrderSummary = storage.isUpdateOrderSummary();
        storage.setUpdateOrderSummary(false);
        productMenu = storage.getProductMenu();
        if(productMenu == null) {
        	productMenu = new ArrayList<ProductUpload>();
        }
        //end
        
        initializeData();
        locationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        Location location = locationManager.getLastKnownLocation(provider);
        
        if (location != null) {
          onLocationChanged(location);
        } else {
        }
        
        searchProducts.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				String queryProduct = searchProducts.getText().toString().toLowerCase();
				if(adapter != null)
					adapter.filter(queryProduct);
			}
		});
        
        btnMenuOrder.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!isUpdateOrderSummary) { 
					if(summaryProducts != null && summaryProducts.size() > 0) {
						menuHashMap = new HashMap<String, Integer>();
						extraInfoHashMap = new HashMap<String, String>();
						for(int i = 0;i<summaryProducts.size();i++) {
							String pdtName = summaryProducts.get(i).productName;
							int quantity = Integer.parseInt(summaryProducts.get(i).productQty);
							//double totalCost = Double.parseDouble(summaryProducts.get(i).getProductTotalCost());
							menuHashMap.put(pdtName, quantity);
						}
						SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
						currentDateandTime = dateFormatter.format(new Date());
						
						extraInfoHashMap.put("TableNumber", tableNumber);
						extraInfoHashMap.put("LocationCoordinates", latitude + "_" + longitude);
						extraInfoHashMap.put("IsBillPrinted", "NO");
						
						String uploadMenuUrl = "http://54.169.108.240:8080/KiranaService/v1/order/create?userToken=" + userToken;
						UploadOrdersBackgroundTask task = new UploadOrdersBackgroundTask();
				        if(Common.IsOnline(context)) {
				        	task.execute(uploadMenuUrl);
				        } else {
				        	Common.ShowNoNetworkToast(context);
				        }
					}
				} else {
					//for update summary
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
						updatedDateTime = dateFormatter.format(new Date());
						
						extraInfoHashMap.put("TableNumber", storage.getTableNumber());
						extraInfoHashMap.put("LocationCoordinates", latitude + "_" + longitude);
						extraInfoHashMap.put("IsBillPrinted", "NO");
						
						String uploadMenuUrl = "http://54.169.108.240:8080/KiranaService/v1/order/update?userToken=" + userToken + "&orderId=" + storage.getTableId();
						UpdateOrdersBackgroundTask task = new UpdateOrdersBackgroundTask();
				        if(Common.IsOnline(context)) {
				        	task.execute(uploadMenuUrl);
				        } else {
				        	Common.ShowNoNetworkToast(context);
				        }
					}
				}
			}
		});
        
        return rootView;
	}
	
	/* Request updates at startup */
	  @Override
	public void onResume() {
	    super.onResume();
	    locationManager.requestLocationUpdates(provider, 400, 1, this);
	  }

    /* Remove the locationlistener updates when Activity is paused */
	  @Override
	public void onPause() {
	    super.onPause();
	    locationManager.removeUpdates(this);
    }

	private void initializeData(){
		List<ProductUpload> products = new ArrayList<ProductUpload>();
		products = storage.getProducts();
		if(products != null) {
			adapter = new MenuOrderAdapter(getActivity(),products, new IOrderQuantity() {
	
				@Override
				public void getOrderQuantity(String strProductName,String strProductPrice) {
					// TODO Auto-generated method stub
					if(!isUpdateOrderSummary) {
						boolean isEdit = false;
						for(int i = 0;i<summaryProducts.size();i++) {
							if(strProductName.equalsIgnoreCase(summaryProducts.get(i).getProductName())) {
								edtProductQty = Integer.parseInt(summaryProducts.get(i).getProductQty());
								isEdit = true;
								break;
							} else {
								edtProductQty = 0;
								isEdit = false;
							}
						}
						Intent ProductQuantity = new Intent(context,OrderQuantityActivity.class);
						ProductQuantity.putExtra("ProductName",strProductName);
						ProductQuantity.putExtra("ProductPrice", strProductPrice);
						if(isEdit)
							ProductQuantity.putExtra("ProductEdtQty", edtProductQty);
						else 
							ProductQuantity.putExtra("ProductEdtQty", 0);
						
						startActivityForResult(ProductQuantity, PRODUCT_QUANTITY);
					} else {
						//for order summary update
						boolean isEdit = false;
						for(int i = 0;i<productMenu.size();i++) {
							if(strProductName.equalsIgnoreCase(productMenu.get(i).getProductId())) {
								edtProductQty = productMenu.get(i).getQuantity();
								isEdit = true;
								break;
							} else {
								edtProductQty = 0;
								isEdit = false;
							}
						}
						Intent ProductQuantity = new Intent(context,OrderQuantityActivity.class);
						ProductQuantity.putExtra("ProductName",strProductName);
						ProductQuantity.putExtra("ProductPrice", strProductPrice);
						if(isEdit)
							ProductQuantity.putExtra("ProductEdtQty", edtProductQty);
						else 
							ProductQuantity.putExtra("ProductEdtQty", 0);
						
						startActivityForResult(ProductQuantity, PRODUCT_QUANTITY);
					}
					//end
				}
			});
			lvwMenuOrder.setAdapter(adapter);
		}
    }
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		ProductOrderSummary summaryProduct;
		if(!isUpdateOrderSummary) {
			if(resultCode == Activity.RESULT_OK) {
				boolean isContainsProduct = false;
				int editedQtyProductPos = 0;
				String productNameSelected = data.getStringExtra("ProductNameSelected");
				int qty = data.getIntExtra("ProductQty", 0);
				//double productPrice = data.getDoubleExtra("ProductPrice", 0.0);
				
				if(qty > 0) {
					for(int i = 0;i<summaryProducts.size();i++) {
						if(productNameSelected.equalsIgnoreCase(summaryProducts.get(i).getProductName())) {
							isContainsProduct = true;
							editedQtyProductPos = i;
							break;
						}
					}
					if(!isContainsProduct) {
						summaryProduct = new ProductOrderSummary();
						summaryProduct.setProductName(productNameSelected);
						summaryProduct.setProductQty(qty + "");
						//summaryProduct.setProductTotalCost(productPrice + "");
						summaryProducts.add(summaryProduct);
					} else {
						summaryProduct = new ProductOrderSummary();
						summaryProduct.setProductName(productNameSelected);
						summaryProduct.setProductQty(qty + "");
						//summaryProduct.setProductTotalCost(productPrice + "");
						summaryProducts.set(editedQtyProductPos, summaryProduct);
					}
				} else {
					boolean isRemoveProduct = false;
					int removePos = 0;
					for(int i = 0;i<summaryProducts.size();i++) {
						if(productNameSelected.equalsIgnoreCase(summaryProducts.get(i).getProductName())) {
							isRemoveProduct = true;
							removePos = i;
							break;
						}
					}
					if(isRemoveProduct) {
						summaryProducts.remove(removePos);
					}
				}
				if(summaryProducts.size() != 0) {
					txtCartQty.setVisibility(View.VISIBLE);
					txtCartQty.setText(summaryProducts.size() + "");
				} else {
					txtCartQty.setVisibility(View.INVISIBLE);
				}
			}
		} else {
			//update order summary
			if(resultCode == Activity.RESULT_OK) {
				boolean isContainsProduct = false;
				int editedQtyProductPos = 0;
				String productNameSelected = data.getStringExtra("ProductNameSelected");
				int qty = data.getIntExtra("ProductQty", 0);
				//double productPrice = data.getDoubleExtra("ProductPrice", 0.0);
				
				if(qty > 0) {
					for(int i = 0;i<productMenu.size();i++) {
						if(productNameSelected.equalsIgnoreCase(productMenu.get(i).getProductId())) {
							isContainsProduct = true;
							editedQtyProductPos = i;
							break;
						}
					}
					if(!isContainsProduct) {
						summaryProductUpdate = new ProductUpload();
						summaryProductUpdate.setProductId(productNameSelected);
						summaryProductUpdate.setQuantity(qty);
						//summaryProduct.setProductTotalCost(productPrice + "");
						productMenu.add(summaryProductUpdate);
					} else {
						summaryProductUpdate = new ProductUpload();
						summaryProductUpdate.setProductId(productNameSelected);
						summaryProductUpdate.setQuantity(qty);
						//summaryProduct.setProductTotalCost(productPrice + "");
						productMenu.set(editedQtyProductPos, summaryProductUpdate);
					}
				} else {
					boolean isRemoveProduct = false;
					int removePos = 0;
					for(int i = 0;i<productMenu.size();i++) {
						if(productNameSelected.equalsIgnoreCase(productMenu.get(i).getProductId())) {
							isRemoveProduct = true;
							removePos = i;
							break;
						}
					}
					if(isRemoveProduct) {
						productMenu.remove(removePos);
					}
				}
				if(productMenu.size() != 0) {
					txtCartQty.setVisibility(View.VISIBLE);
					txtCartQty.setText(productMenu.size() + "");
				} else {
					txtCartQty.setVisibility(View.INVISIBLE);
				}
			}
		}
	}
	
	private class UploadOrdersBackgroundTask extends AsyncTask<String, String, Integer> {
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected Integer doInBackground(String... params) {
			// TODO Auto-generated method stub
			JSONObject obj = new JSONObject();
			try {
				
				String strMenu = menuHashMap.toString().replace('=', ':');
				String strExtraInfo = extraInfoHashMap.toString().replace('=', ':');
				
				JSONObject menuObject = new JSONObject(strMenu);
				JSONObject extraInfoObject = new JSONObject(strExtraInfo);
				
				obj.put("createdAt", currentDateandTime);
				obj.put("updatedAt", currentDateandTime);
				obj.put("orderList", menuObject);
				obj.put("extraInfo", extraInfoObject);
				
				Integer responseCode = null;
				String objString = obj.toString();
				
				URL url = new URL(params[0]);
                HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
                urlConnection.setRequestMethod("POST");
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
                    strResponseAfterOrder = in.readLine();
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
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(result == 200) {
				tableOrder = TableOrderJSONParser.getOrderForTable(strResponseAfterOrder);
				String tableId = tableOrder.getId();
				
				OrderSummaryFragment orderSummaryFragment = new OrderSummaryFragment();
				Bundle tableBundle = new Bundle();
				tableBundle.putString("TableId", tableId);
				
				//doubtful
				storage.setTableId(tableId);
				orderSummaryFragment.setArguments(tableBundle);
				
            	android.support.v4.app.FragmentManager manager = getActivity().getSupportFragmentManager();
            	android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
            	transaction.replace(R.id.homeDrawerFrame, orderSummaryFragment);
            	transaction.commit();
			}
		}
		
	}
	
	private class UpdateOrdersBackgroundTask extends AsyncTask<String, String, Integer> {
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected Integer doInBackground(String... params) {
			JSONObject obj = new JSONObject();
			try {
				
				String strMenu = menuHashMap.toString().replace('=', ':');
				String strExtraInfo = extraInfoHashMap.toString().replace('=', ':');
				
				JSONObject menuObject = new JSONObject(strMenu);
				JSONObject extraInfoObject = new JSONObject(strExtraInfo);
				
				obj.put("createdAt", storage.getCreatedDateTime());
				obj.put("updatedAt", updatedDateTime);
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
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			OrderSummaryFragment orderSummaryFragment = new OrderSummaryFragment();
			Bundle tableBundle = new Bundle();
			tableBundle.putString("TableId", storage.getTableId());
			orderSummaryFragment.setArguments(tableBundle);
			
        	android.support.v4.app.FragmentManager manager = getActivity().getSupportFragmentManager();
        	android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
        	transaction.replace(R.id.homeDrawerFrame, orderSummaryFragment);
        	transaction.commit();
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
