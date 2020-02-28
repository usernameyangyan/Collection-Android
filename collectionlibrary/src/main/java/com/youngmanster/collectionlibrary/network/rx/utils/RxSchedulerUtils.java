/*
 * Copyright (C) 2018 xuexiangjys(xuexiangjys@163.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.youngmanster.collectionlibrary.network.rx.utils;


import java.util.concurrent.Executor;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 线程调度工具
 */
public final class RxSchedulerUtils {

    private static Executor sIOExecutor;

    /**
     * 设置自定义RxJava的线程池
     *
     * @param sExecutor
     */
    public static void setIOExecutor(Executor sExecutor) {
        RxSchedulerUtils.sIOExecutor = sExecutor;
    }

    public static Executor getIOExecutor() {
        return sIOExecutor;
    }

    public static Scheduler io() {
        return io(sIOExecutor);
    }

    public static Scheduler io(Executor executor) {
        return executor != null ? Schedulers.from(executor) : Schedulers.io();
    }

    //==========================Flowable===========================//

    private RxSchedulerUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 回到主线程
     *
     * @param flowable 被观察者
     */
    public static <T> Flowable<T> toMain(Flowable<T> flowable) {
        return flowable.observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 回到io线程
     *
     * @param flowable 被观察者
     */
    public static <T> Flowable<T> toIo(Flowable<T> flowable) {
        return flowable.observeOn(Schedulers.io());
    }

    /**
     * 订阅发生在主线程 （  ->  -> main)
     * 使用compose操作符
     *
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<T, T> _main_f() {
        return new SchedulerTransformer<>(SchedulerType._main);
    }

    /**
     * 订阅发生在io线程 （  ->  -> io)
     * 使用compose操作符
     *
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<T, T> _io_f() {
        return new SchedulerTransformer<>(SchedulerType._io);
    }

    /**
     * 处理在io线程，订阅发生在主线程（ -> io -> main)
     *
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<T, T> _io_main_f() {
        return new SchedulerTransformer<>(SchedulerType._io_main);
    }

    /**
     * 处理在io线程，订阅也发生在io线程（ -> io -> io)
     *
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<T, T> _io_io_f() {
        return new SchedulerTransformer<>(SchedulerType._io_io);
    }

    //==========================Observable==========================//


    /**
     * 回到主线程
     *
     * @param observable 被观察者
     */
    public static <T> Observable<T> toMain(Observable<T> observable) {
        return observable.observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 回到io线程
     *
     * @param observable 被观察者
     */
    public static <T> Observable<T> toIo(Observable<T> observable) {
        return observable.observeOn(Schedulers.io());
    }


    /**
     * 订阅发生在主线程 （  ->  -> main)
     * 使用compose操作符
     *
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> _main_o() {
        return new SchedulerTransformer<>(SchedulerType._main);
    }

    /**
     * 订阅发生在io线程 （  ->  -> io)
     * 使用compose操作符
     *
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> _io_o() {
        return new SchedulerTransformer<>(SchedulerType._io);
    }


    /**
     * 处理在io线程，订阅发生在主线程（ -> io -> main)
     *
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> _io_main_o() {
        return new SchedulerTransformer<>(SchedulerType._io_main);
    }


    /**
     * 处理在io线程，订阅也发生在io线程（ -> io -> io)
     *
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> _io_io_o() {
        return new SchedulerTransformer<>(SchedulerType._io_io);
    }


}
