package com.youngmanster.collection.mvp.model.wechat.okhttpcache;

import com.youngmanster.collection.been.Result;
import com.youngmanster.collection.been.wechat.WeChatNews;
import com.youngmanster.collection.mvp.contract.wechat.okhttpcache.WeChatFeaturedContract;
import com.youngmanster.collection.http.ApiClient;
import com.youngmanster.collection.http.ApiService;
import com.youngmanster.collection.http.ApiUrl;
import com.youngmanster.collectionlibrary.network.RetrofitManager;
import com.youngmanster.collectionlibrary.network.rx.RxSchedulers;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by yangyan
 * on 2018/3/21.
 */

public class WeChatFeaturedModel implements WeChatFeaturedContract.Model {
	@Override
	public Observable<Result<List<WeChatNews>>> loadFeaturedNews(int page, int num) {
		Map<String,Object> map= ApiClient.getRequiredBaseParam();
		map.put("page",page);
		map.put("num",num);
		return RetrofitManager.getApiService(ApiService.class)
				.getWeChatFeaturedNews(ApiUrl.URL_WETCHAT_FEATURED,map);
	}
}
