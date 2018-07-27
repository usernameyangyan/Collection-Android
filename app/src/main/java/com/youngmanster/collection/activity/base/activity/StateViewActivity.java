package com.youngmanster.collection.activity.base.activity;

import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;

import com.youngmanster.collection.R;
import com.youngmanster.collection.adapter.recyclerview.DefaultRecyclerAdapter;
import com.youngmanster.collection.base.BaseActivity;
import com.youngmanster.collectionlibrary.base.StateView;
import com.youngmanster.collectionlibrary.refreshrecyclerview.pulltorefresh.PullToRefreshRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by yangyan
 * on 2018/3/20.
 */

public class StateViewActivity extends BaseActivity implements StateView.OnEmptyViewListener{

	@BindView(R.id.recycler_rv)
	PullToRefreshRecyclerView mRecyclerView;
	@BindView(R.id.state_view)
	StateView stateView;

	private DefaultRecyclerAdapter defaultRefreshAdapter;
	private List<String> mDatas = new ArrayList<>();

	@Override
	public int getLayoutId() {
		return R.layout.activity_state_view;
	}

	@Override
	public void init() {
		setTitleContent(getString(R.string.activity_state_view));
		showHomeAsUp(R.mipmap.ic_back_btn);

		stateView.showViewByState(StateView.STATE_LOADING);
		stateView.setOnEmptyViewListener(this);
		GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
		mRecyclerView.setLayoutManager(layoutManager);
	}

	@Override
	public void requestData() {
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {

				refreshUI();
			}
		},3000);
	}

	public void refreshUI() {
		if(stateView!=null){
			stateView.showViewByState(StateView.STATE_EMPTY);
		}
	}

	@Override
	public void onEmptyViewClick() {

		stateView.showViewByState(StateView.STATE_LOADING);

		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {

				for (int i = 0; i < 15; i++) {
					mDatas.add("Item" + (mDatas.size() + 1));
				}
				setData();
			}
		},3000);

	}

	private void setData(){
		if(mRecyclerView!=null){
			defaultRefreshAdapter = new DefaultRecyclerAdapter(this, mDatas, mRecyclerView);
			mRecyclerView.setAdapter(defaultRefreshAdapter);
			stateView.showViewByState(StateView.STATE_NO_DATA);
		}

	}
}
