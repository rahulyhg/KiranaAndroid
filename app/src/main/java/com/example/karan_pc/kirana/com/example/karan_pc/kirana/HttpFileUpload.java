package com.example.karan_pc.kirana.com.example.karan_pc.kirana;

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

import org.apache.commons.io.IOUtils;
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
import org.apache.http.util.EntityUtils;


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
        //Sending();
        //UploadToServer();
        UploadWithApache();
    }

    void UploadWithApache() {

        InputStream inputStream;
        try {
            inputStream = new FileInputStream(new File("/sdcard/CSVs/Beauty.csv"));
            byte[] data;
            try {
                data = IOUtils.toByteArray(inputStream);

                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost("http://52.0.139.50:8080/KiranaService/v1/product/upload?userToken=fdasd");

                InputStreamBody inputStreamBody = new InputStreamBody(new ByteArrayInputStream(data), "/sdcard/CSVs/Beauty.csv");
                MultipartEntity multipartEntity = new MultipartEntity();
                multipartEntity.addPart("file", inputStreamBody);
                httpPost.setEntity(multipartEntity);

                HttpResponse httpResponse = httpClient.execute(httpPost);

                // Handle response back from script.
                if(httpResponse != null) {

                } else { // Error, no response.

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
    }

    void UploadToServer() throws IOException {
        final String boundary;
        final String LINE_FEED = "\r\n";
        HttpURLConnection httpConn;
        String charset = "UTF-8";
        OutputStream outputStream;
        PrintWriter writer;

        // creates a unique boundary based on time stamp
        boundary = "===" + System.currentTimeMillis() + "===";

        URL url = new URL(urlString);
        httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setUseCaches(false);
        httpConn.setDoOutput(true); // indicates POST method
        httpConn.setDoInput(true);
        httpConn.setRequestProperty("Content-Type",
                "multipart/form-data");
        //httpConn.setRequestProperty("User-Agent", "Karan");
        //httpConn.setRequestProperty("Test", "V J");
        outputStream = httpConn.getOutputStream();
        writer = new PrintWriter(new OutputStreamWriter(outputStream, charset),
                true);

        //writer.append(title + ": " + title).append(LINE_FEED);
        writer.flush();

        //writer.append("--" + boundary).append(LINE_FEED);
        //writer.append("Content-Disposition: form-data; name=\"" + "Yo" + "\"").append(LINE_FEED);
        //writer.append("Content-Type: text/plain; charset=" + charset).append(LINE_FEED);
        //writer.append(LINE_FEED);
        //writer.append("K").append(LINE_FEED);
        //writer.flush();

        String fileName = title;
        //writer.append("--" + boundary).append(LINE_FEED);
        //writer.append("Content-Disposition: form-data; name=\"" + fileInputStream + "\"; filename=\"" + fileName + "\"").append(LINE_FEED);
        writer.append(fileInputStream + "");
        //writer.append("Content-Type: " + URLConnection.guessContentTypeFromName(fileName)).append(LINE_FEED);
        //writer.append("Content-Transfer-Encoding: binary").append(LINE_FEED);
        //writer.append(LINE_FEED);
        //writer.flush();

        //FileInputStream inputStream = new FileInputStream(uploadFile);
        byte[] buffer = new byte[4096];
        int bytesRead = -1;
        while ((bytesRead = fileInputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        outputStream.flush();
        fileInputStream.close();

        //writer.append(LINE_FEED);
        writer.flush();

        List<String> response = new ArrayList<String>();

        //writer.append(LINE_FEED).flush();
        //writer.append("--" + boundary + "--").append(LINE_FEED);
        writer.close();

        // checks server's status code first
        int status = httpConn.getResponseCode();
        if (status == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    httpConn.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                response.add(line);
            }
            reader.close();
            httpConn.disconnect();
        } else {
            throw new IOException("Server returned non-OK status: " + status);
        }
    }

    void Sending(){
        String iFileName = "Beauty.csv";
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        //String boundary = "*****";
        //String Tag="fSnd";
        String boundary = "";
        String Tag="";
        try
        {
            Log.e(Tag,"Starting Http File Sending to URL");

            // Open a HTTP connection to the URL
            HttpURLConnection conn = (HttpURLConnection)connectURL.openConnection();

            // Allow Inputs
            conn.setDoInput(true);

            // Allow Outputs
            conn.setDoOutput(true);

            // Don't use a cached copy.
            conn.setUseCaches(false);

            // Use a post method.
            conn.setRequestMethod("POST");

            conn.setRequestProperty("Connection", "Keep-Alive");

            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

            DataOutputStream dos = new DataOutputStream(conn.getOutputStream());

            /*dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"title\""+ lineEnd);
            dos.writeBytes(lineEnd);
            dos.writeBytes(Title);
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + lineEnd);

            dos.writeBytes("Content-Disposition: form-data; name=\"description\""+ lineEnd);
            dos.writeBytes(lineEnd);
            dos.writeBytes(Description);
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + lineEnd);

            dos.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\"" + iFileName +"\"" + lineEnd);
            dos.writeBytes(lineEnd);

            Log.e(Tag,"Headers are written");*/

            // create a buffer of maximum size
            int bytesAvailable = fileInputStream.available();

            int maxBufferSize = 1024;
            int bufferSize = Math.min(bytesAvailable, maxBufferSize);
            byte[ ] buffer = new byte[bufferSize];

            // read file and write it into form...
            int bytesRead = fileInputStream.read(buffer, 0, bufferSize);

            while (bytesRead > 0)
            {
                dos.write(buffer, 0, bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable,maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0,bufferSize);
            }
            //dos.writeBytes(lineEnd);
            //dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

            // close streams
            fileInputStream.close();

            dos.flush();

            conn.connect();
            String msg = conn.getResponseMessage();
            int code = conn.getResponseCode();
            Log.e(Tag, "File Sent, Response: " + String.valueOf(conn.getResponseCode()));

            InputStream is = conn.getInputStream();

            // retrieve the response from server
            int ch;

            StringBuffer b = new StringBuffer();
            while( ( ch = is.read() ) != -1 ){ b.append( (char)ch ); }
            String s = b.toString();
            Log.i("Response",s);
            dos.close();
        }
        catch (MalformedURLException ex)
        {
            Log.e(Tag, "URL error: " + ex.getMessage(), ex);
        }

        catch (IOException ioe)
        {
            Log.e(Tag, "IO error: " + ioe.getMessage(), ioe);
        }
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
    }
}
