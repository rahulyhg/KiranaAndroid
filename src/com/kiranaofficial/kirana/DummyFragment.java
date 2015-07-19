package com.kiranaofficial.kirana;

import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.PageIndicator;
import com.kiranaofficial.kirana.adapters.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karan-PC on 17-05-2015.
 */
public class DummyFragment extends Fragment {

    private RecyclerView rcvwOfferProducts;
    private List<Product> persons = new ArrayList<>();
    IndicatorPagerAdapter mAdapter;
    ViewPager mPager;
    PageIndicator mIndicator;
    CirclePageIndicator indicator;
    private HomeActivity mContext;


    public DummyFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_dummy, container, false);
        rcvwOfferProducts = (RecyclerView)rootView.findViewById(R.id.rcvwOffers);
        mPager = (ViewPager)rootView.findViewById(R.id.pager);
        indicator = (CirclePageIndicator)rootView.findViewById(R.id.indicator);

        //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        MyLinearLayoutManager linearLayoutManager = new MyLinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rcvwOfferProducts.setLayoutManager(linearLayoutManager);
        rcvwOfferProducts.setHasFixedSize(true);

        FragmentManager fragManager = mContext.getSupportFragmentManager();
        mAdapter = new IndicatorPagerAdapter(fragManager);
        mPager.setAdapter(mAdapter);
        mIndicator = indicator;
        indicator.setViewPager(mPager);

        final float density = getResources().getDisplayMetrics().density;
        //indicator.setBackgroundColor(0xFFCCCCCC);
        indicator.setRadius(5 * density);
        indicator.setPageColor(0xFF888888);
        indicator.setFillColor(0x880000FF);
        indicator.setStrokeColor(0xFF000000);
        indicator.setStrokeWidth(2 * density);

        initializeData();
        initializeAdapter();

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        mContext=(HomeActivity) activity;
        super.onAttach(activity);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void initializeData(){
        persons.add(new Product("Idly", "Rs. 15", R.drawable.idli));
        persons.add(new Product("Vada", "Rs. 10", R.drawable.vada));
        persons.add(new Product("Dosa", "Rs. 20", R.drawable.dosa));
        persons.add(new Product("Idly", "Rs. 15", R.drawable.idli));
        persons.add(new Product("Vada", "Rs. 10", R.drawable.vada));
        persons.add(new Product("Dosa", "Rs. 20", R.drawable.dosa));
        persons.add(new Product("Idly", "Rs. 15", R.drawable.idli));
        persons.add(new Product("Vada", "Rs. 10", R.drawable.vada));
        persons.add(new Product("Dosa", "Rs. 20", R.drawable.dosa));
        persons.add(new Product("Idly", "Rs. 15", R.drawable.idli));
        persons.add(new Product("Vada", "Rs. 10", R.drawable.vada));
        persons.add(new Product("Dosa", "Rs. 20", R.drawable.dosa));
        persons.add(new Product("Idly", "Rs. 15", R.drawable.idli));
        persons.add(new Product("Vada", "Rs. 10", R.drawable.vada));
        persons.add(new Product("Dosa", "Rs. 20", R.drawable.dosa));
    }

    private void initializeAdapter(){
        RVAdapter adapter = new RVAdapter(persons);
        rcvwOfferProducts.setAdapter(adapter);
    }
}
