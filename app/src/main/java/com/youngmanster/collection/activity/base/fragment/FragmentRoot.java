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
        setTitleContent("使用Fragment作为主要交互");
        showHomeAsUp(R.mipmap.ic_back_btn);
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

}
