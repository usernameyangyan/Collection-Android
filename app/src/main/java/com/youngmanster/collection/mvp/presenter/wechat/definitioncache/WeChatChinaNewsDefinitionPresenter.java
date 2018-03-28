package com.youngmanster.collection.mvp.presenter.wechat.definitioncache;

import com.youngmanster.collection.been.Result;
import com.youngmanster.collection.been.wechat.WeChatNews;
import com.youngmanster.collection.common.AppConfig;
import com.youngmanster.collection.mvp.contract.wechat.okhttpcache.WeChatChinaNewsContract;
import com.youngmanster.collectionlibrary.network.NetWorkCodeException;
import com.youngmanster.collectionlibrary.network.RequestManager;
import com.youngmanster.collectionlibrary.network.rx.RxObservableListener;

import java.util.List;

/**
 * Created by yangyan
 * on 2018/3/21.
 */

public class WeChatChinaNewsDefinitionPresenter extends WeChatChinaNewsContract.Presenter {
    @Override
    public void requestChinaNews(int page, int num) {
        String filePath = AppConfig.STORAGE_DIR + "wechat/china/";
        String fileName = "limttime.t";

        rxManager.addObserver(RequestManager.loadFormDiskResultListLimitTime(
                mModel.loadChinaNews(page, num), new RxObservableListener<Result<List<WeChatNews>>>(mView) {
                    @Override
                    public void onNext(Result<List<WeChatNews>> result) {
                        mView.refreshUI(result.getNewslist());
                    }
                }, WeChatNews.class, 1, filePath, fileName));
    }
}
