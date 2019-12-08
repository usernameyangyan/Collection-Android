package com.youngmanster.collectionlibrary.refreshrecyclerview.base.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 基本的BaseViewHolder
 * Created by yangyan
 * on 2018/3/14.
 */
public class BaseViewHolder extends RecyclerView.ViewHolder {

	private SparseArray<View> mViews;
	private View mConvertView;
	private Context mContext;

	public BaseViewHolder(Context context, View itemView) {
		super(itemView);
		mContext = context;
		mConvertView = itemView;
		mViews = new SparseArray<>();
	}

	/**
	 * 创建BaseViewHolder
	 */

	public static BaseViewHolder onCreateViewHolder(Context context, ViewGroup parent, int layoutRes){
		View itemView= LayoutInflater.from(context).inflate(layoutRes,parent,false);
		BaseViewHolder baseViewHolder=new BaseViewHolder(context,itemView);
		return baseViewHolder;
	}

	public static BaseViewHolder onCreateViewHolder(Context context,View itemView){
		BaseViewHolder baseViewHolder=new BaseViewHolder(context,itemView);
		return baseViewHolder;
	}

	public View getConvertView(){
		return mConvertView;
	}


	/**
	 * 根据id获取View
	 * @param viewId
	 * @param <T>
	 * @return
	 */
	public <T extends View> T getView(int viewId){

		View view=mViews.get(viewId);
		if(view==null){
			view=mConvertView.findViewById(viewId);
			mViews.put(viewId,view);
		}

		return (T) view;
	}

	/**======================================View的基本设置==================================================================================*/

	/**
	 * TextView
	 */
	public BaseViewHolder setText(int viewId,String content){
		TextView tv=getView(viewId);
		tv.setText(content);
		return this;
	}

	public BaseViewHolder setText(int viewId,int resId){
		TextView tv=getView(viewId);
		tv.setText(mContext.getString(resId));
		return this;
	}

	public BaseViewHolder setTextColor(int viewId,int color){
		TextView tv=getView(viewId);
		tv.setTextColor(color);
		return this;
	}

	public BaseViewHolder setTextColorRes(int viewId,int colorRes){
		TextView tv=getView(viewId);
		tv.setTextColor(mContext.getResources().getColor(colorRes));
		return this;
	}

	/**
	 * 设置背景颜色
	 */

	public BaseViewHolder setBackgroundColorRes(int viewId,int colorRes){
		View view=getView(viewId);
		view.setBackgroundColor(mContext.getResources().getColor(colorRes));
		return this;
	}

	public BaseViewHolder setBackgroundColor(int viewId,int color){
		View view=getView(viewId);
		view.setBackgroundColor(color);
		return this;
	}

	/**
	 * ImageView
	 */
	public BaseViewHolder setImageResource(int viewId,int resId){
		ImageView view=getView(viewId);
		view.setImageResource(resId);
		return this;
	}

	public BaseViewHolder setImageBitmap(int viewId, Bitmap bitmap){
		ImageView view=getView(viewId);
		view.setImageBitmap(bitmap);
		return this;
	}

	public BaseViewHolder setImageDrawable(int viewId, Drawable drawable){
		ImageView view=getView(viewId);
		view.setImageDrawable(drawable);
		return this;
	}

	/**
	 * View Tag
	 */

	public BaseViewHolder setTag(int viewId,Object tag){
		View view=getView(viewId);
		view.setTag(tag);
		return this;
	}

	public BaseViewHolder setTag(int viewId,int key,Object tag){
		View view=getView(viewId);
		view.setTag(key,tag);
		return this;
	}

	/**
	 * 点击事件
	 */

	public BaseViewHolder setOnClickListener(int viewId,View.OnClickListener onClickListener){
		View view=getView(viewId);
		view.setOnClickListener(onClickListener);
		return this;
	}

	public BaseViewHolder setOnLongClickListener(int viewId,View.OnLongClickListener onLongClickListener){
		View view=getView(viewId);
		view.setOnLongClickListener(onLongClickListener);
		return this;
	}


}
