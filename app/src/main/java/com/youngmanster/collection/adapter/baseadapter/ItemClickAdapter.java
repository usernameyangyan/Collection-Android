package com.youngmanster.collection.adapter.baseadapter;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;

import com.youngmanster.collection.R;
import com.youngmanster.collection.been.ClickItem;
import com.youngmanster.collectionlibrary.refreshrecyclerview.base.adapter.BaseRecyclerViewAdapter;
import com.youngmanster.collectionlibrary.refreshrecyclerview.base.adapter.BaseViewHolder;

import java.util.List;

/**
 * Created by yangyan
 * on 2018/3/20.
 */

public class ItemClickAdapter extends BaseRecyclerViewAdapter<ClickItem> {

	private View.OnClickListener onClickListener;

	public ItemClickAdapter(Context mContext, List<ClickItem> mDatas, View.OnClickListener onClickListener) {
		super(mContext, R.layout.item_click, mDatas);
		this.onClickListener = onClickListener;
	}

	@Override
	protected void convert(BaseViewHolder baseViewHolder, ClickItem clickItem) {
		baseViewHolder.setText(R.id.titleTv, clickItem.getTitle())
				.setImageResource(R.id.ivImg, clickItem.getRes())
				.setOnClickListener(R.id.clickTv, onClickListener);

		baseViewHolder.getView(R.id.clickTv).setVisibility(View.VISIBLE);
	}
}
