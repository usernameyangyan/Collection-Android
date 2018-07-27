package com.youngmanster.collection.activity.base.popupwindow;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

import com.youngmanster.collection.R;
import com.youngmanster.collectionlibrary.base.BasePopupWindow;

/**
 * Created by yangyan
 * on 2018/6/7.
 */

public class PopupSlideBottom extends BasePopupWindow {

	public PopupSlideBottom(Context context) {
		super(context);
	}

	@Override
	public int getPopupLayoutRes() {
		return R.layout.popup_slide_bottom;
	}

	@Override
	public int getPopupAnimationStyleRes() {
		return R.style.animation_bottom;
	}
}
