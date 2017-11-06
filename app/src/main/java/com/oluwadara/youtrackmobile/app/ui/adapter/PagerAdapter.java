package com.oluwadara.youtrackmobile.app.ui.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.oluwadara.youtrackmobile.app.ui.login.LoginFragment;
import com.oluwadara.youtrackmobile.app.ui.register.RegisterFragment;

public class PagerAdapter extends FragmentPagerAdapter{

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return LoginFragment.newInstance("Login");

            case 1:
                return RegisterFragment.newInstance("Register");

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Login";

            case 1:
                return "Register";
            default:
                return null;
        }
    }

}
