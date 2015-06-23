package com.example.karan_pc.kirana.com.example.karan_pc.kirana;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.karan_pc.kirana.R;

/**
 * Created by Karan-PC on 23-06-2015.
 */
public class UploadProductListFragment extends Fragment {
    Button btnBrowseCSV, btnUploadLater;
    Intent fileExplorer;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_upload_product_list, container, false);
        btnBrowseCSV = (Button)rootView.findViewById(R.id.btnBrowseCSV);
        btnUploadLater = (Button)rootView.findViewById(R.id.btnUploadLater);

        btnBrowseCSV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fileExplorer = new Intent(getActivity(), FileExplorerActivity.class);
                startActivity(fileExplorer);
            }
        });

        return rootView;
    }
}
