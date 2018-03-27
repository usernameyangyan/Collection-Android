package com.youngmanster.collection.activity.baseadapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.youngmanster.collection.R;
import com.youngmanster.collection.adapter.baseadapter.ItemClickAdapter;
import com.youngmanster.collection.base.BaseActivity;
import com.youngmanster.collection.been.ClickItem;
import com.youngmanster.collectionlibrary.refreshrecyclerview.base.adapter.BaseRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by yangyan
 * on 2018/3/20.
 */

public class ItemClickActivity extends BaseActivity implements BaseRecyclerViewAdapter.OnItemClickListener,BaseRecyclerViewAdapter.onItemLongClickListener,View.OnClickListener{

	@BindView(R.id.recycler_rv)
	RecyclerView mRecyclerView;

	private List<ClickItem> mDatas=new ArrayList<>();
	private ItemClickAdapter itemClickAdapter;

	@Override
	public int getLayoutId() {
		return R.layout.layout_recyclerview;
	}

	@Override
	public void init() {

		setTitleContent(getString(R.string.activity_item_click_title));
		showHomeAsUp(R.mipmap.ic_back_btn);


		LinearLayoutManager layoutManager = new LinearLayoutManager(this);
		layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

		mRecyclerView.setLayoutManager(layoutManager);
		for(int i=0;i<20;i++){
			ClickItem clickItem=new ClickItem();
			clickItem.setTitle("第"+(i+1)+"个item");
			clickItem.setRes(R.mipmap.header);
			mDatas.add(clickItem);
		}
		itemClickAdapter=new ItemClickAdapter(this,mDatas,this);

		mRecyclerView.setAdapter(itemClickAdapter);
		itemClickAdapter.setOnItemClickListener(this);
		itemClickAdapter.setOnItemLongClickListener(this);

	}

	@Override
	public void requestData() {

	}

	@Override
	public void onClick(View v) {
		showToast("其实点击我没有奖");
	}

	@Override
	public void onItemClick(View view, int position) {
		showToast(mDatas.get(position).getTitle());
	}

	@Override
	public boolean onItemLongClick(View view, int position) {
		showToast("进行长按操作");
		return true;
	}
}
