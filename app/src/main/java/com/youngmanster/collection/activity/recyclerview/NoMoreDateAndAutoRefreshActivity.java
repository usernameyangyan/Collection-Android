package com.youngmanster.collection.activity.recyclerview;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import com.youngmanster.collection.R;
import com.youngmanster.collection.adapter.CollectionFragmentAdapter;
import com.youngmanster.collection.base.BaseActivity;
import com.youngmanster.collection.fragment.recyclerview.FragmentAutoRefresh;
import com.youngmanster.collection.fragment.recyclerview.FragmentNoMoreData;
import com.youngmanster.collection.widget.CollectionViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by yangyan
 * on 2018/3/20.
 */

public class NoMoreDateAndAutoRefreshActivity extends BaseActivity{

	@BindView(R.id.tabLayout)
	TabLayout tabLayout;
	@BindView(R.id.collectionVp)
	CollectionViewPager collectionVp;

	private CollectionFragmentAdapter collectionFragmentAdapter;
	private List<Fragment> fragments = new ArrayList<>();

	private int[] titleIds = new int[]{R.string.no_more_data,
			R.string.auto_refresh};

	private FragmentNoMoreData fragmentNoMoreData;
	private FragmentAutoRefresh fragmentAutoRefresh;

	@Override
	public int getLayoutId() {
		return R.layout.activity_refresh;
	}

	@Override
	public void init() {
		setTitleContent(getString(R.string.fragment_auto_title));
		showHomeAsUp(R.mipmap.ic_back_btn);

		setFragments();
		collectionFragmentAdapter = new CollectionFragmentAdapter(getSupportFragmentManager(), fragments);
		collectionVp.setAdapter(collectionFragmentAdapter);
		tabLayout.setupWithViewPager(collectionVp);
		collectionVp.setOffscreenPageLimit(fragments.size());

		for (int i = 0; i < tabLayout.getTabCount(); i++) {
			tabLayout.getTabAt(i).setText(titleIds[i]);
		}
	}

	private void setFragments() {
		fragmentNoMoreData=new FragmentNoMoreData();
		fragmentAutoRefresh=new FragmentAutoRefresh();
		fragments.add(fragmentNoMoreData);
		fragments.add(fragmentAutoRefresh);
	}


	@Override
	public void requestData() {

	}
}
