package com.youngmanster.collection.fragment.recyclerview;

import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;

import com.youngmanster.collection.R;
import com.youngmanster.collection.adapter.recyclerview.DefinitionRecyclerAdapter;
import com.youngmanster.collection.base.BaseFragment;
import com.youngmanster.collection.view.DefinitionAnimationLoadMoreView;
import com.youngmanster.collection.view.DefinitionAnimationRefreshHeaderView;
import com.youngmanster.collectionlibrary.refreshrecyclerview.pulltorefresh.PullToRefreshRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by yangyan
 * on 2018/3/18.
 */

public class FragmentDefinitionRefreshAndLoading extends BaseFragment implements PullToRefreshRecyclerView.OnRefreshAndLoadMoreListener {

	@BindView(R.id.recycler_rv)
	PullToRefreshRecyclerView mRecyclerView;

	private List<String> mDatas = new ArrayList<>();
	private DefinitionRecyclerAdapter definitionRefreshAdapter;

	@Override
	public int getLayoutId() {
		return R.layout.fragment_default_refresh;
	}

	@Override
	public void init() {

		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
		linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		mRecyclerView.setLayoutManager(linearLayoutManager);
		mRecyclerView.setPullRefreshEnabled(true);
		mRecyclerView.setLoadMoreEnabled(true);
		mRecyclerView.setRefreshAndLoadMoreListener(this);
		mRecyclerView.setRefreshView(new DefinitionAnimationRefreshHeaderView(getActivity()));
		mRecyclerView.setLoadMoreView(new DefinitionAnimationLoadMoreView(getActivity()));
	}

	@Override
	public void requestData() {
		for (int i = 0; i < 10; i++) {
			mDatas.add("DefinitionRefreshItem" + (mDatas.size() + 1));
		}
		refreshUI();
	}

	public void refreshUI() {
		if (definitionRefreshAdapter == null) {
			definitionRefreshAdapter = new DefinitionRecyclerAdapter(getActivity(), mDatas, mRecyclerView);
			mRecyclerView.setAdapter(definitionRefreshAdapter);
		} else {
			if (mRecyclerView != null) {
				if (mRecyclerView.isLoading()) {
					mRecyclerView.loadMoreComplete();
				} else if (mRecyclerView.isRefreshing()) {
					mRecyclerView.refreshComplete();
				}
			}
		}
	}

	@Override
	public void onRecyclerViewRefresh() {
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				mDatas.clear();
				requestData();
			}
		}, 3000);
	}

	@Override
	public void onRecyclerViewLoadMore() {
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				requestData();
			}
		}, 3000);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if(mRecyclerView != null){
			mRecyclerView.destroy();
			mRecyclerView = null;
		}
	}
}
