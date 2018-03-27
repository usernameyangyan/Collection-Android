package com.youngmanster.collection.mvp.ui;

import android.Manifest;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import com.youngmanster.collection.R;
import com.youngmanster.collection.adapter.recyclerview.PullToRecyclerViewAdapter;
import com.youngmanster.collection.base.BaseActivity;
import com.youngmanster.collection.mvp.ui.wechat.activity.WeChatFeaturedActivity;
import com.youngmanster.collection.mvp.ui.wechat.activity.WeChatNewsActivity;
import com.youngmanster.collection.mvp.ui.wechat.activity.WeChatNewsDefinitionActivity;
import com.youngmanster.collectionlibrary.permission.PermissionManager;
import com.youngmanster.collectionlibrary.refreshrecyclerview.base.adapter.BaseRecyclerViewAdapter;
import com.youngmanster.collectionlibrary.refreshrecyclerview.pulltorefresh.PullToRefreshRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by yangyan
 * on 2018/3/21.
 */

public class MVPActivity extends BaseActivity implements BaseRecyclerViewAdapter.OnItemClickListener{
	@BindView(R.id.recycler_rv)
	PullToRefreshRecyclerView mRecyclerView;

	private PullToRecyclerViewAdapter pullToRefreshAdapter;
	private List<String> listData=new ArrayList<>();
	private Intent intent;

	private static final String[] PERMISSIONS = new String[]{
			Manifest.permission.READ_EXTERNAL_STORAGE,
			Manifest.permission.WRITE_EXTERNAL_STORAGE,
	};
	private PermissionManager permissionManager;

	@Override
	public int getLayoutId() {
		return R.layout.activity_main;
	}

	@Override
	public void init() {

		setTitleContent(getString(R.string.all_title));
		showHomeAsUp(R.mipmap.ic_back_btn);

		permissionManager=PermissionManager.with(this)
				.setNecessaryPermissions(PERMISSIONS)
				.build();

		LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
		linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		mRecyclerView.setLayoutManager(linearLayoutManager);
		View header = LayoutInflater.from(this).inflate(R.layout.layout_network_header, null);
		mRecyclerView.addHeaderView(header);
	}

	@Override
	public void requestData() {
		listData.add("Activity中实现MVP+RxJava+Retrofit+OkHttp的缓存机制");
		listData.add("Fragment中实现MVP+RxJava+Retrofit+OkHttp的缓存机制");
		listData.add("Fragment中实现MVP+RxJava+Retrofit+自定义磁盘缓存机制");
		refreshUI();
	}
	public void refreshUI() {

		pullToRefreshAdapter = new PullToRecyclerViewAdapter(this, listData, mRecyclerView);
		mRecyclerView.setAdapter(pullToRefreshAdapter);
		pullToRefreshAdapter.setOnItemClickListener(this);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if(mRecyclerView != null){
			mRecyclerView.destroy();
			mRecyclerView = null;
		}
	}

	@Override
	public void onItemClick(View view, int position) {
		switch (position){
			case 0:
				intent=new Intent(this, WeChatFeaturedActivity.class);
				startActivity(intent);
				break;
			case 1:
				intent=new Intent(this, WeChatNewsActivity.class);
				startActivity(intent);
				break;
			case 2:
				intent=new Intent(this, WeChatNewsDefinitionActivity.class);
				startActivity(intent);
				break;
		}
	}


	//重写
	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		if (requestCode == PermissionManager.PERMISSION_REQUEST_CODE) {//PERMISSION_REQUEST_CODE为请求权限的请求值
			//有必须权限选择了禁止
			if (permissionManager.getShouldShowRequestPermissionsCode() == PermissionManager.EXIST_NECESSARY_PERMISSIONS_PROHIBTED) {
				permissionManager.requestPermissions();
			} //有必须权限选择了禁止不提醒
			else if (permissionManager.getShouldShowRequestPermissionsCode() == PermissionManager.EXIST_NECESSARY_PERMISSIONS_PROHIBTED_NOT_REMIND) {
				permissionManager.startAppSettings();
			}
		}
	}
}
