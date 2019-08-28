package com.youngmanster.collection.activity.base.fragment;

import android.os.Bundle;
import android.view.View;

import com.youngmanster.collection.R;
import com.youngmanster.collection.base.BaseFragment;

import butterknife.OnClick;

/**
 * @author yangyan
 * @Date on 2019/8/27
 * @ Description:
 */
public class FragmentT extends BaseFragment {



    public static FragmentT newInstance() {

        Bundle args = new Bundle();

        FragmentT fragment = new FragmentT();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_t;
    }

    @Override
    public void init() {
        setTitleContent("跳转页面");
        showHomeAsUp(R.mipmap.ic_back_btn);
    }

    @Override
    public void requestData() {

    }

}
