package com.kiranaofficial.kirana;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class TableNumberFragment extends Fragment{
	
	EditText tableNumber;
	Button submitTableNumber;
	String tableNum = null;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_table_number, container, false);
        
        tableNumber = (EditText)rootView.findViewById(R.id.edtTableNum);
        submitTableNumber = (Button)rootView.findViewById(R.id.btnTableNumber);
        
        submitTableNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
	            tableNum = tableNumber.getText().toString().trim();
	            if(tableNum.isEmpty()) {
	            	tableNumber.setError("Please enter Table Number");
	            } else {
	            	getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.homeDrawerFrame, new MenuOrderFragment())
                    .commit();
	            }
            }
        });
        
        return rootView;
	}
}
