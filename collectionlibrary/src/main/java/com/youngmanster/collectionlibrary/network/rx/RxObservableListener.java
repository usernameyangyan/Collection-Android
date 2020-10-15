package com.youngmanster.collectionlibrary.network.rx;

import com.youngmanster.collectionlibrary.network.NetWorkCodeException;

/**
 * Created by yangyan
 * on 2018/3/23.
 */

public abstract class RxObservableListener<T> implements ObservableListener<T>{


	protected RxObservableListener(){
	}

	@Override
	public void onDownloadProgress(long total, long currentLength, float progress) {

	}

	@Override
	public void onUploadProgress(long total, float progress) {

	}



	@Override
	public void onError(NetWorkCodeException.ResponseThrowable e) {
	}
}
