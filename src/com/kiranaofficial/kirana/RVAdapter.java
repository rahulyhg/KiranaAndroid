package com.kiranaofficial.kirana;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Karan-PC on 18-05-2015.
 */
public class RVAdapter extends RecyclerView.Adapter<RVAdapter.PersonViewHolder> {

    public static class PersonViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView productName;
        TextView productPrice;
        ImageView productImage;

        PersonViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cvwHomeOfferProducts);
            productName = (TextView)itemView.findViewById(R.id.txtHomeOfferProductsName);
            productPrice = (TextView)itemView.findViewById(R.id.txtHomeOfferProductsPrice);
            productImage = (ImageView)itemView.findViewById(R.id.imgHomeOfferProducts);
        }
    }

    List<Product> persons;

    RVAdapter(List<Product> persons){
        this.persons = persons;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.lvw_item_home_offer_products, viewGroup, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PersonViewHolder personViewHolder, int i) {
        personViewHolder.productName.setText(persons.get(i).strProductName);
        personViewHolder.productPrice.setText(persons.get(i).strProductPrice);
        personViewHolder.productImage.setImageResource(persons.get(i).photoId);
    }

    @Override
    public int getItemCount() {
        return persons.size();
    }
}

