package com.kiranaofficial.kirana.adapters;

import java.util.List;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnCreateContextMenuListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kiranaofficial.kirana.CSVProduct;
import com.kiranaofficial.kirana.R;

public class CSVProductListAdapter extends RecyclerView.Adapter<CSVProductListAdapter.CSVProductViewHolder> {

    public static class CSVProductViewHolder extends RecyclerView.ViewHolder implements OnCreateContextMenuListener {

        CardView cv;
        TextView txtProductName;

        CSVProductViewHolder(View itemView) {
            super(itemView);
            txtProductName = (TextView)itemView.findViewById(R.id.txtProductName);
            itemView.setOnCreateContextMenuListener(this);
        }

		@Override
		public void onCreateContextMenu(ContextMenu menu, View v,
				ContextMenuInfo menuInfo) {
			// TODO Auto-generated method stub
			menu.add("Edit Product");
			menu.add("Delete Product");
		}
    }

    List<CSVProduct> products;

    public CSVProductListAdapter(List<CSVProduct> persons){
        this.products = persons;
    }
    
    public CSVProductListAdapter(){
    	
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public CSVProductViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.lvw_item_csv_products, viewGroup, false);
        CSVProductViewHolder pvh = new CSVProductViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final CSVProductViewHolder personViewHolder, final int i) {
        personViewHolder.txtProductName.setText(products.get(i).getProductName());
        personViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				setPosition(personViewHolder.getPosition());
				return false;
			}
		});
    }

    @Override
    public int getItemCount() {
        return products.size();
    }
    
    @Override
	public void onViewRecycled(CSVProductViewHolder holder) {
		// TODO Auto-generated method stub
    	holder.itemView.setOnLongClickListener(null);
		super.onViewRecycled(holder);
	}

	private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}

