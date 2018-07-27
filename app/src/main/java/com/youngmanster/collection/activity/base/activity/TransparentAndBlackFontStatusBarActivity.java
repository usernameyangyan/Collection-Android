package com.youngmanster.collection.activity.base.activity;
import android.app.Activity;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.youngmanster.collection.R;
import com.youngmanster.collection.adapter.recyclerview.DefinitionRecyclerAdapter;
import com.youngmanster.collection.base.BaseActivity;
import com.youngmanster.collectionlibrary.refreshrecyclerview.pulltorefresh.PullToRefreshRecyclerView;
import com.youngmanster.collectionlibrary.utils.DisplayUtils;
import com.youngmanster.collectionlibrary.utils.GlideUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by yangyan
 * on 2018/6/6.
 */

public class TransparentAndBlackFontStatusBarActivity extends BaseActivity {
	@BindView(R.id.recycler_rv)
	PullToRefreshRecyclerView mRecyclerView;


	private ImageView imgBg;
	private ImageView ivImage;
	private int ivHeight;

	private DefinitionRecyclerAdapter definitionRefreshAdapter;
	private List<String> mtDatas=new ArrayList<>();
	@Override
	public int getLayoutId() {
		return R.layout.activity_transparent_blackfont_statusbar;
	}

	@Override
	public void init() {
		DisplayUtils.setStatusBarFullTranslucentWithBlackFont(this);
		mCommonToolbar.setBackgroundColor(getResources().getColor(R.color.white));
		showHomeAsUp(R.mipmap.nav_back);
//		setTitleContent("复仇者联盟3");

		if (DisplayUtils.isCanSetStatusBarBlackFontLightMode(this)) {
			mCommonToolbar.getLayoutParams().height = DisplayUtils.getActionBarHeight(this) + DisplayUtils.getStatusBarHeight(this);
			mCommonToolbar.setPadding(0, DisplayUtils.getStatusBarHeight(this), 0, 0);
//			titleTv.setPadding(0,DisplayUtils.getStatusBarHeight(this), 0, 0);
		}

		mRecyclerView.setNestedScrollingEnabled(false);

		ivHeight = DisplayUtils.dip2px(this, 300);


		View header = LayoutInflater.from(this).inflate(R.layout.layout_transprent_statusbar_header, null);
		mRecyclerView.addHeaderView(header);

		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
		linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		mRecyclerView.setLayoutManager(linearLayoutManager);
		for(int i=0;i<10;i++){
			mtDatas.add("item"+i);
		}

		imgBg=header.findViewById(R.id.imgBg);
		ivImage=header.findViewById(R.id.ivImage);
		GlideUtils.loadImg(this,
				"https://gss2.bdstatic.com/9fo3dSag_xI4khGkpoWK1HF6hhy/baike/c0%3Dbaike272%2C5%2C5%2C272%2C90/sign=06f0367c57b5c9ea76fe0bb1b450dd65/d1a20cf431adcbef44627e71a0af2edda3cc9f76.jpg",
				R.mipmap.ic_bttom_loading_01,ivImage);

		GlideUtils.loadImgBlur(this,
				"https://gss2.bdstatic.com/9fo3dSag_xI4khGkpoWK1HF6hhy/baike/c0%3Dbaike272%2C5%2C5%2C272%2C90/sign=06f0367c57b5c9ea76fe0bb1b450dd65/d1a20cf431adcbef44627e71a0af2edda3cc9f76.jpg",
				R.mipmap.ic_bttom_loading_01,imgBg);

		definitionRefreshAdapter = new DefinitionRecyclerAdapter(this, mtDatas, mRecyclerView);
		mRecyclerView.setAdapter(definitionRefreshAdapter);

		mRecyclerView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
			@Override
			public void onScrollChanged() {
				changeToolbarAlpha();
			}
		});
	}

	@Override
	public void requestData() {

	}

	public int getScollYDistance() {
		LinearLayoutManager layoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
		int position = layoutManager.findFirstVisibleItemPosition();
		View firstVisiableChildView = layoutManager.findViewByPosition(position);
		int itemHeight = firstVisiableChildView.getHeight();
		return (position) * itemHeight - firstVisiableChildView.getTop();
	}

	//设置Toolbar的透明度
	private void changeToolbarAlpha() {
		int scrollY = getScollYDistance();
		//快速下拉会引起瞬间scrollY<0
		if (scrollY <= 0) {
			mCommonToolbar.getBackground().mutate().setAlpha(0);
			return;
		}
		//计算当前透明度比率
		float radio = Math.min(1, scrollY / (ivHeight - mCommonToolbar.getHeight() * 1f));
		//设置透明度
		mCommonToolbar.getBackground().mutate().setAlpha((int) (radio * 0xFF));
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
