package com.youngmanster.collectionlibrary.network.request;


import com.youngmanster.collectionlibrary.network.RequestBuilder;

import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;
import okhttp3.ResponseBody;

/**
 * Created by yangy
 * 2020/9/17M
 * Describe:
 */
interface RequestMethod {
    /**
     * 下载文件
     * @param builder
     * @return
     */
    <T> DisposableObserver<ResponseBody> downloadFile(
            RequestBuilder<T> builder
    );

    /**
     * 设置缓存时间，没超过设置的时间不请求网络，只返回缓存数据
     * @param builder
     * @param observable
     * @return list
     */
    <T> DisposableObserver<ResponseBody> loadFormDiskResultListLimitTime(
            RequestBuilder<T> builder,
            Observable<ResponseBody> observable
    );


    /**
     * 设置缓存时间，没超过设置的时间不请求网络，只返回缓存数据
     * @param builder
     * @param observable
     * @return mode
     */

    <T> DisposableObserver<ResponseBody> loadFormDiskModeLimitTime(
            RequestBuilder<T> builder,
            Observable<ResponseBody> observable
    );


    /**
     * 没有网络再请求缓存
     * @param builder
     * @param observable
     * @return list
     */

    <T> DisposableObserver<ResponseBody> loadNoNetWorkWithCacheResultList(
            RequestBuilder<T> builder,
            Observable<ResponseBody> observable
    );


    /**
     * 没有网络再请求缓存
     * @param builder
     * @param observable
     * @return model
     */

    <T> DisposableObserver<ResponseBody> loadNoNetWorkWithCacheModel(
            RequestBuilder<T> builder,
            Observable<ResponseBody> observable
    );
    /**
     * 把结果保存到本地，根据标志是否返回数据，如果本地存在则不需要下载
     * @param builder
     * @param observable
     * @return list
     */

    <T> DisposableObserver<ResponseBody> loadOnlyNetWorkSaveList(
            RequestBuilder<T> builder,
            Observable<ResponseBody> observable
    );


    /**
     * 把结果保存到本地，根据标志是否返回数据，如果本地存在则不需要下载
     * @param builder
     * @param observable
     * @return model
     */

    <T> DisposableObserver<ResponseBody> loadOnlyNetWorkSaveModel(
            RequestBuilder<T> builder,
            Observable<ResponseBody> observable
    );



    /**
     * 把只通过网络返回数据，返回list
     * @param builder
     * @param observable
     * @return list
     */
    <T> DisposableObserver<ResponseBody> loadOnlyNetWorkList(
            RequestBuilder<T> builder,
            Observable<ResponseBody> observable
    );


    /**
     * 通过返回网络请求
     * @param builder
     * @param observable
     * @return model
     */
    <T> DisposableObserver<ResponseBody> loadOnlyNetWorkModel(
            RequestBuilder<T> builder,
            Observable<ResponseBody> observable
    );
}
