package com.kiranaofficial.kirana;

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

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Karan-PC on 17-05-2015.
 */
public class SignupFragment extends Fragment {

    Button btnLogin;
    EditText edtUserName, edtLastName, edtEmailId, edtPassWord, edtPassWordAgain, edtMobileNumber, edtStreet, edtState;
    String strUsername,strLastName, strEmailID, strPassWord, strPassWordAgain, strMobileNumber, strStreet, strState;
    Context context;
    ProgressDialog progress;
    String regex = "^(.+)@(.+)$";

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_signup, container, false);
        btnLogin = (Button)rootView.findViewById(R.id.btnLogin);
        edtUserName = (EditText)rootView.findViewById(R.id.edtUserName);
        edtLastName = (EditText)rootView.findViewById(R.id.edtLastName);
        edtEmailId = (EditText)rootView.findViewById(R.id.edtEmailId);
        edtPassWord = (EditText)rootView.findViewById(R.id.edtPassWord);
        edtPassWordAgain = (EditText)rootView.findViewById(R.id.edtPassWordAgain);
        edtMobileNumber = (EditText)rootView.findViewById(R.id.edtMobileNumber);
        edtStreet = (EditText)rootView.findViewById(R.id.edtStreet);
        edtState = (EditText)rootView.findViewById(R.id.edtState);
        context = getActivity();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                strUsername = edtUserName.getText().toString().trim();
                strLastName = edtLastName.getText().toString().trim();
                strEmailID = edtEmailId.getText().toString().trim();
                strPassWord = edtPassWord.getText().toString().trim();
                strPassWordAgain = edtPassWordAgain.getText().toString().trim();
                strMobileNumber = edtMobileNumber.getText().toString().trim();
                strStreet = edtStreet.getText().toString().trim();
                strState = edtState.getText().toString().trim();

                String strFullName = strUsername + " " + strLastName;
                
                final Context context = getActivity();
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(strEmailID);
                if(strUsername.isEmpty()) {
                	edtUserName.setError("Please enter First Name");
                }else if(strLastName.isEmpty()) {
                	edtLastName.setError("Please enter Last Name");
                } else if(strEmailID.isEmpty()) {
                	edtEmailId.setError("Please enter Email ID");
                } else if(!matcher.matches()) {
            		edtEmailId.setError("Please enter proper Email ID");
            	} else if(strPassWord.isEmpty()) {
                	edtPassWord.setError("Please enter Password");
                } else if(strPassWordAgain.isEmpty()) {
                	edtPassWordAgain.setError("Please enter Re-enter Password");
                } else if(!strPassWord.equals(strPassWordAgain)) {
            		edtPassWordAgain.setError("Re-enter Password not matching Password");
            	} else if(strMobileNumber.isEmpty()) {
                	edtMobileNumber.setError("Please enter Mobile Number");
                } else if(strStreet.isEmpty()) {
                	edtStreet.setError("Please enter Address");
                } else if(strState.isEmpty()) {
                	edtState.setError("Please enter State");
                } else {
                	String[] registerData = {strFullName, strPassWord, strEmailID, strMobileNumber, strStreet, strState};
                    BackgroundTask task = new BackgroundTask();
                    if(Common.IsOnline(context)) {
                    	task.execute(registerData);
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
            publishProgress("Called the register web service");
            progress = ProgressDialog.show(getActivity(), "Kirana", "Registering User");
        }

        @Override
        protected Integer doInBackground(String... params) {
        	Integer responseCode = null;
            JSONObject obj = new JSONObject();
            try {
                obj.put("id",0);
                obj.put("userName", params[0]);
                obj.put("password", params[1]);
                obj.put("userToken", null);
                obj.put("userRole", null);
                obj.put("email", params[2]);
                obj.put("phone", params[3]);
                obj.put("street", params[4]);
                obj.put("state", params[5]);
                obj.put("shop",null);

                String objString = obj.toString();
                String strUrl = "http://52.0.139.50:8080/KiranaService/v1/user/register";
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
                    BufferedReader in = null;
                    if(is != null)
                    	in = new BufferedReader(new InputStreamReader(is));
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        System.out.println(inputLine);
                    }
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
        protected void onPostExecute(Integer responseCode) {
        	progress.dismiss();
        	if(responseCode != null) {
                if(responseCode == 200) {
                	Common.ShowAlertDialog(getActivity(), "User registered successfully!");
                	edtUserName.setText("");
                	edtLastName.setText("");
                	edtEmailId.setText("");
                	edtPassWord.setText("");
                	edtPassWordAgain.setText("");
                	edtMobileNumber.setText("");
                	edtStreet.setText("");
                	edtState.setText("");
                } else if(responseCode == 401) {
                	String serviceMsg = "Unauthorized user";
                	Common.ShowWebServiceResponse(context, serviceMsg);
                } else if(responseCode == 400) {
                	String serviceMsg = "Bad Request";
                	Common.ShowWebServiceResponse(context, serviceMsg);
                } else if(responseCode == 403){
                	String serviceMsg = "Service forbidden";
                	Common.ShowWebServiceResponse(context, serviceMsg);
                } else if(responseCode == 500){
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
