package com.youngmanster.collection.activity.recyclerview;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import com.youngmanster.collection.R;
import com.youngmanster.collection.adapter.CollectionFragmentAdapter;
import com.youngmanster.collection.base.BaseActivity;
import com.youngmanster.collection.fragment.recyclerview.FragmentAddHeader;
import com.youngmanster.collection.fragment.recyclerview.FragmentEmptyView;
import com.youngmanster.collectionlibrary.customview.CollectionViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by yangyan
 * on 2018/3/19.
 */

public class HeaderAndEmptyViewActivity extends BaseActivity{

	@BindView(R.id.tabLayout)
	TabLayout tabLayout;
	@BindView(R.id.collectionVp)
	CollectionViewPager collectionVp;

	private CollectionFragmentAdapter collectionFragmentAdapter;
	private List<Fragment> fragments = new ArrayList<>();

	private int[] titleIds = new int[]{R.string.add_header,
			R.string.set_empty};

	private FragmentAddHeader fragmentAddHeader;
	private FragmentEmptyView fragmentEmptyView;

	@Override
	public int getLayoutId() {
		return R.layout.activity_refresh;
	}

	@Override
	public void init() {
		defineActionBarConfig.setTitle(getString(R.string.fragment_header_title));

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
		fragmentAddHeader = new FragmentAddHeader();
		fragmentEmptyView = new FragmentEmptyView();
		fragments.add(fragmentAddHeader);
		fragments.add(fragmentEmptyView);
	}

	@Override
	public void requestData() {

	}

	public void refreshUI() {

	}
}
