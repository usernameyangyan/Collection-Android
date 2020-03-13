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


import android.support.annotation.NonNull;

/**
 * 通用的遍历数组或者集合的异步任务，在io线程中进行数据处理，在ui线程中刷新ui
 *
 * @author xuexiang
 * @since 2018/6/10 下午9:29
 */
public abstract class RxIteratorTask<T, R> implements IRxIOTask<T, R>, IRxUITask<R> {

    /**
     * 遍历的是否是数组
     */
    private boolean mIsArray;
    /**
     * IO执行任务的入参, 遍历的集合
     */
    private java.lang.Iterable<T> Iterable;

    /**
     * IO执行任务的入参, 遍历的数组
     */
    private T[] Array;

    public RxIteratorTask(@NonNull java.lang.Iterable<T> iterable) {
        Iterable = iterable;
        mIsArray = false;
    }

    public RxIteratorTask(@NonNull T[] array) {
        Array = array;
        mIsArray = true;
    }


    public java.lang.Iterable<T> getIterable() {
        return Iterable;
    }

    public RxIteratorTask setIterable(java.lang.Iterable<T> iterable) {
        Iterable = iterable;
        return this;
    }

    public T[] getArray() {
        return Array;
    }

    public RxIteratorTask setArray(T[] array) {
        Array = array;
        return this;
    }

    public boolean isArray() {
        return mIsArray;
    }
}
