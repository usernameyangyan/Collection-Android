package com.youngmanster.collectionlibrary.network.rx;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;

/**
 * Observables 和 Subscribers管理
 * Created by yangyan
 * on 2018/3/17.
 */

public class RxManager {
    private CompositeDisposable compositeDisposable=new CompositeDisposable();

    /**
     * 添加observer
     * @param observer
     */
    public void addObserver(DisposableObserver observer){
        if(observer!=null){
            compositeDisposable.add(observer);
        }
    }


    public void clear(){
        compositeDisposable.dispose();
    }
}
