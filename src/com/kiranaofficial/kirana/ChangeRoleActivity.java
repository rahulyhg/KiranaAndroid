package com.kiranaofficial.kirana;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
 * Created by Karan-PC on 07-06-2015.
 */
public class ChangeRoleActivity extends Activity{
    Button btnChangeRole;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_role);

        btnChangeRole = (Button)findViewById(R.id.btnChangeRole);
        btnChangeRole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String loginUrl = "http://52.0.139.50:8080/KiranaService/v1/user/change-role";
                BackgroundTask task = new BackgroundTask();
                task.execute(loginUrl);
            }
        });
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
                obj.put("userToken","cef314c4-c7e3-4b6b-9d18-537444d11a2f");
                obj.put("userRole", "USER");
                obj.put("userId", "17");

                String objString = obj.toString();
                String strUrl = "http://52.0.139.50:8080/KiranaService/v1/user/change-role";
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
                    int status = urlConnection.getResponseCode();
                    urlConnection.disconnect();
                }
            } catch(Exception e) {

            }
            System.out.print(obj);
            return "Yo";
        }

        @Override
        protected void onPostExecute(String status) {

        }
    }
}
