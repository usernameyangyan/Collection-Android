package com.youngmanster.collection.activity.base.fragment;

import android.os.Bundle;
import android.view.View;

import com.youngmanster.collection.BuildConfig;
import com.youngmanster.collection.R;
import com.youngmanster.collection.base.BaseFragment;

import butterknife.OnClick;

/**
 * @author yangyan
 * @Date on 2019/8/27
 * @ Description:
 */
public class FragmentRoot extends BaseFragment {



    public static FragmentRoot newInstance() {

        Bundle args = new Bundle();

        FragmentRoot fragment = new FragmentRoot();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_root;
    }

    @Override
    public void init() {
        defineActionBarConfig.setTitle("展示页面");
    }

    @Override
    public void requestData() {

    }


    @OnClick({R.id.status_btn3})
    public void onMenuClick(View view) {
        switch (view.getId()) {
            case R.id.status_btn3:
                start(FragmentT.newInstance());
                break;
        }

    }

    @Override
    public boolean isShowCustomActionBar() {
        return true;
    }
}
