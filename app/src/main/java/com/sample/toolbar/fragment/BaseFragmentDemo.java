package com.sample.toolbar.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

/**
 * Created by Administrator on 2016/6/23.
 */
public class BaseFragmentDemo extends Fragment {

    Activity mActivity;

    AppCompatActivity mAppCompatActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivity = getActivity();

        setHasOptionsMenu(true);

    }

    public Toolbar initToolbar(View view ,int resId) {
//        mAppCompatActivity = (AppCompatActivity) mActivity;

        Toolbar toolbar = (Toolbar) view.findViewById(resId);

//
//        mAppCompatActivity.setSupportActionBar(toolbar);
//
//        ActionBar actionBar = mAppCompatActivity.getActionBar();
//
//        toolbar.setTitle(title);
//
//
//
//        if (actionBar != null) {
//            actionBar.setDisplayHomeAsUpEnabled(false);
//        }


        return toolbar;
    }




}
