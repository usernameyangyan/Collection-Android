package com.youngmanster.collection.activity.base.adapter;

import android.content.Context;

import com.youngmanster.collection.R;
import com.youngmanster.collectionlibrary.refreshrecyclerview.base.adapter.BaseRecyclerViewAdapter;
import com.youngmanster.collectionlibrary.refreshrecyclerview.base.adapter.BaseViewHolder;

import java.util.List;

/**
 * Created by yangyan
 * on 2018/6/7.
 */

public class PopupMenuListAdapter extends BaseRecyclerViewAdapter<String> {
	public PopupMenuListAdapter(Context mContext,List<String> mDatas) {
		super(mContext, R.layout.item_popup, mDatas);
	}

	@Override
	protected void convert(BaseViewHolder baseViewHolder, String s) {
		baseViewHolder.setText(R.id.tvTxt,s);
	}
}
