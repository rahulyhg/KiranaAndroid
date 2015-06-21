package com.example.karan_pc.kirana.com.example.karan_pc.kirana;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.karan_pc.kirana.LoginActivity;
import com.example.karan_pc.kirana.R;

/**
 * Created by Karan-PC on 17-05-2015.
 */
public class LoginFragment extends Fragment {

    Button btnLogin;
    EditText edtUsername, edtPassword;
    Intent homeIntent;
    String strUsername;
    String strPassword;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        btnLogin = (Button)rootView.findViewById(R.id.btnLogin);
        edtUsername = (EditText)rootView.findViewById(R.id.edtUserName);
        edtPassword = (EditText)rootView.findViewById(R.id.edtPassWord);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            strUsername = edtUsername.getText().toString().trim();
            strPassword = edtPassword.getText().toString().trim();
            final String loginUrl = "http://52.0.139.50:8080/KiranaService/v1/user/login?email=" + strUsername + "&password=" + strPassword;
            BackgroundTask task = new BackgroundTask();
            task.execute(loginUrl);
            }
        });
        return rootView;
    }

    /*public boolean IsOnline() {
        ConnectivityManager manager = (ConnectivityManager)getSystemService(this.getActivity().CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            return true;
        } else
            return false;
    }*/

    private class BackgroundTask extends AsyncTask<String, String, User> {

        @Override
        protected void onPreExecute() {
            publishProgress("Called the login web service");
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
                    /*homeIntent = new Intent(getActivity(), HomeActivity.class);
                    homeIntent.putExtra("loggedInUser", user);
                    startActivity(homeIntent);*/
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("loggedInUser", user);
                    //set Fragmentclass Arguments
                    //ShopListFragment fragobj = new ShopListFragment();
                    //fragobj.setArguments(bundle);
                    homeIntent = new Intent(getActivity(), HomeActivity.class);
                    homeIntent.putExtras(bundle);
                    startActivity(homeIntent);
                }
            }
        }
    }
}
