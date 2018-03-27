package com.youngmanster.collection.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.youngmanster.collectionlibrary.base.IBaseFragment;
import com.youngmanster.collectionlibrary.mvp.BaseModel;
import com.youngmanster.collectionlibrary.mvp.BasePresenter;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by yangyan
 * on 2018/3/18.
 */

public abstract class BaseFragment<T extends BaseModel,E extends BasePresenter> extends IBaseFragment {

    private Unbinder unbinder;
    private Toast toast = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        unbinder= ButterKnife.bind(this,mainView);
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
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (toast == null) {
                        toast = Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT);
                    } else {
                        toast.setText(msg);
                        toast.setDuration(Toast.LENGTH_SHORT);
                    }
                    toast.show();
                }
            });
        }
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
