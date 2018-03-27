package com.youngmanster.collection.activity.base;

import com.youngmanster.collection.R;
import com.youngmanster.collection.base.BaseActivity;

/**
 * Created by yangyan
 * on 2018/3/20.
 */

public class IntroductionActivity extends BaseActivity{

	@Override
	public int getLayoutId() {
		return R.layout.layout_introduction;
	}

	@Override
	public void init() {
		setTitleContent(getString(R.string.activity_base_ui_title));
		showHomeAsUp(R.mipmap.ic_back_btn);
	}

	@Override
	public void requestData() {
	}
}
