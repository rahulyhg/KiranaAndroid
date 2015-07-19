package com.kiranaofficial.kirana.adapters;

import java.util.List;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.kiranaofficial.kirana.CSVProduct;
import com.kiranaofficial.kirana.R;

public class CSVEditorRecyclerAdapter extends RecyclerView.Adapter<CSVEditorRecyclerAdapter.CSVViewHolder>{
	
	public static class CSVViewHolder extends RecyclerView.ViewHolder {
		
		EditText edtProductId;
		
        CSVViewHolder(View itemView) {
            super(itemView);
            edtProductId = (EditText)itemView.findViewById(R.id.edtProductId);
        }
    }
	
	List<CSVProduct> products;

    public CSVEditorRecyclerAdapter(List<CSVProduct> products){
        this.products = products;
    }
    
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

	@Override
	public int getItemCount() {
		// TODO Auto-generated method stub
		return products.size();
	}

	@Override
	public void onBindViewHolder(CSVViewHolder holder, int i) {
		// TODO Auto-generated method stub
		holder.edtProductId.setText(products.get(i).getProductName());
	}

	@Override
	public CSVViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
		// TODO Auto-generated method stub
		View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.lvw_item_csv_editor, viewGroup, false);
		CSVViewHolder pvh = new CSVViewHolder(v);
		return pvh;
	}

}
