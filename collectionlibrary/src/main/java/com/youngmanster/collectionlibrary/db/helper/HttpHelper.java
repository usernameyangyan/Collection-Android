package com.youngmanster.collectionlibrary.db.helper;
import com.youngmanster.collectionlibrary.network.RequestBuilder;

import io.reactivex.observers.DisposableObserver;
import okhttp3.ResponseBody;

/**
 * Created by yangyan
 * on 2018/4/2.
 */

public interface HttpHelper {
    <T> DisposableObserver<ResponseBody> httpRequest(RequestBuilder<T> requestBuilder);
}
