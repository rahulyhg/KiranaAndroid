package com.kiranaofficial.kirana;

import java.util.ArrayList;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.kiranaofficial.kirana.adapters.CSVEditorAdapter;

public class CSVEditorFragment extends Fragment{
	
	private CSVEditorAdapter csvEditorAdapter;
	View rootView;
	ArrayList<CSVProduct> productList = new ArrayList<CSVProduct>();
	CSVProduct csvProduct = null;
	private ListView listView;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_csv_editor, container, false);
        listView = (ListView)rootView.findViewById(R.id.lvwCSV);
        listView.setItemsCanFocus(true);
        
        displayListView();
        
        return rootView;
    }
	
	private void displayListView() {
		
	  for(int i = 0;i<30;i++) {
		  csvProduct = new CSVProduct("","","","","","","","","","");
		  productList.add(csvProduct);
	  }
	  csvEditorAdapter = new CSVEditorAdapter(getActivity(),R.layout.lvw_item_csv_editor, productList);
	  listView.setAdapter(csvEditorAdapter);
	 }
}