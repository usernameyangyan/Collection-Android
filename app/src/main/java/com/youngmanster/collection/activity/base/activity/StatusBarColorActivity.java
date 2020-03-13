package com.youngmanster.collection.activity.base.activity;

import android.view.View;

import com.youngmanster.collection.R;
import com.youngmanster.collection.base.BaseActivity;
import com.youngmanster.collectionlibrary.utils.DisplayUtils;

import butterknife.OnClick;

/**
 * Created by yangyan
 * on 2018/6/6.
 */

public class StatusBarColorActivity extends BaseActivity {

	int []colors={R.color.colorAccent,R.color.colorPrimaryDark,R.color.colorPrimary};
	private int clickCount=1;
	private int type;

	@Override
	public int getLayoutId() {
		return R.layout.activity_color_statusbar;
	}

	@Override
	public void init() {
		defineActionBarConfig.setTitle(getString(R.string.status_bar_bg));

		type=getIntent().getIntExtra("type",0);
		if(type==0){
			DisplayUtils.setStatusBarBlackFontBgColor(this,colors[0]);
		}else{
			DisplayUtils.setStatusBarColor(this,colors[0]);
		}
	}

	@OnClick({R.id.status_btn1})
	public void onMenuClick(View view) {

		switch (type){
			case 0:
				if(clickCount%3==0){
					DisplayUtils.setStatusBarBlackFontBgColor(this,colors[0]);
				}else if(clickCount%3==1){
					DisplayUtils.setStatusBarBlackFontBgColor(this,colors[1]);
				}else{
					DisplayUtils.setStatusBarBlackFontBgColor(this,colors[2]);
				}
				break;
			case 1:
				if(clickCount%3==0){
					DisplayUtils.setStatusBarColor(this,colors[0]);
				}else if(clickCount%3==1){
					DisplayUtils.setStatusBarColor(this,colors[1]);
				}else{
					DisplayUtils.setStatusBarColor(this,colors[2]);
				}
				break;
		}

		clickCount++;
	}

	@Override
	public void requestData() {

	}
}
