package com.youngmanster.collection.mvp.presenter.wechat.definitioncache;
import com.youngmanster.collection.been.Result;
import com.youngmanster.collection.been.wechat.WeChatNews;
import com.youngmanster.collection.common.AppConfig;
import com.youngmanster.collection.http.ApiClient;
import com.youngmanster.collection.http.ApiUrl;
import com.youngmanster.collection.mvp.contract.wechat.okhttpcache.WeChatWorldNewsContract;
import com.youngmanster.collectionlibrary.db.DataManager;
import com.youngmanster.collectionlibrary.network.NetWorkCodeException;
import com.youngmanster.collectionlibrary.network.RequestBuilder;
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
        String filePath= AppConfig.STORAGE_DIR+"wechat/world";
        String fileName=page+".t";

        RequestBuilder<Result<List<WeChatNews>>> resultRequestBuilder = new RequestBuilder<>(new RxObservableListener<Result<List<WeChatNews>>>(mView) {
            @Override
            public void onNext(Result<List<WeChatNews>> result) {
                mView.refreshUI(result.getNewslist());
            }
        });

        resultRequestBuilder
                .setFilePathAndFileName(filePath,fileName)
                .setTransformClass(WeChatNews.class)
                .setUrl(ApiUrl.URL_WETCHAT_WORLD_NEWS)
                .setRequestParam(ApiClient.getRequiredBaseParam())
                .setParam("page",page)
                .setParam("num",num)
                .setHttpTypeAndReqType(RequestBuilder.HttpType.DEFAULT_GET,RequestBuilder.ReqType.DISK_CACHE_NO_NETWORK_LIST)
        ;

        rxManager.addObserver(DataManager.getInstance(DataManager.DataType.RETROFIT).httpRequest(resultRequestBuilder));
    }
}
