package com.youngmanster.collection.common;

import android.app.Application;
import android.content.Context;

import com.youngmanster.collection.BuildConfig;
import com.youngmanster.collection.been.Result;
import com.youngmanster.collectionlibrary.config.Config;
import com.youngmanster.collectionlibrary.network.RequestManager;
import com.youngmanster.collectionlibrary.utils.LogUtils;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;
import io.realm.RealmSchema;

/**
 * Created by yangyan
 * on 2018/3/21.
 */

public class AppApplication extends Application{

	@Override
	public void onCreate() {
		super.onCreate();
		config();
	}

	@Override
	protected void attachBaseContext(Context base) {
		super.attachBaseContext(base);
//		MultiDex.install(this);
	}

	private void config(){
		//基本配置
		Config.DEBUG= BuildConfig.DEBUG;
		Config.CONTEXT=this;
		//Retrofit配置
		Config.URL_CACHE=AppConfig.URL_CACHE;
		Config.MClASS= Result.class;
		Config.URL_DOMAIN="http://api.tianapi.com/";
		//SharePreference配置
		Config.USER_CONFIG="Collection_User";
		Config.realmVersion=0;
		Config.realmName="realm.realm";

	}
}
