package com.kiranaofficial.kirana.adapters;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.kiranaofficial.kirana.BillCalculation;
import com.kiranaofficial.kirana.HttpManagerDelete;
import com.kiranaofficial.kirana.IDeleteShop;
import com.kiranaofficial.kirana.IOrderSummary;
import com.kiranaofficial.kirana.ProductUpload;
import com.kiranaofficial.kirana.R;

/**
 * Created by Karan-PC on 21-06-2015.
 */
public class OrderSummaryAdapter extends BaseAdapter {

    List<ProductUpload> summaryProduct;
    Context context;
    IOrderSummary orderSummary;
    public OrderSummaryAdapter(Context context, List<ProductUpload> summaryProduct, IOrderSummary orderSummary) {
        this.context = context;
        this.summaryProduct = summaryProduct;
        this.orderSummary = orderSummary;
    }

    @Override
    public int getCount() {
        return summaryProduct.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.lvw_item_order_summary, null);
        }

        TextView txtProductName = (TextView) convertView.findViewById(R.id.txtProductName);
        TextView txtProductQuantity = (TextView) convertView.findViewById(R.id.txtProductQuantity);
        TextView txtProductSubTotal = (TextView) convertView.findViewById(R.id.txtTotalProductCost);
        TextView txtTaxBracket = (TextView) convertView.findViewById(R.id.txtTaxBracket);
        TextView txtProductPrice = (TextView) convertView.findViewById(R.id.txtProductPrice);

        ImageButton ibtPopupOrderSummary = (ImageButton) convertView.findViewById(R.id.ibtPopupOrderSummary);
        ibtPopupOrderSummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /** Instantiating PopupMenu class */
                PopupMenu popup = new PopupMenu(context, v);

                /** Adding menu items to the popupmenu */
                popup.getMenuInflater().inflate(R.menu.order_summary_pop_up, popup.getMenu());

                /** Defining menu item click listener for the popup menu */
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getItemId() == R.id.popEditItemOrder) {
                            orderSummary.editOrderItem(position);
                        } else if (item.getItemId() == R.id.popDeleteItemOrder) {
                        	orderSummary.deleteOrderItem(position);
                        }
                        return true;
                    }
                });

                /** Showing the popup menu */
                popup.show();
            }
        });
        txtProductName.setText(summaryProduct.get(position).getProductId());
        txtProductQuantity.setText(summaryProduct.get(position).getQuantity() + "");
        txtTaxBracket.setText(summaryProduct.get(position).getTaxBracket() + "%");
        txtProductPrice.setText("Rs." + summaryProduct.get(position).getPrice());
        
        double subTotal = summaryProduct.get(position).getProductSubTotal();
        
        txtProductSubTotal.setText("Rs." + subTotal);
        return convertView;
    }
    
}
