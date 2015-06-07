package com.example.karan_pc.kirana.com.example.karan_pc.kirana.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.karan_pc.kirana.com.example.karan_pc.kirana.LoginFragment;
import com.example.karan_pc.kirana.com.example.karan_pc.kirana.SignupFragment;

/**
 * Created by Karan-PC on 17-05-2015.
 */
public class LoginSignupTabsAdapter extends FragmentPagerAdapter {

    public LoginSignupTabsAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {
        switch(index) {
            case 0:
                return new LoginFragment();
            case 1:
                return new SignupFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
