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


/**
 * 通用的Rx异步执行任务，在io线程中进行数据处理，在ui线程中刷新ui
 */
public abstract class RxAsyncTask<T, R> implements IRxIOTask<T, R>, IRxUITask<R> {

    /**
     * IO执行任务的入参
     */
    private T InData;

    /**
     * IO执行任务的出参,UI执行任务的入参
     */
    private R OutData;

    public RxAsyncTask(T inData) {
        InData = inData;
    }

    public T getInData() {
        return InData;
    }

    public RxAsyncTask setInData(T inData) {
        InData = inData;
        return this;
    }

    public R getOutData() {
        return OutData;
    }

    public RxAsyncTask setOutData(R outData) {
        OutData = outData;
        return this;
    }
}
