package com.youngmanster.collectionlibrary.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.youngmanster.collectionlibrary.config.Config;

/**
 * Created by yangy
 * 2020-02-27
 * Describe:
 */
public class SharePreference {

    private SharePreference() {
    }


    private static SharePreference sharePreference = null;
    private static SharedPreferences sharedPreferences;

     static SharePreference getInstance() {
        if (sharePreference == null) {
            synchronized (SharePreference.class) {
                if (sharePreference == null) {
                    sharePreference = new SharePreference();
                    sharedPreferences=Config.CONTEXT.getSharedPreferences(Config.USER_CONFIG, Context.MODE_PRIVATE);
                }
            }
        }
        return sharePreference;
    }


    /**
     * 保存String到sp
     *
     * @param key     存的字段名称
     * @param content 内容
     * @return
     */
      void putString(String key, String content) {
          sharedPreferences
                .edit()
                .putString(key, content)
                .apply();
    }

    /**
     * 保存int到sp
     *
     * @param key     存的字段名称
     * @param content 内容
     * @return
     */
      void putInt(String key, int content) {
          sharedPreferences
                  .edit()
                .putInt(key, content)
                .apply();
    }

    /**
     * 保存Long值
     *
     * @param key
     * @return
     */
      void putLong(String key, Long value) {
          sharedPreferences.edit()
                .putLong(key, value)
                .apply();
    }

    /**
     * 保存boolean值
     *
     * @param key
     * @param content
     * @return
     */
      void putBoolean(String key, Boolean content) {
          sharedPreferences.edit()
                .putBoolean(
                        key,
                        content
                ).apply();
    }


    /**
     * 保存Float值
     *
     * @param key
     * @param content
     * @return
     */
    void putFloat(String key, Float content) {
        sharedPreferences.edit()
                .putFloat(
                        key,
                        content
                ).apply();
    }

    /**
     * 获取SP中的String内容
     *
     * @param key
     * @return 失败返回null
     */
      String getString(String key, String defaultValue) {
        return sharedPreferences
                .getString(key, defaultValue);
    }


    int getInt(String key, int defaultValue) {
        return sharedPreferences
                .getInt(key, defaultValue);
    }

    Long getLong(String key, Long defaultValue) {
        return sharedPreferences
                .getLong(key, defaultValue);
    }

    boolean getBoolean(String key, boolean defaultValue) {
        return sharedPreferences
                .getBoolean(key, defaultValue);
    }

    float getFloat(String key, Float defaultValue) {
        return sharedPreferences
                .getFloat(key, defaultValue);
    }

}
