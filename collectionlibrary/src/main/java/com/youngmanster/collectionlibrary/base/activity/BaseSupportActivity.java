package com.youngmanster.collectionlibrary.base.activity;

import android.view.MotionEvent;

import com.youngmanster.collectionlibrary.base.fragmet.FragmentAnimator;
import com.youngmanster.collectionlibrary.base.helper.ExtraTransaction;
import com.youngmanster.collectionlibrary.base.helper.SupportActivityDelegate;

/**
 * Created by YoKey on 17/6/13.
 */

public interface BaseSupportActivity {
    SupportActivityDelegate getSupportDelegate();

    ExtraTransaction extraTransaction();

    FragmentAnimator getFragmentAnimator();

    void setFragmentAnimator(FragmentAnimator fragmentAnimator);

    FragmentAnimator onCreateFragmentAnimator();

    void post(Runnable runnable);

    void onBackPressed();

    void onBackPressedSupport();

    boolean dispatchTouchEvent(MotionEvent ev);
}
