package com.youngmanster.collection.mvp.presenter.wechat.definitioncache;
import com.youngmanster.collection.been.Result;
import com.youngmanster.collection.been.wechat.WeChatNews;
import com.youngmanster.collection.common.AppConfig;
import com.youngmanster.collection.mvp.contract.wechat.okhttpcache.WeChatWorldNewsContract;
import com.youngmanster.collectionlibrary.network.NetWorkCodeException;
import com.youngmanster.collectionlibrary.network.RequestManager;
import com.youngmanster.collectionlibrary.network.rx.RxObservableListener;
import com.youngmanster.collectionlibrary.utils.LogUtils;

import java.util.List;


/**
 * Created by yangyan
 * on 2018/3/21.
 */

public class WeChatWorldNewsDefinitionPresenter extends WeChatWorldNewsContract.Presenter {
    @Override
    public void requestWorldNews(int page, int num) {
        String filePath= AppConfig.STORAGE_DIR+"wechat/world/";
        String fileName=page+".t";

        rxManager.addObserver(RequestManager.loadNoNetWorkWithCacheResultList(mModel.loadWorldNews(page, num),
                new RxObservableListener<Result<List<WeChatNews>>>(mView) {
                    @Override
                    public void onNext(Result<List<WeChatNews>> result) {
                        mView.refreshUI(result.getNewslist());
                    }

                },WeChatNews.class,filePath,fileName));
    }
}
