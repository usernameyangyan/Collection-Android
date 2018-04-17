package com.youngmanster.collectionlibrary.network.gson;

import com.google.gson.Gson;
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
	static Gson gson = new Gson();

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

		return gson.fromJson(reader, type);
	}


	/**
	 * 获取json的String值
	 *
	 * @return
	 */
	public static String getStrContent(String json, String key) {
		try {
			JSONObject jsonObj = new JSONObject(json);
			String content=jsonObj.getString(key).replaceAll("\\\\r\\\\n","\n");
			return content;
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return "";
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
		return gson.fromJson(reader, type);
	}

	public static <T> String getJson(T t) {
		return gson.toJson(t);
	}

}
