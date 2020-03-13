package com.youngmanster.collection.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.youngmanster.collection.R;
import com.youngmanster.collectionlibrary.base.activity.IBaseActivity;
import com.youngmanster.collectionlibrary.mvp.BasePresenter;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by yangyan
 * on 2018/3/18.
 */

public abstract class BaseActivity<T extends BasePresenter> extends IBaseActivity {
    private Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        unbinder= ButterKnife.bind(this);

        defineActionBarConfig
                .setBackIcon(R.mipmap.ic_back_btn)
                .setBackClick(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
