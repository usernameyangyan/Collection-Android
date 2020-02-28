package com.youngmanster.collectionlibrary.data.database;

import java.util.ArrayList;


public class PagingList<T> extends ArrayList<T> {

	private static final long serialVersionUID = 5526933443772285251L;
	
	private int mTotalSize;
	
	/**
	 * return total size of your query condition
	 * @return
	 */
	public int getTotalSize(){
		return mTotalSize;
	}
	
	/**
	 * return size of this paing query
	 */
	public int size() {
		return super.size();
	}
	
	void setTotalSize(int totalSize){
		this.mTotalSize = totalSize;
	}
}
