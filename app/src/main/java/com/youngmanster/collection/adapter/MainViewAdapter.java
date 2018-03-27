package com.youngmanster.collection.adapter;

import android.content.Context;

import com.youngmanster.collection.R;
import com.youngmanster.collection.utils.DisplayUtil;
import com.youngmanster.collectionlibrary.refreshrecyclerview.base.adapter.BaseRecyclerViewAdapter;
import com.youngmanster.collectionlibrary.refreshrecyclerview.base.adapter.BaseViewHolder;
import com.youngmanster.collectionlibrary.refreshrecyclerview.pulltorefresh.PullToRefreshRecyclerView;

import java.util.List;

/**
 * Created by yangyan.
 */
public class MainViewAdapter extends BaseRecyclerViewAdapter<String> {

    private int mScreenWidth,mItemWidth;

    public MainViewAdapter(Context context, List<String> datas, PullToRefreshRecyclerView refreshRecyclerView) {
        super(context,R.layout.item_main,datas,refreshRecyclerView);
        mScreenWidth= DisplayUtil.getScreenWidthPixels(context);
        mItemWidth=(mScreenWidth-DisplayUtil.dip2px(context,20))/2;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, String s) {
        baseViewHolder.setText(R.id.title,s);
        baseViewHolder.getView(R.id.card_view).getLayoutParams().height=mItemWidth;
        baseViewHolder.getView(R.id.card_view).getLayoutParams().width=mItemWidth;
    }
}