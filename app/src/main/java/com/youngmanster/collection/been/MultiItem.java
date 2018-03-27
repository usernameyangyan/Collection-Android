package com.youngmanster.collection.been;

import com.youngmanster.collectionlibrary.refreshrecyclerview.base.adapter.BaseMultiItemEntity;

/**
 * Created by yangyan
 * on 2018/3/14.
 */

public class MultiItem implements BaseMultiItemEntity {

	public final static int TYPE_TEXT=1;
	public final static int TYPE_IMG=2;
	public final static int TYPE_TEXT_IMG=3;

	private String title;
	private int res;
	private int type;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getRes() {
		return res;
	}

	public void setRes(int res) {
		this.res = res;
	}

	@Override
	public int getItemViewType() {
		return getType();
	}
}
