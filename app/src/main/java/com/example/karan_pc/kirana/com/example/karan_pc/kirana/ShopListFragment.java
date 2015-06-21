package com.example.karan_pc.kirana.com.example.karan_pc.kirana;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.karan_pc.kirana.R;

/**
 * Created by Karan-PC on 21-06-2015.
 */
public class ShopListFragment extends Fragment {
    ListView lvwShops;

    public ShopListFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_shop_details, container, false);
        Intent intent = getActivity().getIntent();
        Bundle bundle = intent.getExtras();
        User user = (User)intent.getSerializableExtra("loggedInUser");
        lvwShops = (ListView)rootView.findViewById(R.id.lvwShops);
        return rootView;
    }
}
