package com.youngmanster.collectionlibrary.utils.rxutil;

/**
 * Created by yangy
 * 2019-09-08
 * Describe:
 */
/**
 * 通用任务接口
 */
public interface ICommonRxTask {
    /**
     * 在IO线程执行
     */
    void doInIOThread();

    /**
     * 在UI线程执行
     */
    void doInUIThread();
}
