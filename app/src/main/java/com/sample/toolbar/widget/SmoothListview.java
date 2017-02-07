package com.sample.toolbar.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Scroller;

/**
 * Created by Administrator on 2016/6/3.
 */
public class SmoothListview extends ListView {

    private ImageView mImageView;

    private Bitmap mBitmap;

    private Scroller mScroller;

    private LayoutInflater mInflater;

    private final static int RELEASE_TO_REFRESH = 0; // 释放刷新
    private final static int PULL_TO_REFRESH = 1; // 下拉刷新
    private final static int REFRESHING = 2; // 正在刷新
    private final static int DONE = 3; // 完成刷新


    private float startY;//开始时点击的Y坐标
    private float starX;//开始时点击的X坐标

    private float endY;//手指离开时时的Y坐标
    private float endX;//手指离开时时的X坐标

    private float nowY;//滑动时 实时的Y坐标
    private float nowX;//滑动时 实时的X坐标

    private float moveDistance;//滑动的距离

    public int mTouchSlop; //系统自带的需要滑动的最短距离

    private boolean isMove;//是否在滑动

    private boolean mIsBeingDragged;//是否在被拖拽

    private boolean isRefreshEnable;//刷新是否可用

    public SmoothListview(Context context) {
        super(context);
        init(context);
    }

    public SmoothListview(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SmoothListview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SmoothListview(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    private void init(Context context) {

        mInflater = LayoutInflater.from(context);
        mImageView.setImageBitmap(mBitmap);

        ViewConfiguration config = ViewConfiguration.get(context);
        mTouchSlop = config.getScaledTouchSlop();


        addHeadView();

    }

    /**
     * 设置
     *
     * @param imageView
     * @return
     */
    public void setHeaderView(ImageView imageView) {
        this.mImageView = imageView;
    }



    /**
     * 设置是否刷新
     *
     * @param isRefreshEnable
     * @return
     */
    public void setRefreshEnable(boolean isRefreshEnable) {
        this.isRefreshEnable = isRefreshEnable;
    }

    /**
     * 把imageView添加到头部view
     */
    private void addHeadView() {


        addHeaderView(mImageView);
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        int action = ev.getAction();




        switch (action) {
            case MotionEvent.ACTION_DOWN:
                startY = ev.getY();
                starX = ev.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                nowY = ev.getY();
                nowX = ev.getX();

                if (Math.abs(nowY - startY) > mTouchSlop  ) {
                    mIsBeingDragged = true;


                }


                break;
            case MotionEvent.ACTION_UP:
                endY = ev.getY();
                endX = ev.getX();

                mIsBeingDragged =false;

                break;
        }

        return super.onTouchEvent(ev);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        int action = ev.getAction();

        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            mIsBeingDragged = false;
            return false;
        }


        if (action != MotionEvent.ACTION_DOWN) {
            if (action != MotionEvent.ACTION_DOWN && mIsBeingDragged) {
                return true;
            }
        }




        switch (action) {
            case MotionEvent.ACTION_DOWN:


                startY = ev.getY();
                starX = ev.getX();

                mIsBeingDragged = false;


                break;
            case MotionEvent.ACTION_MOVE:

                nowY = ev.getY();
                nowX = ev.getX();

                if (Math.abs(nowY - startY) > mTouchSlop) {
                    mIsBeingDragged = true;
                }


                break;
        }


        return mIsBeingDragged;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
    }

    public void setPullToRefreshEnable(boolean enable){
        this.isRefreshEnable = enable;
    }


    protected boolean isReadyForPullStart() {
        return true;
    }


    @Override
    public void setOnScrollListener(OnScrollListener l) {
        l = new OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (mOnSmoothScrollListener != null) {
                    mOnSmoothScrollListener.onScrollStateChanged(view, scrollState);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (mOnSmoothScrollListener != null) {
                    mOnSmoothScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
                }
            }

        };
    }




    //设置
    public void setOnScrollListener(OnSmoothScrollListener onSmoothScrollListener) {
        onSmoothScrollListener = this.mOnSmoothScrollListener;
    }

    private OnSmoothScrollListener mOnSmoothScrollListener;

    public interface OnSmoothScrollListener {
        public void onSmoothScrolling(View view);

        public void onScrollStateChanged(AbsListView view, int scrollState);

        public void onScroll(AbsListView view, int firstVisibleItem,
                             int visibleItemCount, int totalItemCount);

    }


}
