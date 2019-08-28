package com.youngmanster.collection.activity.base.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.youngmanster.collection.R;
import com.youngmanster.collection.activity.base.popupwindow.PopupWindowDemoActivity;
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

public class BaseUiActivity extends BaseActivity implements BaseRecyclerViewAdapter.OnItemClickListener{


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
		setTitleContent(getString(R.string.activity_base_ui_title));
		showHomeAsUp(R.mipmap.ic_back_btn);

		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
		linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		mRecyclerView.setLayoutManager(linearLayoutManager);
	}

	@Override
	public void requestData() {

		mDatas.add("Base介绍");
		mDatas.add("使用Fragment作为主要交互");
		mDatas.add("StateView");
		mDatas.add("Permission");
		mDatas.add("Dialog的使用");
		mDatas.add("PopupWindow的使用");
		mDatas.add("使用DisplayUtils工具类修改状态栏");
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
				intent = new Intent(this, IntroductionActivity.class);
				startActivity(intent);
				break;
			case 1:
				intent = new Intent(this, UseFragmentActivity.class);
				startActivity(intent);
				break;
			case 2:
				intent = new Intent(this, StateViewActivity.class);
				startActivity(intent);
				break;
			case 3:
				intent = new Intent(this, PermissionActivity.class);
				startActivity(intent);
				break;
			case 4:
				intent = new Intent(this, DialogActivity.class);
				startActivity(intent);
				break;
			case 5:
				intent = new Intent(this, PopupWindowDemoActivity.class);
				startActivity(intent);
				break;
			case 6:
				intent = new Intent(this, ChangeStatusBarActivity.class);
				startActivity(intent);
				break;
		}
	}
}
