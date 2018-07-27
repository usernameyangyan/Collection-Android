package com.youngmanster.collection.customview.fragment;

import android.os.Bundle;
import android.widget.TextView;

import com.youngmanster.collection.R;
import com.youngmanster.collection.base.BaseFragment;

import butterknife.BindView;

/**
 * Created by yangyan
 * on 2018/6/26.
 */

public class ChildFragment extends BaseFragment {

	@BindView(R.id.childTv)
	TextView childTv;

	public static ChildFragment newInstance(String str){
		ChildFragment childFragment = new ChildFragment();
		Bundle bundle = new Bundle();
		bundle.putString("str", str);
		childFragment.setArguments(bundle);
		return childFragment;
	}


	@Override
	public int getLayoutId() {
		return R.layout.fragment_child;
	}

	@Override
	public void init() {
		Bundle bundle = getArguments();
		String str = bundle.getString("str");
		childTv.setText(str);
	}

	@Override
	public void requestData() {

	}
}
