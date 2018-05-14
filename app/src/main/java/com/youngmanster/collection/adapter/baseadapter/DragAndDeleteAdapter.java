package com.youngmanster.collection.adapter.baseadapter;

import android.content.Context;

import com.youngmanster.collection.R;
import com.youngmanster.collectionlibrary.refreshrecyclerview.base.adapter.BaseRecyclerViewAdapter;
import com.youngmanster.collectionlibrary.refreshrecyclerview.base.adapter.BaseViewHolder;
import com.youngmanster.collectionlibrary.utils.DisplayUtils;

import java.util.List;

/**Created by yangyan
 * on 2018/3/14.
 */

public class DragAndDeleteAdapter extends BaseRecyclerViewAdapter<String> {

	private int mHeight;

	public DragAndDeleteAdapter(Context mContext, List<String> mDatas) {
		super(mContext, R.layout.item_main, mDatas);
		mHeight= DisplayUtils.dip2px(mContext,100);
	}

	@Override
	protected void convert(BaseViewHolder baseViewHolder, String s) {
		baseViewHolder.setText(R.id.title,s);
		baseViewHolder.getView(R.id.card_view).getLayoutParams().height=mHeight;
	}
}
