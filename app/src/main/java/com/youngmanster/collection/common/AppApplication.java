package com.youngmanster.collection.common;

import android.app.Application;
import android.content.Context;

import com.youngmanster.collection.BuildConfig;
import com.youngmanster.collection.been.Result;
import com.youngmanster.collection.been.User;
import com.youngmanster.collectionlibrary.config.Config;
import com.youngmanster.collectionlibrary.data.DataManager;
import com.youngmanster.collectionlibrary.data.database.SQLiteVersionMigrate;
import com.youngmanster.collectionlibrary.refreshrecyclerview.LoadingTextConfig;
import com.youngmanster.collectionlibrary.refreshrecyclerview.pulltorefresh.PullToRefreshRecyclerViewUtils;
import com.youngmanster.collectionlibrary.utils.LogUtils;

/**
 * Created by yangyan
 * on 2018/3/21.
 */

public class AppApplication extends Application{


	@Override
	public void onCreate() {
		super.onCreate();
		config();

		SQLiteVersionMigrate sqLiteVersionMigrate=new SQLiteVersionMigrate();
		sqLiteVersionMigrate.setMigrateListener(new SQLiteVersionMigrate.MigrateListener() {
			@Override
			public void onMigrate(int oldVersion, int newVersion) {
				for (int i=oldVersion;i<=newVersion;i++){
					if(i==2){

					}

				}
			}
		});
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
		Config.URL_DOMAIN="https://api.apiopen.top/";
		//SharePreference配置
		Config.USER_CONFIG="Collection_User";
		Config.SQLITE_DB_VERSION=0;
	}
}
