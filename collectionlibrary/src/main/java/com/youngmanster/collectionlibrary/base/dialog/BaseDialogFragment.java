package com.youngmanster.collectionlibrary.base.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.youngmanster.collectionlibrary.utils.DisplayUtils;

/**
 * Created by yangy
 * 2020/5/22
 * Describe:
 */
public abstract class BaseDialogFragment extends DialogFragment implements View.OnTouchListener {

    public   View mainView;
    private OnDismissListener dismissListener;
    public AlertDialog.Builder builder;
    private int  screenWidthPixels = 0;
    private int  space = 0;
    private int height = 0;
    public int  layoutRes=0;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        space = DisplayUtils.dip2px(getActivity(), 20f);

        if(this.layoutRes!=0){
            this.mainView = LayoutInflater.from(getActivity()).inflate(layoutRes, null);
        }else{
            if(setContentView()!=0){
                this.mainView = LayoutInflater.from(getActivity()).inflate(setContentView(), null);
            }

        }
        builder = new AlertDialog.Builder(getActivity());
        if (mainView != null) {
            builder.setView(mainView);
        }

        onViewCreated();
        return builder.create();
    }


    private boolean mCancelable = false;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        boolean isShow  = this.getShowsDialog();
        this.setShowsDialog(false);
        super.onActivityCreated(savedInstanceState);
        this.setShowsDialog(isShow);

        Activity activity  = getActivity();

        if (activity != null){
            this.getDialog().setOwnerActivity(activity);
        }

        this.setCancelable(false);

        this.getDialog().getWindow().getDecorView().setOnTouchListener(this);

        this.getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if(!isAllCancelable){
                    return false;
                }

                if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_ESCAPE){
                    dismiss();
                    return true;
                }

                return false;
            }
        });

        if (savedInstanceState != null)
        {
            Bundle dialogState = savedInstanceState.getBundle("android:savedDialogState");
            if (dialogState != null)
            {
                this.getDialog().onRestoreInstanceState(dialogState);
            }
        }

        showConfig();
    }



    public abstract void onViewCreated();
    /**
     * 设置布局
     */
    public int setContentView(){
        return 0;
    }


    /**
     * 点击返回键和外部不可取消
     */
    private boolean isAllCancelable=true;
    public void setAllCancelable(boolean b) {
        isAllCancelable=b;
    }
    /**
     * 点击返回键可以取消
     */
    public void setOnlyBackPressDialogCancel() {
        mCancelable=true;
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(!this.isAllCancelable){
            return false;
        }
        if (!mCancelable && this.getDialog().isShowing()) {
            dismiss();
            return true;
        }
        return false;
    }


    public void setOnDismissListener(OnDismissListener dismissListener){
        this.dismissListener=dismissListener;
    }

    private void onDismiss(){

        if(dismissListener!=null){
            dismissListener.dismissListener();
        }

    }

    private void showConfig() {
        screenWidthPixels = DisplayUtils.getScreenWidthPixels(getActivity());
        WindowManager.LayoutParams params = this.getDialog().getWindow().getAttributes();
        params.width   = (screenWidthPixels - space * 2);
        params.gravity  = Gravity.CENTER;
        if (height > 0) {
            params.height = height;
        }
        this.getDialog().getWindow().setAttributes(params);
    }




    public void setDialogInterval(int interval) {
        space = interval;
    }

    public void setDialogHeight(int height) {
        this.height = height;
    }


    interface OnDismissListener {
        void dismissListener();
    }

    public void show(FragmentManager manager, String tag) {
        manager.beginTransaction().add(this, tag).commitAllowingStateLoss();
    }


    public int show(FragmentTransaction transaction, String tag) {
        return transaction.add(this, tag).commitAllowingStateLoss();
    }

    public void dismiss() {
        try {
            super.dismiss();
            onDismiss();
        }catch(IllegalStateException e){
        }
    }
}
