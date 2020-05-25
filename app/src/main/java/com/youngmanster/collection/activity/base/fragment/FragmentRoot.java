package com.youngmanster.collection.activity.base.fragment;
import android.view.View;
import com.youngmanster.collection.R;
import com.youngmanster.collection.base.BaseFragment;
import butterknife.OnClick;

/**
 * @author yangyan
 * @Date on 2019/8/27
 * @ Description:
 */
public class FragmentRoot extends BaseFragment {


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

    @OnClick({R.id.btn1,R.id.btn2})
    public void onMenuClick(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                startFragment(FragmentT.class);
                break;
            case R.id.btn2:
                startFragment(FragmentT.class,true);
                break;
        }

    }

    @Override
    public boolean isShowCustomActionBar() {
        return true;
    }
}
