package com.example.karan_pc.kirana.com.example.karan_pc.kirana;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Karan-PC on 22-06-2015.
 */
public class HttpManagerDelete {

    public static String GetServiceData(String strUrl) {
        String responseMessage = null;
        try {
            URL url = new URL(strUrl);
            BufferedReader reader;
            try {
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                connection.setRequestMethod("DELETE");
                responseMessage = connection.getResponseMessage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return responseMessage;
    }
}

