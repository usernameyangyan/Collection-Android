package com.youngmanster.collection.mvp.ui.wechat.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import com.youngmanster.collection.R;
import com.youngmanster.collection.adapter.CollectionFragmentAdapter;
import com.youngmanster.collection.base.BaseActivity;
import com.youngmanster.collection.mvp.ui.wechat.fragment.FragmentChinaNews;
import com.youngmanster.collection.mvp.ui.wechat.fragment.FragmentNBANews;
import com.youngmanster.collection.mvp.ui.wechat.fragment.FragmentWorldNews;
import com.youngmanster.collection.widget.CollectionViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by yangyan
 * on 2018/3/21.
 */

public class WeChatNewsActivity extends BaseActivity {

	@BindView(R.id.tabLayout)
	TabLayout tabLayout;
	@BindView(R.id.collectionVp)
	CollectionViewPager collectionVp;

	private CollectionFragmentAdapter collectionFragmentAdapter;

	private List<Fragment> fragments = new ArrayList<>();

	private int[] titleIds = new int[]{R.string.world_news,
			R.string.guonei_news, R.string.nba_news};

	private FragmentWorldNews fragmentWorldNews;
	private FragmentChinaNews fragmentChinaNews;
	private FragmentNBANews fragmentNBANews;


	@Override
	public int getLayoutId() {
		return R.layout.activity_refresh;
	}

	@Override
	public void init() {

		setTitleContent(getString(R.string.news_title));
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
		fragmentWorldNews=new FragmentWorldNews();
		fragmentChinaNews=new FragmentChinaNews();
		fragmentNBANews=new FragmentNBANews();
		fragments.add(fragmentWorldNews);
		fragments.add(fragmentChinaNews);
		fragments.add(fragmentNBANews);
	}

	@Override
	public void requestData() {
	}
}
