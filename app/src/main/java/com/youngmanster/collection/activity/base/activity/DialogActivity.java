package com.youngmanster.collection.activity.base.activity;

import android.view.View;

import com.youngmanster.collection.R;
import com.youngmanster.collection.activity.base.dialog.CustomizeDialog;
import com.youngmanster.collection.base.BaseActivity;
import com.youngmanster.collectionlibrary.base.CommonDialog;
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
		setTitleContent(getString(R.string.dialog_title));
		showHomeAsUp(R.mipmap.ic_back_btn);
	}

	@Override
	public void requestData() {

	}

	@OnClick({R.id.dialog_btn1, R.id.dialog_btn2, R.id.dialog_btn3, R.id.dialog_btn4, R.id.dialog_btn5, R.id.dialog_btn6})
	public void onMenuClick(View view) {
		switch (view.getId()) {
			case R.id.dialog_btn1:
				commonDialog = new CommonDialog(this, CommonDialog.DIALOG_TEXT_TWO_BUTTON_DEFAULT, "默认样式", "这是一个默认的Dialog样式", new CommonDialog.OnDialogClickListener() {
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
				commonDialog.show();
				break;
			case R.id.dialog_btn2:
				commonDialog = new CommonDialog(this, CommonDialog.DIALOG_LOADING_PROGRASSBAR, "正在加载中，请稍后");
				commonDialog.show();
				break;
			case R.id.dialog_btn3:
				commonDialog = new CommonDialog(this, CommonDialog.DIALOG_TEXT_TWO_BUTTON_CUSTOMIZE, "修改点击按钮", "这是一个修改按钮提示的Dialog样式", "退出", "去设置", new CommonDialog.OnDialogClickListener() {
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
				commonDialog.show();
				break;
			case R.id.dialog_btn4:
				commonDialog = new CommonDialog(this, CommonDialog.DIALOG_DISPLAY_ADVERTISING, "广告图", "这是一个广告图的Dialog样式", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1526872907&di=cace2736f732d0d1716aadbf489e0361&imgtype=jpg&er=1&src=http%3A%2F%2Fscimg.jb51.net%2Fallimg%2F161205%2F106-161205161K5161.jpg", new CommonDialog.OnDialogClickListener() {
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
				}, new CommonDialog.OnAdvertisingClickLister() {
					@Override
					public void onAdvertisingClick() {
						ToastUtils.showToast(DialogActivity.this,"点击了图片");
					}
				});
				commonDialog.show();
				break;
			case R.id.dialog_btn5:
				commonDialog = new CommonDialog(this, CommonDialog.DIALOG_CHOICE_ITEM, "单项选择", items, new CommonDialog.OnDialogClickListener() {
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
				commonDialog.show();
				break;
			case R.id.dialog_btn6:
				CustomizeDialog customizeDialog=new CustomizeDialog(this);
				//如果需要设置dialog的高度
				customizeDialog.setDialogHeight(350);
				customizeDialog.setDialogCancel(false);
				customizeDialog.show();
				break;
		}

	}
}
