package com.youngmanster.collectionlibrary.utils.rxutil;

import com.youngmanster.collectionlibrary.network.rx.RxSchedulers;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * Created by yangy
 * 2019-09-08
 * Describe:
 */
public class RxJavaUtils {

    public static void executeRxTask(final ICommonRxTask commonRxTask) {
        Observable.create(new ObservableOnSubscribe() {
            @Override
            public void subscribe(@NonNull ObservableEmitter e) throws Exception {
                commonRxTask.doInIOThread();
            }
        })
                .compose(RxSchedulers.io_main())
                .subscribeWith(new Observer() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Object o) {
                        commonRxTask.doInUIThread();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


}
