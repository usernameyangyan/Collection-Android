package com.youngmanster.collectionlibrary.network;

import android.text.TextUtils;

import com.youngmanster.collectionlibrary.config.Config;
import com.youngmanster.collectionlibrary.utils.LogUtils;

import java.io.File;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by yangyan
 * on 2018/3/17.
 */

public class RetrofitManager {

    private static Retrofit mRetrofit;
    private static Retrofit mNoCacheRetrofit;

    private static Retrofit getRetrofit() {

        if (mRetrofit == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            //如果不是在正式包，添加拦截 打印响应json
            if (Config.DEBUG) {
                HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(String message) {
                        LogUtils.info("RetrofitManager", "收到响应: " + message);
                    }
                });

                logging.setLevel(HttpLoggingInterceptor.Level.BODY);
                builder.addInterceptor(logging);
            }

            if (!TextUtils.isEmpty(Config.URL_CACHE) && Config.CONTEXT != null) {
                //设置缓存
                File httpCacheDirectory = new File(Config.URL_CACHE);
                builder.cache(new Cache(httpCacheDirectory, Config.MAX_MEMORY_SIZE));
                builder.addInterceptor(RequestManager.getInterceptor());
            }
            OkHttpClient okHttpClient = builder.build();
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(Config.URL_DOMAIN)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(okHttpClient)
                    .build();
        }

        return mRetrofit;
    }

    private static Retrofit getNoCacheRetrofit() {

        if (mNoCacheRetrofit == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            //如果不是在正式包，添加拦截 打印响应json
            if (Config.DEBUG) {
                HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(String message) {
                        LogUtils.info("RetrofitManager", "收到响应: " + message);
                    }
                });

                logging.setLevel(HttpLoggingInterceptor.Level.BODY);
                builder.addInterceptor(logging);
            }

            OkHttpClient okHttpClient = builder.build();
            mNoCacheRetrofit = new Retrofit.Builder()
                    .baseUrl(Config.URL_DOMAIN)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(okHttpClient)
                    .build();
        }

        return mNoCacheRetrofit;
    }

    /**
     * 接口定义类,获取到可以缓存的retrofit
     *
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> T getApiService(Class<T> tClass) {
        return getRetrofit().create(tClass);
    }

    /**
     * 接口定义类,获取到没有缓存的retrofit
     *
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> T getNoCacheApiService(Class<T> tClass) {
        return getNoCacheRetrofit().create(tClass);
    }
}
