package com.youngmanster.collectionlibrary.utils;

import com.youngmanster.collectionlibrary.config.Config;
import com.youngmanster.collectionlibrary.db.impl.DataManagerImpl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by yangyan
 * on 2018/4/15.
 */

public class RealmUtils extends DataManagerImpl {
	private static RealmUtils realmUtils;
	private static Realm realm;

	private RealmUtils() {
	}

	public static RealmUtils getInstance() {
		if (realmUtils == null) {
			synchronized (RealmUtils.class) {
				if (realmUtils == null) {
					realmUtils = new RealmUtils();
					Realm.init(Config.CONTEXT);
					RealmConfiguration.Builder builder = new RealmConfiguration.Builder();
					builder.name(Config.realmName); //文件名
					builder.schemaVersion(Config.realmVersion); //版本号
					if (Config.realmMigration != null) {
						builder.migration(Config.realmMigration);//数据库版本迁移（数据库升级，当数据库中某个表添加字段或者删除字段）
					}
					RealmConfiguration config = builder.build();//创建

					realm=Realm.getInstance(config);
				}
			}
		}
		return realmUtils;
	}

	/**
	 * 保存和更新一个数据,实体类必须指明主键
	 *
	 * @param bean 数据对象，必须继承了RealmObject
	 */
	@Override
	public void saveOrUpdateWithPKByRealm(final RealmObject bean) {
		realm.executeTransaction(new Realm.Transaction() {
			@Override
			public void execute(Realm realm) {
				realm.copyToRealmOrUpdate(bean);
			}
		});
	}

	/**
	 * 保存和更新多条数据，实体类必须指明主键
	 *
	 * @param beans 数据对象，必须继承了RealmObject
	 */
	@Override
	public void saveOrUpdateWithPKByRealm(final List<? extends RealmObject> beans) {
		realm.executeTransaction(new Realm.Transaction() {
			@Override
			public void execute(Realm realm) {
				realm.copyToRealmOrUpdate(beans);
			}
		});
	}

	/**
	 * 保存和更新一个数据,实体类不指名主键
	 *
	 * @param bean 数据对象，必须继承了RealmObject
	 */
	@Override
	public void saveWithoutPKByRealm(final RealmObject bean) {
		realm.executeTransaction(new Realm.Transaction() {
			@Override
			public void execute(Realm realm) {
				realm.copyToRealm(bean);
			}
		});
	}

	/**
	 * 保存和更新多条数据，实体类不指名主键
	 *
	 * @param beans 数据对象，必须继承了RealmObject
	 */
	@Override
	public void saveWithoutPKByRealm(final List<? extends RealmObject> beans) {
		realm.executeTransaction(new Realm.Transaction() {
			@Override
			public void execute(Realm realm) {
				realm.copyToRealm(beans);
			}
		});
	}

	/**
	 * 查询第一条数据
	 *
	 * @param clazz
	 * @return
	 */
	@Override
	public RealmObject queryFirstByRealm(Class<? extends RealmObject> clazz) {
		RealmObject bean = realm.where(clazz).findFirst();
		return bean;
	}

	/**
	 * 查询满足条件的第一个数据
	 *
	 * @param clazz
	 * @param fieldName
	 * @param value
	 * @return
	 */
	@Override
	public RealmObject queryAllWithFieldByRealm(Class<? extends RealmObject> clazz, String fieldName, String value) {
		RealmObject bean = realm.where(clazz).equalTo(fieldName, value).findFirst();
		return bean;
	}

	/**
	 * 查询所有数据
	 *
	 * @param clazz
	 * @return
	 */
	@Override
	public List<? extends RealmObject> queryAllByRealm(Class<? extends RealmObject> clazz) {
		final RealmResults<? extends RealmObject> beans = realm.where(clazz).findAll();
		List<? extends RealmObject> lists = realm.copyFromRealm(beans);
		return lists;
	}

	/**
	 * 查询数据，排序
	 *
	 * @param clazz
	 * @param fieldName
	 * @param isAscendOrDescend true 增序， false 降序
	 * @return
	 */
	@Override
	public List<? extends RealmObject> queryAllWithSortByRealm(Class<? extends RealmObject> clazz, String fieldName, Boolean isAscendOrDescend) {
		Sort sort = isAscendOrDescend ? Sort.ASCENDING : Sort.DESCENDING;
		RealmResults<? extends RealmObject> beans = realm.where(clazz).findAll();
		RealmResults<? extends RealmObject> results = beans.sort(fieldName, sort);
		return realm.copyFromRealm(results);
	}

	/**
	 * 根据 主键 获取 对象，修改对象中的某个属性
	 *
	 * @param clazz
	 * @param primaryKeyName
	 * @param primaryKeyValue
	 * @param fieldName
	 * @param newValue
	 */
	@Override
	public void updateParamWithPKByRealm(Class<? extends RealmObject> clazz, String primaryKeyName, Object primaryKeyValue, String fieldName, Object newValue) {
		RealmObject realmObject = null;
		if (primaryKeyValue instanceof Integer) {
			int primaryIntKeyValue = (int) primaryKeyValue;
			realmObject = realm.where(clazz).equalTo(primaryKeyName, primaryIntKeyValue).findFirst();
		} else if (primaryKeyValue instanceof String) {
			String primaryStringKeyValue = (String) primaryKeyValue;
			realmObject = realm.where(clazz).equalTo(primaryKeyName, primaryStringKeyValue).findFirst();
		}
		realm.beginTransaction();
		try {
			if (newValue instanceof Integer) {
				Method method = null;

				method = clazz.getMethod(fieldName, int.class);

				method.invoke(realmObject, newValue);
			} else {
				Method method = clazz.getMethod(fieldName, newValue.getClass());
				method.invoke(realmObject, newValue);
			}
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		realm.commitTransaction();
	}

	/**
	 * 删除数据库中clazz类第一个元素
	 *
	 * @param clazz
	 */
	@Override
	public void deleteFirstByRealm(Class<? extends RealmObject> clazz) {
		final RealmResults<? extends RealmObject> beans = realm.where(clazz).findAll();
		realm.executeTransaction(new Realm.Transaction() {
			@Override
			public void execute(Realm realm) {
				beans.deleteFirstFromRealm();

			}
		});
	}

	/**
	 * 删除数据库中clazz类所属所有元素
	 *
	 * @param clazz
	 */
	@Override
	public void deleteAllByRealm(Class<? extends RealmObject> clazz) {
		final RealmResults<? extends RealmObject> beans = realm.where(clazz).findAll();
		realm.executeTransaction(new Realm.Transaction() {
			@Override
			public void execute(Realm realm) {
				beans.deleteAllFromRealm();
			}
		});
	}
}
