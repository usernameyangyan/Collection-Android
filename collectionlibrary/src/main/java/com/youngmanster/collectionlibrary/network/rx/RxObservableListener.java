package com.youngmanster.collectionlibrary.network.rx;

import com.youngmanster.collectionlibrary.network.NetWorkCodeException;

/**
 * Created by yangyan
 * on 2018/3/23.
 */

public interface RxObservableListener<T> {
	void onNext(T result);
	void onComplete();
	void onError(NetWorkCodeException.ResponseThrowable e);
}
