package com.youngmanster.collectionlibrary.base;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.youngmanster.collectionlibrary.utils.DisplayUtils;

/**
 * Created by yangyan
 * on 2018/5/13.
 */

public abstract class BaseDialog {

	private AlertDialog alertDialog;
	protected AlertDialog.Builder builder;
	protected int screenWidthPixels;
	protected float space;
	protected float height;
	protected Context context;
	protected View mainView;

	public BaseDialog(Context context) {
		this.context=context;
	}

	public void setContentView(int layoutRes){
		if(layoutRes!=0){
			this.mainView= LayoutInflater.from(context).inflate(layoutRes,null);
		}else{
			this.mainView=null;
		}

		create();
	}

	public void setContentView(View mainView){
		if(mainView!=null) {
			this.mainView = mainView;
		}
		create();
	}

	private void create() {
		screenWidthPixels = DisplayUtils.getScreenWidthPixels(context);
		space = DisplayUtils.dip2pxByFloat(context, 20);
		builder = new AlertDialog.Builder(context);
		if(mainView!=null){
			builder.setView(mainView);
		}
		initUI();
		alertDialog = builder.create();
	}

	public void show() {
		alertDialog.show();
		WindowManager.LayoutParams params = alertDialog.getWindow().getAttributes();
		params.width = (int) (screenWidthPixels-space*2);
		if(height>0){
			params.height= (int) height;
		}
		alertDialog.getWindow().setAttributes(params);
	}

	public boolean isShowing() {
		return alertDialog != null && alertDialog.isShowing();
	}

	public void dismiss() {
		if (alertDialog != null) {
			alertDialog.dismiss();
		}
	}

	/**
	 * 点击返回键和外部不可取消
	 * */
	public void setCancelable(boolean b) {
		if (alertDialog != null) {
			alertDialog.setCancelable(b);
		} else if (builder != null) {
			builder.setCancelable(b);
		}
	}

	/**
	 * 点击返回键可以取消
	 * */
	public void setDialogCancel(boolean isCancel){
		if (alertDialog != null) {
			alertDialog.setCanceledOnTouchOutside(isCancel);
		}
	}

	public void setDialogInterval(int interval){
		space = DisplayUtils.dip2px(context, interval);
	}

	public void setDialogHeight(int height){
		this.height = DisplayUtils.dip2px(context, height);
	}

	protected abstract void initUI();

}
