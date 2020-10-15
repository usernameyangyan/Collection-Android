package com.youngmanster.collection.mvp.view;

import com.youngmanster.collectionlibrary.mvp.BaseView;

public interface IDownloadFileView extends BaseView{
    void updateProgress(float progress);
}