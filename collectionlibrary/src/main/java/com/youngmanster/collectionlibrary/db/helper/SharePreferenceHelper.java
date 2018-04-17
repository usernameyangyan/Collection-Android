package com.youngmanster.collectionlibrary.db.helper;

/**
 * Created by yangyan
 * on 2018/4/1.
 */

public interface  SharePreferenceHelper {
    <T> T queryByNameAndKey(String name,String key,Class<T> clazz);
    <T> T queryByKey(String key,Class<T> clazz);
}
