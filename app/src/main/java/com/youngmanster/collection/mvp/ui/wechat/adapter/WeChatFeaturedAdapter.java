package com.youngmanster.collection.mvp.ui.wechat.adapter;

import android.content.Context;
import android.widget.ImageView;
import com.youngmanster.collection.R;
import com.youngmanster.collection.been.wechat.WeChatNews;
import com.youngmanster.collection.utils.GlideUtil;
import com.youngmanster.collectionlibrary.refreshrecyclerview.base.adapter.BaseRecyclerViewAdapter;
import com.youngmanster.collectionlibrary.refreshrecyclerview.base.adapter.BaseViewHolder;
import com.youngmanster.collectionlibrary.refreshrecyclerview.pulltorefresh.PullToRefreshRecyclerView;

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
		baseViewHolder.setText(R.id.weChatTitleTv,weChatNews.getTitle())
				.setText(R.id.weChatNameTv,weChatNews.getDescription())
				.setText(R.id.weChatTimeTv,weChatNews.getCtime());

		ImageView imageView=baseViewHolder.getView(R.id.weChatIv);
		GlideUtil.loadImg(mContext,weChatNews.getPicUrl(),R.mipmap.ic_bttom_loading_01,R.mipmap.pull_wheel,imageView);
	}
}
