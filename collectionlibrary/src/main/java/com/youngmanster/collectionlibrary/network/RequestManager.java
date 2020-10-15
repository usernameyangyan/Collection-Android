package com.youngmanster.collectionlibrary.network;
import com.youngmanster.collectionlibrary.network.request.RequestMethodImpl;
import com.youngmanster.collectionlibrary.network.synchronization.OkHttpUtils;

import io.reactivex.observers.DisposableObserver;
import okhttp3.ResponseBody;

/**
 * 请求管理
 * Created by yangyan
 * on 2018/3/23.
 */

public class RequestManager {

    private RequestMethodImpl requestMethod;

    private static class SingletonHolder{
        private static final RequestManager INSTANCE = new RequestManager();
    }

    public static RequestManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private RequestManager() {
        requestMethod=new RequestMethodImpl();
    }

    public <T> DisposableObserver<ResponseBody> request(RequestBuilder<T> builder) {
        if (builder.getReqMode() == RequestBuilder.ReqMode.ASYNCHRONOUS) {
          return requestMethod.request(builder);
        } else {
            OkHttpUtils.requestSyncData(builder);
        }
        return null;
    }

}
