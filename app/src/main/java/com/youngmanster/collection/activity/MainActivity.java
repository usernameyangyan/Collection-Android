package com.youngmanster.collection.activity;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;

import com.youngmanster.collection.R;
import com.youngmanster.collection.activity.base.activity.BaseUiActivity;
import com.youngmanster.collection.activity.baseadapter.BaseAdapterActivity;
import com.youngmanster.collection.activity.recyclerview.RecyclerViewActivity;
import com.youngmanster.collection.adapter.MainViewAdapter;
import com.youngmanster.collection.base.BaseActivity;
import com.youngmanster.collection.customview.activity.CustomViewActivity;
import com.youngmanster.collection.data.activity.DataManagerActivity;
import com.youngmanster.collection.mvp.ui.MVPActivity;
import com.youngmanster.collectionlibrary.refreshrecyclerview.base.adapter.BaseRecyclerViewAdapter;
import com.youngmanster.collectionlibrary.refreshrecyclerview.pulltorefresh.PullToRefreshRecyclerView;
import com.youngmanster.collectionlibrary.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by yangyan
 * on 2018/3/8.
 */

public class MainActivity extends BaseActivity implements BaseRecyclerViewAdapter.OnItemClickListener {

	@BindView(R.id.recycler_rv)
	PullToRefreshRecyclerView mRecyclerView;

	private MainViewAdapter mainViewAdapter;
	private List<String> listData = new ArrayList<>();
	private Intent intent;

	@Override
	public int getLayoutId() {
		return R.layout.activity_main;
	}

	@Override
	public void init() {
		defineActionBarConfig
				.hideBackBtn()
				.setTitle(getString(R.string.activity_main_title));

		GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
		mRecyclerView.setLayoutManager(layoutManager);
		View header = LayoutInflater.from(this).inflate(R.layout.layout_main_header, null);
		mRecyclerView.addHeaderView(header);
	}

	@Override
	public void requestData() {
		listData.add("RecyclerView");
		listData.add("BaseAdapter");
		listData.add("MVP+RxJava+Retrofit");
		listData.add("DataManager(Retrofit/SharePreference/Realm)");
		listData.add("Base");
		listData.add("CustomView");
		refreshUI();
	}

	public void refreshUI() {

		mainViewAdapter = new MainViewAdapter(this, listData, mRecyclerView);
		mRecyclerView.setAdapter(mainViewAdapter);
		mainViewAdapter.setOnItemClickListener(this);
	}

	private long currentTime;

	@Override
	public void onBackPressedSupport() {

		if (System.currentTimeMillis() > currentTime) {
			ToastUtils.showToast(MainActivity.this,"再按一次即可退出");
		} else {
			super.onBackPressedSupport();
		}
		currentTime = System.currentTimeMillis() + 2000;
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
				intent = new Intent(this, RecyclerViewActivity.class);
				startActivity(intent);
				break;
			case 1:
				intent = new Intent(this, BaseAdapterActivity.class);
				startActivity(intent);
				break;
			case 2:
				intent = new Intent(this, MVPActivity.class);
				startActivity(intent);
				break;
			case 3:
				intent = new Intent(this, DataManagerActivity.class);
				startActivity(intent);
				break;
			case 4:
				intent = new Intent(this, BaseUiActivity.class);
				startActivity(intent);
				break;
			case 5:
				intent = new Intent(this, CustomViewActivity.class);
				startActivity(intent);
				break;
		}
	}
}
