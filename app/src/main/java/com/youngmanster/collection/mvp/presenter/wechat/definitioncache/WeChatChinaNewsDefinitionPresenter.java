package com.youngmanster.collection.mvp.presenter.wechat.definitioncache;

import com.youngmanster.collection.been.Result;
import com.youngmanster.collection.been.wechat.WeChatNews;
import com.youngmanster.collection.common.AppConfig;
import com.youngmanster.collection.http.ApiUrl;
import com.youngmanster.collection.mvp.contract.wechat.okhttpcache.WeChatChinaNewsContract;
import com.youngmanster.collectionlibrary.data.DataManager;
import com.youngmanster.collectionlibrary.network.RequestBuilder;
import com.youngmanster.collectionlibrary.network.rx.RxObservableListener;

import java.util.List;

/**
 * Created by yangyan
 * on 2018/3/21.
 */

public class WeChatChinaNewsDefinitionPresenter extends WeChatChinaNewsContract.Presenter {
	@Override
	public void requestChinaNews(int page, int num) {
		String filePath = AppConfig.STORAGE_DIR + "wechat/china";
		String fileName = "limttime.t";

		RequestBuilder resultRequestBuilder = new RequestBuilder<>(new RxObservableListener<Result<List<WeChatNews>>>(mView) {
			@Override
			public void onNext(Result<List<WeChatNews>> result) {
				mView.refreshUI(result.getResult());
			}
		}).setFilePathAndFileName(filePath, fileName)
				.setTransformClass(WeChatNews.class)
				.setUrl(ApiUrl.URL_WETCHAT_FEATURED)
				.setHttpTypeAndReqType(RequestBuilder.HttpType.DEFAULT_GET, RequestBuilder.ReqType.DISK_CACHE_LIST_LIMIT_TIME)
				.setParam("page",page)
				.setParam("type","video")
				.setParam("count",num);

		rxManager.addObserver(DataManager.DataForHttp.httpRequest(resultRequestBuilder));
	}
}
