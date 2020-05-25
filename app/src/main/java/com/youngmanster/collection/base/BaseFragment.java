package com.youngmanster.collection.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.youngmanster.collection.R;
import com.youngmanster.collectionlibrary.base.fragmet.IBaseFragment;
import com.youngmanster.collectionlibrary.mvp.BasePresenter;
import com.youngmanster.collectionlibrary.utils.LogUtils;
import com.youngmanster.collectionlibrary.utils.ToastUtils;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by yangyan
 * on 2018/3/18.
 */

public abstract class BaseFragment<T extends BasePresenter> extends IBaseFragment {

    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        unbinder= ButterKnife.bind(this,mainView);


        defineActionBarConfig
                .setBackIcon(R.mipmap.ic_back_btn)
                .setBackClick(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getActivity().onBackPressed();
                    }
                });

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

}
