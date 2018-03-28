package com.youngmanster.collectionlibrary.network.rx;

import com.youngmanster.collectionlibrary.network.NetWorkCodeException;

/**
 * Created by yangyan
 * on 2018/3/28.
 */

public interface ObservableListener<T> {
	void onNext(T result);
	void onComplete();
	void onError(NetWorkCodeException.ResponseThrowable e);
}
