package com.example.karan_pc.kirana.com.example.karan_pc.kirana.adapters;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.karan_pc.kirana.R;
import com.example.karan_pc.kirana.com.example.karan_pc.kirana.HttpManager;
import com.example.karan_pc.kirana.com.example.karan_pc.kirana.HttpManagerDelete;
import com.example.karan_pc.kirana.com.example.karan_pc.kirana.Product;
import com.example.karan_pc.kirana.com.example.karan_pc.kirana.RegisterShopFragment;
import com.example.karan_pc.kirana.com.example.karan_pc.kirana.Shop;
import com.example.karan_pc.kirana.com.example.karan_pc.kirana.ShopListFragment;
import com.example.karan_pc.kirana.com.example.karan_pc.kirana.User;

import java.util.List;

/**
 * Created by Karan-PC on 21-06-2015.
 */
public class ShopListAdapter extends BaseAdapter {

    List<Shop> shop;
    Context context;
    public ShopListAdapter(Context context, List<Shop> shop) {
        this.context = context;
        this.shop = shop;
    }

    @Override
    public int getCount() {
        return shop.size();
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
            convertView = mInflater.inflate(R.layout.lvw_item_shop_detail_card, null);
        }

        TextView txtShopName = (TextView) convertView.findViewById(R.id.txtShopName);
        TextView txtShopType = (TextView) convertView.findViewById(R.id.txtShopType);
        TextView txtAddress = (TextView) convertView.findViewById(R.id.txtAddress);
        TextView txtMobileNumber = (TextView) convertView.findViewById(R.id.txtMobileNumber);
        TextView txtWebsite = (TextView) convertView.findViewById(R.id.txtWebsite);
        TextView txtTinNumber = (TextView) convertView.findViewById(R.id.txtTinNumber);
        TextView txtServicetax = (TextView) convertView.findViewById(R.id.txtServicetax);
        TextView txtServiceCharge = (TextView) convertView.findViewById(R.id.txtServiceCharge);

        ImageButton ibtPopupShopDetails = (ImageButton) convertView.findViewById(R.id.ibtPopupShopDetails);
        ibtPopupShopDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /** Instantiating PopupMenu class */
                PopupMenu popup = new PopupMenu(context, v);

                /** Adding menu items to the popumenu */
                popup.getMenuInflater().inflate(R.menu.shop_pop_up, popup.getMenu());

                /** Defining menu item click listener for the popup menu */
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getItemId() == R.id.popEditUser) {
                            ((Activity) context).getFragmentManager()
                                    .beginTransaction()
                                    .add(R.id.homeDrawerFrame, new RegisterShopFragment())
                                    .commit();
                        } else if (item.getItemId() == R.id.popEditShop) {

                        } else if( item.getItemId() == R.id.popDeleteShop) {
                            final String loginUrl = "http://52.0.139.50:8080/KiranaService/v1/shop/delete/26?userToken=bc09e7ef-51db-44f1-9bb2-ae789798f656";
                            BackgroundTask task = new BackgroundTask();
                            task.execute(loginUrl);
                        }
                        return true;
                    }
                });

                /** Showing the popup menu */
                popup.show();
            }
        });

        txtShopName.setText(shop.get(position).getName());
        txtShopType.setText(shop.get(position).getType());
        txtAddress.setText(shop.get(position).getAddress());
        //txtMobileNumber.setText(shop.get(position).getMobileNumber());
        txtWebsite.setText(shop.get(position).getWebsite());
        txtTinNumber.setText(shop.get(position).getTin());
        txtServicetax.setText(shop.get(position).getServiceTax() + "");
        txtServiceCharge.setText(shop.get(position).getServiceCharge() + "");

        return  convertView;
    }

    private class BackgroundTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            publishProgress("Yo started");
        }

        @Override
        protected String doInBackground(String... params) {
            String response = HttpManagerDelete.GetServiceData(params[0]);
            return null;
        }

        @Override
        protected void onPostExecute(String user) {

        }
    }
}
