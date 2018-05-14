package com.youngmanster.collection.adapter.baseadapter;

import android.content.Context;

import com.youngmanster.collection.R;
import com.youngmanster.collection.been.MultiItem;
import com.youngmanster.collectionlibrary.refreshrecyclerview.base.adapter.BaseRecyclerViewMultiItemAdapter;
import com.youngmanster.collectionlibrary.refreshrecyclerview.base.adapter.BaseViewHolder;
import com.youngmanster.collectionlibrary.utils.DisplayUtils;


import java.util.List;

/**
 * Created by yangyan
 * on 2018/3/14.
 */

public class MultipleAdapter extends BaseRecyclerViewMultiItemAdapter<MultiItem> {

	private int mHeight;

	public MultipleAdapter(Context mContext, List<MultiItem> mDatas) {
		super(mContext, mDatas);
		mHeight = DisplayUtils.dip2px(mContext, 100);
		addItemType(MultiItem.TYPE_TEXT, R.layout.item_main);
		addItemType(MultiItem.TYPE_IMG, R.layout.item_img);
		addItemType(MultiItem.TYPE_TEXT_IMG, R.layout.item_click);
	}

	@Override
	protected void convert(BaseViewHolder baseViewHolder, MultiItem multiItem) {
		switch (baseViewHolder.getItemViewType()) {
			case MultiItem.TYPE_TEXT:
				baseViewHolder.getView(R.id.card_view).getLayoutParams().height = mHeight;
				baseViewHolder.setText(R.id.title, multiItem.getTitle());
				break;
			case MultiItem.TYPE_IMG:
				baseViewHolder.setImageResource(R.id.ivImg, multiItem.getRes());
				break;
			case MultiItem.TYPE_TEXT_IMG:
				baseViewHolder.setImageResource(R.id.ivImg, multiItem.getRes());
				baseViewHolder.setText(R.id.titleTv, multiItem.getTitle());
				break;

		}

	}
}
