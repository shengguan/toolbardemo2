package com.sample.toolbar.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AbsListView;

import com.sample.toolbar.R;
import com.sample.toolbar.adapter.RefreshDemoAdapter;
import com.sample.toolbar.domain.Person;
import com.sample.toolbar.util.ColorUtil;
import com.sample.toolbar.util.DensityUtil;
import com.sample.toolbar.widget.SmoothListView.HeaderAdViewView;
import com.sample.toolbar.widget.SmoothListView.SmoothListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/12.
 */
public class RefreshListDemo extends AppCompatActivity implements SmoothListView.ISmoothListViewListener {
    private Toolbar mToolbar;

    private SmoothListView mSmoothListView;

    private ArrayList<Person> mDatas;

    private List<String> adList = new ArrayList<>(); // 广告数据

    private Context mContext;

    private HeaderAdViewView listViewAdHeaderView;


    private RefreshDemoAdapter mAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = RefreshListDemo.this;

        setContentView(R.layout.activity_refreshdemo);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        mSmoothListView = (SmoothListView) findViewById(R.id.smooth_listview);

        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        initData();

        // 设置广告数据
        listViewAdHeaderView = new HeaderAdViewView(this);
        listViewAdHeaderView.fillView(adList, mSmoothListView);
        if (mAdapter == null) {
            mAdapter = new RefreshDemoAdapter(mDatas, mContext);
        }

        mSmoothListView.setAdapter(mAdapter);
        //默认都为true，但是setLoadMoreEnable代码一定要设置，不然里面的点击事件会不触发
        mSmoothListView.setRefreshEnable(false);
        mSmoothListView.setLoadMoreEnable(true);

        initListener();
    }

    private boolean isScrollIdle = false;

    private int adViewHeight = 180; // 广告视图的高度
    private int adViewTopSpace; // 广告视图距离顶部的距离

    private View itemHeaderAdView; // 从ListView获取的广告子View


    private void initListener() {


        mSmoothListView.setOnScrollListener(new SmoothListView.OnSmoothScrollListener() {
            @Override
            public void onSmoothScrolling(View view) {

            }

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                //判断当前的转态
                isScrollIdle = (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE);
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (isScrollIdle && adViewTopSpace < 0) return;


                System.out.println("firstVisibleItem = " + firstVisibleItem);
                // 获取广告头部View、自身的高度、距离顶部的高度
                if (itemHeaderAdView == null) {
                    itemHeaderAdView = mSmoothListView.getChildAt(1 - firstVisibleItem);
                }
                if (itemHeaderAdView != null) {

                    System.out.println("top = " + itemHeaderAdView.getTop());
                    adViewTopSpace = DensityUtil.px2dip(mContext, itemHeaderAdView.getTop());
                    adViewHeight = DensityUtil.px2dip(mContext, itemHeaderAdView.getHeight());

                    System.out.println("adViewTopSpace = " + adViewTopSpace + ",adViewHeight = " + adViewHeight);
                }

                // 处理标题栏颜色渐变
                handleTitleBarColorEvaluate();
            }
        });


        mSmoothListView.setSmoothListViewListener(this);


    }


    // 处理标题栏颜色渐变
    private void handleTitleBarColorEvaluate() {
        float fraction;
        //如果广告视图距离顶部的距离>0
        if (adViewTopSpace > 0) {
            fraction = 1f - adViewTopSpace * 1f / 60;
            if (fraction < 0f) fraction = 0f;

            System.out.println("adViewTopSpace > 0   fraction = " + fraction);
            mToolbar.setAlpha(fraction);
            return;
        }

        float space = Math.abs(adViewTopSpace) * 1f;
        fraction = space / (adViewHeight - mToolbar.getHeight());
        System.out.println("fraction = " + fraction);
        if (fraction < 0f) fraction = 0f;
        if (fraction > 1f) fraction = 1f;
        mToolbar.setAlpha(1f);

        if (fraction >= 1f) {
//            viewTitleBg.setAlpha(0f);
//            viewActionMoreBg.setAlpha(0f);
            mToolbar.setBackgroundColor(mContext.getResources().getColor(R.color.primary));
        } else {
//            viewTitleBg.setAlpha(1f - fraction);
//            viewActionMoreBg.setAlpha(1f - fraction);
            mToolbar.setBackgroundColor(ColorUtil.getNewColorByStartEndColor(mContext, fraction, R.color.transparent, R.color.primary));
        }
    }


    private void initData() {


        mDatas = new ArrayList<Person>();
        Person person;
        for (int i = 0; i < 20; i++) {
            person = new Person("title" + i, R.mipmap.alex, "content" + i);

            mDatas.add(person);
        }


        adList = new ArrayList<>();
        adList.add("http://p3.so.qhimg.com/sdr/512_768_/t013777de31853a2c98.jpg");
        adList.add("http://p1.so.qhimg.com/sdr/512_768_/t0134d5b8fe8e13bd76.jpg");
        adList.add("http://p0.so.qhimg.com/sdr/512_768_/t017830f61399e0dbe5.jpg");


    }


    @Override
    public void onRefresh() {
        mDatas.clear();
        Person person;
        for (int i = 0; i < 20; i++) {
            person = new Person("title" + i, R.mipmap.alex, "content" + i);

            mDatas.add(person);
        }


        mSmoothListView.stopRefresh();
    }

    @Override
    public void onLoadMore() {
        Person person;


        for (int i = 0; i < 20; i++) {
            person = new Person("title" + i, R.mipmap.alex, "content" + i);

            mDatas.add(person);
        }
        mSmoothListView.stopLoadMore();

    }


}
