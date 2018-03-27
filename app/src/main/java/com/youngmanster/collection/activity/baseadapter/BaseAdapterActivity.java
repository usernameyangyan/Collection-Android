package com.youngmanster.collection.activity.baseadapter;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
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
 * on 2018/3/20.
 */

public class BaseAdapterActivity extends BaseActivity implements BaseRecyclerViewAdapter.OnItemClickListener{

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

		setTitleContent(getString(R.string.activity_base_adapter_title));
		showHomeAsUp(R.mipmap.ic_back_btn);

		View header= LayoutInflater.from(this).inflate(R.layout.layout_base_adapter_header,null);

		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
		linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		mRecyclerView.setLayoutManager(linearLayoutManager);
		mRecyclerView.addHeaderView(header);
	}

	@Override
	public void requestData() {
		mDatas.add("ItemClick/ItemLongClick");
		mDatas.add("MultipleItem");
		mDatas.add("Drag/Delete");
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
				intent = new Intent(this, ItemClickActivity.class);
				startActivity(intent);
				break;
			case 1:
				intent = new Intent(this, MultipleItemActivity.class);
				startActivity(intent);
				break;
			case 2:
				intent = new Intent(this, DragAndDeleteActivity.class);
				startActivity(intent);
				break;
		}
	}
}
