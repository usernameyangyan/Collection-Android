package com.youngmanster.collectionlibrary.network;

import android.text.TextUtils;

import com.youngmanster.collectionlibrary.config.Config;
import com.youngmanster.collectionlibrary.network.interceptor.BasicParamsInterceptor;
import com.youngmanster.collectionlibrary.utils.LogUtils;

import java.io.File;
import java.util.concurrent.TimeUnit;

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
    private static Retrofit mWithoutHeadersRetrofit;
    private static Retrofit mNoCacheRetrofit;
    private static Retrofit mNoCacheRetrofitWithoutHeaders;

    /**************************************获取OKHttp******************************************/

    public static OkHttpClient getOkHttpClient(boolean isCache, boolean isHeaders){
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

        if(isHeaders&& Config.HEADERS!=null&& Config.HEADERS.size()>0){
            BasicParamsInterceptor basicParamsInterceptor=new BasicParamsInterceptor.Builder()
                    .addHeaderParamsMap(Config.HEADERS)
                    .build();

            builder.addInterceptor(basicParamsInterceptor);
        }

        if (isCache&&!TextUtils.isEmpty(Config.URL_CACHE) && Config.CONTEXT != null) {
            //设置缓存
            File httpCacheDirectory = new File(Config.URL_CACHE);
            builder.cache(new Cache(httpCacheDirectory, Config.MAX_MEMORY_SIZE));
            builder.addInterceptor(RequestManager.getInterceptor());
        }

        OkHttpClient okHttpClient = builder.
                connectTimeout(Config.CONNECT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .readTimeout(Config.READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .writeTimeout(Config.WRITE_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .build();

        return okHttpClient;

    }


    /***************************************************************设置缓存**************************************************************/
    private static Retrofit getRetrofit() {

        if (mRetrofit == null) {

            mRetrofit = new Retrofit.Builder()
                    .baseUrl(Config.URL_DOMAIN)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(getOkHttpClient(true,true))
                    .build();
        }

        return mRetrofit;
    }



    private static Retrofit getRetrofitWithoutHeader() {

        if (mWithoutHeadersRetrofit == null) {

            mWithoutHeadersRetrofit = new Retrofit.Builder()
                    .baseUrl(Config.URL_DOMAIN)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(getOkHttpClient(true,false))
                    .build();
        }

        return mWithoutHeadersRetrofit;
    }


    /***************************************************************没有设置缓存**************************************************************/
    private static Retrofit getNoCacheRetrofit() {

        if (mNoCacheRetrofit == null) {
            mNoCacheRetrofit = new Retrofit.Builder()
                    .baseUrl(Config.URL_DOMAIN)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(getOkHttpClient(false,true))
                    .build();
        }

        return mNoCacheRetrofit;
    }



    private static Retrofit getNoCacheRetrofitWithoutHeaders() {

        if (mNoCacheRetrofitWithoutHeaders == null) {
            mNoCacheRetrofitWithoutHeaders = new Retrofit.Builder()
                    .baseUrl(Config.URL_DOMAIN)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(getOkHttpClient(false,false))
                    .build();
        }

        return mNoCacheRetrofitWithoutHeaders;
    }



    /**
     * 接口定义类,获取到可以缓存的retrofit
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> T getApiService(Class<T> tClass) {
        return getRetrofit().create(tClass);
    }

    /**
     * 接口定义类,获取到可以缓存的retrofit 没有设置全局的Headers
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> T getWithoutHeaderApiService(Class<T> tClass) {
        return getRetrofitWithoutHeader().create(tClass);
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

    /**
     * 接口定义类,获取到没有缓存的retrofit  没有设置全局的Headers
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> T getNoCacheAndWithoutHeadersApiService(Class<T> tClass) {
        return getNoCacheRetrofitWithoutHeaders().create(tClass);
    }
}
