package com.youngmanster.collectionlibrary.network.rx.utils;

/**
 * 线程类型
 */
public enum SchedulerType {

    /**
     * 订阅发生在主线程 （  ->  -> main)
     */
    _main,
    /**
     * 订阅发生在io线程 （  ->  -> io)
     */
    _io,
    /**
     * 处理在io线程，订阅发生在主线程（ -> io -> main)
     */
    _io_main,
    /**
     * 处理在io线程，订阅也发生在io线程（ -> io -> io)
     */
    _io_io,
}
