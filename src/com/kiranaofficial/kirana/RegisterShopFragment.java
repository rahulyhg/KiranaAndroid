package com.kiranaofficial.kirana;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Karan-PC on 17-05-2015.
 */
public class RegisterShopFragment extends Fragment {

    Button btnCreateShop;
    EditText edtShopName, edtAddress, edtMobileNumber, edtWebsite, edtTinNumber, edtServiceTax, edtServiceCharge, edtVat;
    Spinner spnShopType;
    String strShopName, strAddress, strMobileNumber, strWebsite, strTinNumber, strServiceTax, strServiceCharge, strShopType, strVat;
    TokenIdStorage storage;
    Context context;
    ProgressDialog progress;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_register_shop, container, false);

        edtShopName = (EditText)rootView.findViewById(R.id.edtShopName);
        edtAddress = (EditText)rootView.findViewById(R.id.edtAddress);
        edtMobileNumber = (EditText)rootView.findViewById(R.id.edtMobileNumber);
        edtWebsite = (EditText)rootView.findViewById(R.id.edtWebsite);
        edtTinNumber = (EditText)rootView.findViewById(R.id.edtTinNumber);
        edtServiceTax = (EditText)rootView.findViewById(R.id.edtServiceTax);
        edtServiceCharge = (EditText)rootView.findViewById(R.id.edtServiceCharge);
        edtVat = (EditText)rootView.findViewById(R.id.edtVat);
        spnShopType = (Spinner)rootView.findViewById(R.id.spnShopType);
        btnCreateShop = (Button)rootView.findViewById(R.id.btnCreateShop);
        context = getActivity();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.shop_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnShopType.setAdapter(adapter);

        storage = (TokenIdStorage) getActivity().getApplicationContext();
        final Context context = getActivity();

        btnCreateShop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                strShopName = edtShopName.getText().toString().trim();
                strAddress = edtAddress.getText().toString().trim();
                strMobileNumber = edtMobileNumber.getText().toString().trim();
                strWebsite = edtWebsite.getText().toString().trim();
                strTinNumber = edtTinNumber.getText().toString().trim();
                strServiceTax = edtServiceTax.getText().toString().trim();
                strServiceCharge = edtServiceCharge.getText().toString().trim();
                strVat = edtVat.getText().toString().trim();
                strShopType = spnShopType.getSelectedItem().toString();
                
                if(strShopName.isEmpty()) {
                	edtShopName.setError("Please enter Shop Name");
                } else if(strAddress.isEmpty()) {
                	edtAddress.setError("Please enter Address");
                } else if(strMobileNumber.isEmpty()) {
                	edtMobileNumber.setError("Please enter Mobile Number");
                } else if(strTinNumber.isEmpty()) {
                	edtTinNumber.setError("Please enter Tin Number");
                } else if(strVat.isEmpty()) {
                	edtVat.setError("Please enter Vat");
                } else if(strServiceTax.isEmpty()) {
                	edtServiceTax.setError("Please enter Service Tax");
                } else if(strServiceCharge.isEmpty()) {
                	edtServiceCharge.setError("Please enter Service Charge");
                } else {
                	String[] registerShopData = {strShopName, strAddress, strMobileNumber, strWebsite, strTinNumber, strServiceTax, strServiceCharge, strVat, strShopType};
                    BackgroundTask task = new BackgroundTask();
                    if(Common.IsOnline(context)) {
                    	task.execute(registerShopData);
                    } else {
    	            	Common.ShowNoNetworkToast(context);
    	            }
                }
            }

        });

        return rootView;
    }

    private class BackgroundTask extends AsyncTask<String, String, Integer> {

        @Override
        protected void onPreExecute() {
            publishProgress("Yo started");
            progress = ProgressDialog.show(getActivity(), "Kirana", "Registering Shop");
        }

        @Override
        protected Integer doInBackground(String... params) {
            JSONObject obj = new JSONObject();
            Integer responseCode = null;
            try {
                obj.put("name", params[0]);
                obj.put("address", params[1]);
                obj.put("website", params[3]);
                obj.put("tin", params[4]);
                obj.put("serviceTax", params[5]);
                obj.put("serviceCharge", params[6]);
                obj.put("vat", params[7]);
                obj.put("type", params[8]);
                String userToken = storage.getUserToken();

                String objString = obj.toString();
                String strUrl = "http://52.0.139.50:8080/KiranaService/v1/shop/register?userToken=" + userToken;
                URL url = new URL(strUrl);
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
                    String inputLine;
                    while ((inputLine = in.readLine()) != null)
                        System.out.println(inputLine);

                    is.close();
                }catch(Exception e) {
                    e.printStackTrace();
                }
                finally {
                    responseCode = urlConnection.getResponseCode();
                    urlConnection.disconnect();
                }
            } catch(Exception e) {

            }
            System.out.print(obj);
            return responseCode;
        }

        @Override
        protected void onPostExecute(Integer statusCode) {
        	progress.dismiss();
        	if(statusCode != null) {
            	if(statusCode == 200) {
            		getFragmentManager()
                    .beginTransaction()
                    .add(R.id.homeDrawerFrame, new UploadProductListFragment())
                    .commit();
                } else if(statusCode == 400) {
                	String serviceMsg = "User already has one shop";
                	Common.ShowWebServiceResponse(context, serviceMsg);
                } else if(statusCode == 401) {
                	String serviceMsg = "Unauthorized user";
                	Common.ShowWebServiceResponse(context, serviceMsg);
                } else if(statusCode == 401){
                	String serviceMsg = "Service forbidden";
                	Common.ShowWebServiceResponse(context, serviceMsg);
                } else if(statusCode == 500){
                	String serviceMsg = "500 Web service error";
                	Common.ShowWebServiceResponse(context, serviceMsg);
                }
        	} else {
        		String serviceMsg = "Web service error";
            	Common.ShowWebServiceResponse(context, serviceMsg);
        	}
        }
    }
}
