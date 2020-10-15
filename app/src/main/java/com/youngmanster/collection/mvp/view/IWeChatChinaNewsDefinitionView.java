package com.youngmanster.collection.mvp.view;

import com.youngmanster.collection.been.wechat.WeChatNews;
import com.youngmanster.collectionlibrary.mvp.BaseView;

import java.util.List;

public interface IWeChatChinaNewsDefinitionView extends BaseView{
    void refreshUI(List<WeChatNews> weChatNews);
}