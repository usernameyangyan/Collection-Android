package com.youngmanster.collection.mvp.ui.wechat.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.youngmanster.collection.R;
import com.youngmanster.collection.base.BaseFragment;
import com.youngmanster.collection.been.wechat.WeChatNews;
import com.youngmanster.collection.mvp.contract.wechat.okhttpcache.WeChatChinaNewsContract;
import com.youngmanster.collection.mvp.presenter.wechat.definitioncache.WeChatChinaNewsDefinitionPresenter;
import com.youngmanster.collection.mvp.ui.wechat.adapter.WeChatFeaturedAdapter;
import com.youngmanster.collectionlibrary.base.StateView;
import com.youngmanster.collectionlibrary.refreshrecyclerview.base.adapter.BaseRecyclerViewAdapter;
import com.youngmanster.collectionlibrary.refreshrecyclerview.pulltorefresh.PullToRefreshRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by yangyan
 * on 2018/3/21.
 */

public class FragmentChinaNewsDefinition extends BaseFragment<WeChatChinaNewsDefinitionPresenter> implements
        WeChatChinaNewsContract.View, BaseRecyclerViewAdapter.OnItemClickListener {
    @BindView(R.id.refreshRv)
	PullToRefreshRecyclerView refreshRv;
    @BindView(R.id.state_view)
	StateView stateView;

    private static final int PAGE_SIZE = 15;
    private int pageSize = 1;

    private List<WeChatNews> mDatas = new ArrayList<>();
    private WeChatFeaturedAdapter weChatFeaturedAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_wechat_news;
    }

    @Override
    public void init() {
        stateView.showViewByState(StateView.STATE_LOADING);
        stateView.setOnDisConnectViewListener(new StateView.OnDisConnectListener() {
            @Override
            public void onDisConnectViewClick() {
                requestData();
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        refreshRv.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void requestData() {
        ((WeChatChinaNewsDefinitionPresenter) mPresenter).requestChinaNews(pageSize, PAGE_SIZE);
    }

    @Override
    public void refreshUI(List<WeChatNews> newsList) {

        if (newsList != null) {
            if (pageSize == 1) {
                mDatas.clear();
                mDatas.addAll(newsList);
            } else {
                mDatas.addAll(newsList);
            }

        }

        if (weChatFeaturedAdapter == null) {
            if (mDatas.size() == 0) {
                stateView.showViewByState(StateView.STATE_EMPTY);
            } else {
                stateView.showViewByState(StateView.STATE_NO_DATA);
            }
            weChatFeaturedAdapter = new WeChatFeaturedAdapter(getActivity(), mDatas, refreshRv);
            refreshRv.setAdapter(weChatFeaturedAdapter);
        }
    }

    @Override
    public void onItemClick(View view, int position) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (refreshRv != null) {
            refreshRv.destroy();
            refreshRv = null;
        }
    }

    @Override
    public void onError(String errorMsg) {
        showToast(errorMsg);
        if (mDatas.size() == 0) {
            stateView.showViewByState(StateView.STATE_DISCONNECT);
        }
    }
}
