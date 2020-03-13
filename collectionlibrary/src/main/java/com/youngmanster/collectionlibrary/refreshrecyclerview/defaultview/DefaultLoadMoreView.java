package com.youngmanster.collectionlibrary.refreshrecyclerview.defaultview;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.youngmanster.collectionlibrary.R;
import com.youngmanster.collectionlibrary.refreshrecyclerview.base.refreshview.BaseLoadMoreView;
import com.youngmanster.collectionlibrary.refreshrecyclerview.pulltorefresh.PullToRefreshRecyclerViewUtils;

/**
 * Created by yangyan
 * on 2018/3/9.
 */

public class DefaultLoadMoreView extends BaseLoadMoreView {

	private TextView noDataTv;
	private LinearLayout loadMoreLl;
	private TextView refreshStatusTv;
	private boolean isDestroy = false;

	public DefaultLoadMoreView(Context context) {
		super(context);
	}

	@Override
	public void initView(Context context){
		mContainer =LayoutInflater.from(context).inflate(R.layout.collection_library_layout_default_loading_more, null);
		addView(mContainer);
		setGravity(Gravity.CENTER);
		noDataTv=mContainer.findViewById(R.id.no_data);
		loadMoreLl=mContainer.findViewById(R.id.loadMore_Ll);
		refreshStatusTv=mContainer.findViewById(R.id.refresh_status_tv);

		if(PullToRefreshRecyclerViewUtils.loadingTextConfig!=null){
			noDataTv.setText(PullToRefreshRecyclerViewUtils.loadingTextConfig.getCollectionNoMoreData());
			refreshStatusTv.setText(PullToRefreshRecyclerViewUtils.loadingTextConfig.getCollectionLoadingMore());
		}
	}

	@Override
	public void setState(int state) {

		if(isDestroy){
			return;
		}

		this.setVisibility(VISIBLE);
		switch (state){
			case STATE_LOADING:
				loadMoreLl.setVisibility(VISIBLE);
				noDataTv.setVisibility(INVISIBLE);
				break;
			case STATE_COMPLETE:
				this.setVisibility(GONE);
				break;
			case STATE_NODATA:
				loadMoreLl.setVisibility(GONE);
				noDataTv.setVisibility(VISIBLE);
				break;
		}
		mState = state;

	}

	@Override
	public void destroy() {
		isDestroy=true;
	}

}
