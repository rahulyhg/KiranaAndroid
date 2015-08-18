package com.kiranaofficial.kirana;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class TableNumberFragment extends Fragment{
	
	EditText tableNumber;
	Button submitTableNumber;
	String tableNum = null;
	FragmentManager fragmentManager;
	TokenIdStorage storage;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_table_number, container, false);
        
        tableNumber = (EditText)rootView.findViewById(R.id.edtTableNum);
        submitTableNumber = (Button)rootView.findViewById(R.id.btnTableNumber);
        fragmentManager = getActivity().getSupportFragmentManager();
        
        storage = (TokenIdStorage) getActivity().getApplicationContext();
        
        submitTableNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
	            tableNum = tableNumber.getText().toString().trim();
	            if(tableNum.isEmpty()) {
	            	tableNumber.setError("Please enter Table Number");
	            } else {
	            	Bundle tableBundle = new Bundle();
	            	tableBundle.putString("TableNumber", tableNum);
	            	storage.setTableNumber(tableNum);
	            	
	            	MenuOrderFragment menuFragment = new MenuOrderFragment();
	            	menuFragment.setArguments(tableBundle);
	            	android.support.v4.app.FragmentManager manager = fragmentManager;
	            	android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
	            	transaction.replace(R.id.homeDrawerFrame, menuFragment);
	            	transaction.addToBackStack(null);
	            	transaction.commit();
	            }
            }
        });
        
        return rootView;
	}
}
