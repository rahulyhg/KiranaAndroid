package com.kiranaofficial.kirana;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class Common {
	
	public static boolean IsOnline(Context context) {
		ConnectivityManager manager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo networkInfo = manager.getActiveNetworkInfo();
	    if(networkInfo != null && networkInfo.isConnectedOrConnecting()) {
	        return true;
	    } else
	        return false;
	}
	
	public static void ShowNoNetworkToast(Context context) {
		Toast.makeText(context, "No Internet Connectivity", Toast.LENGTH_LONG).show();
	}
	
	public static void ShowWebServiceResponse(Context context, String msg) {
		Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
	}
	
	public static void ShowAlertDialog(Context context, String alertMsg) {
		Toast.makeText(context, alertMsg, Toast.LENGTH_LONG).show();
	}
	
	public static void ShowFileNameEmpty(Context context, String alertMsg) {
		Toast.makeText(context, alertMsg, Toast.LENGTH_LONG).show();
	}
}
