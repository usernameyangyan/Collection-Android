package com.youngmanster.collection.mvp.ui.wechat.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import com.youngmanster.collection.R;
import com.youngmanster.collection.adapter.CollectionFragmentAdapter;
import com.youngmanster.collection.base.BaseActivity;
import com.youngmanster.collection.mvp.ui.wechat.fragment.FragmentWeChatFeaturedCommonClass;
import com.youngmanster.collection.mvp.ui.wechat.fragment.FragmentWeChatFeaturedNoCommonClass;
import com.youngmanster.collectionlibrary.customview.CollectionViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by yangyan
 * on 2018/3/21.
 */

public class WeChatFeaturedActivity extends BaseActivity{

	@BindView(R.id.tabLayout)
	TabLayout tabLayout;
	@BindView(R.id.collectionVp)
	CollectionViewPager collectionVp;

	private CollectionFragmentAdapter collectionFragmentAdapter;

	private List<Fragment> fragments = new ArrayList<>();

	private int[] titleIds = new int[]{R.string.use_common_result,
			R.string.no_use_common_result};

	private FragmentWeChatFeaturedCommonClass fragmentWeChatFeaturedCommonClass;
	private FragmentWeChatFeaturedNoCommonClass fragmentWeChatFeaturedNoCommonClass;


	@Override
	public int getLayoutId() {
		return R.layout.activity_refresh;
	}

	@Override
	public void init() {

		defineActionBarConfig.setTitle(getString(R.string.activity_wechat_title));

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
		fragmentWeChatFeaturedCommonClass=new FragmentWeChatFeaturedCommonClass();
		fragmentWeChatFeaturedNoCommonClass=new FragmentWeChatFeaturedNoCommonClass();
		fragments.add(fragmentWeChatFeaturedCommonClass);
		fragments.add(fragmentWeChatFeaturedNoCommonClass);
	}

	@Override
	public void requestData() {
	}
}
