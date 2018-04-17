package com.youngmanster.collectionlibrary.db.impl;
import com.youngmanster.collectionlibrary.db.helper.DbHelper;
import com.youngmanster.collectionlibrary.db.helper.HttpHelper;
import com.youngmanster.collectionlibrary.db.helper.SharePreferenceHelper;
import com.youngmanster.collectionlibrary.network.RequestBuilder;
import io.reactivex.observers.DisposableObserver;
import okhttp3.ResponseBody;

/**
 * Created by yangyan
 * on 2018/4/1.
 */

public  class DataManagerImpl implements SharePreferenceHelper,DbHelper,HttpHelper {
    @Override
    public <T> T queryByNameAndKey(String name, String key, Class<T> clazz) {
        return null;
    }

    @Override
    public <T> T queryByKey(String key, Class<T> clazz) {return null;}

    @Override
    public <T> DisposableObserver<ResponseBody> httpRequest(RequestBuilder<T> requestBuilder) {
        return null;
    }
}
