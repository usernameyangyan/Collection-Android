package com.youngmanster.collection.activity.recyclerview;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.youngmanster.collection.R;
import com.youngmanster.collection.adapter.recyclerview.PullToRecyclerViewAdapter;
import com.youngmanster.collection.base.BaseActivity;
import com.youngmanster.collectionlibrary.refreshrecyclerview.base.adapter.BaseRecyclerViewAdapter;
import com.youngmanster.collectionlibrary.refreshrecyclerview.pulltorefresh.PullToRefreshRecyclerView;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by yangyan
 * on 2018/3/18.
 */

public class RecyclerViewActivity extends BaseActivity implements BaseRecyclerViewAdapter.OnItemClickListener {


	@BindView(R.id.recycler_rv)
	PullToRefreshRecyclerView mRecyclerView;

	private ArrayList<String> mDatas = new ArrayList<>();
	private PullToRecyclerViewAdapter pullToRefreshAdapter;
	private Intent intent;

	@Override
	public int getLayoutId() {
		return R.layout.activity_pull_refresh;
	}

	@Override
	public void init() {

		setTitleContent(getString(R.string.activity_pull_refresh_title));
		showHomeAsUp(R.mipmap.ic_back_btn);

		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
		linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		mRecyclerView.setLayoutManager(linearLayoutManager);
	}

	@Override
	public void requestData() {
		mDatas.add("Refresh/LoadingMore");
		mDatas.add("AddHeader/EmptyView");
		mDatas.add("NoMoreData/AutoRefresh");
		refreshUI();
	}

	public void refreshUI() {
		pullToRefreshAdapter = new PullToRecyclerViewAdapter(this, mDatas, mRecyclerView);
		mRecyclerView.setAdapter(pullToRefreshAdapter);
		pullToRefreshAdapter.setOnItemClickListener(this);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (mRecyclerView != null) {
			mRecyclerView.destroy();
			mRecyclerView = null;
		}
	}

	@Override
	public void onItemClick(View view, int position) {
		switch (position) {
			case 0:
				intent = new Intent(this, RefreshActivity.class);
				startActivity(intent);
				break;
			case 1:
				intent = new Intent(this, HeaderAndEmptyViewActivity.class);
				startActivity(intent);
				break;
			case 2:
				intent = new Intent(this, NoMoreDateAndAutoRefreshActivity.class);
				startActivity(intent);
				break;
		}
	}
}
