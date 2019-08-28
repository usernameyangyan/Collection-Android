package com.youngmanster.collection.activity.base.popupwindow;

import android.content.Context;
import android.view.ViewGroup;

import com.youngmanster.collection.R;
import com.youngmanster.collectionlibrary.base.dialog.BasePopupWindow;

/**
 * Created by yangyan
 * on 2018/6/7.
 */

public class PopupTip extends BasePopupWindow {

	public PopupTip(Context context) {
		super(context, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		setShowMaskView(false);
	}

	@Override
	public int getPopupLayoutRes() {
		return R.layout.popup_tip;
	}

	@Override
	public int getPopupAnimationStyleRes() {
		return R.style.animation_scale;
	}
}
