package com.youngmanster.collection.base;

import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.youngmanster.collection.R;
import com.youngmanster.collectionlibrary.base.fragmet.IBaseFragment;
import com.youngmanster.collectionlibrary.mvp.BasePresenter;
import com.youngmanster.collectionlibrary.utils.DisplayUtils;
import com.youngmanster.collectionlibrary.utils.ToastUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by yangyan
 * on 2018/3/18.
 */

public abstract class BaseFragment<T extends BasePresenter> extends IBaseFragment {

    private Unbinder unbinder;
    private Toast toast = null;
    private RelativeLayout common_bar_panel;
    public TextView titleTv;
    public ImageView backBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        unbinder= ButterKnife.bind(this,mainView);

        //顶部toolbar
        common_bar_panel = mainView.findViewById(R.id.common_bar_panel);
        titleTv = mainView.findViewById(R.id.titleTv);
        backBtn = mainView.findViewById(R.id.btnBack);


        return mainView;
    }

    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    /**
     * 显示一个Toast类型的消息
     *
     * @param msg 显示的消息
     */
    public void showToast(final String msg) {
        ToastUtils.showToast(getActivity(),msg);
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
     * 显示名称
     */
    protected void setTitleContent(String title) {
        if (titleTv != null) {
            titleTv.setVisibility(View.VISIBLE);
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
                    if(!isRootFragment(BaseFragment.this)){
                        pop();
                    }else{
                        getActivity().finish();
                    }
                }
            });
        }
    }


    /**
     * 设置顶部距离，主要是为了解决Fragment之间状态栏透明和不透明的转换
     */

    protected void setTopBarMargins(View view,boolean isFullTranslucent) {
        if (isFullTranslucent) {
            if (view.getLayoutParams() instanceof LinearLayout.LayoutParams) {
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view.getLayoutParams();
                layoutParams.setMargins(0, DisplayUtils.getStatusBarHeight(getActivity()), 0, 0);
            } else if (view.getLayoutParams() instanceof RelativeLayout.LayoutParams) {
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                layoutParams.setMargins(0, DisplayUtils.getStatusBarHeight(getActivity()), 0, 0);
            }
        }
    }

    protected void setTopBarMargins(int viewId,boolean isFullTranslucent) {
        if (isFullTranslucent) {
            View view = mainView.findViewById(viewId);
            if (view.getLayoutParams() instanceof LinearLayout.LayoutParams) {
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view.getLayoutParams();
                layoutParams.setMargins(0, DisplayUtils.getStatusBarHeight(getActivity()), 0, 0);
            } else if (view.getLayoutParams() instanceof RelativeLayout.LayoutParams) {
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                layoutParams.setMargins(0, DisplayUtils.getStatusBarHeight(getActivity()), 0, 0);
            }
        }
    }


}
