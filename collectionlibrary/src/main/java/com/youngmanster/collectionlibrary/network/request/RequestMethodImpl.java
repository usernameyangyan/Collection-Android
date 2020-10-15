package com.youngmanster.collectionlibrary.network.request;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import com.youngmanster.collectionlibrary.network.NetWorkCodeException;
import com.youngmanster.collectionlibrary.network.RequestBuilder;
import com.youngmanster.collectionlibrary.network.RequestService;
import com.youngmanster.collectionlibrary.network.RetrofitManager;
import com.youngmanster.collectionlibrary.network.convert.ConvertParamUtils;
import com.youngmanster.collectionlibrary.network.download.DownloadFileHelper;
import com.youngmanster.collectionlibrary.network.download.DownloadInfo;
import com.youngmanster.collectionlibrary.network.rx.RxSchedulers;
import com.youngmanster.collectionlibrary.network.rx.RxSubscriber;
import com.youngmanster.collectionlibrary.network.rx.utils.RxJavaUtils;
import com.youngmanster.collectionlibrary.network.rx.utils.RxUITask;
import com.youngmanster.collectionlibrary.utils.FileUtils;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * Created by yangy
 * 2020/9/17
 * Describe:
 */
public class RequestMethodImpl implements RequestMethod {

    public <T> DisposableObserver request(RequestBuilder<T> builder) {

        if (builder.getReqType() == RequestBuilder.ReqType.DOWNLOAD_FILE_MODEL) {
            return downloadFile(builder);
        }


        Observable<ResponseBody> observable = getRetrofit(builder);
        if (observable == null) {
            return null;
        }
        switch (builder.getReqType()) {
            case RequestBuilder.ReqType.NO_CACHE_MODEL:
            case RequestBuilder.ReqType.DEFAULT_CACHE_MODEL:
                return loadOnlyNetWorkModel(builder, observable);
            case RequestBuilder.ReqType.NO_CACHE_LIST:
            case RequestBuilder.ReqType.DEFAULT_CACHE_LIST:
                return loadOnlyNetWorkList(builder, observable);
            case RequestBuilder.ReqType.DISK_CACHE_LIST_LIMIT_TIME:
                return loadFormDiskResultListLimitTime(builder, observable);
            case RequestBuilder.ReqType.DISK_CACHE_MODEL_LIMIT_TIME:
                return loadFormDiskModeLimitTime(builder, observable);
            case RequestBuilder.ReqType.DISK_CACHE_NO_NETWORK_LIST:
                return loadNoNetWorkWithCacheResultList(builder, observable);
            case RequestBuilder.ReqType.DISK_CACHE_NO_NETWORK_MODEL:
                return loadNoNetWorkWithCacheModel(builder, observable);
            case RequestBuilder.ReqType.DISK_CACHE_NETWORK_SAVE_RETURN_MODEL:
                return loadOnlyNetWorkSaveModel(builder, observable);
            case RequestBuilder.ReqType.DISK_CACHE_NETWORK_SAVE_RETURN_LIST:
                return loadOnlyNetWorkSaveList(builder, observable);
        }

        return null;
    }


    private <T> Observable<ResponseBody> getRetrofit(RequestBuilder<T> builder) {

        if (builder.getHttpType() == RequestBuilder.HttpType.DEFAULT_GET) {
            if (builder.getHeaders() != null && builder.getHeaders().size() > 0) {
                return RetrofitManager.getApiService(RequestService.class, builder)
                        .getObservableWithQueryMapWithHeaders(builder.getUrl(), builder.getRequestParam(), builder.getHeaders());
            } else {
                return RetrofitManager.getApiService(RequestService.class, builder)
                        .getObservableWithQueryMap(builder.getUrl(), builder.getRequestParam());
            }
        } else if (builder.getHttpType() == RequestBuilder.HttpType.DEFAULT_POST) {
            if (builder.getHeaders() != null && builder.getHeaders().size() > 0) {
                return RetrofitManager.getApiService(RequestService.class, builder)
                        .getObservableWithQueryMapByPostWithHeaders(builder.getUrl(), builder.getRequestParam(), builder.getHeaders());
            } else {
                return RetrofitManager.getApiService(RequestService.class, builder)
                        .getObservableWithQueryMapByPost(builder.getUrl(), builder.getRequestParam());
            }
        } else if (builder.getHttpType() == RequestBuilder.HttpType.MULTIPLE_MULTIPART_POST) {
            if (builder.getHeaders() != null && builder.getHeaders().size() > 0) {
                return RetrofitManager.getApiService(RequestService.class, builder)
                        .uploadFileWithHeaders(builder.getUrl(), builder.getRequestParam(), builder.getParts(), builder.getHeaders());
            } else {
                return RetrofitManager.getApiService(RequestService.class, builder)
                        .uploadFile(builder.getUrl(), builder.getRequestParam(), builder.getParts());
            }
        } else if (builder.getHttpType() == RequestBuilder.HttpType.JSON_PARAM_POST) {
            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"), ConvertParamUtils.convertParamToJson(builder));
            if (builder.getHeaders() != null && builder.getHeaders().size() > 0) {
                return RetrofitManager.getApiService(RequestService.class, builder)
                        .getObservableWithQueryJsonParamWithHeaders(builder.getUrl(), body, builder.getHeaders());
            } else {
                return RetrofitManager.getApiService(RequestService.class, builder)
                        .getObservableWithQueryJsonParam(builder.getUrl(), body);
            }
        }
        return null;

    }

    /**
     * 断点下载文件
     *
     * @param builder
     * @return
     */
    @Override
    public <T> DisposableObserver downloadFile(final RequestBuilder<T> builder) {
        return Observable.just(builder.getUrl())
                .flatMap(new Function<String, ObservableSource<DownloadInfo>>() {
                    @Override
                    public ObservableSource<DownloadInfo> apply(String s) {
                        return Observable.just(DownloadFileHelper.createDownInfo(builder.getUrl()));
                    }
                }).map(new Function<DownloadInfo, DownloadInfo>() {
                    @Override
                    public DownloadInfo apply(DownloadInfo downloadInfo) {
                        return DownloadFileHelper.getRealFileName(downloadInfo,
                                builder.getSaveDownloadFilePath(),
                                builder.getSaveDownloadFileName(),
                                builder.isOpenBreakpointDownloadOrUpload());

                    }
                }).flatMap(new Function<DownloadInfo, Observable<ResponseBody>>() {
                    @Override
                    public Observable<ResponseBody> apply(DownloadInfo downInfo) {
                        return RetrofitManager.getApiService(RequestService.class, builder).downloadFile("bytes=" +
                                        downInfo.getProgress() + "-",
                                downInfo.getUrl());
                    }
                }).map(new Function<ResponseBody, DownloadInfo>() {
                    @Override
                    public DownloadInfo apply(ResponseBody responseBody) {
                        return DownloadFileHelper
                                .writeCache(responseBody, builder.getUrl(), builder.getSaveDownloadFilePath()
                                        , builder.getSaveDownloadFileName());
                    }
                }).compose(RxSchedulers.<DownloadInfo>io_main())
                .subscribeWith(new RxSubscriber<DownloadInfo>() {
                    @SuppressLint("StaticFieldLeak")
                    @Override
                    public void _onNext(DownloadInfo t) {

                        builder.getRxObservableListener().onNext((T) t);
                    }

                    @Override
                    public void _onError(NetWorkCodeException.ResponseThrowable e) {
                        builder.getRxObservableListener().onError(e);
                    }

                    @Override
                    public void _onComplete() {

                    }
                });
    }

    /**
     * 设置缓存时间，没超过设置的时间不请求网络，只返回缓存数据
     *
     * @param builder
     * @param observable
     * @return list
     */
    @SuppressLint("CheckResult")
    @Override
    public <T> DisposableObserver loadFormDiskResultListLimitTime(final RequestBuilder<T> builder, Observable<ResponseBody> observable) {

        if (!FileUtils.isCacheDataFailure(builder.getFilePath() + "/" + builder.getFileName(), builder.getLimitHours())) {

            Observable.create(new ObservableOnSubscribe<T>() {
                @Override
                public void subscribe(ObservableEmitter<T> emitter) {
                    String json = FileUtils.ReadTxtFile(builder.getFilePath() + "/" + builder.getFileName());
                    emitter.onNext(ConvertParamUtils.json2ArrayResult(builder, json));//返回数据不继续执行
                }
            }).compose(RxSchedulers.<T>io_main()).subscribe(new Consumer<T>() {
                @Override
                public void accept(T t) {
                    builder.getRxObservableListener().onNext(t);
                }
            });

            return null;
        } else {
            return observable.map(new Function<ResponseBody, T>() {
                /**
                 * Apply some calculation to the input value and return some other value.
                 *
                 * @param responseBody the input value
                 * @return the output value
                 * @throws Exception on error
                 */
                @Override
                public T apply(ResponseBody responseBody) throws IOException {
                    String s = responseBody.string();
                    FileUtils.WriterTxtFile(builder.getFilePath(), builder.getFileName(), s, false);
                    return ConvertParamUtils.json2ArrayResult(builder, s);
                }
            })
                    .compose(RxSchedulers.<T>io_main())
                    .subscribeWith(new RxSubscriber<T>() {
                        @Override
                        public void _onNext(final T t) {
                            builder.getRxObservableListener().onNext(t);
                        }

                        @Override
                        public void _onError(NetWorkCodeException.ResponseThrowable e) {
                            builder.getRxObservableListener().onError(e);
                        }

                        @Override
                        public void _onComplete() {
                        }
                    });
        }
    }

    /**
     * 设置缓存时间，没超过设置的时间不请求网络，只返回缓存数据
     *
     * @param builder
     * @param observable
     * @return mode
     */
    @SuppressLint("CheckResult")
    @Override
    public <T> DisposableObserver loadFormDiskModeLimitTime(final RequestBuilder<T> builder, Observable<ResponseBody> observable) {
        if (!FileUtils.isCacheDataFailure(builder.getFilePath() + "/" + builder.getFileName(), builder.getLimitHours())) {

            Observable.create(new ObservableOnSubscribe<T>() {
                @Override
                public void subscribe(ObservableEmitter<T> emitter) {
                    String json = FileUtils.ReadTxtFile(builder.getFilePath() + "/" + builder.getFileName());
                    emitter.onNext(ConvertParamUtils.json2ObjectResult(builder, json));//返回数据不继续执行
                }
            }).compose(RxSchedulers.<T>io_main()).subscribe(new Consumer<T>() {
                @Override
                public void accept(T t) {
                    builder.getRxObservableListener().onNext(t);
                }
            });

            return null;
        }

        return observable.map(new Function<ResponseBody, T>() {
            /**
             * Apply some calculation to the input value and return some other value.
             *
             * @param responseBody the input value
             * @return the output value
             * @throws Exception on error
             */
            @Override
            public T apply(ResponseBody responseBody) throws IOException {
                String s = responseBody.string();
                FileUtils.WriterTxtFile(builder.getFilePath(), builder.getFileName(), s, false);
                return ConvertParamUtils.json2ObjectResult(builder, s);
            }
        })
                .compose(RxSchedulers.<T>io_main())
                .subscribeWith(new RxSubscriber<T>() {
                    @Override
                    public void _onNext(final T t) {
                        builder.getRxObservableListener().onNext(t);
                    }

                    @Override
                    public void _onError(NetWorkCodeException.ResponseThrowable e) {
                        builder.getRxObservableListener().onError(e);
                    }

                    @Override
                    public void _onComplete() {
                    }
                });

    }

    /**
     * 没有网络再请求缓存
     *
     * @param builder
     * @param observable
     * @return list
     */
    @Override
    public <T> DisposableObserver loadNoNetWorkWithCacheResultList(final RequestBuilder<T> builder, Observable<ResponseBody> observable) {
        return observable
                .map(new Function<ResponseBody, T>() {
                    /**
                     * Apply some calculation to the input value and return some other value.
                     *
                     * @param responseBody the input value
                     * @return the output value
                     * @throws Exception on error
                     */
                    @Override
                    public T apply(ResponseBody responseBody) throws Exception {
                        String s = responseBody.string();
                        FileUtils.WriterTxtFile(builder.getFilePath(), builder.getFileName(), s, false);
                        return ConvertParamUtils.json2ArrayResult(builder, s);
                    }
                })
                .compose(RxSchedulers.<T>io_main())
                .subscribeWith(new RxSubscriber<T>() {
                    @Override
                    public void _onNext(T t) {
                        builder.getRxObservableListener().onNext(t);
                    }

                    @SuppressLint("CheckResult")
                    @Override
                    public void _onError(final NetWorkCodeException.ResponseThrowable e) {
                        Observable.create(new ObservableOnSubscribe<T>() {
                            @Override
                            public void subscribe(ObservableEmitter<T> emitter) {
                                String json = FileUtils.ReadTxtFile(builder.getFilePath() + "/" + builder.getFileName());
                                if (!TextUtils.isEmpty(json) && !json.equals("")) {
                                    emitter.onNext(ConvertParamUtils.json2ArrayResult(builder, json));
                                } else {
                                    RxJavaUtils.doInUIThread(new RxUITask<NetWorkCodeException.ResponseThrowable>(e) {
                                        /**
                                         * 在UI线程中执行
                                         *
                                         * @param responseThrowable 任务执行的入参
                                         */
                                        @Override
                                        public void doInUIThread(NetWorkCodeException.ResponseThrowable responseThrowable) {
                                            builder.getRxObservableListener().onError(responseThrowable);
                                        }
                                    });

                                }
                            }
                        }).compose(RxSchedulers.<T>io_main()).subscribe(new Consumer<T>() {
                            @Override
                            public void accept(T t) {
                                builder.getRxObservableListener().onNext(t);
                            }
                        });
                    }

                    @Override
                    public void _onComplete() {
                    }
                });

    }

    /**
     * 没有网络再请求缓存
     *
     * @param builder
     * @param observable
     * @return model
     */
    @Override
    public <T> DisposableObserver loadNoNetWorkWithCacheModel(final RequestBuilder<T> builder, Observable<ResponseBody> observable) {

        return observable.map(new Function<ResponseBody, T>() {
            /**
             * Apply some calculation to the input value and return some other value.
             *
             * @param responseBody the input value
             * @return the output value
             * @throws Exception on error
             */
            @Override
            public T apply(ResponseBody responseBody) throws IOException {
                String s = responseBody.string();
                FileUtils.WriterTxtFile(builder.getFilePath(), builder.getFileName(), s, false);
                return ConvertParamUtils.json2ObjectResult(builder, s);
            }
        }).compose(RxSchedulers.<T>io_main())
                .subscribeWith(new RxSubscriber<T>() {
                    @Override
                    public void _onNext(T t) {
                        builder.getRxObservableListener().onNext(t);
                    }

                    @SuppressLint("CheckResult")
                    @Override
                    public void _onError(final NetWorkCodeException.ResponseThrowable e) {
                        Observable.create(new ObservableOnSubscribe<T>() {
                            @Override
                            public void subscribe(ObservableEmitter<T> emitter) {
                                String json = FileUtils.ReadTxtFile(builder.getFilePath() + "/" + builder.getFileName());
                                if (!TextUtils.isEmpty(json) && !json.equals("")) {
                                    emitter.onNext(ConvertParamUtils.json2ObjectResult(builder, json));//返回数据不继续执行
                                } else {
                                    RxJavaUtils.doInUIThread(new RxUITask<NetWorkCodeException.ResponseThrowable>(e) {
                                        /**
                                         * 在UI线程中执行
                                         *
                                         * @param responseThrowable 任务执行的入参
                                         */
                                        @Override
                                        public void doInUIThread(NetWorkCodeException.ResponseThrowable responseThrowable) {
                                            builder.getRxObservableListener().onError(responseThrowable);
                                        }
                                    });
                                }
                            }
                        }).compose(RxSchedulers.<T>io_main()).subscribe(new Consumer<T>() {
                            @Override
                            public void accept(T t) {
                                builder.getRxObservableListener().onNext(t);
                            }
                        });
                    }

                    @Override
                    public void _onComplete() {
                    }
                });
    }

    /**
     * 把结果保存到本地，根据标志是否返回数据，如果本地存在则不需要下载
     *
     * @param builder
     * @param observable
     * @return list
     */
    @Override
    public <T> DisposableObserver loadOnlyNetWorkSaveList(final RequestBuilder<T> builder, Observable<ResponseBody> observable) {
        if (FileUtils.checkFileExists(builder.getFilePath() + "/" + builder.getFileName())) { // 已经在SD卡中存在
            return null;
        }

        return observable.map(new Function<ResponseBody, T>() {
            /**
             * Apply some calculation to the input value and return some other value.
             *
             * @param responseBody the input value
             * @return the output value
             * @throws Exception on error
             */
            @Override
            public T apply(ResponseBody responseBody) throws IOException {

                if (builder.isDiskCacheNetworkSaveReturn()) {
                    String s = responseBody.string();
                    FileUtils.WriterTxtFile(builder.getFilePath(), builder.getFileName(), s, false);
                    return ConvertParamUtils.json2ArrayResult(builder, s);
                }
                return null;
            }
        }).compose(RxSchedulers.<T>io_main())
                .subscribeWith(new RxSubscriber<T>() {
                    @Override
                    public void _onNext(T t) {
                        if (builder.isDiskCacheNetworkSaveReturn()) {
                            builder.getRxObservableListener().onNext(t);
                        }
                    }

                    @Override
                    public void _onError(NetWorkCodeException.ResponseThrowable e) {
                        if (builder.isDiskCacheNetworkSaveReturn()) {
                            builder.getRxObservableListener().onError(e);
                        }
                    }

                    @Override
                    public void _onComplete() {
                    }
                });
    }

    /**
     * 把结果保存到本地，根据标志是否返回数据，如果本地存在则不需要下载
     *
     * @param builder
     * @param observable
     * @return model
     */
    @Override
    public <T> DisposableObserver loadOnlyNetWorkSaveModel(final RequestBuilder<T> builder, Observable<ResponseBody> observable) {
        if (FileUtils.checkFileExists(builder.getFilePath() + "/" + builder.getFileName())) { // 已经在SD卡中存在
            return null;
        }

        return observable.map(new Function<ResponseBody, T>() {
            /**
             * Apply some calculation to the input value and return some other value.
             *
             * @param responseBody the input value
             * @return the output value
             * @throws Exception on error
             */
            @Override
            public T apply(ResponseBody responseBody) throws IOException {
                if (builder.isDiskCacheNetworkSaveReturn()) {
                    String s = responseBody.string();
                    FileUtils.WriterTxtFile(builder.getFilePath(), builder.getFileName(), s, false);
                    ConvertParamUtils.json2ObjectResult(builder, s);
                }
                return null;
            }
        }).compose(RxSchedulers.<T>io_main())
                .subscribeWith(new RxSubscriber<T>() {
                    @Override
                    public void _onNext(T t) {
                        if (builder.isDiskCacheNetworkSaveReturn()) {
                            builder.getRxObservableListener().onNext(t);
                        }
                    }

                    @Override
                    public void _onError(NetWorkCodeException.ResponseThrowable e) {
                        if (builder.isDiskCacheNetworkSaveReturn()) {
                            builder.getRxObservableListener().onError(e);
                        }
                    }

                    @Override
                    public void _onComplete() {
                    }
                });
    }

    /**
     * 把只通过网络返回数据，返回list
     *
     * @param builder
     * @param observable
     * @return list
     */
    @Override
    public <T> DisposableObserver loadOnlyNetWorkList(final RequestBuilder<T> builder, Observable<ResponseBody> observable) {
        return observable.map(new Function<ResponseBody, T>() {

            /**
             * Apply some calculation to the input value and return some other value.
             *
             * @param responseBody the input value
             * @return the output value
             * @throws Exception on error
             */
            @Override
            public T apply(ResponseBody responseBody) {
                return ConvertParamUtils.body2ArrayResult(builder, responseBody);
            }
        }).compose(RxSchedulers.<T>io_main())
                .subscribeWith(new RxSubscriber<T>() {

                    /**
                     * 定义处理事件
                     *
                     * @param t
                     */
                    @Override
                    public void _onNext(T t) {
                        builder.getRxObservableListener().onNext(t);
                    }

                    @Override
                    public void _onError(NetWorkCodeException.ResponseThrowable e) {
                        builder.getRxObservableListener().onError(e);
                    }

                    @Override
                    public void _onComplete() {

                    }
                });
    }

    /**
     * 通过返回网络请求
     *
     * @param builder
     * @param observable
     * @return model
     */
    @Override
    public <T> DisposableObserver loadOnlyNetWorkModel(final RequestBuilder<T> builder, Observable<ResponseBody> observable) {
        return
                observable.map(new Function<ResponseBody, T>() {

                    /**
                     * Apply some calculation to the input value and return some other value.
                     *
                     * @param responseBody the input value
                     * @return the output value
                     * @throws Exception on error
                     */
                    @Override
                    public T apply(ResponseBody responseBody) {
                        return ConvertParamUtils.body2ObjectResult(builder, responseBody);
                    }
                }).compose(RxSchedulers.<T>io_main())
                        .subscribeWith(new RxSubscriber<T>() {
                            @Override
                            public void _onNext(T t) {
                                builder.getRxObservableListener().onNext(t);
                            }

                            @Override
                            public void _onError(NetWorkCodeException.ResponseThrowable e) {
                                builder.getRxObservableListener().onError(e);
                            }

                            @Override
                            public void _onComplete() {

                            }
                        });


    }
}
