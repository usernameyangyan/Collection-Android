package com.youngmanster.collection.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;


/**
 * Created by yangyan
 * on 2017/12/29.
 */

public class GlideUtil {

	/**
	 * 处理Glide You cannot start a load for a destroyed activity问题
	 * @param context
	 * @param url 网络图片
	 * @param defaultLoadingImg 默认加载图片
	 * @param imageView
	 */
	public static void loadImg(Context context, String url, int defaultLoadingImg,int defaultError,ImageView imageView){
		try{
			RequestOptions options = new RequestOptions()
					.placeholder(defaultLoadingImg)
					.error(defaultError);

			Glide.with(context)
					.load(url)
					.apply(options)
					.into(imageView);

		}catch (Exception exception){
			return;
		}

	}
}
