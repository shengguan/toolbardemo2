package com.sample.toolbar.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sample.toolbar.R;

/**
 * Created by Administrator on 2016/6/8.
 */
public class PullToRreshListView extends ListView {

    private boolean mRefreshEnable = true;
    private boolean mLoadMoreEnable = true;

    private View mHeaderView;

    /**
     * headerView根视图
     */
    private RelativeLayout mHeaderViewContent;

    /**
     * 箭头view
     */
    private ImageView mArrowImageView;
    /**
     * 进度条loading
     */
    private ProgressBar mProgressBarHeader;

    /**
     * 刷新提示信息
     */
    private TextView mTvHeaderHintTextView;
    /**
     * 向上的动画
     */
    private Animation mRotateUpAnim;
    /**
     * 向下的动画
     */
    private Animation mRotateDownAnim;
    /**
     * 动画时间
     */
    private final int ROTATE_ANIM_DURATION = 180;


    private View mFooterView;
    /**
     * 内容视图
     */
    private View mContentView;
    /**
     * loading 提示视图
     */
    private ProgressBar mProgressBarFooter;
    /**
     * 提示消息
     */
    private TextView mTvFooterHintView;

    private LayoutInflater mInflater;

    private int mTouchSlop;//view移动需要的最短距离

    private float mLastY;

    private float mCurrY;

    private float deltaY;//移动的距离


    /**
     * listView所有的子项数量
     */
    private int mTotalItemCount;

    private final static float OFFSET_RADIO = 1.8f; // support iOS like pull


    /**
     * 刷新的3个状态   正常状态
     */
    private final static int STATE_HEADER_NORMAL = 0;
    /**
     * 刷新的3个状态   准备刷新，下拉移动
     */
    private final static int STATE_HEADER_READY = 1;
    /**
     * 刷新的3个状态   正在刷新，下拉后手放开
     */
    private final static int STATE_HEADER_REFRESHING = 2;

    private final static int STATE_FOOTER_NORMAL = 0;
    private final static int STATE_FOOTER_READY = 1;
    private final static int STATE_FOOTER_LOADING = 2;
    /**
     * 头部的转态
     */
    private int mHeaderState = STATE_HEADER_NORMAL;
    /**
     * 底部的状态
     */
    private int mFooterState = STATE_FOOTER_NORMAL;


    private boolean mIsFooterReady = false;


    public PullToRreshListView(Context context) {
        super(context);

        init(context);
    }

    public PullToRreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PullToRreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    /**
     * 初始化
     *
     * @param context
     */
    private void init(Context context) {

        mInflater = LayoutInflater.from(context);

        ViewConfiguration config = ViewConfiguration.get(context);
        mTouchSlop = config.getScaledTouchSlop();

        mHeaderView = mInflater.inflate(R.layout.header_view, null);

        mHeaderViewContent = (RelativeLayout) mHeaderView.findViewById(R.id.smoothlistview_header_content);
        mArrowImageView = (ImageView) mHeaderView.findViewById(R.id.smoothlistview_header_arrow);
        mTvHeaderHintTextView = (TextView) mHeaderView.findViewById(R.id.smoothlistview_header_hint_textview);
        mProgressBarHeader = (ProgressBar) mHeaderView.findViewById(R.id.smoothlistview_header_progressbar);

        mRotateUpAnim = new RotateAnimation(0.0f, -180.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        mRotateUpAnim.setDuration(ROTATE_ANIM_DURATION);
        mRotateUpAnim.setFillAfter(true);
        mRotateDownAnim = new RotateAnimation(-180.0f, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        mRotateDownAnim.setDuration(ROTATE_ANIM_DURATION);
        mRotateDownAnim.setFillAfter(true);


        mFooterView = mInflater.inflate(R.layout.footer_view, null);

        mContentView = mFooterView.findViewById(R.id.smoothlistview_footer_content);
        mProgressBarFooter = (ProgressBar) mFooterView.findViewById(R.id.smoothlistview_footer_progressbar);
        mTvFooterHintView = (TextView) mFooterView.findViewById(R.id.smoothlistview_footer_hint_textview);

    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        int action = ev.getAction();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mLastY = ev.getRawY();
                break;
            case MotionEvent.ACTION_UP:

                if (true) {

                }


                break;
            case MotionEvent.ACTION_MOVE:

                deltaY = ev.getRawY() - mLastY;

                mLastY = ev.getRawY();
                //可是项为第一项的时候

                if (getFirstVisiblePosition() == 0 && (deltaY > 0 || mTouchSlop > 0)) {

                }

                //向下移动时，可视项为最后一项时
                if (getLastVisiblePosition() == (mTotalItemCount - 1) && (deltaY > 0 || mTouchSlop > 0)) {

                }

                break;

        }


        return super.onTouchEvent(ev);
    }

    /**
     * 在刷新或者拉伸的时候点击事件的
     *
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {


        int action = ev.getAction();

        switch (action) {
            case MotionEvent.ACTION_DOWN:

                break;
            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP:

                break;
        }


        return super.onInterceptTouchEvent(ev);
    }

    /**
     * 下拉过程更新头部高度
     */
    public void updateHeaderHeight() {

    }

    /**
     * 加载更多更新底部高度
     */
    public void updateFooterHeight() {

    }


    /**
     * 开始加载更多
     */
//    private void startLoadMore() {
//        mLoadMoreEnable = true;
//        mFooterView.setState(SmoothListViewFooter.STATE_LOADING);
//        if (mListViewListener != null) {
//            mListViewListener.onLoadMore();
//        }
//    }


    /**
     * 设置状态
     *
     * @param state
     */
    public void setHeaderState(int state) {
        if (state == mHeaderState) return;//状态没有改变
        //如果传进来的是正在刷新，控件的显示
        if (state == STATE_HEADER_REFRESHING) {    // 显示进度，箭头的动画消失，箭头隐藏
            mArrowImageView.clearAnimation();
            mArrowImageView.setVisibility(View.INVISIBLE);
            mProgressBarHeader.setVisibility(View.VISIBLE);
        } else {    // 显示箭头图片
            mArrowImageView.setVisibility(View.VISIBLE);
            mProgressBarHeader.setVisibility(View.INVISIBLE);
        }

        switch (state) {//判断传进来的状态参数
            case STATE_HEADER_NORMAL:
                if (mHeaderState == STATE_HEADER_READY) {//如果传进来的是正常状态，而当前是准备刷新，说明是在下拉转态，加载箭头向下动画
                    mArrowImageView.startAnimation(mRotateDownAnim);
                }
                if (mHeaderState == STATE_HEADER_REFRESHING) {//如果传进来的是正常状态，而当前是正在刷新，说明是在刷新完成，清除动画
                    mArrowImageView.clearAnimation();
                }
                mTvFooterHintView.setText(R.string.smoothlistview_header_hint_normal);
                break;
            case STATE_HEADER_READY:
                if (mHeaderState != STATE_HEADER_READY) {//如果传进来的是准备刷新，而当前是不是准备刷新，说明是在下拉准备刷新，向上动画
                    mArrowImageView.clearAnimation();
                    mArrowImageView.startAnimation(mRotateUpAnim);
                    mTvFooterHintView.setText(R.string.smoothlistview_header_hint_ready);
                }
                break;
            case STATE_HEADER_REFRESHING://如果传进来的是正在刷新，设置    正在加载...  信息
                mTvFooterHintView.setText(R.string.smoothlistview_header_hint_loading);
                break;
            default:
        }

        mHeaderState = state;
    }

    public OnRefreshListener mOnRefreshListener;

    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        this.mOnRefreshListener = onRefreshListener;
    }


    public interface OnRefreshListener {
        void refreshData();

        void loadMoreData();
    }

    /**
     * 刷新完成的动作
     */
    public void setRefreshComplete() {

    }

    /**
     * 加载更多完成的动作
     */
    public void setLoadMoreComplete() {

    }

    /**
     * set last refresh time
     * 设置最后的刷新时间
     *
     * @param time
     */
    public void setRefreshTime(String time) {
//        mHeaderTimeView.setText(time);
    }

    /**
     * 设置刷新是否可用
     *
     * @param refreshEnable
     */
    public void setRefreshEnable(boolean refreshEnable) {
        this.mRefreshEnable = refreshEnable;
        if (mRefreshEnable) {
            mHeaderView.setVisibility(View.VISIBLE);
        } else {
            mHeaderView.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 设置加载更多是否可用
     *
     * @param loadMoreEnable
     */
    public void setLoadMoreEnable(boolean loadMoreEnable) {
        this.mLoadMoreEnable = loadMoreEnable;
        if (mLoadMoreEnable) {
            mFooterView.setVisibility(View.VISIBLE);
        } else {
            mFooterView.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 设置适配器
     *
     * @param adapter
     */
    @Override
    public void setAdapter(ListAdapter adapter) {
        //确保footerView是最后一个footer view，并且只加入一次
        if (mIsFooterReady == false) {
            mIsFooterReady = true;
            addFooterView(mFooterView);
        }
        super.setAdapter(adapter);
    }



}
