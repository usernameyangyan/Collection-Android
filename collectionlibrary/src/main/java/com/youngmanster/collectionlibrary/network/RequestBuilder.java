package com.youngmanster.collectionlibrary.network;

import com.youngmanster.collectionlibrary.network.rx.RxObservableListener;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;

/**
 * Created by yangyan
 * on 2018/4/2.
 */

public class RequestBuilder<T> {

    private ReqType reqType=ReqType.DEFAULT_CACHE_LIST;
    private Class clazz;
    private String url;
    private String filePath;
    private String fileName;
    private int limtHours=1;
    private boolean isUserCommonClass=true;
    private boolean isReturnOriginJson=false;
    private HttpType httpType=HttpType.DEFAULT_GET;
    private RxObservableListener<T> rxObservableListener;
    private Map<String, Object> requestParam;
    private MultipartBody.Part part;
    private boolean isDiskCacheNetworkSaveReturn;
    private Map<String,String> headers;

    public enum ReqType {
        //没有缓存
        NO_CACHE_MODEL,
        No_CACHE_LIST,
        //默认Retrofit缓存
        DEFAULT_CACHE_MODEL,
        DEFAULT_CACHE_LIST,
        //自定义磁盘缓存，返回List
        DISK_CACHE_LIST_LIMIT_TIME,
        //自定义磁盘缓存，返回Model
        DISK_CACHE_MODEL_LIMIT_TIME,
        //自定义磁盘缓存，没有网络返回磁盘缓存，返回List
        DISK_CACHE_NO_NETWORK_LIST,
        //自定义磁盘缓存，没有网络返回磁盘缓存，返回Model
        DISK_CACHE_NO_NETWORK_MODEL,
        //保存网络数据到本地磁盘，可以设定网络请求是否返回数据
        DISK_CACHE_NETWORK_SAVE_RETURN_MODEL,
        DISK_CACHE_NETWORK_SAVE_RETURN_LIST
    }

    public enum HttpType {
        DEFAULT_GET,
        DEFAULT_POST,
        JSON_PARAM_POST,
        FIELDMAP_POST,
        ONE_MULTIPART_POST
    }

    public RequestBuilder(RxObservableListener<T> rxObservableListener) {
        this.rxObservableListener = rxObservableListener;
        requestParam = new HashMap<>();
        headers=new HashMap<>();
    }


    public RequestBuilder setTransformClass(Class clazz) {
        this.clazz = clazz;
        return this;
    }

    public Class getTransformClass() {
        return clazz;
    }

    public RequestBuilder setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public RequestBuilder setFilePathAndFileName(String filePath,String fileName) {
        this.filePath = filePath;
        this.fileName=fileName;
        return this;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public RequestBuilder setLimtHours(int limtHours) {
        this.limtHours = limtHours;
        return this;
    }

    public int getLimtHours() {
        return limtHours;
    }

    public RequestBuilder setParam(String key, Object object) {
        requestParam.put(key, object);
        return this;
    }

    public RequestBuilder setHeader(String key, String value){
        headers.put(key, value);
        return this;
    }


    public RequestBuilder setHeaders(Map<String, String> headers) {
        this.headers.putAll(headers);
        return this;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public RequestBuilder setRequestParam(Map<String, Object> requestParam) {
        this.requestParam.putAll(requestParam);
        return this;
    }

    public Map<String, Object> getRequestParam() {
        return requestParam;
    }

    public RequestBuilder setDiskCacheNetworkSaveReturn(boolean isDiskCacheNetworkSaveReturn) {
        this.isDiskCacheNetworkSaveReturn = isDiskCacheNetworkSaveReturn;
        return this;
    }

    public boolean isDiskCacheNetworkSaveReturn() {
        return isDiskCacheNetworkSaveReturn;
    }

    public RequestBuilder setHttpTypeAndReqType(HttpType httpType,ReqType reqType) {
        this.httpType = httpType;
        this.reqType = reqType;
        return this;
    }

    public HttpType getHttpType() {
        return httpType;
    }

    public ReqType getReqType() {
        return reqType;
    }

    public MultipartBody.Part getPart() {
        return part;
    }

    public RequestBuilder setPart(MultipartBody.Part part) {
        this.part = part;
        return this;
    }

    public boolean isUserCommonClass() {
        return isUserCommonClass;
    }

    public RequestBuilder setUserCommonClass(boolean userCommonClass) {
        isUserCommonClass = userCommonClass;
        return this;
    }

    public boolean isReturnOriginJson() {
        return isReturnOriginJson;
    }

    public RequestBuilder setReturnOriginJson(boolean returnOriginJson) {
        isReturnOriginJson = returnOriginJson;
        return this;
    }

    public RxObservableListener<T> getRxObservableListener() {
        return rxObservableListener;
    }

}
