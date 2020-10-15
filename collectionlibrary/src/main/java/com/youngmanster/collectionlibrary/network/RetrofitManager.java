package com.youngmanster.collectionlibrary.network;

import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;

import com.youngmanster.collectionlibrary.config.Config;
import com.youngmanster.collectionlibrary.network.download.DownloadProgressBody;
import com.youngmanster.collectionlibrary.network.interceptor.BasicParamsInterceptor;
import com.youngmanster.collectionlibrary.network.progress.UploadProgressBody;
import com.youngmanster.collectionlibrary.utils.LogUtils;
import com.youngmanster.collectionlibrary.utils.NetworkUtils;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by yangyan
 * on 2018/3/17.
 */

public class RetrofitManager {

    private static Retrofit getRetrofit(RequestBuilder requestBuilder) {

        return  new Retrofit.Builder().baseUrl(Config.URL_DOMAIN)
                .client(getOkHttpClient(requestBuilder))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }


    public static OkHttpClient getOkHttpClient(final RequestBuilder requestBuilder) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //如果不是在正式包，添加拦截 打印响应json
        if (Config.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(@NotNull String message) {
                    Log.d("RetrofitManager", "收到响应: " + message);
                }
            });

            if (requestBuilder.getHttpType() == RequestBuilder.HttpType.MULTIPLE_MULTIPART_POST ||
                    requestBuilder.getHttpType() == RequestBuilder.HttpType.DOWNLOAD_FILE_GET
            ) {
                logging.level(HttpLoggingInterceptor.Level.HEADERS);
            } else {
                logging.level(HttpLoggingInterceptor.Level.BODY);
            }

            builder.addInterceptor(logging);

        }

        if (requestBuilder.getHttpType() == RequestBuilder.HttpType.DOWNLOAD_FILE_GET) {
            builder.addInterceptor(new Interceptor() {
                @NotNull
                @Override
                public Response intercept(@NotNull Chain chain) throws IOException {
                    Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder().body(new DownloadProgressBody(originalResponse.body(),
                            requestBuilder,
                            originalResponse.code()))
                            .build();
                }
            });

        } else {
            if (Config.HEADERS != null && Config.HEADERS.size() > 0) {
                BasicParamsInterceptor basicParamsInterceptor = new BasicParamsInterceptor.Builder()
                        .addHeaderParamsMap(Config.HEADERS)
                        .build();

                builder.addInterceptor(basicParamsInterceptor);
            }

            if (isCache(requestBuilder.getReqType()) && !TextUtils.isEmpty(Config.URL_CACHE) && Config.CONTEXT != null) {
                //设置缓存
                File httpCacheDirectory = new File(Config.URL_CACHE);
                builder.cache(new Cache(httpCacheDirectory, Config.MAX_MEMORY_SIZE));
                builder.addInterceptor(getInterceptor());


            }
            if (requestBuilder.getHttpType() == RequestBuilder.HttpType.MULTIPLE_MULTIPART_POST) {
                builder.addInterceptor(new Interceptor() {
                    @NotNull
                    @Override
                    public Response intercept(@NotNull Chain chain) throws IOException {
                        Request original = chain.request();
                        Request request = original.newBuilder()
                                .method(
                                        original.method(),
                                        new UploadProgressBody(original.body(), requestBuilder)
                                )
                                .build();
                        return chain.proceed(request);
                    }
                });
            }

        }


        return builder.connectTimeout(Config.CONNECT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .readTimeout(Config.READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .writeTimeout(Config.WRITE_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .build();

    }

    //是否需要设置Retrofit缓存
    private static boolean isCache(int type) {
        if (type == RequestBuilder.ReqType.DEFAULT_CACHE_LIST ||
                type == RequestBuilder.ReqType.DEFAULT_CACHE_MODEL
        ) {
            return true;
        }
        return false;
    }


    /**
     * ===============================Retrofit+OkHttp的缓存机制=========================================
     */
    private static Interceptor getInterceptor() {
        return new Interceptor() {

            @NotNull
            @Override
            public Response intercept(@NotNull Chain chain) throws IOException {

                CacheControl.Builder cacheBuilder = new CacheControl.Builder();
                cacheBuilder.maxAge(0, TimeUnit.SECONDS);
                cacheBuilder.maxStale(365, TimeUnit.DAYS);
                CacheControl cacheControl = cacheBuilder.build();
                Request request = chain.request();
                if (!NetworkUtils.isNetworkConnected(Config.CONTEXT)) {
                    request = request.newBuilder()
                            .cacheControl(cacheControl)
                            .build();
                }
                Response originalResponse = chain.proceed(request);
                if (NetworkUtils.isNetworkConnected(Config.CONTEXT)) {
                    int maxAge = 0;// read from cache
                    return originalResponse.newBuilder()
                            .removeHeader("Pragma")
                            .header("Cache-Control", "public ,max-age=" + maxAge)
                            .build();
                } else {
                    long maxStale =
                            Config.MAX_CACHE_SECONDS; // tolerate 4-weeks stale
                    return originalResponse.newBuilder()
                            .removeHeader("Pragma")
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .build();
                }
            }
        };
    }


    /**
     * 接口定义类
     *
     * @param tClass
     * @param requestBuilder
     * @return
     */
    public static <T> T getApiService(Class<T> tClass, RequestBuilder requestBuilder) {
        return getRetrofit(requestBuilder).create(tClass);
    }


    /**
     * 自定义接口请求
     *
     * @return
     */
    private static class SingletonHolder{
        private static final CustomizeRequest INSTANCE = new CustomizeRequest();
    }

    public static CustomizeRequest getCustomizeRequest() {
        return SingletonHolder.INSTANCE;
    }


    private static class CustomizeRequest {
        private OkHttpClient okHttpClient = null;

        private CustomizeRequest(){ }
        /**
         * 自定义
         */
        CustomizeRequest setCustomizeOkHttpClient(OkHttpClient okHttpClient) {
            this.okHttpClient = okHttpClient;
            return this;
        }


        public <T> T getCustomizeApiService(Class<T> tClass) {
            return getRetrofit().create(tClass);
        }


        /**
         * 自定义默认Retrofit
         */
        private Retrofit getRetrofit() {
            if (okHttpClient == null) {
                okHttpClient = getDefaultOkHttpClient();
            }
            return new Retrofit.Builder().baseUrl(Config.URL_DOMAIN)
                    .client(okHttpClient)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }


        /**
         * 自定义默认OkHttpClient
         */
        private OkHttpClient getDefaultOkHttpClient() {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            //如果不是在正式包，添加拦截 打印响应json
            if (Config.DEBUG) {

                HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(@NotNull String message) {
                        Log.d("RetrofitManager", "收到响应: " + message);
                    }
                });

                logging.level(HttpLoggingInterceptor.Level.BODY);
                builder.addInterceptor(logging);

            }

            if (Config.HEADERS != null && Config.HEADERS.size() > 0) {
                BasicParamsInterceptor basicParamsInterceptor = new BasicParamsInterceptor.Builder()
                        .addHeaderParamsMap(Config.HEADERS)
                        .build();

                builder.addInterceptor(basicParamsInterceptor);
            }

            if (!TextUtils.isEmpty(Config.URL_CACHE) && Config.CONTEXT != null) {
                //设置缓存
                File httpCacheDirectory = new File(Config.URL_CACHE);
                builder.cache(new Cache(httpCacheDirectory, Config.MAX_MEMORY_SIZE));
                builder.addInterceptor(getInterceptor());
            }

            return builder.connectTimeout(
                    Config.CONNECT_TIMEOUT_SECONDS,
                    TimeUnit.SECONDS
            )
                    .readTimeout(Config.READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                    .writeTimeout(Config.WRITE_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                    .build();
        }

    }
}
