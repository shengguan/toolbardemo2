package com.sample.toolbar.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sample.toolbar.R;

/**
 * Created by Administrator on 2016/6/23.
 */
public class Fragment4 extends BaseFragmentDemo {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmet4_demo, null);

        Toolbar toolbar = initToolbar(view,R.id.demo4_toolbar);

        toolbar.setTitle("更多");

        toolbar.inflateMenu(R.menu.demo4_menu);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.setting4:
                        Toast.makeText(mActivity, "Fragment4   setting4   点击", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.more4:
                        Toast.makeText(mActivity, "Fragment4     more4   点击", Toast.LENGTH_SHORT).show();
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
