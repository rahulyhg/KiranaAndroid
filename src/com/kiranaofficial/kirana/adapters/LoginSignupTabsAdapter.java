package com.kiranaofficial.kirana.adapters;

import com.kiranaofficial.kirana.LoginFragment;
import com.kiranaofficial.kirana.SignupFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


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
