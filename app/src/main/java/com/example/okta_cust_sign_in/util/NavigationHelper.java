package com.example.okta_cust_sign_in.util;

import android.app.Activity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.okta_cust_sign_in.interfaces.AppNavigation;

public class NavigationHelper implements AppNavigation {
    private Activity activity;
    private FragmentManager manager;
    private int container;

    public NavigationHelper(Activity activity, FragmentManager fragmentManager, int container) {
        this.activity = activity;
        this.manager = fragmentManager;
        this.container = container;
    }

    @Override
    public void close() {
        this.activity.finish();
    }

    @Override
    public void present(Fragment fragment) {
        this.manager.beginTransaction()
                .replace(container, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void push(Fragment fragment) {
        this.manager.beginTransaction()
                .add(container, fragment)
                .addToBackStack(null)
                .commit();
    }
}
