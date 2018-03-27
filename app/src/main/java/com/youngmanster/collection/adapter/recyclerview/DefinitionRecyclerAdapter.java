package com.youngmanster.collection.adapter.recyclerview;

import android.content.Context;

import com.youngmanster.collection.R;
import com.youngmanster.collectionlibrary.refreshrecyclerview.base.adapter.BaseRecyclerViewAdapter;
import com.youngmanster.collectionlibrary.refreshrecyclerview.base.adapter.BaseViewHolder;
import com.youngmanster.collectionlibrary.refreshrecyclerview.pulltorefresh.PullToRefreshRecyclerView;

import java.util.List;

/**
 * Created by yangyan
 * on 2018/3/18.
 */

public class DefinitionRecyclerAdapter extends BaseRecyclerViewAdapter<String> {

    public DefinitionRecyclerAdapter(Context mContext, List<String> mDatas, PullToRefreshRecyclerView pullToRefreshRecyclerView) {
        super(mContext, R.layout.item_pull_refresh, mDatas, pullToRefreshRecyclerView);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, String s) {
        baseViewHolder.setText(R.id.title,s);
    }
}
