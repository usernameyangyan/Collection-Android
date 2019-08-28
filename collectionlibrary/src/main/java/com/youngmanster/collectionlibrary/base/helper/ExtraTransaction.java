package com.youngmanster.collectionlibrary.base.helper;

import android.os.Build;
import android.support.annotation.AnimRes;
import android.support.annotation.AnimatorRes;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.youngmanster.collectionlibrary.base.fragmet.BaseSupportFragment;

import java.util.ArrayList;

public abstract class ExtraTransaction {

    /**
     * @param tag Optional tag name for the fragment, to later retrieve the
     *            fragment with {@link SupportHelper#findFragment(FragmentManager, String)}
     *            , pop(String)
     *            or FragmentManager.findFragmentByTag(String).
     */
    public abstract ExtraTransaction setTag(String tag);

    /**
     * Set specific animation resources to run for the fragments that are
     * entering and exiting in this transaction. These animations will not be
     * played when popping the back stack.
     */
    public abstract ExtraTransaction setCustomAnimations(@AnimatorRes @AnimRes int targetFragmentEnter,
                                                         @AnimatorRes @AnimRes int currentFragmentPopExit);

    /**
     * Set specific animation resources to run for the fragments that are
     * entering and exiting in this transaction. The <code>currentFragmentPopEnter</code>
     * and <code>targetFragmentExit</code> animations will be played for targetFragmentEnter/currentFragmentPopExit
     * operations specifically when popping the back stack.
     */
    public abstract ExtraTransaction setCustomAnimations(@AnimatorRes @AnimRes int targetFragmentEnter,
                                                         @AnimatorRes @AnimRes int currentFragmentPopExit,
                                                         @AnimatorRes @AnimRes int currentFragmentPopEnter,
                                                         @AnimatorRes @AnimRes int targetFragmentExit);

    /**
     * Used with custom Transitions to map a View from a removed or hidden
     * Fragment to a View from a shown or added Fragment.
     * <var>sharedElement</var> must have a unique transitionName in the View hierarchy.
     *
     * @param sharedElement A View in a disappearing Fragment to match with a View in an
     *                      appearing Fragment.
     * @param sharedName    The transitionName for a View in an appearing Fragment to match to the shared
     *                      element.
     * @see Fragment#setSharedElementReturnTransition(Object)
     * @see Fragment#setSharedElementEnterTransition(Object)
     */
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    public abstract ExtraTransaction addSharedElement(View sharedElement, String sharedName);

    public abstract void loadRootFragment(int containerId, BaseSupportFragment toFragment);

    public abstract void loadRootFragment(int containerId, BaseSupportFragment toFragment, boolean addToBackStack, boolean allowAnim);

    public abstract void start(BaseSupportFragment toFragment);

    public abstract void startDontHideSelf(BaseSupportFragment toFragment);

    public abstract void startDontHideSelf(BaseSupportFragment toFragment, @BaseSupportFragment.LaunchMode int launchMode);

    public abstract void start(BaseSupportFragment toFragment, @BaseSupportFragment.LaunchMode int launchMode);

    public abstract void startForResult(BaseSupportFragment toFragment, int requestCode);

    public abstract void startForResultDontHideSelf(BaseSupportFragment toFragment, int requestCode);

    public abstract void startWithPop(BaseSupportFragment toFragment);

    public abstract void startWithPopTo(BaseSupportFragment toFragment, String targetFragmentTag, boolean includeTargetFragment);

    public abstract void replace(BaseSupportFragment toFragment);

    /**
     * 使用setTag()自定义Tag时，使用下面popTo()／popToChild()出栈
     *
     * @param targetFragmentTag     通过setTag()设置的tag
     * @param includeTargetFragment 是否包含目标(Tag为targetFragmentTag)Fragment
     */
    public abstract void popTo(String targetFragmentTag, boolean includeTargetFragment);

    public abstract void popTo(String targetFragmentTag, boolean includeTargetFragment, Runnable afterPopTransactionRunnable, int popAnim);

    public abstract void popToChild(String targetFragmentTag, boolean includeTargetFragment);

    public abstract void popToChild(String targetFragmentTag, boolean includeTargetFragment, Runnable afterPopTransactionRunnable, int popAnim);

    /**
     * Don't add this extraTransaction to the back stack.
     */
    public abstract DontAddToBackStackTransaction dontAddToBackStack();

    /**
     * 使用dontAddToBackStack() 加载Fragment时， 使用remove()移除Fragment
     */
    public abstract void remove(BaseSupportFragment fragment, boolean showPreFragment);

    public interface DontAddToBackStackTransaction {
        /**
         * add() +  hide(preFragment)
         */
        void start(BaseSupportFragment toFragment);

        /**
         * Only add()
         */
        void add(BaseSupportFragment toFragment);

        /**
         * replace()
         */
        void replace(BaseSupportFragment toFragment);
    }

    /**
     * Impl
     */
    final static class ExtraTransactionImpl<T extends BaseSupportFragment> extends ExtraTransaction implements DontAddToBackStackTransaction {
        private FragmentActivity mActivity;
        private T mSupportF;
        private Fragment mFragment;
        private TransactionDelegate mTransactionDelegate;
        private boolean mFromActivity;
        private TransactionRecord mRecord;

        ExtraTransactionImpl(FragmentActivity activity, T supportF, TransactionDelegate transactionDelegate, boolean fromActivity) {
            this.mActivity = activity;
            this.mSupportF = supportF;
            this.mFragment = (Fragment) supportF;
            this.mTransactionDelegate = transactionDelegate;
            this.mFromActivity = fromActivity;
            mRecord = new TransactionRecord();
        }

        @Override
        public ExtraTransaction setTag(String tag) {
            mRecord.tag = tag;
            return this;
        }

        @Override
        public ExtraTransaction setCustomAnimations(@AnimRes int targetFragmentEnter
                , @AnimRes int currentFragmentPopExit) {
            mRecord.targetFragmentEnter = targetFragmentEnter;
            mRecord.currentFragmentPopExit = currentFragmentPopExit;
            mRecord.currentFragmentPopEnter = 0;
            mRecord.targetFragmentExit = 0;
            return this;
        }

        @Override
        public ExtraTransaction setCustomAnimations(@AnimRes int targetFragmentEnter,
                                                    @AnimRes int currentFragmentPopExit,
                                                    @AnimRes int currentFragmentPopEnter,
                                                    @AnimRes int targetFragmentExit) {
            mRecord.targetFragmentEnter = targetFragmentEnter;
            mRecord.currentFragmentPopExit = currentFragmentPopExit;
            mRecord.currentFragmentPopEnter = currentFragmentPopEnter;
            mRecord.targetFragmentExit = targetFragmentExit;
            return this;
        }

        @Override
        public ExtraTransaction addSharedElement(View sharedElement, String sharedName) {
            if (mRecord.sharedElementList == null) {
                mRecord.sharedElementList = new ArrayList<>();
            }
            mRecord.sharedElementList.add(new TransactionRecord.SharedElement(sharedElement, sharedName));
            return this;
        }

        @Override
        public void loadRootFragment(int containerId, BaseSupportFragment toFragment) {
            loadRootFragment(containerId, toFragment, true, false);
        }

        @Override
        public void loadRootFragment(int containerId, BaseSupportFragment toFragment, boolean addToBackStack, boolean allowAnim) {
            toFragment.getSupportDelegate().mTransactionRecord = mRecord;
            mTransactionDelegate.loadRootTransaction(getFragmentManager(), containerId, toFragment, addToBackStack, allowAnim);
        }

        @Override
        public DontAddToBackStackTransaction dontAddToBackStack() {
            mRecord.dontAddToBackStack = true;
            return this;
        }

        @Override
        public void remove(BaseSupportFragment fragment, boolean showPreFragment) {
            mTransactionDelegate.remove(getFragmentManager(), (Fragment) fragment, showPreFragment);
        }

        @Override
        public void popTo(String targetFragmentTag, boolean includeTargetFragment) {
            popTo(targetFragmentTag, includeTargetFragment, null, TransactionDelegate.DEFAULT_POPTO_ANIM);
        }

        @Override
        public void popTo(String targetFragmentTag, boolean includeTargetFragment, Runnable afterPopTransactionRunnable, int popAnim) {
            mTransactionDelegate.popTo(targetFragmentTag, includeTargetFragment, afterPopTransactionRunnable, getFragmentManager(), popAnim);
        }

        @Override
        public void popToChild(String targetFragmentTag, boolean includeTargetFragment) {
            popToChild(targetFragmentTag, includeTargetFragment, null, TransactionDelegate.DEFAULT_POPTO_ANIM);
        }

        @Override
        public void popToChild(String targetFragmentTag, boolean includeTargetFragment, Runnable afterPopTransactionRunnable, int popAnim) {
            if (mFromActivity) {
                popTo(targetFragmentTag, includeTargetFragment, afterPopTransactionRunnable, popAnim);
            } else {
                mTransactionDelegate.popTo(targetFragmentTag, includeTargetFragment, afterPopTransactionRunnable, mFragment.getChildFragmentManager(), popAnim);
            }
        }

        @Override
        public void add(BaseSupportFragment toFragment) {
            toFragment.getSupportDelegate().mTransactionRecord = mRecord;
            mTransactionDelegate.dispatchStartTransaction(getFragmentManager(), mSupportF, toFragment, 0, BaseSupportFragment.STANDARD, TransactionDelegate.TYPE_ADD_WITHOUT_HIDE);
        }

        @Override
        public void start(BaseSupportFragment toFragment) {
            start(toFragment, BaseSupportFragment.STANDARD);
        }

        @Override
        public void startDontHideSelf(BaseSupportFragment toFragment) {
            toFragment.getSupportDelegate().mTransactionRecord = mRecord;
            mTransactionDelegate.dispatchStartTransaction(getFragmentManager(), mSupportF, toFragment, 0, BaseSupportFragment.STANDARD, TransactionDelegate.TYPE_ADD_WITHOUT_HIDE);
        }

        @Override
        public void startDontHideSelf(BaseSupportFragment toFragment, @BaseSupportFragment.LaunchMode int launchMode) {
            toFragment.getSupportDelegate().mTransactionRecord = mRecord;
            mTransactionDelegate.dispatchStartTransaction(getFragmentManager(), mSupportF, toFragment, 0, launchMode, TransactionDelegate.TYPE_ADD_WITHOUT_HIDE);
        }

        @Override
        public void start(BaseSupportFragment toFragment, @BaseSupportFragment.LaunchMode int launchMode) {
            toFragment.getSupportDelegate().mTransactionRecord = mRecord;
            mTransactionDelegate.dispatchStartTransaction(getFragmentManager(), mSupportF, toFragment, 0, launchMode, TransactionDelegate.TYPE_ADD);
        }

        @Override
        public void startForResult(BaseSupportFragment toFragment, int requestCode) {
            toFragment.getSupportDelegate().mTransactionRecord = mRecord;
            mTransactionDelegate.dispatchStartTransaction(getFragmentManager(), mSupportF, toFragment, requestCode, BaseSupportFragment.STANDARD, TransactionDelegate.TYPE_ADD_RESULT);
        }

        @Override
        public void startForResultDontHideSelf(BaseSupportFragment toFragment, int requestCode) {
            toFragment.getSupportDelegate().mTransactionRecord = mRecord;
            mTransactionDelegate.dispatchStartTransaction(getFragmentManager(), mSupportF, toFragment, requestCode, BaseSupportFragment.STANDARD, TransactionDelegate.TYPE_ADD_RESULT_WITHOUT_HIDE);
        }

        @Override
        public void startWithPop(BaseSupportFragment toFragment) {
            toFragment.getSupportDelegate().mTransactionRecord = mRecord;
            mTransactionDelegate.startWithPop(getFragmentManager(), mSupportF, toFragment);
        }

        @Override
        public void startWithPopTo(BaseSupportFragment toFragment, String targetFragmentTag, boolean includeTargetFragment) {
            toFragment.getSupportDelegate().mTransactionRecord = mRecord;
            mTransactionDelegate.startWithPopTo(getFragmentManager(), mSupportF, toFragment, targetFragmentTag, includeTargetFragment);
        }

        @Override
        public void replace(BaseSupportFragment toFragment) {
            toFragment.getSupportDelegate().mTransactionRecord = mRecord;
            mTransactionDelegate.dispatchStartTransaction(getFragmentManager(), mSupportF, toFragment, 0, BaseSupportFragment.STANDARD, TransactionDelegate.TYPE_REPLACE);
        }

        private FragmentManager getFragmentManager() {
            if (mFragment == null) {
                return mActivity.getSupportFragmentManager();
            }
            return mFragment.getFragmentManager();
        }
    }
}
