package com.youngmanster.collection.activity.baseadapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.youngmanster.collection.R;
import com.youngmanster.collection.adapter.baseadapter.DragAndDeleteAdapter;
import com.youngmanster.collection.base.BaseActivity;
import com.youngmanster.collectionlibrary.refreshrecyclerview.base.adapter.BaseRecyclerViewAdapter;
import com.youngmanster.collectionlibrary.refreshrecyclerview.base.helper.BaseRecycleItemTouchHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by yangyan
 * on 2018/3/14.
 */

public class DragAndDeleteActivity extends BaseActivity implements BaseRecyclerViewAdapter.OnDragAndDeleteListener{

	@BindView(R.id.recycler_rv)
	RecyclerView mRecyclerView;
	private DragAndDeleteAdapter dragAndDeleteAdapter;

	private List<String> mDatas=new ArrayList<>();
	@Override
	public int getLayoutId() {
		return R.layout.layout_recyclerview;
	}

	@Override
	public void init() {

		setTitleContent(getString(R.string.activity_drag_delete_title));
		showHomeAsUp(R.mipmap.ic_back_btn);

		LinearLayoutManager layoutManager = new LinearLayoutManager(this);
		layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

		mRecyclerView.setLayoutManager(layoutManager);

		for(int i=0;i<20;i++){
			mDatas.add((i+1)+"   左右滑动删除/长按拖动");
		}


		dragAndDeleteAdapter=new DragAndDeleteAdapter(this,mDatas);
		dragAndDeleteAdapter.setDragAndDeleteListener(this);
		mRecyclerView.setAdapter(dragAndDeleteAdapter);

		ItemTouchHelper.Callback callback=new BaseRecycleItemTouchHelper(dragAndDeleteAdapter);
		ItemTouchHelper itemTouchHelper=new ItemTouchHelper(callback);
		itemTouchHelper.attachToRecyclerView(mRecyclerView);

	}

	@Override
	public void requestData() {

	}


	@Override
	public void onDragAndDeleteFinished() {

		mRecyclerView.postDelayed(new Runnable() {
			@Override
			public void run() {
				dragAndDeleteAdapter.notifyDataSetChanged();
				showToast("操作完成");
			}
		},300);
	}
}
