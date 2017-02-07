package com.sample.toolbar.widget.SmoothListView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sample.toolbar.R;


/**
 * 刷新时SmoothListView 的底部视图
 *
 */
public class SmoothListViewFooter extends LinearLayout {
	public final static int STATE_NORMAL = 0;
	public final static int STATE_READY = 1;
	public final static int STATE_LOADING = 2;

	private Context mContext;
	/**
	 * 内容视图
	 */
	private View mContentView;
	/**
	 * loading 提示视图
	 */
	private View mProgressBar;
	/**
	 * 提示消息
	 */
	private TextView mHintView;
	
	public SmoothListViewFooter(Context context) {
		super(context);
		initView(context);
	}
	
	public SmoothListViewFooter(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	/**
	 * 设置当前的状态
	 * @param state
     */
	public void setState(int state) {
		//先全部不可见
		mHintView.setVisibility(View.INVISIBLE);
		mProgressBar.setVisibility(View.INVISIBLE);
		mHintView.setVisibility(View.INVISIBLE);
		//如果传入的是准备加载的，说明在下拉状态
		if (state == STATE_READY) {
			mHintView.setVisibility(View.VISIBLE);
			mHintView.setText(R.string.smoothlistview_footer_hint_ready);
		} else if (state == STATE_LOADING) {//如果传入的是正在加载的，说明在松开状态，设置ProgressBar可见
			mProgressBar.setVisibility(View.VISIBLE);
		} else {
			mHintView.setVisibility(View.VISIBLE);
			mHintView.setText(R.string.smoothlistview_footer_hint_normal);
		}
	}

	/**
	 * 设置距离底部的margin
	 * @param height
     */
	public void setBottomMargin(int height) {
		if (height < 0) return ;
		LayoutParams lp = (LayoutParams)mContentView.getLayoutParams();
		lp.bottomMargin = height;
		mContentView.setLayoutParams(lp);
	}

	/**
	 * 获取距离底部的margin
	 * @return
     */
	public int getBottomMargin() {
		LayoutParams lp = (LayoutParams)mContentView.getLayoutParams();
		return lp.bottomMargin;
	}
	
	
	/**
	 * normal status
	 */
	public void normal() {
		mHintView.setVisibility(View.VISIBLE);
		mProgressBar.setVisibility(View.GONE);
	}
	
	
	/**
	 * loading status 
	 */
	public void loading() {
		mHintView.setVisibility(View.GONE);
		mProgressBar.setVisibility(View.VISIBLE);
	}
	
	/**
	 * hide footer when disable pull load more
	 * 隐藏   加载更多视图   设置  mContentView  高度为0
	 */
	public void hide() {
		LayoutParams lp = (LayoutParams)mContentView.getLayoutParams();
		lp.height = 0;
		mContentView.setLayoutParams(lp);
	}
	
	/**
	 * show footer
	 * 显示底部视图，并设置高度
	 */
	public void show() {
		LayoutParams lp = (LayoutParams)mContentView.getLayoutParams();
		lp.height = LayoutParams.WRAP_CONTENT;
		mContentView.setLayoutParams(lp);
	}

	/**
	 * 初始化视图
	 * @param context
     */
	private void initView(Context context) {
		mContext = context;
		LinearLayout moreView = (LinearLayout)LayoutInflater.from(mContext).inflate(R.layout.smoothlistview_footer, null);
		addView(moreView);
		moreView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		
		mContentView = moreView.findViewById(R.id.smoothlistview_footer_content);
		mProgressBar = moreView.findViewById(R.id.smoothlistview_footer_progressbar);
		mHintView = (TextView)moreView.findViewById(R.id.smoothlistview_footer_hint_textview);
	}
	
	
}
