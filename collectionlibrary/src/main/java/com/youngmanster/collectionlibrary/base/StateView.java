package com.youngmanster.collectionlibrary.base;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.youngmanster.collectionlibrary.R;

/**
 * 页面状态转换
 * Created by yangyan
 * on 2018/3/18.
 */

public class StateView extends LinearLayout {

	//当前的加载状态
	public static final int STATE_NO_DATA = 0;
	public static final int STATE_LOADING = 1;
	public static final int STATE_EMPTY = 2;
	public static final int STATE_DISCONNECT=3;

	//布局添加位置
	private static final int VIEW_POSITION = 0;

	//加载控件
	private View mLoadingView;
	private int loadingViewDrawable;
	private String loadingText;
	//空布局
	private int mEmptyImageId;
	private String mEmptyText;
	private View mEmptyView;
	//无网络
	private int mDisConnectImageId;
	private String mDisConnectText;
	private View mDisConnectView;

	private int mTextColor;
	private int mTextSize;

	private LayoutInflater mInflater;
	private ViewGroup.LayoutParams params;
	private OnEmptyViewListener mEmptyViewListener;
	private OnDisConnectListener mDisConnectViewListener;
	//动画
	private AnimationDrawable animationDrawable;


	public StateView(@NonNull Context context) {
		this(context, null);
	}

	public StateView(@NonNull Context context, @Nullable AttributeSet attrs) {
		this(context, attrs, R.attr.styleStateView);
	}

	public StateView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.StateView, defStyleAttr, R.style.StateView_Config);

		loadingViewDrawable = typedArray.getResourceId(R.styleable.StateView_loadingViewAnimation, NO_ID);
		loadingText = typedArray.getString(R.styleable.StateView_loadingText);

		mEmptyImageId = typedArray.getResourceId(R.styleable.StateView_emptyImage, NO_ID);
		mEmptyText = typedArray.getString(R.styleable.StateView_emptyText);

		mDisConnectImageId = typedArray.getResourceId(R.styleable.StateView_disConnectImage, NO_ID);
		mDisConnectText = typedArray.getString(R.styleable.StateView_disConnectText);

		mTextColor = typedArray.getColor(R.styleable.StateView_tipTextColor, 0x8a000000);
		mTextSize = typedArray.getDimensionPixelSize(R.styleable.StateView_tipTextSize, 14);

		typedArray.recycle();

		mInflater = LayoutInflater.from(context);
		params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		setBackgroundColor(getContext().getResources().getColor(R.color.white));

		setLoadingView();
		setEmptyView();
		setDisConnectView();
	}

	/**
	 * 显示加载中的状态
	 */
	private void setLoadingView() {

		if(mLoadingView==null){
			mLoadingView = mInflater.inflate(R.layout.library_view_loading, null);
			LinearLayout loadMore_Ll = mLoadingView.findViewById(R.id.library_loadMore_Ll);
			ProgressBar loadingBar = mLoadingView.findViewById(R.id.library_loadingBar);
			ImageView loadingIv = mLoadingView.findViewById(R.id.library_loadingIv);
			TextView loadingTv = mLoadingView.findViewById(R.id.library_loadingTv);

			if (loadingViewDrawable != NO_ID) {
				loadingIv.setImageResource(loadingViewDrawable);
				loadingBar.setVisibility(GONE);
				loadMore_Ll.setVisibility(VISIBLE);
				loadingTv.setText(loadingText);
				loadingTv.setTextColor(mTextColor);
				loadingTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
				animationDrawable = (AnimationDrawable) loadingIv.getDrawable();
				if (animationDrawable != null) {
					animationDrawable.start();
				}

			} else {
				loadingBar.setVisibility(VISIBLE);
				loadMore_Ll.setVisibility(GONE);
			}
			addView(mLoadingView, VIEW_POSITION, params);
		}
	}

	/**
	 * 显示无数据状态
	 */
	private void setEmptyView() {
		if(mEmptyView==null){
			mEmptyView = mInflater.inflate(R.layout.library_view_empty, null);
			ImageView emptyImage = mEmptyView.findViewById(R.id.library_empty_image);
			TextView emptyText = mEmptyView.findViewById(R.id.library_empty_text);
			if (null != emptyImage && mEmptyImageId != NO_ID) {
				emptyImage.setImageResource(mEmptyImageId);
			}

			if (null != emptyText && !TextUtils.isEmpty(mEmptyText)) {
				emptyText.setText(mEmptyText);
				emptyText.setTextColor(mTextColor);
				emptyText.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
			}

			addView(mEmptyView, VIEW_POSITION, params);

		}

		mEmptyView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				if (null != mEmptyViewListener) {
					mEmptyViewListener.onEmptyViewClick();
				}
			}
		});
	}

	/**
	 * 显示无网络
	 */
	private void setDisConnectView() {
		if(mDisConnectView==null){
			mDisConnectView = mInflater.inflate(R.layout.library_view_disconnect, null);
			ImageView disConnectImage = mDisConnectView.findViewById(R.id.library_disconnect_image);
			TextView disConnectText = mDisConnectView.findViewById(R.id.library_disconnect_text);
			if (null != disConnectImage && mDisConnectImageId != NO_ID) {
				disConnectImage.setImageResource(mDisConnectImageId);
			}

			if (null != disConnectText && !TextUtils.isEmpty(mDisConnectText)) {
				disConnectText.setText(mDisConnectText);
				disConnectText.setTextColor(mTextColor);
				disConnectText.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
			}

			addView(mDisConnectView, VIEW_POSITION, params);
		}

		mDisConnectView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				if (null != mDisConnectViewListener) {
					mDisConnectViewListener.onDisConnectViewClick();
				}
			}
		});
	}


	/**
	 * 设置状态
	 *
	 * @param state
	 */
	public void showViewByState(int state) {
		//如果当前状态为加载成功，隐藏此View，反之显示
		this.setVisibility(state == STATE_NO_DATA ? View.GONE : View.VISIBLE);

		if (state == STATE_NO_DATA) {
			if (animationDrawable != null) {
				animationDrawable.stop();
			}
		}

		if (null != mLoadingView) {
			mLoadingView.setVisibility(state == STATE_LOADING ? View.VISIBLE : View.GONE);
		}

		if (null != mEmptyView) {
			mEmptyView.setVisibility(state == STATE_EMPTY ? View.VISIBLE : View.GONE);
		}

		if(null!=mDisConnectView){
			mDisConnectView.setVisibility(state == STATE_DISCONNECT ? View.VISIBLE : View.GONE);
		}
	}

	/**
	 * ============================空布局点击监听=================================================
	 */

	public void setOnEmptyViewListener(OnEmptyViewListener listener) {
		mEmptyViewListener = listener;
	}

	public interface OnEmptyViewListener {
		void onEmptyViewClick();
	}

	/**
	 * ============================网络连接失败=================================================
	 */

	public void setOnDisConnectViewListener(OnDisConnectListener listener) {
		mDisConnectViewListener = listener;
	}

	public interface OnDisConnectListener {
		void onDisConnectViewClick();
	}
}
