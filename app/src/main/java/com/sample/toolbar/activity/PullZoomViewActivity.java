package com.sample.toolbar.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ecloud.pulltozoomview.PullToZoomScrollViewEx;
import com.sample.toolbar.R;
import com.sample.toolbar.util.ColorUtil;

/**
 * Created by Administrator on 2016/6/6.
 */
public class PullZoomViewActivity extends AppCompatActivity {

    private PullToZoomScrollViewEx scrollView;

    private Toolbar mToolbar;

    private Context mContext;
    private View mHeadView;
    private View mZoomView;
    private View mContentView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;
        setContentView(R.layout.activity_pullzoom);
        mToolbar = (Toolbar) findViewById(R.id.id_toolbar);

        setSupportActionBar(mToolbar);


        ActionBar actionBar = getSupportActionBar();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        loadViewForCode();
        scrollView = (PullToZoomScrollViewEx) findViewById(R.id.scroll_view);
        scrollView.getPullRootView().findViewById(R.id.tv_test1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("zhuwenwu", "onClick 111-->");

                Toast.makeText(mContext, "onClick 111-->", Toast.LENGTH_SHORT).show();
            }
        });

        scrollView.getPullRootView().findViewById(R.id.tv_test2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("zhuwenwu", "onClick 222-->");
                Toast.makeText(mContext, "onClick 222 -->", Toast.LENGTH_SHORT).show();
            }
        });

        scrollView.getPullRootView().findViewById(R.id.tv_test3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("zhuwenwu", "onClick 333-->");
                Toast.makeText(mContext, "onClick 333-->", Toast.LENGTH_SHORT).show();
            }
        });
        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
        int mScreenHeight = localDisplayMetrics.heightPixels;
        int mScreenWidth = localDisplayMetrics.widthPixels;
        LinearLayout.LayoutParams localObject = new LinearLayout.LayoutParams(mScreenWidth, (int) (9.0F * (mScreenWidth / 16.0F)));
        scrollView.setHeaderLayoutParams(localObject);

        System.out.println("222222222221");


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {

            System.out.println("11111111111111");

            scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                    zoomViewHeight = mZoomView.getHeight();

                    zoomViewTopSpace = mZoomView.getTop();

                    System.out.println("zoomViewHeight = " + zoomViewHeight + ",zoomViewTopSpace = " + zoomViewTopSpace);

                    handleTitleBarColorEvaluate();
                }
            });
        }
        scrollView.getScrollY();


//        scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
//            @Override
//            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//
//            }
//        });

    }

    private int toolbarHeight;
    private int zoomViewHeight;
    private int zoomViewTopSpace;

    /**
     * 获取toolbar的高度
     */
    public void getToolBarHeight() {
        mToolbar.getHeight();
    }


    // 处理标题栏颜色渐变
    private void handleTitleBarColorEvaluate() {
        float fraction;
        if (zoomViewTopSpace > 0) {
            fraction = 1f - zoomViewTopSpace * 1f / 60;
            if (fraction < 0f) fraction = 0f;
            mToolbar.setAlpha(fraction);
            return;
        }

        float space = Math.abs(zoomViewTopSpace) * 1f;
        fraction = space / (zoomViewHeight - toolbarHeight);
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.scroll_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
//        else if (id == R.id.action_settings) {
//            loadViewForCode();
//            return true;
//        }
        else if (id == R.id.action_normal) {
            scrollView.setParallax(false);
            return true;
        } else if (id == R.id.action_parallax) {
            scrollView.setParallax(true);
            return true;
        } else if (id == R.id.action_show_head) {
//            scrollView.showHeaderView();
            scrollView.setHideHeader(false);
            return true;
        } else if (id == R.id.action_hide_head) {
//            scrollView.hideHeaderVie˛w();
            scrollView.setHideHeader(true);
            return true;
        } else if (id == R.id.action_disable_zoom) {
//            scrollView.setEnableZoom(false);
            scrollView.setZoomEnabled(false);
            return true;
        } else if (id == R.id.action_enable_zoom) {
//            scrollView.setEnableZoom(true);
            scrollView.setZoomEnabled(true);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadViewForCode() {
        PullToZoomScrollViewEx scrollView = (PullToZoomScrollViewEx) findViewById(R.id.scroll_view);

        mHeadView = LayoutInflater.from(this).inflate(R.layout.profile_head_view, null, false);
        mZoomView = LayoutInflater.from(this).inflate(R.layout.profile_zoom_view, null, false);
        mContentView = LayoutInflater.from(this).inflate(R.layout.profile_content_view, null, false);
        scrollView.setHeaderView(mHeadView);
        scrollView.setZoomView(mZoomView);
        scrollView.setScrollContentView(mContentView);
    }
}