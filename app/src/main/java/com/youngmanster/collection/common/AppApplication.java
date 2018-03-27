package com.youngmanster.collection.common;

import android.app.Application;
import android.content.Context;

import com.youngmanster.collection.BuildConfig;
import com.youngmanster.collection.been.Result;
import com.youngmanster.collectionlibrary.config.Config;
import com.youngmanster.collectionlibrary.network.RequestManager;

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
		Config.DEBUG= BuildConfig.DEBUG;
		Config.URL_CACHE=AppConfig.URL_CACHE;
		Config.CONTEXT=this;
		Config.MClASS= Result.class;
		Config.URL_DOMAIN="http://api.tianapi.com/";
	}
}
