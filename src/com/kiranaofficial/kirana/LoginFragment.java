package com.kiranaofficial.kirana;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Karan-PC on 17-05-2015.
 */
public class LoginFragment extends Fragment {

    Button btnLogin;
    EditText edtUsername, edtPassword;
    Intent homeIntent;
    String strUsername;
    String strPassword;
    TokenIdStorage storage;
    Context context;
    ProgressDialog progress;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        btnLogin = (Button)rootView.findViewById(R.id.btnLogin);
        edtUsername = (EditText)rootView.findViewById(R.id.edtUserName);
        edtPassword = (EditText)rootView.findViewById(R.id.edtPassWord);

        storage = (TokenIdStorage) getActivity().getApplicationContext();
        context = getActivity();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
	            strUsername = edtUsername.getText().toString().trim();
	            strPassword = edtPassword.getText().toString().trim();
	            if(strUsername.isEmpty()) {
	            	edtUsername.setError("Please enter Email ID");
	            } else if(strPassword.isEmpty()) {
	            	edtPassword.setError("Please enter Password");
	            } else {
		            final String loginUrl = "http://54.169.108.240:8080/KiranaService/v1/user/login?email=" + strUsername + "&password=" + strPassword;
		            BackgroundTask task = new BackgroundTask();
		            if(Common.IsOnline(context)) {
		            	task.execute(loginUrl);
		            } else {
		            	Common.ShowNoNetworkToast(context);
		            }
	            }
            }
        });
        return rootView;
    }

    private class BackgroundTask extends AsyncTask<String, String, User> {

        @Override
        protected void onPreExecute() {
            publishProgress("Called the login web service");
            progress = ProgressDialog.show(getActivity(), "Kirana", "Logging In");
        }

        @Override
        protected User doInBackground(String... params) {
            String serviceContent = HttpManager.GetServiceData(params[0]);
            User user = new User();
            if(serviceContent != null)
            	user = LoginJSONParser.getLoginData(serviceContent);
            return user;
        }

        @Override
        protected void onPostExecute(User user) {
        	progress.dismiss();
            if(user != null) {
                if(user.getMajorCode() == 200) {
                	
                	edtUsername.setText("");
                	edtPassword.setText("");
                    storage.setId(user.getId());
                    storage.setUserToken(user.getUserToken());
                    storage.setUserName(user.getUserName());
                    storage.setShop(user.getShop());
                    
                    final String productsUrl = "http://54.169.108.240:8080/KiranaService/v1/product/own?userToken=" + user.getUserToken();
                    DownloadMenuBackgroundTask task = new DownloadMenuBackgroundTask();
                    if(Common.IsOnline(context)) {
                    	task.execute(productsUrl);
                    } else {
                    	Common.ShowNoNetworkToast(context);
                    }
                    
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("loggedInUser", user);
                    homeIntent = new Intent(getActivity(), HomeActivity.class);
                    homeIntent.putExtras(bundle);
                    startActivity(homeIntent);
                } else if(user.getMajorCode() == 401) {
                	String serviceMsg = "Unauthorized user";
                	Common.ShowWebServiceResponse(context, serviceMsg);
                } else if(user.getMajorCode() == 403){
                	String serviceMsg = "Service forbidden";
                	Common.ShowWebServiceResponse(context, serviceMsg);
                } else if(user.getMajorCode() == 500){
                	String serviceMsg = "500 Web service error";
                	Common.ShowWebServiceResponse(context, serviceMsg);
                } else if(user.getMajorCode() == 0){
                	String serviceMsg = "Unauthorized user";
                	Common.ShowWebServiceResponse(context, serviceMsg);
                }
            } else {
            	String serviceMsg = "Web service error";
            	Common.ShowWebServiceResponse(context, serviceMsg);
            }
        }
    }
    
    private class DownloadMenuBackgroundTask extends AsyncTask<String, String, List<ProductUpload>> {

        @Override
        protected void onPreExecute() {
            publishProgress("Called the login web service");
            progress = ProgressDialog.show(getActivity(), "Kirana", "Loading Menu");
        }

        @Override
        protected List<ProductUpload> doInBackground(String... params) {
            String serviceContent = HttpManagerProductGet.GetServiceData(params[0]);
            List<ProductUpload> products = new ArrayList<ProductUpload>();
            if(serviceContent != null)
            	products = ProductJSONParser.getLoginData(serviceContent);
            return products;
        }

        @Override
        protected void onPostExecute(List<ProductUpload> products) {
        	progress.dismiss();
        	storage.setProducts(products);
            if(products != null) {
            	if(products.size() != 0) {
	            	int code = products.get(0).getMajorCode();
	            	if(code == 200) {
	            		
	            	} else if(code == 401) {
	                	String serviceMsg = "Unauthorized user";
	                	Common.ShowWebServiceResponse(context, serviceMsg);
	                } else if(code == 403){
	                	String serviceMsg = "Service forbidden";
	                	Common.ShowWebServiceResponse(context, serviceMsg);
	                } else if(code == 500){
	                	String serviceMsg = "500 Web service error";
	                	Common.ShowWebServiceResponse(context, serviceMsg);
	                }
            	}
            }  else {
            	String serviceMsg = "Web service error";
            	Common.ShowWebServiceResponse(context, serviceMsg);
            }
        }
    }
}
