package com.sample.toolbar.widget.SmoothListView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;

import com.sample.toolbar.R;


public class SmoothListView extends ListView implements OnScrollListener {

    private float mLastY = -1; // save event y
    private Scroller mScroller; // used for scroll back
    private OnScrollListener mScrollListener; // user's scroll listener

    // the interface to trigger refresh and load more.
    private ISmoothListViewListener mListViewListener;

    // -- header view
    private SmoothListViewHeader mHeaderView;
    // header view content, use it to calculate the Header's height. And hide it
    // when disable pull refresh.
    private RelativeLayout mHeaderViewContent;
    /**
     * 刷新时头部时间的TextView
     */
    private TextView mHeaderTimeView;
    /**
     * header Relative的高度
     */
    private int mHeaderViewHeight; // header view's height

    /**
     * 下拉刷新是否可用的标志
     */
    private boolean mEnablePullRefresh = true;
    /**
     * 下拉正在刷新的标志
     */
    private boolean mPullRefreshing = false; // is refreashing.

    // -- footer view
    private SmoothListViewFooter mFooterView;
    /**
     * 加载更多是否可用的标志
     */
    private boolean mEnablePullLoad = true;
    /**
     * 下正在加载更多的标志
     */
    private boolean mPullLoading;

    private boolean mIsFooterReady = false;

    // total list items, used to detect is at the bottom of listview.
    /**
     * listView所有的子项数量
     */
    private int mTotalItemCount;

    // for mScroller, scroll back from header or footer.
    private int mScrollBack;    //对 Scroller对象来说，是从header还是从footer回滚
    private final static int SCROLLBACK_HEADER = 0;
    private final static int SCROLLBACK_FOOTER = 1;
    /**
     * 回滚的时间
     */
    private final static int SCROLL_DURATION = 400; // scroll back duration

    /**
     * 上拉要大于此值才能够显示加载更多
     */
    private final static int PULL_LOAD_MORE_DELTA = 50; // when pull up >= 50px
    // at bottom, trigger
    // load more.
    private final static float OFFSET_RADIO = 1.8f; // support iOS like pull
    // feature.

    /**
     * @param context
     */
    public SmoothListView(Context context) {
        super(context);
        initWithContext(context);
    }

    public SmoothListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initWithContext(context);
    }

    public SmoothListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initWithContext(context);
    }

    private void initWithContext(Context context) {
        mScroller = new Scroller(context, new DecelerateInterpolator());
        // XListView need the scroll event, and it will dispatch the event to
        // user's listener (as a proxy).
        // 自定义XListView需要滚动事件，并交个用户自己的监听器（相当于回滚）
        super.setOnScrollListener(this);

        // init header view  初始化headerView
        mHeaderView = new SmoothListViewHeader(context);
        mHeaderViewContent = (RelativeLayout) mHeaderView.findViewById(R.id.smoothlistview_header_content);
        mHeaderTimeView = (TextView) mHeaderView.findViewById(R.id.smoothlistview_header_time);
        addHeaderView(mHeaderView);

        // init footer view    初始化footview
        mFooterView = new SmoothListViewFooter(context);

        // init header height      获取listView头部的高度
        mHeaderView.getViewTreeObserver().addOnGlobalLayoutListener(
                new OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        mHeaderViewHeight = mHeaderViewContent.getHeight();//80dp
                        getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }
                });
    }

    /**
     * 设置适配器
     * @param adapter
     */
    @Override
    public void setAdapter(ListAdapter adapter) {
        // make sure XListViewFooter is the last footer view, and only add once.
        //确保footerView是最后一个footer view，并且只加入一次
        if (mIsFooterReady == false) {
            mIsFooterReady = true;
            addFooterView(mFooterView);
        }
        super.setAdapter(adapter);
    }

    /**
     * enable or disable pull down refresh feature.
     * 设置下拉刷新可用，可用，显示下拉刷新的视图，否则，隐藏
     * @param enable
     */
    public void setRefreshEnable(boolean enable) {
        mEnablePullRefresh = enable;
        if (!mEnablePullRefresh) { // disable, hide the content
            mHeaderViewContent.setVisibility(View.INVISIBLE);
        } else {
            mHeaderViewContent.setVisibility(View.VISIBLE);
        }
    }

    /**
     * enable or disable pull up load more feature.
     * 设置加载更多可用，可用，显示加载更多的视图，否则，隐藏
     * @param enable
     */
    public void setLoadMoreEnable(boolean enable) {
        mEnablePullLoad = enable;
        if (!mEnablePullLoad) {
            mFooterView.hide();
            mFooterView.setOnClickListener(null);
            //make sure "pull up" don't show a line in bottom when listview with one page
            setFooterDividersEnabled(false);
        } else {
            mPullLoading = false;
            mFooterView.show();
            mFooterView.setState(SmoothListViewFooter.STATE_NORMAL);
            //make sure "pull up" don't show a line in bottom when listview with one page
            setFooterDividersEnabled(true);
            // both "pull up" and "click" will invoke load more.
            mFooterView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    startLoadMore();
                }
            });
        }
    }

    /**
     * stop refresh, reset header view.
     * 刷新完毕，重置header高度
     */
    public void stopRefresh() {
        if (mPullRefreshing == true) {
            mPullRefreshing = false;
            resetHeaderHeight();
        }
    }

    /**
     * stop load more, reset footer view.
     * 加载完毕，
     */
    public void stopLoadMore() {
        if (mPullLoading == true) {
            mPullLoading = false;
            mFooterView.setState(SmoothListViewFooter.STATE_NORMAL);
        }
    }

    /**
     * set last refresh time
     * 设置最后的刷新时间
     * @param time
     */
    public void setRefreshTime(String time) {
        mHeaderTimeView.setText(time);
    }

    /**
     * 唤起   滑动事件
     */
    private void invokeOnScrolling() {
        //如果
        if (mScrollListener instanceof OnSmoothScrollListener) {
            OnSmoothScrollListener l = (OnSmoothScrollListener) mScrollListener;
            l.onSmoothScrolling(this);
        }
    }

    /**
     * 更新header的高度
     * @param delta
     */
    private void updateHeaderHeight(float delta) {
        mHeaderView.setVisiableHeight((int) delta
                + mHeaderView.getVisiableHeight());
        if (mEnablePullRefresh && !mPullRefreshing) { // 未处于刷新状态，更新箭头
            if (mHeaderView.getVisiableHeight() > mHeaderViewHeight) {
                mHeaderView.setState(SmoothListViewHeader.STATE_READY);
            } else {
                mHeaderView.setState(SmoothListViewHeader.STATE_NORMAL);
            }
        }
        setSelection(0); // scroll to top each time   每次回滚到顶部
    }

    /**
     * reset header view's height.
     * 重置 headerView 的高度
     */
    private void resetHeaderHeight() {
        int height = mHeaderView.getVisiableHeight();
        if (height == 0) // not visible.
            return;
        // refreshing and header isn't shown fully. do nothing.
        if (mPullRefreshing && height <= mHeaderViewHeight) {
            return;
        }
        int finalHeight = 0; // default: scroll back to dismiss header.
        // is refreshing, just scroll back to show all the header.
        if (mPullRefreshing && height > mHeaderViewHeight) {
            finalHeight = mHeaderViewHeight;
        }
        mScrollBack = SCROLLBACK_HEADER;
        mScroller.startScroll(0, height, 0, finalHeight - height,
                SCROLL_DURATION);
        // trigger computeScroll
        invalidate();
    }

    /**
     * 更新footer view高度
     * @param delta  Y轴滑动距离
     */
    private void updateFooterHeight(float delta) {
        int height = mFooterView.getBottomMargin() + (int) delta;
        if (mEnablePullLoad && !mPullLoading) {
            if (height > PULL_LOAD_MORE_DELTA) { // height enough to invoke load
                // more.
                mFooterView.setState(SmoothListViewFooter.STATE_READY);
            } else {
                mFooterView.setState(SmoothListViewFooter.STATE_NORMAL);
            }
        }
        mFooterView.setBottomMargin(height);

//		setSelection(mTotalItemCount - 1); // scroll to bottom
    }

    /**
     * 重置footer view的高度
     */
    private void resetFooterHeight() {
        int bottomMargin = mFooterView.getBottomMargin();
        if (bottomMargin > 0) {
            mScrollBack = SCROLLBACK_FOOTER;
            mScroller.startScroll(0, bottomMargin, 0, -bottomMargin, SCROLL_DURATION);//开始滚动
            invalidate();
        }
    }

    /**
     * 开始加载更多
     */
    private void startLoadMore() {
        mPullLoading = true;
        mFooterView.setState(SmoothListViewFooter.STATE_LOADING);
        if (mListViewListener != null) {
            mListViewListener.onLoadMore();
        }
    }

    /**
     * 触摸事件的处理
     * @param ev
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mLastY == -1) {//如果最后的Y坐标为-1，需要获取到Y坐标
            mLastY = ev.getRawY();
        }

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                final float deltaY = ev.getRawY() - mLastY;     //移动的Y坐标距离
                mLastY = ev.getRawY();
                if (getFirstVisiblePosition() == 0  //如果第一个可视项为0，并且 headerView的高度大于0，滑动距离大于0（下滑）
                        && (mHeaderView.getVisiableHeight() > 0 || deltaY > 0)) {
                    // the first item is showing, header has shown or pull down.
                    updateHeaderHeight(deltaY / OFFSET_RADIO);
                    invokeOnScrolling();
                }
                //如果底部可视项为最后一项，并且 footerView的高度大于0，滑动距离小于0（上拉）
                else if (getLastVisiblePosition() == mTotalItemCount - 1
                        && (mFooterView.getBottomMargin() > 0 || deltaY < 0)) {
                    // last item, already pulled up or want to pull up.
                    updateFooterHeight(-deltaY / OFFSET_RADIO);
                }
                break;
            default:
                mLastY = -1; // reset
                if (getFirstVisiblePosition() == 0) {
                    // invoke refresh
                    if (mEnablePullRefresh
                            && mHeaderView.getVisiableHeight() > mHeaderViewHeight) {
                        mPullRefreshing = true;
                        mHeaderView.setState(SmoothListViewHeader.STATE_REFRESHING);
                        if (mListViewListener != null) {
                            mListViewListener.onRefresh();
                        }
                    }
                    resetHeaderHeight();
                } else if (getLastVisiblePosition() == mTotalItemCount - 1) {
                    // invoke load more.
                    if (mEnablePullLoad
                            && mFooterView.getBottomMargin() > PULL_LOAD_MORE_DELTA
                            && !mPullLoading) {
                        startLoadMore();
                    }
                    resetFooterHeight();
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {//当你想要知道新位置的时候回调，如果返回true，表明动画还没有开始
            if (mScrollBack == SCROLLBACK_HEADER) {//如果是从header回滚
                mHeaderView.setVisiableHeight(mScroller.getCurrY());//设置头布局的高度
            } else {
                mFooterView.setBottomMargin(mScroller.getCurrY());//设置footer的高度
            }
            postInvalidate();//异步重绘视图
            invokeOnScrolling();
        }
        super.computeScroll();
    }

    @Override
    public void setOnScrollListener(OnScrollListener l) {
        mScrollListener = l;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (mScrollListener != null) {
            mScrollListener.onScrollStateChanged(view, scrollState);
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        // send to user's listener
        mTotalItemCount = totalItemCount;
        if (mScrollListener != null) {
            mScrollListener.onScroll(view, firstVisibleItem, visibleItemCount,
                    totalItemCount);
        }
    }

    public void setSmoothListViewListener(ISmoothListViewListener l) {
        mListViewListener = l;
    }



    /**
     * you can listen ListView.OnScrollListener or this one. it will invoke
     * onSmoothScrolling when header/footer scroll back.
     */
    public interface OnSmoothScrollListener extends OnScrollListener {
        void onSmoothScrolling(View view);
    }

    /**
     * implements this interface to get refresh/load more event.
     * 一个定义了刷新跟加载跟多的接口
     */
    public interface ISmoothListViewListener {
        void onRefresh();

        void onLoadMore();
    }
}
