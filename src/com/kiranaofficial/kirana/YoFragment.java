package com.kiranaofficial.kirana;

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
import android.widget.EditText;
import android.widget.ListView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Karan-PC on 25-05-2015.
 */
public class YoFragment extends Fragment{
    ListView grdItems;
    private List<Product> persons = new ArrayList<>();
    private static final int REQUEST_PATH = 1;
    String curFileName;
    EditText edittext;
    Button skipButton, btnUpload;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_location_found, container, false);
        //grdItems = (ListView)rootView.findViewById(R.id.grdItems);

        //initializeData();
        //grdItems.setAdapter(new GridAdapter(getActivity(), persons));
        //ScrollViewHelper.getListViewSize(grdItems);

        edittext = (EditText)rootView.findViewById(R.id.editText);
        skipButton = (Button)rootView.findViewById(R.id.skipButton);
        btnUpload = (Button)rootView.findViewById(R.id.btnUpload);

        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1 = new Intent(getActivity(), FileExplorerActivity.class);
                startActivityForResult(intent1, REQUEST_PATH);
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
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

    public void getfile(View view){
        Intent intent1 = new Intent(getActivity(), FileExplorerActivity.class);
        startActivityForResult(intent1,REQUEST_PATH);
    }

    // Listen for results.
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        // See which child activity is calling us back.
        if (requestCode == REQUEST_PATH){
            if (resultCode == Activity.RESULT_OK) {
                String path = Environment.getExternalStorageDirectory().toString();
                curFileName = data.getStringExtra("GetFileName");
                edittext.setText(curFileName);
            }
        }
    }

    private void initializeData(){
        persons.add(new Product("Idly", "Rs. 15", R.drawable.idli));
        persons.add(new Product("Vada", "Rs. 10", R.drawable.vada));
        persons.add(new Product("Dosa", "Rs. 20", R.drawable.dosa));
        persons.add(new Product("Idly", "Rs. 15", R.drawable.idli));
        persons.add(new Product("Vada", "Rs. 10", R.drawable.vada));
        persons.add(new Product("Dosa", "Rs. 20", R.drawable.dosa));
        persons.add(new Product("Idly", "Rs. 15", R.drawable.idli));
        persons.add(new Product("Vada", "Rs. 10", R.drawable.vada));
        persons.add(new Product("Dosa", "Rs. 20", R.drawable.dosa));
        persons.add(new Product("Idly", "Rs. 15", R.drawable.idli));
        persons.add(new Product("Vada", "Rs. 10", R.drawable.vada));
        persons.add(new Product("Dosa", "Rs. 20", R.drawable.dosa));
        persons.add(new Product("Idly", "Rs. 15", R.drawable.idli));
        persons.add(new Product("Vada", "Rs. 10", R.drawable.vada));
        persons.add(new Product("Dosa", "Rs. 20", R.drawable.dosa));
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
