package com.youngmanster.collectionlibrary.data;

import android.content.Context;

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

     static SharePreference getInstance() {
        if (sharePreference == null) {
            synchronized (SharePreference.class) {
                if (sharePreference == null) {
                    sharePreference = new SharePreference();
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
        Config.CONTEXT.getSharedPreferences(Config.USER_CONFIG, Context.MODE_PRIVATE)
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
        Config.CONTEXT.getSharedPreferences(Config.USER_CONFIG, Context.MODE_PRIVATE).edit()
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
        Config.CONTEXT.getSharedPreferences(Config.USER_CONFIG, Context.MODE_PRIVATE).edit()
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
        Config.CONTEXT.getSharedPreferences(Config.USER_CONFIG, Context.MODE_PRIVATE).edit()
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
        Config.CONTEXT.getSharedPreferences(Config.USER_CONFIG, Context.MODE_PRIVATE).edit()
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
        return Config.CONTEXT.getSharedPreferences(Config.USER_CONFIG, Context.MODE_PRIVATE)
                .getString(key, defaultValue);
    }


    int getInt(String key, int defaultValue) {
        return Config.CONTEXT.getSharedPreferences(Config.USER_CONFIG, Context.MODE_PRIVATE)
                .getInt(key, defaultValue);
    }

    Long getLong(String key, Long defaultValue) {
        return Config.CONTEXT.getSharedPreferences(Config.USER_CONFIG, Context.MODE_PRIVATE)
                .getLong(key, defaultValue);
    }

    boolean getBoolean(String key, boolean defaultValue) {
        return Config.CONTEXT.getSharedPreferences(Config.USER_CONFIG, Context.MODE_PRIVATE)
                .getBoolean(key, defaultValue);
    }

    float getFloat(String key, Float defaultValue) {
        return Config.CONTEXT.getSharedPreferences(Config.USER_CONFIG, Context.MODE_PRIVATE)
                .getFloat(key, defaultValue);
    }

}
