package com.youngmanster.collection.download;

import android.view.View;
import android.widget.TextView;

import com.mvp.annotation.MvpAnnotation;
import com.mvp.config.AnnotationConfig;
import com.youngmanster.collection.R;
import com.youngmanster.collection.base.BaseActivity;
import com.youngmanster.collection.mvp.presenter.DownloadFilePresenter;
import com.youngmanster.collection.mvp.view.IDownloadFileView;
import com.youngmanster.collectionlibrary.mvp.BasePresenter;
import com.youngmanster.collectionlibrary.mvp.BaseView;

/**
 * Created by yangy
 * 2020/10/15
 * Describe:
 */
@MvpAnnotation(
        prefixName = "DownloadFile",
        basePresenterClazz = BasePresenter.class,
        baseViewClazz = BaseView.class,
        language = AnnotationConfig.LANGUAGE_JAVA
)
public class DownFileActivity extends BaseActivity<DownloadFilePresenter> implements IDownloadFileView {
    /**
     * 布局文件加载
     */
    @Override
    public int getLayoutId() {
        return R.layout.activity_downlaod;
    }

    /**
     * 初始化参数
     */
    @Override
    public void init() {
        defineActionBarConfig.setTitle("断点下载文件");

        findViewById(R.id.downloadBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((DownloadFilePresenter) mPresenter).downloadFile();
            }
        });

        findViewById(R.id.stopDownloadBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((DownloadFilePresenter) mPresenter).stopDownload();
            }
        });

    }

    /**
     * 请求数据
     */
    @Override
    public void requestData() {

    }

    @Override
    public void updateProgress(float progress) {
        ((TextView)findViewById(R.id.progressTv)).setText("已下载："+ String.format("%.1f",progress*100)+"%");
    }

    @Override
    public void onError(String errorMsg) {

    }
}
