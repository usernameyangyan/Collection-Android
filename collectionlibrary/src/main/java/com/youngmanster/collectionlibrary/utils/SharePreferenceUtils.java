package com.youngmanster.collectionlibrary.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.youngmanster.collectionlibrary.config.Config;
import com.youngmanster.collectionlibrary.db.impl.DataManagerImpl;

/**
 * SharePreference的使用
 *
 * @author yangyan
 */
public class SharePreferenceUtils extends DataManagerImpl{


	private static SharePreferenceUtils sharePreferenceUtils;
	private static Context context;

	private SharePreferenceUtils(){}

	public static SharePreferenceUtils getInstance(){
		if(sharePreferenceUtils==null){
			synchronized (SharePreferenceUtils.class){
				if(sharePreferenceUtils==null){
					sharePreferenceUtils=new SharePreferenceUtils();
					context=Config.CONTEXT;
				}
			}
		}
		return sharePreferenceUtils;
	}


	/**
	 * 用户自定义配置的文件名
	 */
	private static final String USER_CONFIG = Config.USER_CONFIG;

	/**
	 * 保存String到默认sp（USER_CONFIG）
	 *
	 * @param context
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean putStringByDefaultSP(Context context, String key, String value) {
		return putString(context, SharePreferenceUtils.USER_CONFIG, key, value);
	}

	/**
	 * 保存String到sp
	 *
	 * @param context
	 * @param name
	 * @param content
	 * @return
	 */
	public boolean putString(Context context, String name, String content) {
		return putString(context, name, name, content);
	}

	/**
	 * 保存String到sp
	 *
	 * @param context
	 * @param name    sp的名称
	 * @param key     存的字段名称
	 * @param content 内容
	 * @return
	 */
	public boolean putString(Context context, String name, String key, String content) {
		if (content != null && name != null && context != null) {
			return context.getSharedPreferences(name, Context.MODE_PRIVATE)
					.edit()
					.putString(key, content)
					.commit();
		}
		return false;
	}

	/**
	 * 保存int到sp，sp name使用USER_CONFING
	 *
	 * @param context
	 * @param key     存的字段名称
	 * @param content 内容
	 * @return
	 */
	public boolean putInt(Context context, String key, int content) {
		return putInt(context, USER_CONFIG, key, content);
	}

	/**
	 * 保存int到sp
	 *
	 * @param context
	 * @param name    sp的名称
	 * @param key     存的字段名称
	 * @param content 内容
	 * @return
	 */
	public boolean putInt(Context context, String name, String key, int content) {
		if (name != null && context != null) {
			return context.getSharedPreferences(name, Context.MODE_PRIVATE).edit().putInt(key, content).commit();
		}
		return false;
	}

	public boolean putLong(Context context, String spFileName, String key, long value) {
		return notNull(context, spFileName) && getSharedPreferences(context, spFileName).edit().putLong(key, value).commit();
	}

	public boolean putLong(Context context, String key, long value) {
		return putLong(context, USER_CONFIG, key, value);
	}

	/**
	 * 保存boolean值
	 *
	 * @param context
	 * @param name
	 * @param key
	 * @param content
	 * @return
	 */
	public boolean putBoolean(Context context, String name, String key, boolean content) {
		if (context != null) {
			return context.getSharedPreferences(name, Context.MODE_PRIVATE)
					.edit()
					.putBoolean(key, content)
					.commit();
		}
		return false;
	}

	public boolean putBooleanByDefaultSP(Context context, String key, boolean value) {
		return putBoolean(context, USER_CONFIG, key, value);
	}

	/**
	 * 获取boolean值
	 *
	 * @param context
	 * @param name
	 * @return
	 */
	public boolean getBoolean(Context context, String name, String key) {
		if (context != null && name != null) {
			return context.getSharedPreferences(name, Context.MODE_PRIVATE).getBoolean(key, false);
		}
		return false;
	}

	public boolean getBooleanByDefaultSP(Context context, String key, boolean defaultValue) {
		boolean result = defaultValue;
		if (context != null && !TextUtils.isEmpty(key)) {
			result = context.getSharedPreferences(USER_CONFIG, Context.MODE_PRIVATE).getBoolean(key, defaultValue);
		}
		return result;
	}

	/**
	 * 获取默认sp（USER_CONFIG）中的String内容
	 */
	public String getStringByDefaultSP(Context context, String key, String defaultValue) {
		return getString(context, USER_CONFIG, key, defaultValue);
	}

	/**
	 * 获取SP中的String内容
	 *
	 * @param context
	 * @param name
	 * @param key
	 * @return 失败返回null
	 */
	public String getString(Context context, String name, String key) {
		if (context != null && name != null) {
			return context.getSharedPreferences(name, Context.MODE_PRIVATE).getString(key, null);
		}
		return null;
	}

	/**
	 * 获取SP中的String内容
	 *
	 * @return 失败返回默认值defaultValue
	 */
	public String getString(Context context, String name, String key, String defaultValue) {
		String result = defaultValue;
		if (context != null && !TextUtils.isEmpty(name) && !TextUtils.isEmpty(key)) {
			result = context.getSharedPreferences(name, Context.MODE_PRIVATE).getString(key, defaultValue);
		}
		return result;
	}

	public int getInt(Context context, String spFileName, String key, int defaultValue) {
		if (notNull(context, spFileName)) {
			return getSharedPreferences(context, spFileName).getInt(key, defaultValue);
		}
		return defaultValue;
	}

	public int getInt(Context context, String key, int defaultValue) {
		return getInt(context, USER_CONFIG, key, defaultValue);
	}

	public long getLong(Context context, String spFileName, String key, long defaultValue) {
		if (notNull(context, spFileName)) {
			return getSharedPreferences(context, spFileName).getLong(key, defaultValue);
		}
		return defaultValue;
	}

	public long getLong(Context context, String key, long defaultValue) {
		return getLong(context, USER_CONFIG, key, defaultValue);
	}

	private boolean notNull(Context context, String spFileName) {
		return !TextUtils.isEmpty(spFileName) && context != null;
}

	private SharedPreferences getSharedPreferences(Context context, String spFileName) {
		return context.getSharedPreferences(spFileName, Context.MODE_PRIVATE);
	}

	@Override
	public <T> T queryByNameAndKey(String name, String key, Class<T> clazz) {
		if (clazz == Integer.class){
			return (T)Integer.valueOf(getInt(context,name,key.toString(),0));
		}else if(clazz == String.class){
			return (T)String.valueOf(getString(context,name,key.toString(),""));
		}else if (clazz == Boolean.class){
			return (T)Boolean.valueOf(getBoolean(context,name,key.toString()));
		}else if(clazz==Long.class){
			return (T)Long.valueOf(getLong(context,name,key.toString(),0));
		}
		return null;
	}

	@Override
	public <T> T queryByKey(String key, Class<T> clazz) {
		if (clazz == Integer.class){
			return (T)Integer.valueOf(getInt(context,key.toString(),0));
		}else if(clazz == String.class){
			return (T)String.valueOf(getStringByDefaultSP(context,key.toString(),""));
		}else if (clazz == Boolean.class){
			return (T)Boolean.valueOf(getBooleanByDefaultSP(context,key.toString(),true));
		}else if(clazz==Long.class){
			return (T)Long.valueOf(getLong(context,key.toString(),0));
		}
		return null;
	}
}
