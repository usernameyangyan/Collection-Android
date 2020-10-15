package com.youngmanster.collectionlibrary.network.synchronization;

import com.youngmanster.collectionlibrary.config.Config;
import com.youngmanster.collectionlibrary.network.NetWorkCodeException;
import com.youngmanster.collectionlibrary.network.RequestBuilder;
import com.youngmanster.collectionlibrary.network.RetrofitManager;
import com.youngmanster.collectionlibrary.network.convert.GsonUtils;
import com.youngmanster.collectionlibrary.utils.NetworkUtils;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by yangy
 * 2019-12-14
 * Describe:
 */
public class OkHttpUtils {

    public static <T> void requestSyncData(RequestBuilder<T> requestBuilder) {
        try {
            OkHttpClient okHttpClient;
            Request.Builder builder = new Request.Builder();

            if ((requestBuilder.getHttpType() == RequestBuilder.HttpType.DEFAULT_GET ||
                    requestBuilder.getHttpType() == RequestBuilder.HttpType.DEFAULT_POST ||
                    requestBuilder.getHttpType() == RequestBuilder.HttpType.JSON_PARAM_POST||
                    requestBuilder.getHttpType() == RequestBuilder.HttpType.MULTIPLE_MULTIPART_POST) &&
                    (requestBuilder.getReqType() == RequestBuilder.ReqType.NO_CACHE_MODEL ||
                            requestBuilder.getReqType() == RequestBuilder.ReqType.NO_CACHE_LIST ||
                            requestBuilder.getReqType() == RequestBuilder.ReqType.DEFAULT_CACHE_MODEL ||
                            requestBuilder.getReqType() == RequestBuilder.ReqType.DEFAULT_CACHE_LIST)) {

                okHttpClient = RetrofitManager.getOkHttpClient(requestBuilder);

                String url;
                if (requestBuilder.getUrl().contains("https://") || requestBuilder.getUrl().contains("http://")) {
                    url = requestBuilder.getUrl();
                } else {
                    url = Config.URL_DOMAIN+ requestBuilder.getUrl();
                }

                if (requestBuilder.getHttpType() == RequestBuilder.HttpType.DEFAULT_GET) {
                    HttpUrl.Builder urlBuilder = HttpUrl.parse(url)
                            .newBuilder();
                    Set set = requestBuilder.getRequestParam().keySet();
                    for (Iterator iter = set.iterator(); iter.hasNext(); ) {
                        String key = (String) iter.next();
                        Object value = requestBuilder.getRequestParam().get(key);
                        urlBuilder.addQueryParameter(key, value.toString());
                    }
                    builder.url(urlBuilder.build()).get();

                } else if (requestBuilder.getHttpType() == RequestBuilder.HttpType.DEFAULT_POST) {
                    FormBody.Builder requestBody = new FormBody.Builder();
                    Set set = requestBuilder.getRequestParam().keySet();
                    for (Iterator iter = set.iterator(); iter.hasNext(); ) {
                        String key = (String) iter.next();
                        Object value = requestBuilder.getRequestParam().get(key);
                        requestBody.add(key, value.toString());
                    }
                    builder.post(requestBody.build());
                }else if(requestBuilder.getHttpType() == RequestBuilder.HttpType.MULTIPLE_MULTIPART_POST){

                    //上传图片需要 MultipartBody

                    MultipartBody.Builder body = new MultipartBody.Builder().setType(MultipartBody.FORM);

                    for(MultipartBody.Part part:requestBuilder.getParts()){
                        body.addPart(part);
                    }

                    Set set = requestBuilder.getRequestParam().keySet();
                    for (Iterator iter = set.iterator(); iter.hasNext(); ) {
                        String key = (String) iter.next();
                        Object value = requestBuilder.getRequestParam().get(key);
                        body.addFormDataPart(key,  value.toString());
                    }

                    RequestBody body1 = body.build();
                    builder.url(url)
                            .post(body1);

                } else {
                    String data = GsonUtils.getGsonWithoutExpose().toJson(requestBuilder.getRequestParam());
                    MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                    RequestBody requestBody = RequestBody.create(JSON, data);
                    builder.url(url)
                            .post(requestBody);
                }

                Request okRequest = builder.build();
                Response response = okHttpClient.newCall(okRequest).execute();

                if (response.code() == 200) {
                    String body = response.body().string();

                    T a;
                    if (requestBuilder.isReturnOriginJson()) {
                        a = (T) body;
                    } else if (Config.MClASS == null) {
                        a = GsonUtils.fromJsonNoCommonClass(body, requestBuilder.getTransformClass());
                    } else if (!requestBuilder.isUseCommonClass()) {
                        a = GsonUtils.fromJsonNoCommonClass(body, requestBuilder.getTransformClass());
                    } else {
                        if (requestBuilder.getReqType() == RequestBuilder.ReqType.NO_CACHE_MODEL ||
                                requestBuilder.getReqType() == RequestBuilder.ReqType.DEFAULT_CACHE_MODEL) {
                            a = GsonUtils.fromJsonObject(body, requestBuilder.getTransformClass());
                        } else {
                            a = GsonUtils.fromJsonArray(body, requestBuilder.getTransformClass());
                        }
                    }

                    requestBuilder.getRxObservableListener().onNext(a);

                } else {
                    NetWorkCodeException.ResponseThrowable e = new NetWorkCodeException.ResponseThrowable();
                    e.code=response.code();
                    e.message=response.message();
                    requestBuilder.getRxObservableListener().onError(e);
                }

            } else {
                NetWorkCodeException.ResponseThrowable e = new NetWorkCodeException.ResponseThrowable();
                e.code=1000;
                e.message="暂只支持DEFAULT_GET、DEFAULT_POST、JSON_PARAM_POST和MULTIPLE_MULTIPART_POST四种请求方式,支持NO_CACHE_MODEL、No_CACHE_LIST、DEFAULT_CACHE_MODEL以及DEFAULT_CACHE_LIST四种数据方式，其它的请求以及数据方式正在开发中";

            }
        } catch (IOException e) {
            NetWorkCodeException.ResponseThrowable e1 = new NetWorkCodeException.ResponseThrowable();
            if(!NetworkUtils.isNetworkConnected(Config.CONTEXT)){
                e1.code=NetWorkCodeException.NETWORD_ERROR;
                e1.message=NetWorkCodeException.NETWORD_ERROR_MESSAGE;
            }else{
                e1.code=NetWorkCodeException.HTTP_ERROR;
                e1.message=NetWorkCodeException.HTTP_ERROR_MESSAGE;
            }


            requestBuilder.getRxObservableListener().onError(e1);
        }
    }
}
