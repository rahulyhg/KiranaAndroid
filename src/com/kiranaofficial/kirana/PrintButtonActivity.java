package com.kiranaofficial.kirana;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class PrintButtonActivity extends Fragment {
	
	Button btnPrint, btnBtSettings;
	byte FONT_TYPE;
	private static BluetoothSocket btsocket;
	private static OutputStream btoutputstream;
	
	InputStream mmInputStream;
    Thread workerThread;
 
    byte[] readBuffer;
    int readBufferPosition;
    int counter;
    volatile boolean stopWorker;
    
    private int FOLDER_REQUEST_PATH = 1;

	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.activity_print_button_sample, container, false);
		btnPrint = (Button)rootView.findViewById(R.id.btnPrint);
		btnBtSettings = (Button)rootView.findViewById(R.id.btnBtSettings);
		
		btnBtSettings.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				openBtSettings();
			}
		});
		
		btnPrint.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//connect();
				//startBTTransfer();
				Intent fileExplorer = new Intent(getActivity(), FileExplorerActivity.class);
                startActivityForResult(fileExplorer, FOLDER_REQUEST_PATH);
			}
		});
		
		return rootView;
	}
	
	private void openBtSettings() {
		Intent intentBluetooth = new Intent();
	    intentBluetooth.setAction(android.provider.Settings.ACTION_BLUETOOTH_SETTINGS);
	    startActivity(intentBluetooth);
	}
	
	private void startBTTransfer(String fileName) {
		Intent sharingIntent = new Intent(Intent.ACTION_SEND);
		//Uri screenshotUri = Uri.parse(picURI);
		File file = new File(fileName);

		sharingIntent.setType("text/plain");
		sharingIntent.setPackage("com.android.bluetooth");
		sharingIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
		startActivity(Intent.createChooser(sharingIntent, "Share file"));
	}
	
	protected void connect() {
		if(btsocket == null){
			Intent BTIntent = new Intent(getActivity().getApplicationContext(), BTDeviceList.class);
			this.startActivityForResult(BTIntent, BTDeviceList.REQUEST_CONNECT_BT);
		}
		else{
            
			OutputStream opstream = null;
			try {
				opstream = btsocket.getOutputStream();
			} catch (IOException e) { 
				e.printStackTrace();
			}
			btoutputstream = opstream;
			
			print_bt();

		}
	}
	
	// After opening a connection to bluetooth printer device,
    // we have to listen and check if a data were sent to be printed.
    private void beginListenForData() {
        try {
            final Handler handler = new Handler();
 
            // This is the ASCII code for a newline character
            final byte delimiter = 10;
 
            stopWorker = false;
            readBufferPosition = 0;
            readBuffer = new byte[1024];
 
            workerThread = new Thread(new Runnable() {
                public void run() {
                    while (!Thread.currentThread().isInterrupted()
                            && !stopWorker) {
 
                        try {
 
                            int bytesAvailable = mmInputStream.available();
                            if (bytesAvailable > 0) {
                                byte[] packetBytes = new byte[bytesAvailable];
                                mmInputStream.read(packetBytes);
                                for (int i = 0; i < bytesAvailable; i++) {
                                    byte b = packetBytes[i];
                                    if (b == delimiter) {
                                        byte[] encodedBytes = new byte[readBufferPosition];
                                        System.arraycopy(readBuffer, 0,
                                                encodedBytes, 0,
                                                encodedBytes.length);
                                        final String data = new String(
                                                encodedBytes, "US-ASCII");
                                        readBufferPosition = 0;
 
                                        handler.post(new Runnable() {
                                            public void run() {
                                                String yo = "yo";
                                            }
                                        });
                                    } else {
                                        readBuffer[readBufferPosition++] = b;
                                    }
                                }
                            }
 
                        } catch (IOException ex) {
                            stopWorker = true;
                        }
 
                    }
                }
            });
 
            workerThread.start();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


	private void print_bt() {
		try {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			btoutputstream = btsocket.getOutputStream();
			InputStream is = getActivity().getAssets().open("rectangle_bg_shape.xml");
			BufferedInputStream bs = new BufferedInputStream(is);
			OutputStream os = btoutputstream;
			
			try {
		        int bufferSize = 1024;
		        byte[] buffer = new byte[bufferSize];
		 
		        // we need to know how may bytes were read to write them to the byteBuffer
		        int len = 0;
		        while ((len = bs.read(buffer)) != -1) {
		            os.write(buffer, 0, len);
		        }
		    } finally {
		    	bs.close();
		        os.flush();
		        os.close();
		        btoutputstream.close();
		    }

			/*byte[] printformat = { 0x1B, 0x21, FONT_TYPE };
			btoutputstream.write(printformat);
			String msg = "YOYO";
			btoutputstream.write(msg.getBytes());
			btoutputstream.write(0x0D);
			btoutputstream.write(0x0D);
			btoutputstream.write(0x0D);
			btoutputstream.flush();*/
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if(btsocket!= null){
			try {
				btoutputstream.close();
				btsocket.close();
				btsocket = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
    
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == FOLDER_REQUEST_PATH) {
			if (resultCode == Activity.RESULT_OK) {
                String strCurFileName = data.getStringExtra("GetFileName");
                if(strCurFileName != null && strCurFileName != "")
                	startBTTransfer(strCurFileName);
                else {
                	Common.ShowFileNameEmpty(getActivity(),"Filename Empty or not proper");
                }
            } else {
            	Common.ShowFileNameEmpty(getActivity(),"Canceled file selection");
            }
		}
		/*try {
			btsocket = BTDeviceList.getSocket();
			if(btsocket != null){
				print_bt();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}*/
	} 

}
