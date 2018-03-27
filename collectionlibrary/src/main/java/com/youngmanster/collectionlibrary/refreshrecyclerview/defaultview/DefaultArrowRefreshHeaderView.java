package com.youngmanster.collectionlibrary.refreshrecyclerview.defaultview;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wang.avi.AVLoadingIndicatorView;
import com.youngmanster.collectionlibrary.R;
import com.youngmanster.collectionlibrary.refreshrecyclerview.base.refreshview.BasePullToRefreshView;
import com.youngmanster.collectionlibrary.refreshrecyclerview.pulltorefresh.PullToRefreshRecyclerViewUtils;

/**
 * 默认的刷新头部
 * 刷新用的是AVLoadingIndicatorView动画库
 * Created by yangyan
 * on 2018/3/9.
 */

public class DefaultArrowRefreshHeaderView extends BasePullToRefreshView implements BasePullToRefreshView.OnStateChangeListener {

	private static final int ROTATE_DURATION = 180;

	private LinearLayout mRefrehsContainer;//刷新时间布局
	private ImageView arrowIv;
	private TextView refreshStateTv;
	private AVLoadingIndicatorView progressView;
	private TextView lastRefreshTimeTv;
	//刷新箭头装换方向动画
	private Animation mRotateUpAnim;
	private Animation mRotateDownAnim;

	private Context context;

	public DefaultArrowRefreshHeaderView(Context context) {
		super(context);
		onStateChangeListener = this;
	}


	/**
	 * 初始化HearView
	 */
	@Override
	public void initView(Context context) {
		this.context = context;
		mContainer = LayoutInflater.from(context).inflate(R.layout.layout_default_arrow_refresh, null);
		mRefrehsContainer = mContainer.findViewById(R.id.refresh_time_container);

		//把刷新头部的高度初始化为0
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		lp.setMargins(0, 0, 0, 0);
		this.setLayoutParams(lp);
		this.setPadding(0, 0, 0, 0);
		addView(mContainer, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0));
		setGravity(Gravity.BOTTOM);

		arrowIv = mContainer.findViewById(R.id.refresh_arrow);
		refreshStateTv = mContainer.findViewById(R.id.refresh_status_tv);
		progressView = mContainer.findViewById(R.id.av_progressbar);
		progressView.setIndicatorColor(0xffB5B5B5);
		lastRefreshTimeTv = mContainer.findViewById(R.id.last_refresh_time);

		mRotateUpAnim = new RotateAnimation(0.0f, -180.0f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		mRotateUpAnim.setDuration(ROTATE_DURATION);
		mRotateUpAnim.setFillAfter(true);
		mRotateDownAnim = new RotateAnimation(-180.0f, 0.0f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		mRotateDownAnim.setDuration(ROTATE_DURATION);
		mRotateDownAnim.setFillAfter(true);

		//测量高度
		measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		mMeasuredHeight = getMeasuredHeight();
	}

	@Override
	public void setRefreshTimeVisible(boolean show) {
		if (mRefrehsContainer != null)
			mRefrehsContainer.setVisibility(show ? VISIBLE : GONE);
	}

	@Override
	public void destroy() {
		if (progressView != null) {
			progressView = null;
		}
		if (mRotateUpAnim != null) {
			mRotateUpAnim.cancel();
			mRotateUpAnim = null;
		}
		if (mRotateDownAnim != null) {
			mRotateDownAnim.cancel();
			mRotateDownAnim = null;
		}
	}

	@Override
	public void onStateChange(int state) {
		//下拉时状态相同不做继续保持原有的状态
		if (state == mState) return;

		//根据状态处理刷新控件的外显
		if (state == STATE_REFRESHING) {
			arrowIv.clearAnimation();
			arrowIv.setVisibility(View.INVISIBLE);
			if (progressView != null)
				progressView.setVisibility(View.VISIBLE);
			scrollTo(mMeasuredHeight);
		} else if (state == STATE_DONE) {//执行这一步之前会先执行重置刷新布局，因此这里不需要设置布局高度
			arrowIv.setVisibility(View.INVISIBLE);
			if (progressView != null)
				progressView.setVisibility(View.INVISIBLE);
		} else {//正常显示箭头
			arrowIv.setVisibility(View.VISIBLE);
			if (progressView != null) {
				progressView.setVisibility(View.INVISIBLE);
			}
		}

		//根据状态进行动画显示
		switch (state) {
			case STATE_PULL_DOWN:
				arrowIv.clearAnimation();
				arrowIv.startAnimation(mRotateDownAnim);
				refreshStateTv.setText(R.string.collection_pull_to_refresh);
				break;
			case STATE_RELEASE_REFRESH:
				//时间更新
				lastRefreshTimeTv.setText(PullToRefreshRecyclerViewUtils.getTimeConvert(PullToRefreshRecyclerViewUtils.getLastRefreshTime(context)));
				arrowIv.clearAnimation();
				arrowIv.startAnimation(mRotateUpAnim);
				refreshStateTv.setText(R.string.collection_release_refresh);
				break;
			case STATE_REFRESHING:
				lastRefreshTimeTv.setText(PullToRefreshRecyclerViewUtils.getTimeConvert(PullToRefreshRecyclerViewUtils.getLastRefreshTime(context)));
				refreshStateTv.setText(R.string.collection_refreshing);
				break;
			case STATE_DONE:
				PullToRefreshRecyclerViewUtils.saveLastRefreshTime(context, System.currentTimeMillis());
				refreshStateTv.setText(R.string.collection_refresh_done);
				break;
		}
		mState = state;
	}
}
