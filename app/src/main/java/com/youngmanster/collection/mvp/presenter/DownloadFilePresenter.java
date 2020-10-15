package com.youngmanster.collection.mvp.presenter;

import com.youngmanster.collection.common.AppConfig;
import com.youngmanster.collection.mvp.view.IDownloadFileView;
import com.youngmanster.collectionlibrary.data.DataManager;
import com.youngmanster.collectionlibrary.mvp.BasePresenter;
import com.youngmanster.collectionlibrary.network.RequestBuilder;
import com.youngmanster.collectionlibrary.network.download.DownloadInfo;
import com.youngmanster.collectionlibrary.network.rx.RxObservableListener;
import com.youngmanster.collectionlibrary.utils.LogUtils;

import io.reactivex.observers.DisposableObserver;
import okhttp3.ResponseBody;

public class DownloadFilePresenter extends BasePresenter<IDownloadFileView> {

    private DisposableObserver<ResponseBody> disposableObserver;

    public void downloadFile() {

        RequestBuilder<DownloadInfo> resultRequestBuilder = new RequestBuilder<>(new RxObservableListener<DownloadInfo>() {
            @Override
            public void onNext(DownloadInfo result) {

            }

            @Override
            public void onDownloadProgress(long total, long currentLength, float progress) {
                super.onDownloadProgress(total, currentLength, progress);
                mView.updateProgress(progress);
            }
        });


        resultRequestBuilder.setUrl("http://acj6.0098118.com/pc6_soure/2020-8-20/8e52be6ad1a369aDOC1IU99ueAbq6.apk")
                .setSaveDownloadFilePathAndFileName(AppConfig.STORAGE_DIR, "1.apk")
                .setTransformClass(DownloadInfo.class)
                .setHttpTypeAndReqType(RequestBuilder.HttpType.DOWNLOAD_FILE_GET, RequestBuilder.ReqType.DOWNLOAD_FILE_MODEL)
                .setOpenBreakpointDownloadOrUpload(true);
        disposableObserver = DataManager.DataForHttp.httpRequest(resultRequestBuilder);
        rxManager.addObserver(disposableObserver);
    }

    public void stopDownload() {
        disposableObserver.dispose();
    }
}