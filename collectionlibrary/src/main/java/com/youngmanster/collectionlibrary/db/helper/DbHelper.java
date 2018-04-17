package com.youngmanster.collectionlibrary.db.helper;

import java.util.List;

import io.realm.RealmObject;

/**
 * Created by yangyan
 * on 2018/4/1.
 */

public interface  DbHelper {
	/**
	 * 保存操作
	 */
	void saveOrUpdateWithPKByRealm(final RealmObject bean);
	void saveOrUpdateWithPKByRealm(final List<? extends RealmObject> beans);
	void saveWithoutPKByRealm(final RealmObject bean);
	void saveWithoutPKByRealm(final List<? extends RealmObject> beans);

	/**
	 * 查询操作
	 */
	RealmObject queryFirstByRealm(Class<? extends RealmObject> clazz);
	RealmObject queryAllWithFieldByRealm(Class<? extends RealmObject> clazz, String fieldName, String value);
	List<? extends RealmObject> queryAllByRealm(Class<? extends RealmObject> clazz);
	List<? extends RealmObject> queryAllWithSortByRealm(Class<? extends RealmObject> clazz, String fieldName,Boolean isAscendOrDescend);

	/**
	 * 修改操作
	 */
	void updateParamWithPKByRealm(Class<? extends RealmObject> clazz, String primaryKeyName, Object primaryKeyValue, String fieldName,Object newValue);

	/**
	 * 删除操作
	 */
	void deleteFirstByRealm(Class<? extends RealmObject> clazz);
	void deleteAllByRealm(Class<? extends RealmObject> clazz);
}
