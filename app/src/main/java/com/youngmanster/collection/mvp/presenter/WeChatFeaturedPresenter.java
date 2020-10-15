package com.youngmanster.collection.mvp.presenter;

import com.youngmanster.collection.been.Result;
import com.youngmanster.collection.been.wechat.WeChatNews;
import com.youngmanster.collection.http.ApiUrl;
import com.youngmanster.collection.mvp.view.IWeChatFeaturedView;
import com.youngmanster.collectionlibrary.data.DataManager;
import com.youngmanster.collectionlibrary.mvp.BasePresenter;
import com.youngmanster.collectionlibrary.network.RequestBuilder;
import com.youngmanster.collectionlibrary.network.rx.RxObservableListener;

import java.util.List;

public class WeChatFeaturedPresenter extends BasePresenter<IWeChatFeaturedView> {

    public void requestFeaturedNews(int page, int num) {

        RequestBuilder<Result<List<WeChatNews>>> resultRequestBuilder = new RequestBuilder<>(new RxObservableListener<Result<List<WeChatNews>>>() {
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