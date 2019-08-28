package com.youngmanster.collectionlibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by yangyan on 2018/10/24
 * Describe:键盘控制
 **/
public class SoftInputUtils {
    public static void hideSoftInput(Context context){
        View view = ((Activity)context).getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
