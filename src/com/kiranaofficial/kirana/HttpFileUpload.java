package com.kiranaofficial.kirana;

/**
 * Created by Karan-PC on 31-05-2015.
 */
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;

/*import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;*/

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.kiranaofficial.kirana.AndroidMultiPartEntity.ProgressListener;


public class HttpFileUpload implements Runnable{
    URL connectURL;
    String responseString;
    String Title;
    String Description;
    byte[ ] dataToServer;
    FileInputStream fileInputStream = null;
    String urlString, title;

    public HttpFileUpload(String urlString, String vTitle, String vDesc){
        try{
            this.urlString = urlString;
            this.title = vTitle;
            connectURL = new URL(urlString);
            Title= vTitle;
            Description = vDesc;
        }catch(Exception ex){
            Log.i("HttpFileUpload","URL Malformatted");
        }
    }

    public void Send_Now(FileInputStream fStream) throws IOException {
        //fileInputStream = fStream;
        UploadWithApache();
    }

    void UploadWithApache() {

        /*InputStream inputStream;
        try {
            inputStream = new FileInputStream(new File("/sdcard/CSVs/Beauty.csv"));
            byte[] data;
            try {
                data = IOUtils.toByteArray(inputStream);

                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost("http://52.0.139.50:8080/KiranaService/v1/product/upload?userToken=36bc5fab-ed91-4caf-87ee-444f9277f697");

                InputStreamBody inputStreamBody = new InputStreamBody(new ByteArrayInputStream(data), "/sdcard/CSVs/Beauty.csv");
                MultipartEntity multipartEntity = new MultipartEntity();
                multipartEntity.addPart("file", inputStreamBody);
                httpPost.setEntity(multipartEntity);

                HttpResponse httpResponse = httpClient.execute(httpPost);

                // Handle response back from script.
                if(httpResponse != null) {
                	Log.v("The response is good",httpResponse.toString());
                	String response = httpResponse.toString();
                			
                } else { // Error, no response.
                	Log.v("The response is bad",httpResponse.toString());
                	String response = httpResponse.toString();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }*/
    	
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
    }
}
