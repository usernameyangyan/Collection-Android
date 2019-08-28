package com.youngmanster.collection.customview.activity;

import com.youngmanster.collection.R;
import com.youngmanster.collection.base.BaseActivity;

/**
 * @author yangyan
 * @Date on 2019/8/27
 * @ Description:
 */
public class WrapLinearLayoutActivity extends BaseActivity {
    @Override
    public int getLayoutId() {
        return R.layout.activity_wrap_linear;
    }

    @Override
    public void init() {
        setTitleContent("AutoLinefeedLayout");
        showHomeAsUp(R.mipmap.ic_back_btn);
    }

    @Override
    public void requestData() {

    }
}
