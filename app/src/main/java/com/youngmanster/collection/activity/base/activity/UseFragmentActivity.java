package com.youngmanster.collection.activity.base.activity;

import android.content.Intent;
import android.view.View;

import com.youngmanster.collection.BuildConfig;
import com.youngmanster.collection.R;
import com.youngmanster.collection.activity.base.fragment.FragmentRoot;
import com.youngmanster.collection.base.BaseActivity;
import com.youngmanster.collectionlibrary.base.fragmet.FragmentAnimator;
import com.youngmanster.collectionlibrary.base.helper.Fragmentation;
import com.youngmanster.collectionlibrary.base.helper.anim.DefaultHorizontalAnimator;
import com.youngmanster.collectionlibrary.base.helper.anim.DefaultVerticalAnimator;

/**
 * @author yangyan
 * @Date on 2019/8/27
 * @ Description:
 */
public class UseFragmentActivity extends BaseActivity {
    @Override
    public int getLayoutId() {
        return R.layout.activity_use_fragment;
    }

    @Override
    public void init() {
        loadRootFragment(R.id.fl_container, FragmentRoot.newInstance());
    }

    @Override
    public void requestData() {

    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultHorizontalAnimator();
    }
}
