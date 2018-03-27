package com.youngmanster.collection.base;

import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.youngmanster.collection.R;
import com.youngmanster.collectionlibrary.base.IBaseActivity;
import com.youngmanster.collectionlibrary.mvp.BaseModel;
import com.youngmanster.collectionlibrary.mvp.BasePresenter;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by yangyan
 * on 2018/3/18.
 */

public abstract class BaseActivity<T extends BaseModel,E extends BasePresenter> extends IBaseActivity{
    private Unbinder unbinder;
    private Toast toast = null;
    public Toolbar mCommonToolbar;
    private TextView titleTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        unbinder= ButterKnife.bind(this);

        mCommonToolbar = ButterKnife.findById(this, R.id.common_toolbar);
        titleTv= ButterKnife.findById(this, R.id.titleTv);
        if(mCommonToolbar!=null){
            setupToolbar(mCommonToolbar);
            setTitle("");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    /**
     * 显示{@link Toast}通知
     *
     * @param showText 字符串资源id
     */
    public void showToast(final String showText) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (toast == null) {
                    toast = Toast.makeText(BaseActivity.this, showText, Toast.LENGTH_SHORT);
                } else {
                    toast.setText(showText);
                    toast.setDuration(Toast.LENGTH_SHORT);
                }
                toast.show();
            }
        });
    }

    /**
     * 显示{@link Toast}通知
     *
     * @param strResId 字符串资源id
     */
    public void showToast(final int strResId) {
        String text = getString(strResId);
        showToast(text);
    }

    /**
     * 设置Toolbar成ActionBar
     */
    protected void setupToolbar(Toolbar toolbar) {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }

    /** ActionBar显示返回图标 */
    protected void showHomeAsUp(@DrawableRes int resId) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(resId);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * 自定义Toolbar的title内容
     */
    protected void setTitleContent(String title) {
        if (titleTv != null) {
            titleTv.setVisibility(View.VISIBLE);
            titleTv.setText(title);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
