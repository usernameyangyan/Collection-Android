package com.youngmanster.collection.activity.baseadapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.youngmanster.collection.R;
import com.youngmanster.collection.adapter.baseadapter.MultipleAdapter;
import com.youngmanster.collection.base.BaseActivity;
import com.youngmanster.collection.been.MultiItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by yangyan
 * on 2018/3/14.
 */

public class MultipleItemActivity extends BaseActivity{

	@BindView(R.id.recycler_rv)
	RecyclerView mRecyclerView;
	private MultipleAdapter multiAdapter;

	private List<MultiItem> mDatas = new ArrayList<>();

	@Override
	public int getLayoutId() {
		return R.layout.layout_recyclerview;
	}

	@Override
	public void init() {

		defineActionBarConfig.setTitle(getString(R.string.activity_multiple_title));

		LinearLayoutManager layoutManager = new LinearLayoutManager(this);
		layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		mRecyclerView.setLayoutManager(layoutManager);

	}

	@Override
	public void requestData() {
		for (int i = 0; i < 20; i++) {
			MultiItem multiItem = new MultiItem();
			multiItem.setTitle("第" + i + "个item");
			multiItem.setRes(R.mipmap.header);

			if (i < 10) {
				if (i % 3 == 0) {
					multiItem.setType(MultiItem.TYPE_TEXT);
				} else if (i % 3 == 1) {
					multiItem.setType(MultiItem.TYPE_IMG);
				} else {
					multiItem.setType(MultiItem.TYPE_TEXT_IMG);
				}
			} else {
				if (i % 3 == 1) {
					multiItem.setType(MultiItem.TYPE_TEXT);
				} else if (i % 3 == 0) {
					multiItem.setType(MultiItem.TYPE_IMG);
				} else {
					multiItem.setType(MultiItem.TYPE_TEXT_IMG);
				}
			}
			mDatas.add(multiItem);
		}


		multiAdapter = new MultipleAdapter(this, mDatas);
		mRecyclerView.setAdapter(multiAdapter);

	}
}
