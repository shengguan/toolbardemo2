package com.sample.toolbar.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sample.toolbar.R;
import com.sample.toolbar.activity.ToolBarWithImageActivity;

/**
 * Created by Administrator on 2016/6/23.
 */
public class Fragment2 extends BaseFragmentDemo {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmet2_demo, null);

        Toolbar toolbar = initToolbar(view, R.id.demo2_toolbar);

        toolbar.setTitle("好训");

        toolbar.inflateMenu(R.menu.demo2_menu);


        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.setting2:
                        startActivity(new Intent(getActivity(), ToolBarWithImageActivity.class));
                        Toast.makeText(mActivity, "Fragment2   setting2   点击", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.contantus2:
                        startActivity(new Intent(getActivity(), ToolBarWithImageActivity.class));
                        Toast.makeText(mActivity, "Fragment2   contantus2   点击", Toast.LENGTH_SHORT).show();
                        break;
                }

                return false;
            }
        });



        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

    }



}
