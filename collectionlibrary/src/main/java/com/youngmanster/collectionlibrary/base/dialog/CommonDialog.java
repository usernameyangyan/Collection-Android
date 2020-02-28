package com.youngmanster.collectionlibrary.base.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.youngmanster.collectionlibrary.R;
import com.youngmanster.collectionlibrary.utils.GlideUtils;

/**
 * Created by yangyan
 * on 2018/5/13.
 */

public class CommonDialog extends BaseDialog {

	public static final int DIALOG_TEXT_TWO_BUTTON_DEFAULT = 1;// 提示信息确认和取消
	public static final int DIALOG_TEXT_TWO_BUTTON_CUSTOMIZE = 2;//自定义按钮
	public static final int DIALOG_LOADING_PROGRASSBAR = 3;// 等待中
	public static final int DIALOG_CHOICE_ITEM = 4;//单项选择

	public static final int ONCLICK_LEFT = 0x10001;// 点击对话框的左边
	public static final int ONCLICK_RIGHT = 0x1002;// 点击对话框的右边

	//点击事件监听
	private OnDialogClickListener listener;

	private int type;
	private String title;
	private String content;
	private String leftBtn;
	private String rightBtn;
	private String[] items;

	public CommonDialog(Context context, int type, String content) {
		super(context);
		this.type = type;
		this.content = content;
		setContentView(getLayoutRes());
	}

	public CommonDialog(Context context, int type, String title, String[] items, OnDialogClickListener listener) {
		super(context);
		this.type = type;
		this.title = title;
		this.items = items;
		this.listener = listener;
		setContentView(getLayoutRes());
	}

	public CommonDialog(Context context, int type, String title, String content, OnDialogClickListener listener) {
		super(context);
		this.type = type;
		this.title = title;
		this.content = content;
		this.listener = listener;
		setContentView(getLayoutRes());
	}

	public CommonDialog(Context context, int type, String title, String content, String leftBtn, String rightBtn, OnDialogClickListener listener) {
		super(context);
		this.type = type;
		this.title = title;
		this.content = content;
		this.leftBtn = leftBtn;
		this.rightBtn = rightBtn;
		this.listener = listener;
		setContentView(getLayoutRes());
	}


	private int getLayoutRes() {
		int layoutRes = 0;
		switch (type) {
			case DIALOG_TEXT_TWO_BUTTON_DEFAULT:
				layoutRes = R.layout.collection_library_dialog_title_text_two_button;
				break;
			case DIALOG_TEXT_TWO_BUTTON_CUSTOMIZE:
				layoutRes = R.layout.collection_library_dialog_title_text_two_button;
				break;
			case DIALOG_LOADING_PROGRASSBAR:
				layoutRes = R.layout.collection_library_dialog_progress;
			case DIALOG_CHOICE_ITEM:
				break;
		}
		return layoutRes;
	}

	@Override
	protected void onViewCreated() {
		switch (type) {
			case DIALOG_TEXT_TWO_BUTTON_DEFAULT:
				((TextView) mainView.findViewById(R.id.dg_content)).setText(content);
				builder.setTitle(title);
				builder.setPositiveButton(R.string.collection_dialog_submit, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (listener != null)
							listener.onDialogClick(ONCLICK_RIGHT);
					}
				});
				builder.setNegativeButton(R.string.collection_dialog_cancel, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (listener != null)
							listener.onDialogClick(ONCLICK_LEFT);
					}
				});

				break;
			case DIALOG_TEXT_TWO_BUTTON_CUSTOMIZE:
				((TextView) mainView.findViewById(R.id.dg_content)).setText(content);
				builder.setTitle(title);
				builder.setPositiveButton(rightBtn, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (listener != null)
							listener.onDialogClick(ONCLICK_RIGHT);
					}
				});
				builder.setNegativeButton(leftBtn, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (listener != null)
							listener.onDialogClick(ONCLICK_LEFT);
					}
				});
				break;

			case DIALOG_LOADING_PROGRASSBAR:
				((TextView) mainView.findViewById(R.id.tv_ProgressTip)).setText(content);
				break;
			case DIALOG_CHOICE_ITEM:
				builder.setTitle(title);
				DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
							case AlertDialog.BUTTON_POSITIVE:
								if (listener != null)
									listener.onDialogClick(ONCLICK_RIGHT);
								break;
							case AlertDialog.BUTTON_NEGATIVE:
								if (listener != null)
									listener.onDialogClick(ONCLICK_LEFT);
								break;
							default:
								if (listener != null)
									listener.onDialogClick(which);
								break;
						}

					}
				};
				builder.setSingleChoiceItems(items, 0, onClickListener);
				builder.setPositiveButton(R.string.collection_dialog_submit, onClickListener);
				builder.setNegativeButton(R.string.collection_dialog_cancel, onClickListener);
				break;
		}
	}

	/**
	 * 点击监听
	 */
	public interface OnDialogClickListener {
		void onDialogClick(int state);
	}

}
