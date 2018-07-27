package com.youngmanster.collection.customview.activity;

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
 * on 2018/6/26.
 */

public class CommonTabLayoutActivity extends BaseActivity implements BaseRecyclerViewAdapter.OnItemClickListener{

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
		setTitleContent(getString(R.string.custom_tab_diff));
		showHomeAsUp(R.mipmap.ic_back_btn);

		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
		linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		mRecyclerView.setLayoutManager(linearLayoutManager);

	}
	@Override
	public void requestData() {

		mDatas.add("样式1");
		mDatas.add("样式2");
		mDatas.add("样式3");
		mDatas.add("样式4");
		mDatas.add("样式5");
		mDatas.add("样式6");
		mDatas.add("样式7");
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
				intent = new Intent(this, CommonLayoutStyleOneActivity.class);
				startActivity(intent);
				break;
			case 1:
				intent = new Intent(this, CommonLayoutStyleTwoActivity.class);
				startActivity(intent);
				break;
			case 2:
				intent = new Intent(this, CommonLayoutStyleThreeActivity.class);
				startActivity(intent);
				break;
			case 3:
				intent = new Intent(this, CommonLayoutStyleFourActivity.class);
				startActivity(intent);
				break;
			case 4:
				intent = new Intent(this, CommonTaLayoutStyleFiveActivity.class);
				startActivity(intent);
				break;
			case 5:
				intent = new Intent(this, CommonLayoutStyleSixActivity.class);
				startActivity(intent);
				break;
			case 6:
				intent = new Intent(this, CommonLayoutStyleSevenActivity.class);
				startActivity(intent);
				break;
		}
	}
}
