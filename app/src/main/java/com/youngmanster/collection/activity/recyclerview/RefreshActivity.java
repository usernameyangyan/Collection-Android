package com.youngmanster.collection.activity.recyclerview;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import com.youngmanster.collection.R;
import com.youngmanster.collection.adapter.CollectionFragmentAdapter;
import com.youngmanster.collection.base.BaseActivity;
import com.youngmanster.collection.fragment.recyclerview.FragmentDefaultRefreshAndLoading;
import com.youngmanster.collection.fragment.recyclerview.FragmentDefinitionRefreshAndLoading;
import com.youngmanster.collection.fragment.recyclerview.FragmentGoogleRefreshAndLoading;
import com.youngmanster.collectionlibrary.customview.CollectionViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by yangyan
 * on 2018/3/18.
 */

public class RefreshActivity extends BaseActivity{

	@BindView(R.id.tabLayout)
	TabLayout tabLayout;
	@BindView(R.id.collectionVp)
	CollectionViewPager collectionVp;

	private CollectionFragmentAdapter collectionFragmentAdapter;

	private FragmentDefaultRefreshAndLoading fragmentDefaultRefresh;
	private FragmentDefinitionRefreshAndLoading fragmentDefinitionRefresh;
	private FragmentGoogleRefreshAndLoading fragmentGoogleRefresh;

	private List<Fragment> fragments = new ArrayList<>();

	private int[] titleIds = new int[]{R.string.default_refresh,
			R.string.definition_refresh, R.string.google_refresh};

	@Override
	public int getLayoutId() {
		return R.layout.activity_refresh;
	}

	@Override
	public void init() {

		defineActionBarConfig.setTitle(getString(R.string.fragment_refresh_title));

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
		fragmentDefaultRefresh = new FragmentDefaultRefreshAndLoading();
		fragmentDefinitionRefresh = new FragmentDefinitionRefreshAndLoading();
		fragmentGoogleRefresh = new FragmentGoogleRefreshAndLoading();
		fragments.add(fragmentDefaultRefresh);
		fragments.add(fragmentDefinitionRefresh);
		fragments.add(fragmentGoogleRefresh);
	}

	@Override
	public void requestData() {

	}

}
