package com.example.karan_pc.kirana.com.example.karan_pc.kirana.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.karan_pc.kirana.R;
import com.example.karan_pc.kirana.com.example.karan_pc.kirana.Product;

import java.util.List;

/**
 * Created by Karan-PC on 26-05-2015.
 */
public class GridAdapter extends BaseAdapter{

    List<Product> persons;
    Context context;
    public GridAdapter(Context context, List<Product> persons) {
        this.context = context;
        this.persons = persons;
    }

    @Override
    public int getCount() {
        return persons.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.lvw_item_home_offer_products, null);
        }

        ImageView imgIcon = (ImageView) convertView.findViewById(R.id.imgHomeOfferProducts);
        TextView txtTitle = (TextView) convertView.findViewById(R.id.txtHomeOfferProductsName);
        TextView txtPrice = (TextView) convertView.findViewById(R.id.txtHomeOfferProductsPrice);

        txtTitle.setText(persons.get(position).strProductName);
        txtPrice.setText(persons.get(position).strProductPrice);
        imgIcon.setImageResource(persons.get(position).photoId);

        return convertView;
    }
}
