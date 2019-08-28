package com.youngmanster.collectionlibrary.base.helper;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentationMagician;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.youngmanster.collectionlibrary.base.activity.BaseSupportActivity;
import com.youngmanster.collectionlibrary.base.fragmet.BaseSupportFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YoKey on 17/6/13.
 */

public class SupportHelper {
    private static final long SHOW_SPACE = 200L;

    private SupportHelper() {
    }

    /**
     * 显示软键盘
     */
    public static void showSoftInput(final View view) {
        if (view == null || view.getContext() == null) return;
        final InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        view.requestFocus();
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
            }
        }, SHOW_SPACE);
    }

    /**
     * 隐藏软键盘
     */
    public static void hideSoftInput(View view) {
        if (view == null || view.getContext() == null) return;
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 显示栈视图dialog,调试时使用
     */
    public static void showFragmentStackHierarchyView(BaseSupportActivity support) {
        support.getSupportDelegate().showFragmentStackHierarchyView();
    }

    /**
     * 显示栈视图日志,调试时使用
     */
    public static void logFragmentStackHierarchy(BaseSupportActivity support, String TAG) {
        support.getSupportDelegate().logFragmentStackHierarchy(TAG);
    }

    /**
     * 获得栈顶SupportFragment
     */
    public static BaseSupportFragment getTopFragment(FragmentManager fragmentManager) {
        return getTopFragment(fragmentManager, 0);
    }

    public static BaseSupportFragment getTopFragment(FragmentManager fragmentManager, int containerId) {
        List<Fragment> fragmentList = FragmentationMagician.getActiveFragments(fragmentManager);
        if (fragmentList == null) return null;

        for (int i = fragmentList.size() - 1; i >= 0; i--) {
            Fragment fragment = fragmentList.get(i);
            if (fragment instanceof BaseSupportFragment) {
                BaseSupportFragment iFragment = (BaseSupportFragment) fragment;
                if (containerId == 0) return iFragment;

                if (containerId == iFragment.getSupportDelegate().mContainerId) {
                    return iFragment;
                }
            }
        }
        return null;
    }

    /**
     * 获取目标Fragment的前一个SupportFragment
     *
     * @param fragment 目标Fragment
     */
    public static BaseSupportFragment getPreFragment(Fragment fragment) {
        FragmentManager fragmentManager = fragment.getFragmentManager();
        if (fragmentManager == null) return null;

        List<Fragment> fragmentList = FragmentationMagician.getActiveFragments(fragmentManager);
        if (fragmentList == null) return null;

        int index = fragmentList.indexOf(fragment);
        for (int i = index - 1; i >= 0; i--) {
            Fragment preFragment = fragmentList.get(i);
            if (preFragment instanceof BaseSupportFragment) {
                return (BaseSupportFragment) preFragment;
            }
        }
        return null;
    }

    /**
     * Same as fragmentManager.findFragmentByTag(fragmentClass.getName());
     * find Fragment from FragmentStack
     */
    @SuppressWarnings("unchecked")
    public static <T extends BaseSupportFragment> T findFragment(FragmentManager fragmentManager, Class<T> fragmentClass) {
        return findStackFragment(fragmentClass, null, fragmentManager);
    }

    /**
     * Same as fragmentManager.findFragmentByTag(fragmentTag);
     * <p>
     * find Fragment from FragmentStack
     */
    @SuppressWarnings("unchecked")
    public static <T extends BaseSupportFragment> T findFragment(FragmentManager fragmentManager, String fragmentTag) {
        return findStackFragment(null, fragmentTag, fragmentManager);
    }

    /**
     * 从栈顶开始，寻找FragmentManager以及其所有子栈, 直到找到状态为show & userVisible的Fragment
     */
    public static BaseSupportFragment getActiveFragment(FragmentManager fragmentManager) {
        return getActiveFragment(fragmentManager, null);
    }

    @SuppressWarnings("unchecked")
    static <T extends BaseSupportFragment> T findStackFragment(Class<T> fragmentClass, String toFragmentTag, FragmentManager fragmentManager) {
        Fragment fragment = null;

        if (toFragmentTag == null) {
            List<Fragment> fragmentList = FragmentationMagician.getActiveFragments(fragmentManager);
            if (fragmentList == null) return null;

            int sizeChildFrgList = fragmentList.size();

            for (int i = sizeChildFrgList - 1; i >= 0; i--) {
                Fragment brotherFragment = fragmentList.get(i);
                if (brotherFragment instanceof BaseSupportFragment && brotherFragment.getClass().getName().equals(fragmentClass.getName())) {
                    fragment = brotherFragment;
                    break;
                }
            }
        } else {
            fragment = fragmentManager.findFragmentByTag(toFragmentTag);
            if (fragment == null) return null;
        }
        return (T) fragment;
    }

    private static BaseSupportFragment getActiveFragment(FragmentManager fragmentManager, BaseSupportFragment parentFragment) {
        List<Fragment> fragmentList = FragmentationMagician.getActiveFragments(fragmentManager);
        if (fragmentList == null) {
            return parentFragment;
        }
        for (int i = fragmentList.size() - 1; i >= 0; i--) {
            Fragment fragment = fragmentList.get(i);
            if (fragment instanceof BaseSupportFragment) {
                if (fragment.isResumed() && !fragment.isHidden() && fragment.getUserVisibleHint()) {
                    return getActiveFragment(fragment.getChildFragmentManager(), (BaseSupportFragment) fragment);
                }
            }
        }
        return parentFragment;
    }

    /**
     * Get the topFragment from BackStack
     */
    public static BaseSupportFragment getBackStackTopFragment(FragmentManager fragmentManager) {
        return getBackStackTopFragment(fragmentManager, 0);
    }

    /**
     * Get the topFragment from BackStack
     */
    public static BaseSupportFragment getBackStackTopFragment(FragmentManager fragmentManager, int containerId) {
        int count = fragmentManager.getBackStackEntryCount();

        for (int i = count - 1; i >= 0; i--) {
            FragmentManager.BackStackEntry entry = fragmentManager.getBackStackEntryAt(i);
            Fragment fragment = fragmentManager.findFragmentByTag(entry.getName());
            if (fragment instanceof BaseSupportFragment) {
                BaseSupportFragment supportFragment = (BaseSupportFragment) fragment;
                if (containerId == 0) return supportFragment;

                if (containerId == supportFragment.getSupportDelegate().mContainerId) {
                    return supportFragment;
                }
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    static <T extends BaseSupportFragment> T findBackStackFragment(Class<T> fragmentClass, String toFragmentTag, FragmentManager fragmentManager) {
        int count = fragmentManager.getBackStackEntryCount();

        if (toFragmentTag == null) {
            toFragmentTag = fragmentClass.getName();
        }

        for (int i = count - 1; i >= 0; i--) {
            FragmentManager.BackStackEntry entry = fragmentManager.getBackStackEntryAt(i);

            if (toFragmentTag.equals(entry.getName())) {
                Fragment fragment = fragmentManager.findFragmentByTag(entry.getName());
                if (fragment instanceof BaseSupportFragment) {
                    return (T) fragment;
                }
            }
        }
        return null;
    }

    static List<Fragment> getWillPopFragments(FragmentManager fm, String targetTag, boolean includeTarget) {
        Fragment target = fm.findFragmentByTag(targetTag);
        List<Fragment> willPopFragments = new ArrayList<>();

        List<Fragment> fragmentList = FragmentationMagician.getActiveFragments(fm);
        if (fragmentList == null) return willPopFragments;

        int size = fragmentList.size();

        int startIndex = -1;
        for (int i = size - 1; i >= 0; i--) {
            if (target == fragmentList.get(i)) {
                if (includeTarget) {
                    startIndex = i;
                } else if (i + 1 < size) {
                    startIndex = i + 1;
                }
                break;
            }
        }

        if (startIndex == -1) return willPopFragments;

        for (int i = size - 1; i >= startIndex; i--) {
            Fragment fragment = fragmentList.get(i);
            if (fragment != null && fragment.getView() != null) {
                willPopFragments.add(fragment);
            }
        }
        return willPopFragments;
    }

    /**
     * 保存根Fragment
     */
    private static List<BaseSupportFragment> rootFragments=new ArrayList<>();
    public static void addRootFragment(BaseSupportFragment rootFragment){
        rootFragments.add(rootFragment);
    }

    /**
     * 获取根Fragment
     */
    public static List<BaseSupportFragment> getRootFragment(){
        return rootFragments;
    }
}
