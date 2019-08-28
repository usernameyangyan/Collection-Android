package com.youngmanster.collectionlibrary.db;

import com.youngmanster.collectionlibrary.db.impl.DataManagerImpl;
import com.youngmanster.collectionlibrary.network.RequestBuilder;
import com.youngmanster.collectionlibrary.network.RequestManager;
import com.youngmanster.collectionlibrary.utils.RealmUtils;
import com.youngmanster.collectionlibrary.utils.SharePreferenceUtils;

import java.util.List;

import io.reactivex.observers.DisposableObserver;
import io.realm.RealmObject;
import okhttp3.ResponseBody;

/**
 * Created by yangyan
 * on 2018/4/1.
 */

public class DataManager extends DataManagerImpl {

	private DataManagerImpl dataManagerStub;
	private static DataManager dataManager;


	public enum DataType {
		RETROFIT, REALM, SHAREPREFERENCE
	}

	private DataManager() {

	}

	public static DataManager getInstance(DataType type) {
		if (dataManager == null) {
			synchronized (SharePreferenceUtils.class) {
				if (dataManager == null) {
					dataManager = new DataManager();
				}
			}
		}

		dataManager.getManagerStub(type);

		return dataManager;
	}

	private DataManagerImpl getManagerStub(DataType type) {
		switch (type) {
			case RETROFIT:
				dataManagerStub = new RequestManager();
				break;
			case REALM:
                dataManagerStub = RealmUtils.getInstance();
				break;
			case SHAREPREFERENCE:
				dataManagerStub = SharePreferenceUtils.getInstance();
				break;
			default:
		}

		return dataManagerStub;
	}

	/**
	 * SharePreferenceUtils查询
	 */

	@Override
	public void saveByNameAndKeyWithSP(String name, String key, Object object) {
		dataManagerStub.saveByNameAndKeyWithSP(name, key, object);
	}

	@Override
	public void saveByKeyWithSP(String key, Object object) {
		dataManagerStub.saveByKeyWithSP(key, object);
	}

	/**
	 *
	 * @param name  SharePreferenceUtils的保存文件
	 * @param key
	 * @param claZZ
	 * @param <T>
	 * @return
	 */
	@Override
	public <T> T queryByNameAndKeyWithSP(String name, String key, Class<T> claZZ,Object defaultValue) {
		return dataManagerStub.queryByNameAndKeyWithSP(name, key, claZZ,defaultValue);
	}

	@Override
	public <T> T queryByKeyWithSP(String key, Class<T> clazz,Object defaultValue) {
		return dataManagerStub.queryByKeyWithSP(key, clazz,defaultValue);
	}

	/***
	 * HttpRequest
	 * @param requestBuilder
	 * @param <T>
	 * @return
	 */
	@Override
	public <T> DisposableObserver<ResponseBody> httpRequest(RequestBuilder<T> requestBuilder) {
		return dataManagerStub.httpRequest(requestBuilder);
	}

	/**
	 * Realm查询
	 */
	@Override
	public void saveOrUpdateWithPKByRealm(RealmObject bean) {
		dataManagerStub.saveOrUpdateWithPKByRealm(bean);
	}

	@Override
	public void saveOrUpdateWithPKByRealm(List<? extends RealmObject> beans) {
		dataManagerStub.saveOrUpdateWithPKByRealm(beans);
	}

	@Override
	public void saveWithoutPKByRealm(RealmObject bean) {
		dataManagerStub.saveWithoutPKByRealm(bean);
	}

	@Override
	public void saveWithoutPKByRealm(List<? extends RealmObject> beans) {
		dataManagerStub.saveWithoutPKByRealm(beans);
	}

	@Override
	public RealmObject queryFirstByRealm(Class<? extends RealmObject> clazz) {
		return dataManagerStub.queryFirstByRealm(clazz);
	}

	@Override
	public List<? extends RealmObject> queryAllWithFieldByRealm(Class<? extends RealmObject> clazz, String fieldName, String value) {
		return dataManagerStub.queryAllWithFieldByRealm(clazz, fieldName, value);
	}

	@Override
	public RealmObject queryWithFieldByRealm(Class<? extends RealmObject> clazz, String fieldName, String value) {
		return dataManagerStub.queryWithFieldByRealm(clazz, fieldName, value);
	}

	@Override
	public List<? extends RealmObject> queryAllByRealm(Class<? extends RealmObject> clazz) {
		return dataManagerStub.queryAllByRealm(clazz);
	}

	@Override
	public List<? extends RealmObject> queryAllWithSortByRealm(Class<? extends RealmObject> clazz, String fieldName, Boolean isAscendOrDescend) {
		return dataManagerStub.queryAllWithSortByRealm(clazz, fieldName, isAscendOrDescend);
	}

	@Override
	public void updateParamWithPKByRealm(Class<? extends RealmObject> clazz, String primaryKeyName, Object primaryKeyValue, String fieldName, Object newValue) {
		dataManagerStub.updateParamWithPKByRealm(clazz, primaryKeyName, primaryKeyValue, fieldName, newValue);
	}

	@Override
	public void deleteFirstByRealm(Class<? extends RealmObject> clazz) {
		dataManagerStub.deleteFirstByRealm(clazz);
	}

	@Override
	public void deleteAllByRealm(Class<? extends RealmObject> clazz) {
		dataManagerStub.deleteAllByRealm(clazz);
	}

	@Override
	public void deleteAllWithFieldByRealm(Class<? extends RealmObject> clazz, String fieldName, String value) {
		dataManagerStub.deleteAllWithFieldByRealm(clazz,fieldName,value);
	}
}
