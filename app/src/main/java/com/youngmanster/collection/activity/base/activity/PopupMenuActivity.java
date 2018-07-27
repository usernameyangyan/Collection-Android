package com.youngmanster.collection.activity.base.activity;

import android.widget.LinearLayout;

import com.youngmanster.collection.R;
import com.youngmanster.collection.activity.base.popupwindow.PopupMenuList;
import com.youngmanster.collection.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by yangyan
 * on 2018/6/7.
 */

public class PopupMenuActivity extends BaseActivity {

	@BindView(R.id.clickLl)
	LinearLayout clickLl;
	@Override
	public int getLayoutId() {
		return R.layout.activity_popup_list;
	}

	@Override
	public void init() {
		setTitleContent(getString(R.string.popup_title));
		showHomeAsUp(R.mipmap.ic_back_btn);
	}

	@Override
	public void requestData() {

	}

	@OnClick(R.id.clickLl)
	public void onMenClick(){
		PopupMenuList popupMenuList=new PopupMenuList(this);
		popupMenuList.showPopupAsDropDown(clickLl);
	}
}
