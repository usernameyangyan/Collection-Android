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

import android.util.Log;

/**
 * 自定义错误
 */
public class RxException extends Exception {
    /**
     * 默认错误码
     */
    public static final int DEFAULT_ERROR = -1;

    /**
     * 自定义的错误码
     */
    private int mCode;

    public RxException(String message, int code) {
        super(message);
        mCode = code;
    }

    public RxException(Throwable e, int code) {
        super(e);
        mCode = code;
    }

    /**
     * 获取自定义的错误码
     *
     * @return
     */
    public int getCode() {
        return mCode;
    }

    @Override
    public String getMessage() {
        return "Code:" + mCode + ", Message:" + getDetailMessage();
    }

    /**
     * 获取详情信息
     *
     * @return
     */
    public String getDetailMessage() {
        return super.getMessage();
    }

    /**
     * 获取错误堆栈信息
     *
     * @return
     */
    public String getExceptionStackTraceInfo() {
        return Log.getStackTraceString(this);
    }
}
