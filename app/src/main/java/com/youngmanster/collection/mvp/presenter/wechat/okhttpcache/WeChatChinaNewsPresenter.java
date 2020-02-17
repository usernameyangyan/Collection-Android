package com.youngmanster.collection.mvp.presenter.wechat.okhttpcache;

import com.youngmanster.collection.been.Result;
import com.youngmanster.collection.been.wechat.WeChatNews;
import com.youngmanster.collection.http.ApiClient;
import com.youngmanster.collection.http.ApiUrl;
import com.youngmanster.collection.mvp.contract.wechat.okhttpcache.WeChatChinaNewsContract;
import com.youngmanster.collectionlibrary.db.DataManager;
import com.youngmanster.collectionlibrary.network.RequestBuilder;
import com.youngmanster.collectionlibrary.network.rx.RxObservableListener;

import java.util.List;

/**
 * Created by yangyan
 * on 2018/3/21.
 */

public class WeChatChinaNewsPresenter extends WeChatChinaNewsContract.Presenter {
	@Override
	public void requestChinaNews(int page, int num) {

		RequestBuilder resultRequestBuilder = new RequestBuilder<>(new RxObservableListener<Result<List<WeChatNews>>>(mView) {
			@Override
			public void onNext(Result<List<WeChatNews>> result) {
				mView.refreshUI(result.getResult());
			}
		}).setUrl(ApiUrl.URL_WETCHAT_FEATURED)
				.setTransformClass(WeChatNews.class)
				.setRequestParam(ApiClient.getRequiredBaseParam())
				.setParam("page",page)
				.setParam("type","video")
				.setParam("count",num);


		rxManager.addObserver(DataManager.getInstance(DataManager.DataType.RETROFIT).httpRequest(resultRequestBuilder));
	}
}
