package com.youngmanster.collection.mvp.presenter.wechat.okhttpcache;

import com.youngmanster.collection.been.Result;
import com.youngmanster.collection.been.wechat.WeChatNews;
import com.youngmanster.collection.mvp.contract.wechat.okhttpcache.WeChatFeaturedContract;
import com.youngmanster.collectionlibrary.network.NetWorkCodeException;
import com.youngmanster.collectionlibrary.network.RequestManager;
import com.youngmanster.collectionlibrary.network.rx.RxObservableListener;

import java.util.List;

import io.reactivex.observers.DisposableObserver;

/**
 * Created by yangyan
 * on 2018/3/21.
 */

public class WeChatFeaturedPresenter extends WeChatFeaturedContract.Presenter {
	@Override
	public void requestFeaturedNews(int page, int num) {
		rxManager.addObserver(RequestManager.loadOnlyNetWork(mModel.loadFeaturedNews(page, num),
				new RxObservableListener<Result<List<WeChatNews>>>(mView) {
					@Override
					public void onNext(Result<List<WeChatNews>> result) {
						mView.refreshUI(result.getNewslist());
					}
				}));
	}
}
