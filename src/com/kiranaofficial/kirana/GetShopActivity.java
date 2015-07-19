package com.kiranaofficial.kirana;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.json.JSONException;
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
public class GetShopActivity extends Activity{
    Button btnGetShop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_role);

        btnGetShop = (Button)findViewById(R.id.btnGetShop);
        btnGetShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String loginUrl = "http://52.0.139.50:8080/KiranaService/v1/shop/1?userToken=a38cac3b-a474-4267-abdd-cf7beadd641f";
                BackgroundTask task = new BackgroundTask();
                task.execute(loginUrl);
            }
        });
    }

    private class BackgroundTask extends AsyncTask<String, String, Shop> {

        @Override
        protected void onPreExecute() {
            publishProgress("Yo started");
        }

        @Override
        protected Shop doInBackground(String... params) {
            String serviceContent = HttpManager.GetServiceData(params[0]);
            JSONObject object = null;
            try {
                object = new JSONObject(serviceContent);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JSONObject parentObject = new JSONObject();
            try {
                parentObject = object.getJSONObject("object");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Shop shop = new Shop();
            try {
                shop.setMajorCode(object.getInt("majorCode"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                shop.setId(parentObject.getInt("id"));
                shop.setName(parentObject.getString("name"));
                shop.setType(parentObject.getString("type"));
                shop.setAddress(parentObject.getString("address"));
                shop.setTin(parentObject.getString("tin"));
                shop.setServiceTax(parentObject.getDouble("serviceTax"));
                shop.setServiceCharge(parentObject.getDouble("serviceCharge"));
                shop.setVat(parentObject.getDouble("vat"));
                shop.setWebsite(parentObject.getString("website"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return shop;
        }

        @Override
        protected void onPostExecute(Shop status) {

        }
    }
}
