package com.youngmanster.collectionlibrary.network.convert;

import android.text.TextUtils;

import com.google.gson.ExclusionStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.youngmanster.collectionlibrary.config.Config;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by yangyan
 * on 2018/3/24.
 */

public class GsonUtils {

    /**
     * 转化为object
     *
     * @param reader        json数据
     * @param tranformClass 要转化的Class
     * @param <T>
     * @return
     */
    public static <T> T fromJsonObject(String reader, Class tranformClass) {

        Type type = new ParameterizedTypeImpl(Config.MClASS, new Class[]{tranformClass});

        if (!TextUtils.isEmpty(Config.EXPOSEPARAM)) {
            try {
                JSONObject jsonObject = new JSONObject(reader);
                if (!jsonObject.has(Config.EXPOSEPARAM)||TextUtils.isEmpty(jsonObject.get(Config.EXPOSEPARAM).toString())) {
                    return getGsonExpose().fromJson(reader, type);
                } else {
                    return getGsonWithoutExpose().fromJson(reader, type);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        } else {
            return getGsonWithoutExpose().fromJson(reader, type);
        }
    }

    /**
     * 转化为列表
     *
     * @param reader    json数据
     * @param listClass 要转化的Class
     * @param <T>
     * @return
     */
    public static <T> T fromJsonArray(String reader, Class listClass) {
        // 生成List<T> 中的 List<T>
        Type listType = new ParameterizedTypeImpl(List.class, new Class[]{listClass});
        // 根据List<T>生成完整的Result<List<T>>
        Type type = new ParameterizedTypeImpl(Config.MClASS, new Type[]{listType});

        if (!TextUtils.isEmpty(Config.EXPOSEPARAM)) {
            try {
                JSONObject jsonObject = new JSONObject(reader);
                if (TextUtils.isEmpty(jsonObject.get(Config.EXPOSEPARAM).toString())) {
                    return getGsonExpose().fromJson(reader, type);
                } else {
                    return getGsonWithoutExpose().fromJson(reader, type);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        } else {
            return getGsonWithoutExpose().fromJson(reader, type);
        }
    }


    public static <T> T fromJsonNoCommonClass(String reader, Class listClass) {
        T result = (T) getGsonWithoutExpose().fromJson(reader, listClass);
        return result;
    }

    private static Gson gsonWithoutExpose;
    private static Gson gsonExpose;

    public static Gson getGsonWithoutExpose() {
        if (gsonWithoutExpose == null) {
            gsonWithoutExpose = new Gson();
        }
        return gsonWithoutExpose;
    }

    private static Gson getGsonExpose() {
        if (gsonExpose == null) {
            ExclusionStrategy excludeStrategy = new SetterExclusionStrategy(Config.EXPOSEPARAM);
            gsonExpose = new GsonBuilder().setExclusionStrategies(excludeStrategy)
                    .create();
        }
        return gsonExpose;
    }
}
