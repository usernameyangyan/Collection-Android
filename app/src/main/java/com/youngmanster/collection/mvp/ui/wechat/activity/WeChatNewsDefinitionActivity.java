package com.youngmanster.collection.mvp.ui.wechat.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import com.youngmanster.collection.R;
import com.youngmanster.collection.adapter.CollectionFragmentAdapter;
import com.youngmanster.collection.base.BaseActivity;
import com.youngmanster.collection.mvp.ui.wechat.fragment.FragmentChinaNewsDefinition;
import com.youngmanster.collection.mvp.ui.wechat.fragment.FragmentWorldNewsDefinition;
import com.youngmanster.collectionlibrary.customview.CollectionViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by yangyan
 * on 2018/3/21.
 */

public class WeChatNewsDefinitionActivity extends BaseActivity {

	@BindView(R.id.tabLayout)
	TabLayout tabLayout;
	@BindView(R.id.collectionVp)
	CollectionViewPager collectionVp;

	private CollectionFragmentAdapter collectionFragmentAdapter;

	private List<Fragment> fragments = new ArrayList<>();

	private int[] titleIds = new int[]{R.string.fen_ye_cache,
			R.string.limit_time_cache};

	private FragmentWorldNewsDefinition fragmentWorldNewsDefinition;
	private FragmentChinaNewsDefinition fragmentChinaNewsDefinition;


	@Override
	public int getLayoutId() {
		return R.layout.activity_refresh;
	}

	@Override
	public void init() {

		defineActionBarConfig.setTitle("自定义磁盘缓存");

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
		fragmentWorldNewsDefinition=new FragmentWorldNewsDefinition();
		fragmentChinaNewsDefinition=new FragmentChinaNewsDefinition();
		fragments.add(fragmentWorldNewsDefinition);
		fragments.add(fragmentChinaNewsDefinition);
	}

	@Override
	public void requestData() {
	}
}
