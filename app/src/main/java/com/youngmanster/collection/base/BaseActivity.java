package com.youngmanster.collection.base;

import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.youngmanster.collection.R;
import com.youngmanster.collectionlibrary.base.activity.IBaseActivity;
import com.youngmanster.collectionlibrary.mvp.BasePresenter;
import com.youngmanster.collectionlibrary.utils.DisplayUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by yangyan
 * on 2018/3/18.
 */

public abstract class BaseActivity<T extends BasePresenter> extends IBaseActivity {
    private Unbinder unbinder;
    public RelativeLayout common_bar_panel;
    public TextView titleTv;
    public ImageView backBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        unbinder= ButterKnife.bind(this);

        //顶部toolbar
        common_bar_panel = findViewById(R.id.common_bar_panel);
        titleTv = findViewById(R.id.titleTv);
        backBtn = findViewById(R.id.btnBack);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
    /**
     * 设置Toolbar成ActionBar
     */
    protected void setupToolbar(Toolbar toolbar) {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }

    /**
     * 显示返回按钮
     */
    protected void showHomeAsUp(@DrawableRes int resId) {
        if (backBtn != null) {
            backBtn.setVisibility(View.VISIBLE);
            backBtn.setImageResource(resId);
            backBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
    }

    /**
     * 显示名称
     */
    protected void setTitleContent(String title) {
        if (titleTv != null) {
            titleTv.setVisibility(View.VISIBLE);
            titleTv.setText(title);
            titleTv.setText(title);
        }
    }

    /**
     * 显示名称id
     */
    protected void setTitleContent(int titleId) {
        if (titleTv != null) {
            titleTv.setVisibility(View.VISIBLE);
            titleTv.setText(getString(titleId));
            titleTv.setText(getString(titleId));
        }
    }


    protected void setTopBarMargins(View view,boolean isFullTranslucent) {
        if (isFullTranslucent) {
            if (view.getLayoutParams() instanceof LinearLayout.LayoutParams) {
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view.getLayoutParams();
                layoutParams.setMargins(0, DisplayUtils.getStatusBarHeight(this), 0, 0);
            } else if (view.getLayoutParams() instanceof RelativeLayout.LayoutParams) {
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                layoutParams.setMargins(0, DisplayUtils.getStatusBarHeight(this), 0, 0);
            }
        }
    }
}
