package com.youngmanster.collectionlibrary.mvp;

import com.youngmanster.collectionlibrary.network.NetWorkCodeException;

/**
 * Created by yangyan
 * on 2018/3/17.
 */

public interface BaseView {
	void onError(NetWorkCodeException.ResponseThrowable e);
}
