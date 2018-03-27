package com.youngmanster.collection.http;

import com.youngmanster.collection.been.Result;
import com.youngmanster.collection.been.wechat.WeChatNews;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Created by yangyan
 * on 2018/3/21.
 */

public interface ApiService {
	/**
	 * 微信精选
	 * @param url
	 * @param map
	 * @return
	 */
	@GET
	Observable<Result<List<WeChatNews>>> getWeChatFeaturedNews(@Url String url, @QueryMap Map<String,Object> map);

	/**
	 * 国际新闻
	 * @param url
	 * @param map
	 * @return
	 */
	@GET
	Observable<Result<List<WeChatNews>>> getWeChatWorldNews(@Url String url,@QueryMap Map<String,Object> map);

	/**
	 * 国内新闻
	 * @param url
	 * @param map
	 * @return
	 */
	@GET
	Observable<Result<List<WeChatNews>>> getWeChatChinaNews(@Url String url,@QueryMap Map<String,Object> map);

	/**
	 * NBA新闻
	 * @param url
	 * @param map
	 * @return
	 */
	@GET
	Observable<Result<List<WeChatNews>>> getWeChatNBAews(@Url String url,@QueryMap Map<String,Object> map);
}
