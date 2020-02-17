package com.youngmanster.collection.mvp.ui.wechat.adapter;

import android.content.Context;
import android.widget.ImageView;
import com.youngmanster.collection.R;
import com.youngmanster.collection.been.wechat.WeChatNews;
import com.youngmanster.collectionlibrary.refreshrecyclerview.base.adapter.BaseRecyclerViewAdapter;
import com.youngmanster.collectionlibrary.refreshrecyclerview.base.adapter.BaseViewHolder;
import com.youngmanster.collectionlibrary.refreshrecyclerview.pulltorefresh.PullToRefreshRecyclerView;
import com.youngmanster.collectionlibrary.utils.GlideUtils;

import java.util.List;

/**
 * Created by yangyan
 * on 2018/3/21.
 */

public class WeChatFeaturedAdapter extends BaseRecyclerViewAdapter<WeChatNews>{

	public WeChatFeaturedAdapter(Context mContext, List<WeChatNews> mDatas, PullToRefreshRecyclerView pullToRefreshRecyclerView) {
		super(mContext, R.layout.item_wechat_featured, mDatas, pullToRefreshRecyclerView);
	}

	@Override
	protected void convert(BaseViewHolder baseViewHolder, WeChatNews weChatNews) {
		baseViewHolder.setText(R.id.weChatTitleTv,weChatNews.getName())
				.setText(R.id.weChatNameTv,weChatNews.getText())
				.setText(R.id.weChatTimeTv,weChatNews.getPasstime());

		ImageView imageView=baseViewHolder.getView(R.id.weChatIv);
		GlideUtils.loadImg(mContext,weChatNews.getThumbnail(),R.mipmap.ic_bttom_loading_01,imageView);
	}
}
