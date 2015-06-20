package com.example.karan_pc.kirana.com.example.karan_pc.kirana;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.karan_pc.kirana.R;

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
public class SignupFragment extends Fragment {

    Button btnLogin;
    EditText edtUserName, edtLastName, edtEmailId, edtPassWord, edtPassWordAgain, edtMobileNumber, edtStreet, edtState;
    String strUsername,strLastName, strEmailID, strPassWord, strPassWordAgain, strMobileNumber, strStreet, strState;

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

                String[] registerData = {strFullName, strPassWord, strEmailID, strMobileNumber, strStreet, strState};
                BackgroundTask task = new BackgroundTask();
                task.execute(registerData);
            }
        });

        return rootView;
    }

    private class BackgroundTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            publishProgress("Called the register web service");
        }

        @Override
        protected String doInBackground(String... params) {
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
                    BufferedReader in = new BufferedReader(new InputStreamReader(is));
                    String inputLine;
                    while ((inputLine = in.readLine()) != null)
                        System.out.println(inputLine);

                    is.close();
                }catch(Exception e) {
                    e.printStackTrace();
                }
                finally {
                    urlConnection.disconnect();
                }
            } catch(Exception e) {

            }
            System.out.print(obj);
            return "Success";
        }

        @Override
        protected void onPostExecute(String user) {
            publishProgress(user);
        }
    }
}
