package com.example.karan_pc.kirana.com.example.karan_pc.kirana;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.karan_pc.kirana.R;

/**
 * Created by Karan-PC on 05-06-2015.
 */
public class GetUserActity extends Activity{
    Button btnGetUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_user_service);

        btnGetUser = (Button)findViewById(R.id.btnGetUser);
        btnGetUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String loginUrl = "http://52.0.139.50:8080/KiranaService/v1/user/16?userToken=137fa7d2-840b-4b8d-ab88-69532f73f6da";
                BackgroundTask task = new BackgroundTask();
                task.execute(loginUrl);
            }
        });
    }

    private class BackgroundTask extends AsyncTask<String, String, User> {

        @Override
        protected void onPreExecute() {
            publishProgress("Yo started");
        }

        @Override
        protected User doInBackground(String... params) {
            String serviceContent = HttpManager.GetServiceData(params[0]);
            User user = new User();
            user = LoginJSONParser.getLoginData(serviceContent);
            return user;
        }

        @Override
        protected void onPostExecute(User user) {
            if(user != null) {
                if(user.getMajorCode() == 200) {
                    publishProgress("Hehe");
                }
            }
        }
    }
}
