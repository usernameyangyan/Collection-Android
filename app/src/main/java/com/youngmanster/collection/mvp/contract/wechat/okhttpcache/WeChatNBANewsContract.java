package com.youngmanster.collection.mvp.contract.wechat.okhttpcache;
import com.youngmanster.collection.been.Result;
import com.youngmanster.collection.been.wechat.WeChatNews;
import com.youngmanster.collectionlibrary.mvp.BaseModel;
import com.youngmanster.collectionlibrary.mvp.BasePresenter;
import com.youngmanster.collectionlibrary.mvp.BaseView;
import com.youngmanster.collectionlibrary.network.NetWorkCodeException;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by yangyan
 * on 2018/3/21.
 */

public interface WeChatNBANewsContract {
	interface Model extends BaseModel{
		Observable<Result<List<WeChatNews>>> loadNBANews(int page, int num);
	}

	interface View extends BaseView{
		void refreshUI(List<WeChatNews> newsLists);
	}

	abstract class Presenter extends BasePresenter<Model,View>{
		public abstract void requestNBANews(int page,int num);
	}
}
