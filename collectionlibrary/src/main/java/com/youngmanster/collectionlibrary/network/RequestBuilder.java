package com.youngmanster.collectionlibrary.network;

import android.support.annotation.IntDef;

import com.youngmanster.collectionlibrary.network.rx.RxObservableListener;

import java.io.File;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by yangyan
 * on 2018/4/2.
 */

public class RequestBuilder<T> {

    private int reqType= ReqType.DEFAULT_CACHE_LIST;
    private int reqMode= ReqMode.ASYNCHRONOUS;
    private Class clazz;//转化Class
    private String url;
    private String filePath;
    private String fileName;
    private String saveDownFilePath;
    private String saveDownFileName;
    private boolean isOpenBreakpointDownloadOrUpload;
    private int limitHours=1;
    private boolean isUseCommonClass=true;
    private boolean isReturnOriginJson=false;
    private int httpType= HttpType.DEFAULT_GET;
    private RxObservableListener<T> rxObservableListener;
    private Map<String, Object> requestParam;
    private MultipartBody.Part []parts;
    private boolean isDiskCacheNetworkSaveReturn;
    private Map<String, String> headers;

    public static class ReqType {

        public final static int DOWNLOAD_FILE_MODEL=0;
        //没有缓存
        public final static int NO_CACHE_MODEL=1;
        public final static int NO_CACHE_LIST=2;
        //默认Retrofit缓存
        public final static int DEFAULT_CACHE_MODEL=3;
        public final static int DEFAULT_CACHE_LIST=4;
        //自定义磁盘缓存，返回List
        public final static int DISK_CACHE_LIST_LIMIT_TIME=5;
        //自定义磁盘缓存，返回Model
        public final static int DISK_CACHE_MODEL_LIMIT_TIME=6;
        //自定义磁盘缓存，没有网络返回磁盘缓存，返回List
        public final static int DISK_CACHE_NO_NETWORK_LIST=7;
        //自定义磁盘缓存，没有网络返回磁盘缓存，返回Model
        public final static int DISK_CACHE_NO_NETWORK_MODEL=8;
        //保存网络数据到本地磁盘，可以设定网络请求是否返回数据
        public final static int DISK_CACHE_NETWORK_SAVE_RETURN_MODEL=9;
        public final static int DISK_CACHE_NETWORK_SAVE_RETURN_LIST=10;

        @IntDef({
        DOWNLOAD_FILE_MODEL,
        NO_CACHE_MODEL,
        NO_CACHE_LIST,
        DEFAULT_CACHE_MODEL,
        DEFAULT_CACHE_LIST,
        DISK_CACHE_LIST_LIMIT_TIME,
        DISK_CACHE_MODEL_LIMIT_TIME,
        DISK_CACHE_NO_NETWORK_LIST,
        DISK_CACHE_NO_NETWORK_MODEL,
        DISK_CACHE_NETWORK_SAVE_RETURN_MODEL,
        DISK_CACHE_NETWORK_SAVE_RETURN_LIST
        })


        @Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})//限制范围为字段/方法/参数
        @Retention(RetentionPolicy.SOURCE) //仅在源码期间有效，不会在编译进class文件，影响性能
        public @interface ReqTypeConstant {}
    }

    public static class HttpType{
        public final static int DEFAULT_GET  = 0;
        public final static int DEFAULT_POST  = 1;
        public final static int JSON_PARAM_POST  = 2;
        public final static int MULTIPLE_MULTIPART_POST  = 3;
        public final static int DOWNLOAD_FILE_GET  =4;

        @IntDef({ DEFAULT_GET,DEFAULT_POST,JSON_PARAM_POST,MULTIPLE_MULTIPART_POST,DOWNLOAD_FILE_GET})
        @Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})//限制范围为字段/方法/参数
        @Retention(RetentionPolicy.SOURCE) //仅在源码期间有效，不会在编译进class文件，影响性能
        public @interface HttpTypeConstant {}
    }

    public static class ReqMode{
        public final static int ASYNCHRONOUS  = 0;
        public final static int SYNCHRONIZATION  = 1;

        @IntDef({ ASYNCHRONOUS,SYNCHRONIZATION})
        @Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})//限制范围为字段/方法/参数
        @Retention(RetentionPolicy.SOURCE) //仅在源码期间有效，不会在编译进class文件，影响性能
        public @interface ReqModeConstant {}
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

    public RequestBuilder setSaveDownloadFilePathAndFileName(String filePath, String fileName){
        this.saveDownFilePath=filePath;
        this.saveDownFileName=fileName;
        return this;
    }


    public boolean isOpenBreakpointDownloadOrUpload() {
        return isOpenBreakpointDownloadOrUpload;
    }

    public RequestBuilder setOpenBreakpointDownloadOrUpload(boolean openBreakpointDownloadOrUpload) {
        isOpenBreakpointDownloadOrUpload = openBreakpointDownloadOrUpload;
        return this;
    }

    public String getSaveDownloadFilePath() {
        return saveDownFilePath;
    }

    public String getSaveDownloadFileName() {
        return saveDownFileName;
    }

    public RequestBuilder setFilePathAndFileName(String filePath, String fileName) {
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

    public int getLimitHours() {
        return limitHours;
    }

    public void setLimitHours(int limitHours) {
        this.limitHours = limitHours;
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

    public RequestBuilder setHttpTypeAndReqType(@HttpType.HttpTypeConstant int httpType, @ReqType.ReqTypeConstant int reqType) {
        this.httpType = httpType;
        this.reqType = reqType;
        return this;
    }

    public int getHttpType() {
        return httpType;
    }

    public int getReqType() {
        return reqType;
    }


    public RequestBuilder setFilePaths(String key, String[]imagePaths) {

        parts =new MultipartBody.Part[imagePaths.length];
        for(int i=0;i<imagePaths.length;i++){
            File file = new File(imagePaths[i]);
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part part = MultipartBody.Part.createFormData(key, file.getName(), requestFile);
            parts[i]=part;
        }
        return this;
    }

    public MultipartBody.Part[] getParts() {
        return parts;
    }

    public boolean isUseCommonClass() {
        return isUseCommonClass;
    }

    public RequestBuilder setUseCommonClass(boolean useCommonClass) {
        isUseCommonClass = useCommonClass;
        return this;
    }

    public boolean isReturnOriginJson() {
        return isReturnOriginJson;
    }

    public RequestBuilder setReturnOriginJson(boolean returnOriginJson) {
        isReturnOriginJson = returnOriginJson;
        return this;
    }


    public int getReqMode() {
        return reqMode;
    }

    public RequestBuilder setReqMode(@ReqMode.ReqModeConstant int reqMode) {
        this.reqMode = reqMode;
        return this;
    }

    public RxObservableListener<T> getRxObservableListener() {
        return rxObservableListener;
    }
}
