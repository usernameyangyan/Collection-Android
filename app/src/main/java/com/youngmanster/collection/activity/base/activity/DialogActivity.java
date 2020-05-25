package com.youngmanster.collection.activity.base.activity;

import android.view.View;

import com.youngmanster.collection.R;
import com.youngmanster.collection.activity.base.dialog.CustomizeDialog;
import com.youngmanster.collection.base.BaseActivity;
import com.youngmanster.collectionlibrary.base.dialog.CommonDialog;
import com.youngmanster.collectionlibrary.utils.DisplayUtils;
import com.youngmanster.collectionlibrary.utils.ToastUtils;

import butterknife.OnClick;

/**
 * Created by yangyan
 * on 2018/5/14.
 */

public class DialogActivity extends BaseActivity {

	private CommonDialog commonDialog;
	private String[] items = {"男生","女生"};

	@Override
	public int getLayoutId() {
		return R.layout.activity_dialog;
	}

	@Override
	public void init() {
		defineActionBarConfig.setTitle(getString(R.string.dialog_title));
	}

	@Override
	public void requestData() {

	}

	@OnClick({R.id.dialog_btn1, R.id.dialog_btn2, R.id.dialog_btn3, R.id.dialog_btn5, R.id.dialog_btn6})
	public void onMenuClick(View view) {
		switch (view.getId()) {
			case R.id.dialog_btn1:
				commonDialog = new CommonDialog(CommonDialog.DIALOG_TEXT_TWO_BUTTON_DEFAULT, "默认样式", "这是一个默认的Dialog样式", new CommonDialog.OnDialogClickListener() {
					@Override
					public void onDialogClick(int state) {
						switch (state) {
							case CommonDialog.ONCLICK_LEFT:
								ToastUtils.showToast(DialogActivity.this,"点击了取消按钮");
								break;
							case CommonDialog.ONCLICK_RIGHT:
								ToastUtils.showToast(DialogActivity.this,"点击了确定按钮");
								break;
						}
					}
				});
				commonDialog.show(getFragmentManager(),null);
				break;
			case R.id.dialog_btn2:
				commonDialog = new CommonDialog( CommonDialog.DIALOG_LOADING_PROGRASSBAR, "正在加载中，请稍后");
				commonDialog.show(getFragmentManager(),null);
				break;
			case R.id.dialog_btn3:
				commonDialog = new CommonDialog(CommonDialog.DIALOG_TEXT_TWO_BUTTON_CUSTOMIZE, "修改点击按钮", "这是一个修改按钮提示的Dialog样式", "退出", "去设置", new CommonDialog.OnDialogClickListener() {
					@Override
					public void onDialogClick(int state) {
						switch (state) {
							case CommonDialog.ONCLICK_LEFT:
								ToastUtils.showToast(DialogActivity.this,"点击了退出按钮");
								break;
							case CommonDialog.ONCLICK_RIGHT:
								ToastUtils.showToast(DialogActivity.this,"点击了去设置按钮");
								break;
						}
					}
				});
				commonDialog.show(getFragmentManager(),null);
				break;
			case R.id.dialog_btn5:
				commonDialog = new CommonDialog(CommonDialog.DIALOG_CHOICE_ITEM, "单项选择", items, new CommonDialog.OnDialogClickListener() {
					@Override
					public void onDialogClick(int state) {
						switch (state) {
							case CommonDialog.ONCLICK_LEFT:
								ToastUtils.showToast(DialogActivity.this,"点击了取消按钮");
								break;
							case CommonDialog.ONCLICK_RIGHT:
								ToastUtils.showToast(DialogActivity.this,"点击了确定按钮");
								break;
							default:
								ToastUtils.showToast(DialogActivity.this,items[state]);
								break;
						}
					}
				});
				commonDialog.show(getFragmentManager(),null);
				break;
			case R.id.dialog_btn6:
				CustomizeDialog customizeDialog=new CustomizeDialog();
				//如果需要设置dialog的高度
				customizeDialog.setDialogHeight(DisplayUtils.dip2px(this,300f));
				customizeDialog.setOnlyBackPressDialogCancel();
				customizeDialog.show(getFragmentManager(),null);
				break;
		}

	}
}
