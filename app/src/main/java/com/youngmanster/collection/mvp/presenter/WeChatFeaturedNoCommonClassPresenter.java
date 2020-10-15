package com.youngmanster.collection.mvp.presenter;

import com.youngmanster.collection.been.WeChatNewsResult;
import com.youngmanster.collection.http.ApiUrl;
import com.youngmanster.collection.mvp.view.IWeChatFeaturedNoCommonClassView;
import com.youngmanster.collectionlibrary.data.DataManager;
import com.youngmanster.collectionlibrary.mvp.BasePresenter;
import com.youngmanster.collectionlibrary.network.RequestBuilder;
import com.youngmanster.collectionlibrary.network.rx.RxObservableListener;

public class WeChatFeaturedNoCommonClassPresenter extends BasePresenter<IWeChatFeaturedNoCommonClassView> {
    public void requestFeaturedNews(int page, int num) {

        RequestBuilder<WeChatNewsResult> resultRequestBuilder = new RequestBuilder<>(new RxObservableListener<WeChatNewsResult>() {
            @Override
            public void onNext(WeChatNewsResult result) {
                mView.refreshUI(result.getResult());
            }
        });

        resultRequestBuilder
                .setUrl(ApiUrl.URL_WETCHAT_FEATURED)
                .setTransformClass(WeChatNewsResult.class)
                .setUseCommonClass(false)
                .setParam("page",page)
                .setParam("num",num);

        rxManager.addObserver(DataManager.DataForHttp.httpRequest(resultRequestBuilder));

    }
}