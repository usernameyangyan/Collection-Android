package com.youngmanster.collection.adapter.recyclerview;

import android.content.Context;

import com.youngmanster.collection.R;
import com.youngmanster.collectionlibrary.refreshrecyclerview.base.adapter.BaseRecyclerViewAdapter;
import com.youngmanster.collectionlibrary.refreshrecyclerview.base.adapter.BaseViewHolder;
import com.youngmanster.collectionlibrary.refreshrecyclerview.pulltorefresh.PullToRefreshRecyclerView;
import com.youngmanster.collectionlibrary.utils.DisplayUtils;

import java.util.List;

/**
 * Created by yangyan
 * on 2018/3/18.
 */

public class DefaultRecyclerAdapter extends BaseRecyclerViewAdapter<String> {

    private int mScreenWidth,mItemWidth;

    public DefaultRecyclerAdapter(Context mContext, List<String> mDatas, PullToRefreshRecyclerView pullToRefreshRecyclerView) {
        super(mContext, R.layout.item_pull_refresh, mDatas, pullToRefreshRecyclerView);
        mScreenWidth= DisplayUtils.getScreenWidthPixels(mContext);
        mItemWidth=(mScreenWidth-DisplayUtils.dip2px(mContext,30))/3;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, String s) {
        baseViewHolder.setText(R.id.title,s);
        baseViewHolder.getView(R.id.card_view).getLayoutParams().height=mItemWidth;
        baseViewHolder.getView(R.id.card_view).getLayoutParams().width=mItemWidth;
    }
}
