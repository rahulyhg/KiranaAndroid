package com.example.karan_pc.kirana.com.example.karan_pc.kirana;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.karan_pc.kirana.R;

/**
 * Created by Karan-PC on 07-06-2015.
 */
public class DeleteUserActivity extends Activity {
    Button btnDeleteUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_user);

        btnDeleteUser = (Button)findViewById(R.id.btnDeleteUser);
        btnDeleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String loginUrl = "http://52.0.139.50:8080/KiranaService/v1/user/delete/16?userToken=137fa7d2-840b-4b8d-ab88-69532f73f6da";
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
            String serviceContent = HttpManager.GetServiceData(params[0]);
            return null;
        }

        @Override
        protected void onPostExecute(String user) {

        }
    }
}
