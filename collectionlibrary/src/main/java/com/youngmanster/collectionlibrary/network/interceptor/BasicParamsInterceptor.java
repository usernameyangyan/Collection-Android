package com.youngmanster.collectionlibrary.network.interceptor;

import android.text.TextUtils;

import com.youngmanster.collectionlibrary.utils.LogUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * @author yangyan
 * @Date on 2019/7/9
 * @ Description:OkHttp请求添加公共参数、公共Headers
 */
public class BasicParamsInterceptor implements Interceptor {
    Map<String, String> queryParamsMap = new HashMap<>(); // 添加到 URL 末尾，Get Post 方法都使用
    Map<String, String> paramsMap = new HashMap<>(); // 添加到公共参数到消息体，适用 Post 请求
    Map<String, String> headerParamsMap = new HashMap<>(); // 公共 Headers 添加
    List<String> headerLinesList = new ArrayList<>(); // 消息头 集合形式，一次添加一行

    // 私有构造器
    private BasicParamsInterceptor() {}

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        Request.Builder requestBuilder = request.newBuilder();

        // process header params inject
        Headers.Builder headerBuilder = request.headers().newBuilder();
        // 以 Entry 添加消息头
        if (headerParamsMap.size() > 0) {
            Iterator iterator = headerParamsMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                headerBuilder.add((String) entry.getKey(), (String) entry.getValue());
            }
        }
        // 以 String 形式添加消息头
        if (headerLinesList.size() > 0) {
            for (String line: headerLinesList) {
                headerBuilder.add(line);

            }
        }
        requestBuilder.headers(headerBuilder.build());

        // process queryParams inject whatever it's GET or POST
        if (queryParamsMap.size() > 0) {
            request = injectParamsIntoUrl(request.url().newBuilder(), requestBuilder, queryParamsMap);
        }

        // process post body inject
        if (paramsMap.size() > 0) {
            if (canInjectIntoBody(request)) {
                FormBody.Builder formBodyBuilder = new FormBody.Builder();
                for(Map.Entry<String, String> entry : paramsMap.entrySet()) {
                    formBodyBuilder.add(entry.getKey(),entry.getValue());
                }

                RequestBody formBody = formBodyBuilder.build();
                String postBodyString = bodyToString(request.body());
                postBodyString += ((postBodyString.length() > 0) ? "&" : "") +  bodyToString(formBody);
                requestBuilder.post(RequestBody.create(MediaType.parse("application/x-www-form-urlencoded;charset=UTF-8"), postBodyString));
            }
        }
        request = requestBuilder.build();
        return chain.proceed(request);
    }

    /**
     *  确认是否是 post 请求
     * @param request 发出的请求
     * @return true 需要注入公共参数
     */
    private boolean canInjectIntoBody(Request request) {
        if (request == null) {
            return false;
        }
        if (!TextUtils.equals(request.method(), "POST")) {
            return false;
        }
        RequestBody body = request.body();
        if (body == null) {
            return false;
        }
        MediaType mediaType = body.contentType();
        if (mediaType == null) {
            return false;
        }
        if (!TextUtils.equals(mediaType.subtype(), "x-www-form-urlencoded")) {
            return false;
        }
        return true;
    }

    // func to inject params into url
    private Request injectParamsIntoUrl(HttpUrl.Builder httpUrlBuilder, Request.Builder requestBuilder, Map<String, String> paramsMap) {
        if (paramsMap.size() > 0) {
            Iterator iterator = paramsMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                httpUrlBuilder.addQueryParameter((String) entry.getKey(), (String) entry.getValue());
            }
            requestBuilder.url(httpUrlBuilder.build());
            return requestBuilder.build();
        }
        return null;
    }

    private static String bodyToString(final RequestBody request){
        try {
            final RequestBody copy = request;
            final Buffer buffer = new Buffer();
            if(copy != null)
                copy.writeTo(buffer);
            else
                return "";
            return buffer.readUtf8();
        }
        catch (final IOException e) {
            return "did not work";
        }
    }

    public static class Builder {

        BasicParamsInterceptor interceptor;

        public Builder() {
            interceptor = new BasicParamsInterceptor();
        }

        // 添加公共参数到 post 消息体
        public Builder addParam(String key, String value) {
            interceptor.paramsMap.put(key, value);
            return this;
        }

        // 添加公共参数到 post 消息体
        public Builder addParamsMap(Map<String, String> paramsMap) {
            interceptor.paramsMap.putAll(paramsMap);
            return this;
        }

        // 添加公共参数到消息头
        public Builder addHeaderParam(String key, String value) {
            interceptor.headerParamsMap.put(key, value);
            return this;
        }

        // 添加公共参数到消息头
        public Builder addHeaderParamsMap(Map<String, String> headerParamsMap) {
            interceptor.headerParamsMap.putAll(headerParamsMap);
            return this;
        }

        // 添加公共参数到消息头
        public Builder addHeaderLine(String headerLine) {
            int index = headerLine.indexOf(":");
            if (index == -1) {
                throw new IllegalArgumentException("Unexpected header: " + headerLine);
            }
            interceptor.headerLinesList.add(headerLine);
            return this;
        }

        // 添加公共参数到消息头
        public Builder addHeaderLinesList(List<String> headerLinesList) {
            for (String headerLine: headerLinesList) {
                int index = headerLine.indexOf(":");
                if (index == -1) {
                    throw new IllegalArgumentException("Unexpected header: " + headerLine);
                }
                interceptor.headerLinesList.add(headerLine);
            }
            return this;
        }

        // 添加公共参数到 URL
        public Builder addQueryParam(String key, String value) {
            interceptor.queryParamsMap.put(key, value);
            return this;
        }

        // 添加公共参数到 URL
        public Builder addQueryParamsMap(Map<String, String> queryParamsMap) {
            interceptor.queryParamsMap.putAll(queryParamsMap);
            return this;
        }

        public BasicParamsInterceptor build() {
            return interceptor;
        }

    }
}
