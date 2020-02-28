package com.youngmanster.collectionlibrary.refreshrecyclerview;

import android.content.Context;

import com.youngmanster.collectionlibrary.R;

/**
 * Created by yangy
 * 2020-02-28
 * Describe:
 */

public class LoadingTextConfig {

    private static LoadingTextConfig loadingTextConfig;

    public static LoadingTextConfig getInstance(Context context){
        if(loadingTextConfig==null){
            synchronized(LoadingTextConfig.class){
                if(loadingTextConfig==null){
                    loadingTextConfig=new LoadingTextConfig(context);
                }
            }
        }

        return loadingTextConfig;
    }




    private Context context;
    private String collection_pull_to_refresh;
    private String collection_release_refresh;
    private String collection_refresh_done;
    private String collection_refreshing;
    private String collection_last_refresh_time;
    private String collection_loading_more;
    private String collection_no_more_data;

    private LoadingTextConfig(Context context){
        this.context=context;
        collection_pull_to_refresh=context.getString(R.string.collection_pull_to_refresh);
        collection_release_refresh=context.getString(R.string.collection_release_refresh);
        collection_refresh_done=context.getString(R.string.collection_refresh_done);
        collection_refreshing=context.getString(R.string.collection_refreshing);
        collection_last_refresh_time=context.getString(R.string.collection_last_refresh_time);
        collection_loading_more=context.getString(R.string.collection_loading_more);
        collection_no_more_data=context.getString(R.string.collection_no_more_data);
    }

    public LoadingTextConfig setCollectionRefreshDone(String text){
        collection_refresh_done=text;
        return this;
    }

    public String getCollectionRefreshDone(){
        return collection_refresh_done;
    }

    public LoadingTextConfig setCollectionRefreshing(String text){
        collection_refreshing=text;
        return this;
    }

    public String getCollectionRefreshing(){
        return collection_refreshing;
    }

    public LoadingTextConfig setCollectionLastRefreshTimeTip(String text){
        collection_last_refresh_time=text;
        return this;
    }

    public String getCollectionLastRefreshTime(){
        return collection_last_refresh_time;
    }

    public LoadingTextConfig setCollectionLoadingMore(String text){
        collection_loading_more=text;
        return this;
    }

    public String getCollectionLoadingMore(){
        return collection_loading_more;
    }

    public LoadingTextConfig setCollectionNoMoreData(String text){
        collection_no_more_data=text;
        return this;
    }

    public String getCollectionNoMoreData(){
        return collection_no_more_data;
    }

    public LoadingTextConfig setCollectionPullDownRefreshText(String text){
        collection_pull_to_refresh=text;
        return this;
    }

    public String getCollectionPullRefreshText(){
        return collection_pull_to_refresh;
    }

    public LoadingTextConfig setCollectionPullReleaseText(String text){
        collection_release_refresh=text;
        return this;
    }

    public String getCollectionPullReleaseText(){
        return collection_release_refresh;
    }

}
