package com.kiranaofficial.kirana.adapters;

import java.util.List;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.kiranaofficial.kirana.ProductUpload;
import com.kiranaofficial.kirana.R;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.PersonViewHolder> {

    public static class PersonViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView productName, productPrice, productQty, productDiscount, productTaxBracket;
        ImageButton popupProductDetails;

        PersonViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cvwProductDetail);
            productName = (TextView)itemView.findViewById(R.id.txtProductName);
            productPrice = (TextView)itemView.findViewById(R.id.txtProductPrice);
            productQty = (TextView)itemView.findViewById(R.id.txtProductQty);
            productDiscount = (TextView)itemView.findViewById(R.id.txtProductDiscount);
            productTaxBracket = (TextView)itemView.findViewById(R.id.txtProductTax);
            popupProductDetails = (ImageButton)itemView.findViewById(R.id.ibtPopupProductDetails);
        }
    }

    List<ProductUpload> products;

    public ProductListAdapter(List<ProductUpload> persons){
        this.products = persons;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.lvw_item_products, viewGroup, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PersonViewHolder personViewHolder, int i) {
        personViewHolder.productName.setText(products.get(i).getProductId());
        personViewHolder.productPrice.setText(products.get(i).getPrice() + "");
        personViewHolder.productQty.setText(products.get(i).getQuantity() + "");
        personViewHolder.productDiscount.setText(products.get(i).getDiscount() + "");
        personViewHolder.productTaxBracket.setText(products.get(i).getTaxBracket() + "");
    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}

