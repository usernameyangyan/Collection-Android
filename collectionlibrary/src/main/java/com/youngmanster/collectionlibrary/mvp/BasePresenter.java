package com.youngmanster.collectionlibrary.mvp;

import com.youngmanster.collectionlibrary.network.rx.RxManager;

/**
 * Created by yangyan
 * on 2018/3/18.
 */

public abstract class BasePresenter<T,E> {
    public T mModel;
    public E mView;

    public RxManager rxManager=new RxManager();

    public void setMV(T m,E v){
        this.mModel=m;
        this.mView=v;
    }

    public void onDestroy(){
        rxManager.clear();
        rxManager=null;
    }
}
