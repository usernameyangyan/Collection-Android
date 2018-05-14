
package com.youngmanster.collection.activity.base;

import android.content.Context;
import com.youngmanster.collection.R;
import com.youngmanster.collectionlibrary.refreshrecyclerview.base.adapter.BaseRecyclerViewAdapter;
import com.youngmanster.collectionlibrary.refreshrecyclerview.base.adapter.BaseViewHolder;

import java.util.List;

/**
 * 批量订阅
 */
public class ItemRecycleAdapter extends BaseRecyclerViewAdapter<String> {
	private Context context;
	private int operationType;

	public ItemRecycleAdapter(Context mContext,List<String> mDatas) {
		super(mContext, R.layout.item_layout, mDatas);
	}

	@Override
	protected void convert(BaseViewHolder baseViewHolder, String s) {
		baseViewHolder.setText(R.id.tv_Label,s);
	}
}