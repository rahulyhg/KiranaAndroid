package com.example.karan_pc.kirana.com.example.karan_pc.kirana.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.karan_pc.kirana.R;
import com.example.karan_pc.kirana.com.example.karan_pc.kirana.TestFragment;

public class IndicatorPagerAdapter extends FragmentPagerAdapter  {
    private int[] offerImages = {
            R.drawable.idli_large,
            R.drawable.vada_large,
            R.drawable.dosa_large,
            R.drawable.idli_large
    };

    private int mCount = offerImages.length;

    public IndicatorPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return new TestFragment(offerImages[position]);
    }

    @Override
    public int getCount() {
        return mCount;
    }

    public void setCount(int count) {
        if (count > 0 && count <= 10) {
            mCount = count;
            notifyDataSetChanged();
        }
    }
}