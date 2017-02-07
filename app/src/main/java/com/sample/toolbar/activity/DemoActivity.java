package com.sample.toolbar.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.sample.toolbar.R;
import com.sample.toolbar.fragment.Fragment1;
import com.sample.toolbar.fragment.Fragment2;
import com.sample.toolbar.fragment.Fragment3;
import com.sample.toolbar.fragment.Fragment4;

/**
 * Created by Administrator on 2016/6/22.
 */
public class DemoActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    private FrameLayout mContainer;

    private RadioGroup mRadioGroup;

    private Fragment1 mFragment1;
    private Fragment2 mFragment2;
    private Fragment3 mFragment3;
    private Fragment4 mFragment4;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_demo);

        mContainer = (FrameLayout) findViewById(R.id.demo_container);

        mRadioGroup = (RadioGroup) findViewById(R.id.demo_group);


        mRadioGroup.setOnCheckedChangeListener(this);
        mRadioGroup.check(R.id.demo_rb1);

    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        FragmentManager manager = getSupportFragmentManager();

        FragmentTransaction transaction = manager.beginTransaction();

        hideFragment(transaction);

        switch (checkedId) {
            case R.id.demo_rb1:
                if (mFragment1 == null) {
                    mFragment1 = new Fragment1();
                    transaction.add(R.id.demo_container, mFragment1);
                } else {
                    transaction.show(mFragment1);
                }
                break;

            case R.id.demo_rb2:
                if (mFragment2 == null) {
                    mFragment2 = new Fragment2();
                    transaction.add(R.id.demo_container, mFragment2);
                } else {
                    transaction.show(mFragment2);
                }
                break;

            case R.id.demo_rb3:
                if (mFragment3 == null) {
                    mFragment3 = new Fragment3();
                    transaction.add(R.id.demo_container, mFragment3);
                } else {
                    transaction.show(mFragment3);
                }
                break;

            case R.id.demo_rb4:
                if (mFragment4 == null) {
                    mFragment4 = new Fragment4();
                    transaction.add(R.id.demo_container, mFragment4);
                } else {
                    transaction.show(mFragment4);
                }
                break;

            default:

                break;
        }


        transaction.commit();


    }

    private void hideFragment(FragmentTransaction transaction) {

        if (mFragment1 != null) {
            transaction.hide(mFragment1);
        }

        if (mFragment2 != null) {
            transaction.hide(mFragment2);
        }

        if (mFragment3 != null) {
            transaction.hide(mFragment3);
        }

        if (mFragment4 != null) {
            transaction.hide(mFragment4);
        }

    }
}
