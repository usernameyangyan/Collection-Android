package com.youngmanster.collectionlibrary.refreshrecyclerview.base.refreshview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

/**
 * 下拉刷新基类，如果要自定义加载布局只需要继承该基类，在对应的方法中进行逻辑整理
 * Created by yangyan
 * on 2018/3/9.
 */

public abstract class BasePullToRefreshView extends LinearLayout {
	/***
	 * 下拉刷新分为4个状态
	 */
	//下拉的状态（还没到下拉到固定的高度时）
	public static final int STATE_PULL_DOWN=0;//
	//下拉到固定高度提示释放刷新的状态
	public static final int STATE_RELEASE_REFRESH=1;
	//刷新状态
	public static final int STATE_REFRESHING=2;
	//刷新完成
	public static final int STATE_DONE=3;

	//初始化状态
	public int mState = STATE_PULL_DOWN;

	public View mContainer;
	public int mMeasuredHeight;

	public OnStateChangeListener onStateChangeListener;

	public BasePullToRefreshView(Context context) {
		super(context);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);
		this.setLayoutParams(lp);
		initView(context);
	}

	/**
	 * 根据下拉的移动的距离进行状态切换以及顶部刷新View高度控制
	 */
	public void onMove(float distance) {
		if(getVisibleHeight() > 0 || distance > 0) {
			setVisibleHeight((int) distance + getVisibleHeight());
			if (mState <= STATE_RELEASE_REFRESH) { // 未处于刷新状态，更新箭头
				if (getVisibleHeight() > mMeasuredHeight) {
					onStateChangeListener.onStateChange(STATE_RELEASE_REFRESH);
				}else {
					onStateChangeListener.onStateChange(STATE_PULL_DOWN);
				}
			}
		}

	}


	/**
	 *处理下拉刷新释放后的动作
	 */
	public boolean dealReleaseAction() {
		boolean isRefreshing = false;
		//如果刷新布局没有显示，就不用进行下一步了
		int height = getVisibleHeight();
		if (height == 0)
			isRefreshing = false;

		//下拉和释放两种状态之后就进入正在刷新状态
		if(getVisibleHeight() > mMeasuredHeight &&  mState < STATE_REFRESHING){
			onStateChangeListener.onStateChange(STATE_REFRESHING);
			isRefreshing = true;
		}

		if (mState != STATE_REFRESHING) {
			scrollTo(0);
		}

		if (mState == STATE_REFRESHING) {
			scrollTo(mMeasuredHeight);
		}
		return isRefreshing;
	}



	/**
	 * 刷新完成
	 */
	public void refreshComplete() {
		onStateChangeListener.onStateChange(STATE_DONE);
		new Handler().postDelayed(new Runnable(){
			public void run() {
				reset();
			}
		}, 200);
	}
	/**
	 * 重置刷新布局
	 */
	public void reset() {
		scrollTo(0);
		new Handler().postDelayed(new Runnable() {
			public void run() {
				onStateChangeListener.onStateChange(STATE_PULL_DOWN);
			}
		}, 500);
	}
	/**
	 * 获取状态
	 */
	public int getState() {
		return mState;
	}
	/**
	 * 滚动到的位置
	 */
	public void scrollTo(int height) {
		//动画设置高度
		ValueAnimator animator = ValueAnimator.ofInt(getVisibleHeight(), height);
		animator.setDuration(300).start();
		animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation)
			{
				setVisibleHeight((int) animation.getAnimatedValue());
			}
		});
		animator.start();
	}
	/**
	 *根据状态设置刷新顶部的高度
	 */
	public void setVisibleHeight(int height) {
		if (height < 0) height = 0;
		LayoutParams lp = (LayoutParams) mContainer .getLayoutParams();
		lp.height = height;
		mContainer.setLayoutParams(lp);
	}
	/**
	 * 获取刷新布局的高度
	 */
	public int getVisibleHeight() {
		LayoutParams lp = (LayoutParams) mContainer .getLayoutParams();
		return lp.height;
	}

	/**
	 * 组件初始化
	 */
	public abstract void initView(Context context);
	/**
	 * 显示加载更新时间
	 */
	public abstract void setRefreshTimeVisible(boolean show);

	/**
	 * 销毁页面对象和动画，防止内存泄漏
	 */
	public abstract void destroy();

	/**
	 * 状态变化通知
	 */
	public interface OnStateChangeListener{
		void onStateChange(int state);
	}
}
