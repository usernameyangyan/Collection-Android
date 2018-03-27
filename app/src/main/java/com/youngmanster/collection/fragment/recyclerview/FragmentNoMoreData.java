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

public class FragmentNoMoreData extends BaseFragment implements PullToRefreshRecyclerView.OnRefreshAndLoadMoreListener{

	@BindView(R.id.recycler_rv)
	PullToRefreshRecyclerView mRecyclerView;

	private DefinitionRecyclerAdapter definitionRefreshAdapter;
	private List<String> mDatas=new ArrayList<>();
	private int times=0;

	@Override
	public int getLayoutId() {
		return R.layout.fragment_nomore_auto;
	}

	@Override
	public void init() {
		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
		linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		mRecyclerView.setLayoutManager(linearLayoutManager);
		mRecyclerView.setPullRefreshEnabled(true);
		mRecyclerView.setLoadMoreEnabled(true);
		mRecyclerView.setRefreshAndLoadMoreListener(this);
	}

	@Override
	public void requestData() {
		for (int i = 0; i < 10; i++) {
			mDatas.add("Item" + (mDatas.size() + 1));
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
		times=0;
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

		if(times<1){
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					requestData();
				}
			}, 3000);

			times++;
		}else{
			if(mRecyclerView!=null){
				mRecyclerView.setNoMoreDate(true);
			}
		}


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
