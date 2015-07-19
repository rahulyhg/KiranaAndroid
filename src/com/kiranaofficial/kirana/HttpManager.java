package com.kiranaofficial.kirana;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Karan-PC on 30-05-2015.
 */
public class HttpManager {

    public static String GetServiceData(String strUrl) {

        try {
            URL url = new URL(strUrl);
            BufferedReader reader;
            try {
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                connection.setRequestMethod("POST");
                    String responseMessage = connection.getResponseMessage();
                    StringBuilder builder = new StringBuilder();
                    try{
                        reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        String line = "";

                        while((line = reader.readLine()) != null) {
                            builder.append(line + "\n");
                        }
                        reader.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return builder.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
