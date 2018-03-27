package com.youngmanster.collectionlibrary.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;

import java.util.Locale;

/**
 * 网络工具类
 * Created by zhengjinbin on 2016/5/17.
 */
public class NetworkUtils {
	public static final int NET_TYPE_NOT = 0x00;
	public static final int NET_TYPE_WIFI = 0x01;
	public static final int NET_TYPE_CM_WAP = 0x02;
	public static final int NET_TYPE_CM_NET = 0x03;

	public static boolean isNetworkConnected(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
			if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 获取当前网络类型
	 *
	 * @return 0：没有网络 1：WIFI网络 2：WAP网络 3：NET网络
	 */
	public static int getNetworkType(Context context) {
		int netType = NetworkUtils.NET_TYPE_NOT;
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		if (networkInfo == null) {
			return netType;
		}
		int networkInfoType = networkInfo.getType();
		if (networkInfoType == ConnectivityManager.TYPE_MOBILE) {
			String extraInfo = networkInfo.getExtraInfo();
			if (!TextUtils.isEmpty(extraInfo)) {
				if (extraInfo.toLowerCase(Locale.getDefault()).equals("cmnet")) {
					netType = NetworkUtils.NET_TYPE_CM_NET;
				} else {
					netType = NetworkUtils.NET_TYPE_CM_WAP;
				}
			}
		} else if (networkInfoType == ConnectivityManager.TYPE_WIFI) {
			netType = NetworkUtils.NET_TYPE_WIFI;
		}
		return netType;
	}
}
