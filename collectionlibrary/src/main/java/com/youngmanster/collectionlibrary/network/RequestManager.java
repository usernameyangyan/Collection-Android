package com.youngmanster.collectionlibrary.network;

import android.text.TextUtils;

import com.youngmanster.collectionlibrary.config.Config;
import com.youngmanster.collectionlibrary.network.gson.GsonUtils;
import com.youngmanster.collectionlibrary.network.rx.RxObservableListener;
import com.youngmanster.collectionlibrary.network.rx.RxSchedulers;
import com.youngmanster.collectionlibrary.network.rx.RxSubscriber;
import com.youngmanster.collectionlibrary.utils.FileUtils;
import com.youngmanster.collectionlibrary.utils.NetworkUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 请求管理
 * Created by yangyan
 * on 2018/3/23.
 */

public class RequestManager {

	/**
	 * ============================自定义的缓存机制网络+本地json缓存===========================================
	 */

	/**
	 * 设置缓存时间，没超过设置的时间不请求网络，只返回缓存数据
	 * 返回List
	 */
	public static <T> DisposableObserver<T> loadFormDiskResultListLimitTime(final Observable<T> netWorkObservable,
																			final RxObservableListener<T> rxObservableListener,
																			final Class cls,
																			final int hours,
																			final String filePath,
																			final String fileName) {


		if (!FileUtils.isCacheDataFailure(filePath + "/" + fileName, hours)) {

			Observable.create(new ObservableOnSubscribe<T>() {
				@Override
				public void subscribe(ObservableEmitter<T> emitter) throws Exception {
					String json = FileUtils.ReadTxtFile(filePath + "/" + fileName);
					T a = GsonUtils.fromJsonArray(json, cls);
					rxObservableListener.onNext(a);
				}
			}).subscribe(new Consumer<T>() {
				@Override
				public void accept(T t) throws Exception {

				}
			});

			return null;
		}

		DisposableObserver<T> observer = netWorkObservable.compose(RxSchedulers.<T>io_main())
				.subscribeWith(new RxSubscriber<T>() {
					@Override
					public void _onNext(final T t) {
						rxObservableListener.onNext(t);
						if (t != null) {
							Observable.create(new ObservableOnSubscribe<T>() {
								@Override
								public void subscribe(ObservableEmitter<T> emitter) throws Exception {
									FileUtils.WriterTxtFile(filePath, fileName, GsonUtils.getJson(t), false);
								}
							}).subscribe(new Consumer<T>() {
								@Override
								public void accept(T t) throws Exception {

								}
							});

						}
					}

					@Override
					public void _onError(NetWorkCodeException.ResponseThrowable e) {
						rxObservableListener.onError(e);
					}

					@Override
					public void _onComplete() {
						rxObservableListener.onComplete();
					}
				});

		return observer;
	}

	/**
	 * 设置缓存时间，没超过设置的时间不请求网络，只返回缓存数据
	 * 返回Model
	 */
	public static <T> DisposableObserver<T> loadFormDiskModeLimitTime(final Observable<T> netWorkObservable,
																	  final RxObservableListener<T> rxObservableListener,
																	  final Class cls,
																	  final int hours,
																	  final String filePath,
																	  final String fileName) {

		if (!FileUtils.isCacheDataFailure(filePath + "/" + fileName, hours)) {

			Observable.create(new ObservableOnSubscribe<T>() {
				@Override
				public void subscribe(ObservableEmitter<T> emitter) throws Exception {
					String json = FileUtils.ReadTxtFile(filePath + "/" + fileName);
					T a = GsonUtils.fromJsonObject(json, cls);
					rxObservableListener.onNext(a);
				}
			}).subscribe(new Consumer<T>() {
				@Override
				public void accept(T t) throws Exception {

				}
			});

			return null;
		}


		DisposableObserver<T> observer = netWorkObservable.compose(RxSchedulers.<T>io_main())
				.subscribeWith(new RxSubscriber<T>() {
					@Override
					public void _onNext(final T t) {
						rxObservableListener.onNext(t);
						if (t != null) {
							Observable.create(new ObservableOnSubscribe<T>() {
								@Override
								public void subscribe(ObservableEmitter<T> emitter) throws Exception {
									FileUtils.WriterTxtFile(filePath, fileName, GsonUtils.getJson(t), false);
								}
							}).subscribe(new Consumer<T>() {
								@Override
								public void accept(T t) throws Exception {

								}
							});

						}
					}

					@Override
					public void _onError(NetWorkCodeException.ResponseThrowable e) {
						rxObservableListener.onError(e);
					}

					@Override
					public void _onComplete() {
						rxObservableListener.onComplete();
					}
				});

		return observer;
	}

	/**
	 * 没有网络再请求缓存
	 * 返回List
	 */
	public static <T> DisposableObserver<T> loadNoNetWorkWithCacheResultList(final Observable<T> netWorkObservable,
																			 final RxObservableListener<T> rxObservableListener,
																			 final Class cls,
																			 final String filePath,
																			 final String fileName) {


		final Observable<T> observable = Observable.create(new ObservableOnSubscribe<T>() {
			@Override
			public void subscribe(ObservableEmitter<T> emitter) throws Exception {
				String json = FileUtils.ReadTxtFile(filePath + "/" + fileName);
				if (!TextUtils.isEmpty(json) && !json.equals("")) {
					T a = GsonUtils.fromJsonArray(json, cls);
					emitter.onNext(a);//返回数据不继续执行
					emitter.onComplete();
				} else {
					NetWorkCodeException.ResponseThrowable e = new NetWorkCodeException.ResponseThrowable();
					e.code = NetWorkCodeException.NETWORD_ERROR;
					e.message = "网络错误";
					rxObservableListener.onError(e);
				}
			}
		});

		DisposableObserver<T> observer = netWorkObservable.filter(new Predicate<T>() {
			@Override
			public boolean test(T t) throws Exception {
				if (t != null) {
					FileUtils.WriterTxtFile(filePath, fileName, GsonUtils.getJson(t), false);
				}
				return true;
			}
		}).compose(RxSchedulers.<T>io_main())
				.subscribeWith(new RxSubscriber<T>() {
					@Override
					public void _onNext(T t) {
						rxObservableListener.onNext(t);
					}

					@Override
					public void _onError(NetWorkCodeException.ResponseThrowable e) {
						observable.subscribe(new Consumer<T>() {
							@Override
							public void accept(T t) throws Exception {
								rxObservableListener.onNext(t);
							}
						});
					}

					@Override
					public void _onComplete() {
						rxObservableListener.onComplete();
					}
				});


		return observer;
	}


	/**
	 * 没有网络再请求缓存
	 * 返回Model
	 */
	public static <T> DisposableObserver<T> loadNoNetWorkWithCacheModel(final Observable<T> netWorkObservable,
																		final RxObservableListener<T> rxObservableListener,
																		final Class cls,
																		final String filePath,
																		final String fileName) {

		final Observable<T> observable = Observable.create(new ObservableOnSubscribe<T>() {
			@Override
			public void subscribe(ObservableEmitter<T> emitter) throws Exception {
				String json = FileUtils.ReadTxtFile(filePath + "/" + fileName);
				if (!TextUtils.isEmpty(json) && !json.equals("")) {
					T a = GsonUtils.fromJsonObject(json, cls);
					emitter.onNext(a);//返回数据不继续执行
					emitter.onComplete();
				} else {
					NetWorkCodeException.ResponseThrowable e = new NetWorkCodeException.ResponseThrowable();
					e.code = NetWorkCodeException.NETWORD_ERROR;
					e.message = "网络错误";
					rxObservableListener.onError(e);
				}
			}
		});

		DisposableObserver<T> observer = netWorkObservable.filter(new Predicate<T>() {
			@Override
			public boolean test(T t) throws Exception {
				if (t != null) {
					FileUtils.WriterTxtFile(filePath, fileName, GsonUtils.getJson(t), false);
				}
				return true;
			}
		}).compose(RxSchedulers.<T>io_main())
				.subscribeWith(new RxSubscriber<T>() {
					@Override
					public void _onNext(T t) {
						rxObservableListener.onNext(t);

					}

					@Override
					public void _onError(NetWorkCodeException.ResponseThrowable e) {
						observable.subscribe(new Consumer<T>() {
							@Override
							public void accept(T t) throws Exception {
								rxObservableListener.onNext(t);
							}
						});
					}

					@Override
					public void _onComplete() {
						rxObservableListener.onComplete();
					}
				});

		return observer;
	}

	/**
	 * 把结果保存到本地，根据标志是否返回数据，如果本地存在则不需要下载
	 */
	public static <T> DisposableObserver<T> loadOnlyNetWorkSaveResult(final Observable<T> netWorkObservable,
																	  final RxObservableListener<T> rxObservableListener,
																	  final String saveFilePath,
																	  final String fileName,
																	  final boolean isReturn) {

		if (FileUtils.checkFileExists(saveFilePath)) { // 已经在SD卡中存在
			return null;
		}

		DisposableObserver<T> observer = netWorkObservable.filter(new Predicate<T>() {
			@Override
			public boolean test(T t) throws Exception {
				if (t != null) {
					FileUtils.WriterTxtFile(saveFilePath, fileName, GsonUtils.getJson(t), false);
				}
				return true;
			}
		}).compose(RxSchedulers.<T>io_main())
				.subscribeWith(new RxSubscriber<T>() {
					@Override
					public void _onNext(T t) {
						if (isReturn == true) {
							rxObservableListener.onNext(t);
						}

					}

					@Override
					public void _onError(NetWorkCodeException.ResponseThrowable e) {
						if (isReturn == true) {
							rxObservableListener.onError(e);
						}
					}

					@Override
					public void _onComplete() {
						if (isReturn == true) {
							rxObservableListener.onComplete();
						}
					}
				});

		return observer;
	}

	/**
	 * 只通过网络返回数据
	 */
	public static <T> DisposableObserver<T> loadOnlyNetWork(final Observable<T> netWorkObservable,
															final RxObservableListener<T> rxObservableListener) {

		DisposableObserver<T> observer = netWorkObservable.compose(RxSchedulers.<T>io_main())
				.subscribeWith(new RxSubscriber<T>() {
					@Override
					public void _onNext(T t) {
						rxObservableListener.onNext(t);
					}

					@Override
					public void _onError(NetWorkCodeException.ResponseThrowable e) {
						rxObservableListener.onError(e);
					}

					@Override
					public void _onComplete() {
						rxObservableListener.onComplete();
					}
				});

		return observer;
	}

	/**
	 * ===============================Retrofit+OkHttp的缓存机制=========================================
	 */

	public static Interceptor getInterceptor() {
		Interceptor interceptor = new Interceptor() {
			@Override
			public Response intercept(Chain chain) throws IOException {

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
					int maxAge = 0; // read from cache
					return originalResponse.newBuilder()
							.removeHeader("Pragma")
							.header("Cache-Control", "public ,max-age=" + maxAge)
							.build();
				} else {
					long maxStale = Config.MAX_CACHE_SECONDS; // tolerate 4-weeks stale
					return originalResponse.newBuilder()
							.removeHeader("Pragma")
							.header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
							.build();
				}
			}
		};

		return interceptor;
	}
}
