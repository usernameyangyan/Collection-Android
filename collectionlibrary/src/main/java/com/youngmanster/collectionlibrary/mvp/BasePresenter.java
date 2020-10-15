package com.youngmanster.collectionlibrary.mvp;

import com.youngmanster.collectionlibrary.network.rx.RxManager;
/**
 * Created by yangyan
 * on 2018/3/18.
 */

public abstract class BasePresenter<T> {
    public T mView;

    public RxManager rxManager=new RxManager();

    public void setV(T v){
        this.mView=v;
    }

    public void onDestroy(){
        this.mView=null;
        rxManager.clear();
    }
}
