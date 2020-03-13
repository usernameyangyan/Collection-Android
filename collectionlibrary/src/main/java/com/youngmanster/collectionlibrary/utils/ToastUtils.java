package com.youngmanster.collectionlibrary.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Toast提示类
 * Created by yangyan
 * on 2018/5/14.
 */

public class ToastUtils {

	public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}

	public static void showToast(Context context,int strResId) {
		String text = context.getString(strResId);
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}

	public static void showLongToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
	}

	public static void showLongToast(Context context,int strResId) {
		String text = context.getString(strResId);
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
	}



    public static void showToast(Context context, String message,int gravity) {
        Toast toast=Toast.makeText(context, message, Toast.LENGTH_SHORT);
        setGravity(toast,gravity);
        toast.show();
    }

    public static void showToast(Context context,int strResId,int gravity) {
        String text = context.getString(strResId);
        Toast toast=Toast.makeText(context, text, Toast.LENGTH_SHORT);
        setGravity(toast,gravity);
        toast.show();

    }

    public static void showLongToast(Context context, String message,int gravity) {
        Toast toast=Toast.makeText(context, message, Toast.LENGTH_LONG);
        setGravity(toast,gravity);
        toast.show();
    }

    public static void showLongToast(Context context,int strResId,int gravity) {
        String text = context.getString(strResId);
        Toast toast=Toast.makeText(context, text, Toast.LENGTH_LONG);
        setGravity(toast,gravity);
        toast.show();
    }


    private static void setGravity(Toast toast,int gravity){
	    toast.setGravity(gravity,0,0);
    }
}
