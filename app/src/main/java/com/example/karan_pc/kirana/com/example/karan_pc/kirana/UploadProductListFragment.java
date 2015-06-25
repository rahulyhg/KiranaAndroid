package com.example.karan_pc.kirana.com.example.karan_pc.kirana;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.karan_pc.kirana.R;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by Karan-PC on 23-06-2015.
 */
public class UploadProductListFragment extends Fragment {
    Button btnBrowseCSV, btnUploadLater;
    Intent fileExplorer;
    private static final int REQUEST_PATH = 1;
    String curFileName;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_upload_product_list, container, false);
        btnBrowseCSV = (Button)rootView.findViewById(R.id.btnBrowseCSV);
        btnUploadLater = (Button)rootView.findViewById(R.id.btnUploadLater);

        btnBrowseCSV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fileExplorer = new Intent(getActivity(), FileExplorerActivity.class);
                startActivityForResult(fileExplorer, REQUEST_PATH);
            }
        });

        btnUploadLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    BackgroundTask task = new BackgroundTask();
                    String uploadUrl = "http://52.0.139.50:8080/KiranaService/v1/product/upload?userToken=a38cac3b-a474-4267-abdd-cf7beadd641f";
                    task.execute(uploadUrl);


                } catch (Exception e) {
                    // Error: File not found
                }
            }
        });

        return rootView;
    }

    // Listen for results.
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        // See which child activity is calling us back.
        if (requestCode == REQUEST_PATH){
            if (resultCode == Activity.RESULT_OK) {
                String path = Environment.getExternalStorageDirectory().toString();
                curFileName = data.getStringExtra("GetFileName");
                //edittext.setText(curFileName);
            }
        }
    }

    private class BackgroundTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            publishProgress("Yo started");
        }

        @Override
        protected String doInBackground(String... params) {
            // Set your file path here
            FileInputStream fstrm = null;
            try {
                fstrm = new FileInputStream(curFileName);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            // Set your server page url (and the file title/description)
            HttpFileUpload hfu = new HttpFileUpload(params[0],
                    "Beauty.csv","This is a beautiful CSV file");

            try {
                hfu.Send_Now(fstrm);
            } catch (IOException e) {
                String msg = e.getMessage();
                e.printStackTrace();
            }

            return "Up";
        }

        @Override
        protected void onPostExecute(String uploadStatus) {
            publishProgress("Haha");
        }
    }
}
