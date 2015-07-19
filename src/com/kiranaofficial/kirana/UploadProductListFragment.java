package com.kiranaofficial.kirana;

import java.io.File;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.kiranaofficial.kirana.AndroidMultiPartEntity.ProgressListener;

/**
 * Created by Karan-PC on 23-06-2015.
 */
public class UploadProductListFragment extends Fragment {
    Button btnBrowseCSV, btnUploadLater, btnUploadNow;
    TextView txtFilePath;
    Intent fileExplorer;
    private static final int REQUEST_PATH = 1;
    String curFileName;
    private ProgressBar progressBar;
    long totalSize = 0;
    Context context;
    TokenIdStorage storage;
    ProgressDialog progress;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_upload_product_list, container, false);
        btnBrowseCSV = (Button)rootView.findViewById(R.id.btnBrowseCSV);
        btnUploadLater = (Button)rootView.findViewById(R.id.btnUploadLater);
        btnUploadNow = (Button)rootView.findViewById(R.id.btnUploadNow);
        txtFilePath = (TextView)rootView.findViewById(R.id.txtFilePath);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        context = getActivity();
        storage = (TokenIdStorage) getActivity().getApplicationContext();

        btnBrowseCSV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fileExplorer = new Intent(getActivity(), FileExplorerActivity.class);
                startActivityForResult(fileExplorer, REQUEST_PATH);
            }
        });

        btnUploadNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	String fileName = curFileName;
                try {
                	if(curFileName != null || !curFileName.isEmpty()) {
                		BackgroundTask task = new BackgroundTask();
                        String userToken = storage.getUserToken();
                        String uploadUrl = "http://52.0.139.50:8080/KiranaService/v1/product/register?userToken=" + userToken;
                        if(Common.IsOnline(context)) {
                        	task.execute(uploadUrl);
                        } else {
        	            	Common.ShowNoNetworkToast(context);
        	            }
                	} else {
                		Toast.makeText(context, "Please select a CSV file to upload", Toast.LENGTH_LONG).show();
                	}
                } catch (Exception e) {
                	Toast.makeText(context, "File not found!", Toast.LENGTH_LONG).show();
                }
            }
        });
        
        btnUploadLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	getFragmentManager()
                .beginTransaction()
                .add(R.id.homeDrawerFrame, new ShopListFragment())
                .commit();
            }
        });

        return rootView;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == REQUEST_PATH){
            if (resultCode == Activity.RESULT_OK) {
                String path = Environment.getExternalStorageDirectory().toString();
                curFileName = data.getStringExtra("GetFileName");
                txtFilePath.setText(curFileName);
            }
        }
    }

    private class BackgroundTask extends AsyncTask<String, Integer, Integer> {

        @Override
        protected void onPreExecute() {
        	progressBar.setProgress(0);
        	progress = ProgressDialog.show(getActivity(), "Kirana", "Uploading CSV File");
        	super.onPreExecute();
        }

        @Override
		protected void onProgressUpdate(Integer... progress) {
			// TODO Auto-generated method stub
        	progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(progress[0]);
            //txtPercentage.setText(String.valueOf(progress[0]) + "%");
		}

		@Override
        protected Integer doInBackground(String... params) {
        	
        	String responseString = null;
        	Integer statusCode = null;
        	File sourceFile = null;
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(params[0]);
 
            try {
                AndroidMultiPartEntity entity = new AndroidMultiPartEntity(new ProgressListener() {
	 
                    @Override
                    public void transferred(long num) {
                        publishProgress((int) ((num / (float) totalSize) * 100));
                    }
                });
                if(curFileName != null || !curFileName.isEmpty())
                	sourceFile = new File(curFileName);//new
                entity.addPart("file", new FileBody(sourceFile));//new
                totalSize = entity.getContentLength();
                httppost.setEntity(entity);
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity r_entity = response.getEntity();
 
                statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    responseString = EntityUtils.toString(r_entity);
                } else {
                    responseString = statusCode + "";
                }
            } catch (ClientProtocolException e) {
                responseString = e.toString();
            } catch (IOException e) {
                responseString = e.toString();
            }
            return statusCode;
        }

        @Override
        protected void onPostExecute(Integer statusCode) {
        	progress.dismiss();
        	progressBar.setVisibility(View.GONE);
        	if(statusCode != null) {
                if(statusCode == 200) {
                	Common.ShowAlertDialog(getActivity(), "Product list file uploaded successfully!");
                	getFragmentManager()
                    .beginTransaction()
                    .add(R.id.homeDrawerFrame, new ProductListFragment())
                    .commit();
                } else if(statusCode == 400) {
                	String serviceMsg = "Bad Request";
                	Common.ShowWebServiceResponse(context, serviceMsg);
                } else if(statusCode == 401) {
                	String serviceMsg = "Unauthorized user";
                	Common.ShowWebServiceResponse(context, serviceMsg);
                } else if(statusCode == 403){
                	String serviceMsg = "Service forbidden";
                	Common.ShowWebServiceResponse(context, serviceMsg);
                } else if(statusCode == 500){
                	String serviceMsg = "500 Web service error";
                	Common.ShowWebServiceResponse(context, serviceMsg);
                }
        	} else {
        		String serviceMsg = "Web service error";
            	Common.ShowWebServiceResponse(context, serviceMsg);
        	}
        }
    }
}
