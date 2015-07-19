package com.kiranaofficial.kirana;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Karan-PC on 07-06-2015.
 */
public class GetUserListActivity extends Activity {
    Button btnGetUserList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        btnGetUserList = (Button)findViewById(R.id.btnGetUsers);
        btnGetUserList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String loginUrl = "http://52.0.139.50:8080/KiranaService/v1/user/list?userToken=a38cac3b-a474-4267-abdd-cf7beadd641f";
                BackgroundTask task = new BackgroundTask();
                task.execute(loginUrl);
            }
        });
    }

    private class BackgroundTask extends AsyncTask<String, String, UserList> {

        @Override
        protected void onPreExecute() {
            publishProgress("Yo started");
        }

        @Override
        protected UserList doInBackground(String... params) {
            String serviceContent = HttpManager.GetServiceData(params[0]);
            UserList users = new UserList();
            users = UserListParser.getUserList(serviceContent);
            return users;
        }

        @Override
        protected void onPostExecute(UserList user) {
            if(user != null) {
                if(user.getMajorCode() == 200) {
                    publishProgress("Hehe");
                }
            }
        }
    }
}
