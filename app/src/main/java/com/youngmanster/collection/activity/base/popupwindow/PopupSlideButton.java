package com.youngmanster.collection.activity.base.popupwindow;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

import com.youngmanster.collection.R;
import com.youngmanster.collectionlibrary.base.BasePopupWindow;

/**
 * Created by yangyan
 * on 2018/6/7.
 */

public class PopupSlideButton extends BasePopupWindow {

	public PopupSlideButton(Context context) {
		super(context,ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		setShowMaskView(false);
	}

	@Override
	public int getPopupLayoutRes() {
		return R.layout.popup_slide_button;
	}

	@Override
	public int getPopupAnimationStyleRes() {
		return 0;
	}
}
