package com.youngmanster.collection.activity.base.activity;
import com.youngmanster.collection.R;
import com.youngmanster.collection.activity.base.fragment.FragmentRoot;
import com.youngmanster.collection.base.BaseActivity;
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
        startFragment(FragmentRoot.class);
    }

    @Override
    public void requestData() {

    }

    @Override
    public boolean isShowCustomActionBar() {
        return false;
    }

    @Override
    public int fragmentLayoutId() {
        return R.id.fl_container;
    }
}
