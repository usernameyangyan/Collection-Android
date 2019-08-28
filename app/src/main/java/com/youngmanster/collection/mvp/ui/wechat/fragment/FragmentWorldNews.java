package com.youngmanster.collection.mvp.ui.wechat.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.youngmanster.collection.R;
import com.youngmanster.collection.base.BaseFragment;
import com.youngmanster.collection.been.wechat.WeChatNews;
import com.youngmanster.collection.mvp.contract.wechat.okhttpcache.WeChatWorldNewsContract;
import com.youngmanster.collection.mvp.presenter.wechat.okhttpcache.WeChatWorldNewsPresenter;
import com.youngmanster.collection.mvp.ui.wechat.adapter.WeChatFeaturedAdapter;
import com.youngmanster.collectionlibrary.base.stateview.StateView;
import com.youngmanster.collectionlibrary.refreshrecyclerview.base.adapter.BaseRecyclerViewAdapter;
import com.youngmanster.collectionlibrary.refreshrecyclerview.pulltorefresh.PullToRefreshRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by yangyan
 * on 2018/3/21.
 */

public class FragmentWorldNews extends BaseFragment<WeChatWorldNewsPresenter> implements
		WeChatWorldNewsContract.View, PullToRefreshRecyclerView.OnRefreshAndLoadMoreListener,
		BaseRecyclerViewAdapter.OnItemClickListener,SwipeRefreshLayout.OnRefreshListener {

	@BindView(R.id.swipeRefreshLayout)
	SwipeRefreshLayout swl_Refresh;
	@BindView(R.id.refreshRv)
	PullToRefreshRecyclerView refreshRv;
	@BindView(R.id.state_view)
	StateView stateView;

	private static final int PAGE_SIZE = 15;
	private int pageSize = 1;

	private List<WeChatNews> mDatas = new ArrayList<>();
	private WeChatFeaturedAdapter weChatFeaturedAdapter;

	@Override
	public int getLayoutId() {
		return R.layout.fragment_wechat_news1;
	}

	@Override
	public void init() {
		stateView.showViewByState(StateView.STATE_LOADING);
		stateView.setOnDisConnectViewListener(new StateView.OnDisConnectListener() {
			@Override
			public void onDisConnectViewClick() {
				requestData();
			}
		});


		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
		linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		refreshRv.setLayoutManager(linearLayoutManager);
		refreshRv.setPullRefreshEnabled(false);
		refreshRv.setLoadMoreEnabled(true);
		refreshRv.setRefreshAndLoadMoreListener(this);
		swl_Refresh.setColorSchemeResources(R.color.colorAccent);
		swl_Refresh.setOnRefreshListener(this);
	}

	@Override
	public void requestData() {
		((WeChatWorldNewsPresenter) mPresenter).requestWorldNews(pageSize, PAGE_SIZE);
	}

	@Override
	public void refreshUI(List<WeChatNews> newsList) {

		if (newsList != null) {
			if (pageSize == 1) {
				mDatas.clear();
				mDatas.addAll(newsList);
			} else {
				mDatas.addAll(newsList);
			}

		}

		if (weChatFeaturedAdapter == null) {
			if (mDatas.size() == 0) {
				stateView.showViewByState(StateView.STATE_EMPTY);
			} else {
				stateView.showViewByState(StateView.STATE_NO_DATA);
			}
			weChatFeaturedAdapter = new WeChatFeaturedAdapter(getActivity(), mDatas, refreshRv);
			refreshRv.setAdapter(weChatFeaturedAdapter);
		} else {

			if (swl_Refresh!=null&&swl_Refresh.isRefreshing()) {
				swl_Refresh.setRefreshing(false);
				weChatFeaturedAdapter.notifyDataSetChanged();
			}else if (refreshRv.isLoading()) {
				refreshRv.loadMoreComplete();
				if (newsList==null||newsList.size() == 0) {
					refreshRv.setNoMoreDate(true);
				}
			}
		}
	}


	@Override
	public void onItemClick(View view, int position) {

	}

	@Override
	public void onRefresh() {
		pageSize = 1;
		requestData();
	}

	@Override
	public void onRecyclerViewRefresh() {

	}

	@Override
	public void onRecyclerViewLoadMore() {
		pageSize++;
		requestData();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (refreshRv != null) {
			refreshRv.destroy();
			refreshRv = null;
		}
	}

	@Override
	public void onError(String errorMsg) {
		showToast(errorMsg);
		if(mDatas.size()==0){
			stateView.showViewByState(StateView.STATE_DISCONNECT);
		}
	}
}
