package com.youngmanster.collectionlibrary.network;

import android.net.ParseException;

import com.google.gson.JsonParseException;
import com.youngmanster.collectionlibrary.config.Config;
import com.youngmanster.collectionlibrary.utils.NetworkUtils;

import org.json.JSONException;


import okhttp3.Connection;
import retrofit2.HttpException;

/**
 * Created by yangyan
 * on 2018/3/21.
 */

public class NetWorkCodeException{

    /**
     * ========================返回的code==================================
     */
    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;

    /**
     * 自定义的code
     */

    //未知错误
    public static final int UNKNOWN = 1000;
    //解析错误
    public static final int PARSE_ERROR = 1001;
    //网络错误
    public static final int NETWORD_ERROR = 1002;
    //协议出错
    public static final int HTTP_ERROR = 1003;
    //证书出错
    public static final int SSL_ERROR = 1005;
    //服务器连接失败
    public static final int CONNECTION_SERVICE_ERROR = 1006;


    public static final String CHECK_PERMISSION="请检查权限";
    public static final String HTTP_ERROR_MESSAGE="服务器错误";
    public static final String PARSE_ERROR_MESSAGE="解析错误";
    public static final String NETWORD_ERROR_MESSAGE="网络错误";
    public static final String CONNECTION_SERVICE_ERROR_MESSAGE="连接服务器错误";
    public static final String SSL_ERROR_MESSAGE="证书验证失败";

    public static ResponseThrowable getResponseThrowable(Throwable e) {
        ResponseThrowable ex;

        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            ex = new ResponseThrowable();
            switch (httpException.code()) {
                case UNAUTHORIZED:
                case FORBIDDEN:
                    ex.code = HTTP_ERROR;
                    ex.message = CHECK_PERMISSION;
                    break;
                case NOT_FOUND:
                case REQUEST_TIMEOUT:
                case GATEWAY_TIMEOUT:
                case INTERNAL_SERVER_ERROR:
                case BAD_GATEWAY:
                case SERVICE_UNAVAILABLE:
                default:
                    ex.code = HTTP_ERROR;
                    ex.message = HTTP_ERROR_MESSAGE;
                    break;
            }
            return ex;
        } else if (e instanceof ServerException) {
            ServerException resultException = (ServerException) e;
            ex = new ResponseThrowable();
            ex.code = resultException.code;
            ex.message = resultException.message;
            return ex;
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            ex = new ResponseThrowable();
            ex.code = PARSE_ERROR;
            ex.message = PARSE_ERROR_MESSAGE;
            return ex;
        } else if (e instanceof Connection) {
            ex = new ResponseThrowable();

            if(NetworkUtils.isNetworkConnected(Config.CONTEXT)){
                ex.code = CONNECTION_SERVICE_ERROR;
                ex.message =CONNECTION_SERVICE_ERROR_MESSAGE;
            }else{
                ex.code = NETWORD_ERROR;
                ex.message =NETWORD_ERROR_MESSAGE;
            }

            return ex;
        } else if (e instanceof javax.net.ssl.SSLHandshakeException) {
            ex = new ResponseThrowable();
            ex.code = SSL_ERROR;
            ex.message =SSL_ERROR_MESSAGE;
            return ex;
        } else {
            ex = new ResponseThrowable();
            ex.code = NETWORD_ERROR;
            ex.message = NETWORD_ERROR_MESSAGE;
            return ex;
        }
    }


    public static class ResponseThrowable extends Exception {
        public int code;
        public String message;
    }

    public class ServerException extends RuntimeException {
        public int code;
        public String message;
    }


    public static boolean isError(int code){
        if(code==UNAUTHORIZED||code==FORBIDDEN||code==NOT_FOUND||
                code==REQUEST_TIMEOUT||code==INTERNAL_SERVER_ERROR||code==BAD_GATEWAY||
                code==SERVICE_UNAVAILABLE||code==GATEWAY_TIMEOUT||code==PARSE_ERROR||
                code==NETWORD_ERROR||code==HTTP_ERROR||code==SSL_ERROR||
                code==CONNECTION_SERVICE_ERROR){
            return true;
        }
        return false;
    }
}
