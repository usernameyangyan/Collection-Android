package com.youngmanster.collectionlibrary.network.rx;

import android.text.TextUtils;

import com.youngmanster.collectionlibrary.mvp.BaseView;
import com.youngmanster.collectionlibrary.network.NetWorkCodeException;

/**
 * Created by yangyan
 * on 2018/3/23.
 */

public abstract class RxObservableListener<T> implements ObservableListener<T>{


	private BaseView mView;
	private String mErrorMsg;

	protected RxObservableListener(BaseView view){
		this.mView = view;
	}

	protected RxObservableListener(BaseView view, String errorMsg){
		this.mView = view;
		this.mErrorMsg = errorMsg;
	}

	@Override
	public void onComplete() {

	}

	@Override
	public void onError(NetWorkCodeException.ResponseThrowable e) {
		if (mView == null) {
			return;
		} if (mErrorMsg != null && !TextUtils.isEmpty(mErrorMsg)) {
			mView.onError(mErrorMsg);
		}else {
			mView.onError(e.message);
		}
	}
}
