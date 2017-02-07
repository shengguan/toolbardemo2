package com.sample.toolbar.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sample.toolbar.R;

/**
 * Created by Administrator on 2016/6/23.
 */
public class Fragment3 extends BaseFragmentDemo {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmet3_demo, null);
        Toolbar toolbar  = initToolbar(view,R.id.demo3_toolbar);

        toolbar.setTitle("我有");

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

    }




}
