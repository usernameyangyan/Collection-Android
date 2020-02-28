package com.youngmanster.collection.mvp.presenter.wechat.okhttpcache;

import com.youngmanster.collection.been.Result;
import com.youngmanster.collection.been.wechat.WeChatNews;
import com.youngmanster.collection.http.ApiUrl;
import com.youngmanster.collection.mvp.contract.wechat.okhttpcache.WeChatNBANewsContract;
import com.youngmanster.collectionlibrary.data.DataManager;
import com.youngmanster.collectionlibrary.network.RequestBuilder;
import com.youngmanster.collectionlibrary.network.rx.RxObservableListener;

import java.util.List;


/**
 * Created by yangyan
 * on 2018/3/21.
 */

public class WeChatNBANewsPresenter extends WeChatNBANewsContract.Presenter {
    @Override
    public void requestNBANews(int page, int num) {

        RequestBuilder<Result<List<WeChatNews>>> resultRequestBuilder = new RequestBuilder<>(new RxObservableListener<Result<List<WeChatNews>>>(mView) {
            @Override
            public void onNext(Result<List<WeChatNews>> result) {
                mView.refreshUI(result.getResult());
            }
        });

        resultRequestBuilder
                .setUrl(ApiUrl.URL_WETCHAT_FEATURED)
                .setTransformClass(WeChatNews.class)
                .setParam("page",page)
                .setParam("type","video")
                .setParam("count",num);

        rxManager.addObserver(DataManager.DataForHttp.httpRequest(resultRequestBuilder));
    }
}
