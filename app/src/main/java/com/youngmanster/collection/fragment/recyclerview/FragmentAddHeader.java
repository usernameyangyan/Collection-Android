package com.youngmanster.collection.fragment.recyclerview;

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

public class FragmentAddHeader extends BaseFragment {

	@BindView(R.id.recycler_rv)
	PullToRefreshRecyclerView mRecyclerView;

	private DefinitionRecyclerAdapter definitionRefreshAdapter;
	private List<String> mtDatas=new ArrayList<>();


	@Override
	public int getLayoutId() {
		return R.layout.fragment_addheader_empty;
	}

	@Override
	public void init() {

		View header = LayoutInflater.from(getActivity()).inflate(R.layout.layout_main_header, null);
		mRecyclerView.addHeaderView(header);

		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
		linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		mRecyclerView.setLayoutManager(linearLayoutManager);

		for(int i=0;i<10;i++){
			mtDatas.add("item"+i);
		}

		definitionRefreshAdapter = new DefinitionRecyclerAdapter(getActivity(), mtDatas, mRecyclerView);
		mRecyclerView.setAdapter(definitionRefreshAdapter);

	}

	@Override
	public void requestData() {

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
