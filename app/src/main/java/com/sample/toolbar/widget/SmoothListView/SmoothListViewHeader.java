package com.sample.toolbar.widget.SmoothListView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sample.toolbar.R;


/**
 * SmoothListView刷新时的head视图
 */
public class SmoothListViewHeader extends LinearLayout {
	/**
	 * 布局容器
	 */
	private LinearLayout mContainer;
	/**
	 * 箭头view
	 */
	private ImageView mArrowImageView;
	/**
	 * 进度条loading
	 */
	private ProgressBar mProgressBar;

	/**
	 * 刷新提示信息
	 */
	private TextView mHintTextView;
	/**
	 * 现在的状态
	 */
	private int mState = STATE_NORMAL;
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
	/**
	 * 刷新的3个状态   正常状态
	 */
	public final static int STATE_NORMAL = 0;
	/**
	 * 刷新的3个状态   准备刷新，下拉移动
	 */
	public final static int STATE_READY = 1;
	/**
	 * 刷新的3个状态   正在刷新，下拉后手放开
	 */
	public final static int STATE_REFRESHING = 2;

	public SmoothListViewHeader(Context context) {
		super(context);
		initView(context);
	}

	public SmoothListViewHeader(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	/**
	 * 初始化view ，动画等
	 * @param context
     */
	private void initView(Context context) {
		// 初始情况，设置下拉刷新view高度为0
		LayoutParams lp = new LayoutParams(
				LayoutParams.FILL_PARENT, 0);
		mContainer = (LinearLayout) LayoutInflater.from(context).inflate(
				R.layout.smoothlistview_header, null);
		addView(mContainer, lp);  //将这个布局容器加入到自定义视图中
		setGravity(Gravity.BOTTOM);//设置布局位置

		mArrowImageView = (ImageView)findViewById(R.id.smoothlistview_header_arrow);
		mHintTextView = (TextView)findViewById(R.id.smoothlistview_header_hint_textview);
		mProgressBar = (ProgressBar)findViewById(R.id.smoothlistview_header_progressbar);
		
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
	}

	/**
	 * 设置状态
	 * @param state
     */
	public void setState(int state) {
		if (state == mState) return ;//状态没有改变
		
		if (state == STATE_REFRESHING) {	// 显示进度，箭头的动画消失，箭头隐藏
			mArrowImageView.clearAnimation();
			mArrowImageView.setVisibility(View.INVISIBLE);
			mProgressBar.setVisibility(View.VISIBLE);
		} else {	// 显示箭头图片
			mArrowImageView.setVisibility(View.VISIBLE);
			mProgressBar.setVisibility(View.INVISIBLE);
		}
		
		switch(state){//判断传进来的状态参数
		case STATE_NORMAL:
			if (mState == STATE_READY) {//如果传进来的是正常状态，而当前是准备刷新，说明是在下拉转态，加载箭头向下动画
				mArrowImageView.startAnimation(mRotateDownAnim);
			}
			if (mState == STATE_REFRESHING) {//如果传进来的是正常状态，而当前是正在刷新，说明是在刷新完成，清除动画
				mArrowImageView.clearAnimation();
			}
			mHintTextView.setText(R.string.smoothlistview_header_hint_normal);
			break;
		case STATE_READY:
			if (mState != STATE_READY) {//如果传进来的是准备刷新，而当前是不是准备刷新，说明是在下拉准备刷新，向上动画
				mArrowImageView.clearAnimation();
				mArrowImageView.startAnimation(mRotateUpAnim);
				mHintTextView.setText(R.string.smoothlistview_header_hint_ready);
			}
			break;
		case STATE_REFRESHING://如果传进来的是正在刷新，设置    正在加载...  信息
			mHintTextView.setText(R.string.smoothlistview_header_hint_loading);
			break;
			default:
		}
		
		mState = state;
	}

	/**
	 * 设置容器布局的高度
	 * @param height
     */
	public void setVisiableHeight(int height) {
		if (height < 0)
			height = 0;
		LayoutParams lp = (LayoutParams) mContainer
				.getLayoutParams();
		lp.height = height;
		mContainer.setLayoutParams(lp);
	}

	/**
	 * 获取根布局高度
	 * @return
     */
	public int getVisiableHeight() {
		return mContainer.getHeight();
	}

}
