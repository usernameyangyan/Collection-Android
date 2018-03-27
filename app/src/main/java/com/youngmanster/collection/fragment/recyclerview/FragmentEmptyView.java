package com.youngmanster.collection.fragment.recyclerview;

import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;


import com.youngmanster.collection.R;

import com.youngmanster.collection.adapter.recyclerview.DefinitionRecyclerAdapter;
import com.youngmanster.collection.base.BaseFragment;
import com.youngmanster.collectionlibrary.refreshrecyclerview.pulltorefresh.PullToRefreshRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by yangyan
 * on 2018/3/19.
 */

public class FragmentEmptyView extends BaseFragment implements PullToRefreshRecyclerView.OnRefreshAndLoadMoreListener{

	@BindView(R.id.recycler_rv)
	PullToRefreshRecyclerView mRecyclerView;

	private DefinitionRecyclerAdapter definitionRefreshAdapter;
	private List<String> mDatas=new ArrayList<>();
	private boolean isFirst=true;

	@Override
	public int getLayoutId() {
		return R.layout.fragment_addheader_empty;
	}

	@Override
	public void init() {
		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
		linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		mRecyclerView.setLayoutManager(linearLayoutManager);
		mRecyclerView.setPullRefreshEnabled(true);
		mRecyclerView.setLoadMoreEnabled(true);
		mRecyclerView.setRefreshAndLoadMoreListener(this);

		View emptyView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_empty,null);
		mRecyclerView.setEmptyView(emptyView);
		emptyView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				for(int i=0;i<10;i++){
					mDatas.add("item"+i);
				}
				definitionRefreshAdapter.notifyDataSetChanged();
			}
		});
	}

	@Override
	public void requestData() {

		if(!isFirst){
			for (int i = 0; i < 15; i++) {
				mDatas.add("Item" + (mDatas.size() + 1));
			}
		}
		isFirst=false;
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
}
