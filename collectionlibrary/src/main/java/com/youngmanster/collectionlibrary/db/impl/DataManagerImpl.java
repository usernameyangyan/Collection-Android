package com.youngmanster.collectionlibrary.db.impl;

import com.youngmanster.collectionlibrary.db.helper.DbHelper;
import com.youngmanster.collectionlibrary.db.helper.HttpHelper;
import com.youngmanster.collectionlibrary.db.helper.SharePreferenceHelper;
import com.youngmanster.collectionlibrary.network.RequestBuilder;

import java.util.List;

import io.reactivex.observers.DisposableObserver;
import io.realm.RealmObject;
import okhttp3.ResponseBody;

/**
 * Created by yangyan
 * on 2018/4/1.
 */

public  class DataManagerImpl implements SharePreferenceHelper,DbHelper,HttpHelper {


    @Override
    public void saveByNameAndKeyWithSP(String name, String key, Object object) {

    }

    @Override
    public void saveByKeyWithSP(String key, Object object) {

    }

    /**
     *  SharePreference
     */
    @Override
    public <T> T queryByNameAndKeyWithSP(String name, String key, Class<T> clazz) {
        return null;
    }

    @Override
    public <T> T queryByKeyWithSP(String key, Class<T> clazz) {return null;}

    /***
     * retrofit
     */
    @Override
    public <T> DisposableObserver<ResponseBody> httpRequest(RequestBuilder<T> requestBuilder) {
        return null;
    }

    /**
     *  realm
     */

    @Override
    public void saveOrUpdateWithPKByRealm(RealmObject bean) {

    }

    @Override
    public void saveOrUpdateWithPKByRealm(List<? extends RealmObject> beans) {

    }

    @Override
    public void saveWithoutPKByRealm(RealmObject bean) {

    }

    @Override
    public void saveWithoutPKByRealm(List<? extends RealmObject> beans) {

    }

    @Override
    public RealmObject queryFirstByRealm(Class<? extends RealmObject> clazz) {
        return null;
    }

    @Override
    public RealmObject queryAllWithFieldByRealm(Class<? extends RealmObject> clazz, String fieldName, String value) {
        return null;
    }

    @Override
    public List<? extends RealmObject> queryAllByRealm(Class<? extends RealmObject> clazz) {
        return null;
    }

    @Override
    public List<? extends RealmObject> queryAllWithSortByRealm(Class<? extends RealmObject> clazz, String fieldName, Boolean isAscendOrDescend) {
        return null;
    }

    @Override
    public void updateParamWithPKByRealm(Class<? extends RealmObject> clazz, String primaryKeyName, Object primaryKeyValue, String fieldName, Object newValue) {

    }

    @Override
    public void deleteFirstByRealm(Class<? extends RealmObject> clazz) {

    }

    @Override
    public void deleteAllByRealm(Class<? extends RealmObject> clazz) {

    }
}
