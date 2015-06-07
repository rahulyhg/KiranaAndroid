package com.example.karan_pc.kirana.com.example.karan_pc.kirana;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_signup, container, false);
        btnLogin = (Button)rootView.findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BackgroundTask task = new BackgroundTask();
                task.execute("Haha");
            }
        });

        return rootView;
    }

    private class BackgroundTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            publishProgress("Yo started");
        }

        @Override
        protected String doInBackground(String... params) {
            JSONObject obj = new JSONObject();
            try {
                obj.put("id",0);
                obj.put("userName", "aaavo");
                obj.put("password", "pwdpwd");
                obj.put("userToken", null);
                obj.put("userRole", null);
                obj.put("email", "qwerty@asd.com");
                obj.put("phone", "3355");
                obj.put("street", "Baker St.");
                obj.put("state", "KL");
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
                    //InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    //readStream(in);
                }catch(Exception e) {
                    e.printStackTrace();
                }
                finally {
                    urlConnection.disconnect();
                }
            } catch(Exception e) {

            }
            System.out.print(obj);
            return "Yo";
        }

        @Override
        protected void onPostExecute(String user) {
            publishProgress(user);
        }
    }
}
