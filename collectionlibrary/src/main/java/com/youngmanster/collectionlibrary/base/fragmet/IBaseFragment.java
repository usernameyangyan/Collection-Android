package com.youngmanster.collectionlibrary.base.fragmet;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.youngmanster.collectionlibrary.R;
import com.youngmanster.collectionlibrary.base.activity.IBaseActivity;
import com.youngmanster.collectionlibrary.base.helper.ExtraTransaction;
import com.youngmanster.collectionlibrary.base.helper.SupportFragmentDelegate;
import com.youngmanster.collectionlibrary.base.helper.SupportHelper;
import com.youngmanster.collectionlibrary.mvp.BasePresenter;
import com.youngmanster.collectionlibrary.mvp.ClassGetUtil;
import com.youngmanster.collectionlibrary.utils.LogUtils;

import java.util.List;

/**
 * Created by yangyan
 * on 2018/3/18.
 */

public abstract class IBaseFragment<T extends BasePresenter> extends Fragment implements BaseSupportFragment {
    public View mainView;
    public T mPresenter;

    final SupportFragmentDelegate mDelegate = new SupportFragmentDelegate(this);
    protected FragmentActivity _mActivity;

    private FrameLayout frame_caption_container;
    private FrameLayout frame_content_container;
    public View customBar;
    public DefaultDefineActionBarConfig defineActionBarConfig;


    /***********************************************************生命周期回调**************************************************/
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDelegate.onCreate(savedInstanceState);
    }
    
    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView=inflater.inflate(R.layout.collection_library_default_base_fragment,container,false);
        frame_caption_container=mainView.findViewById(R.id.frame_caption_container);
        frame_content_container=mainView.findViewById(R.id.frame_content_container);

        if (getLayoutId() != 0) {
            addContainerFrame(getLayoutId());
        }else{
            throw new IllegalArgumentException("请设置getLayoutId");
        }

        mPresenter= ClassGetUtil.getClass(this,0);
        if(mPresenter!=null){
            mPresenter.setV(this);
        }

        defineActionBarConfig=new DefaultDefineActionBarConfig();
        boolean isShowCustomActionBar = isShowCustomActionBar();
        int customRes = setCustomActionBar();
        if (isShowCustomActionBar) {
            if (customBar != null) {
                customBar=LayoutInflater.from(getActivity())
                        .inflate(customRes, frame_caption_container, false);
                frame_caption_container.addView(customBar);
            } else {
                View defaultBar = LayoutInflater.from(getActivity())
                        .inflate(R.layout.collection_library_default_common_toolbar, frame_caption_container, false);
                frame_caption_container.addView(defaultBar);
                defineActionBarConfig.defaultDefineView=defaultBar;
            }

            frame_caption_container.setVisibility(View.VISIBLE);
        } else {
            frame_caption_container.setVisibility(View.GONE);
        }


        return mainView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    @Override
    public void onResume() {
        super.onResume();
        mDelegate.onResume();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        mDelegate.setUserVisibleHint(isVisibleToUser);
    }
    
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mDelegate.onAttach(activity);
        _mActivity = mDelegate.getActivity();
    }



    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return mDelegate.onCreateAnimation(transit, enter, nextAnim);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mDelegate.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mDelegate.onSaveInstanceState(outState);
    }


    @Override
    public void onPause() {
        super.onPause();
        mDelegate.onPause();
    }

    @Override
    public void onDestroyView() {
        mDelegate.onDestroyView();
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        mDelegate.onDestroy();
        super.onDestroy();
        if(mPresenter!=null){
            mPresenter.onDestroy();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        mDelegate.onHiddenChanged(hidden);
    }


    /**
     * 设置自定义ActionBar
     */

    public class DefaultDefineActionBarConfig {
        View defaultDefineView;

        public DefaultDefineActionBarConfig hideBackBtn(){
            if(defaultDefineView==null){
                return this;
            }else{
                defaultDefineView.findViewById(R.id.btnBack).setVisibility(View.GONE);
                return this;
            }

        }

        public DefaultDefineActionBarConfig setBarBackgroundColor(Context context, int bgColor) {
            if(defaultDefineView==null){
                return this;
            }else{
                defaultDefineView.findViewById(R.id.common_bar_panel).setBackgroundColor(ContextCompat.getColor(context,bgColor));
                return this;
            }
        }

        public DefaultDefineActionBarConfig setBarHeight(int height) {
            if(defaultDefineView==null){
                return this;
            }else{
                defaultDefineView.findViewById(R.id.common_bar_panel).getLayoutParams().height = height;
                return this;
            }
        }

        public DefaultDefineActionBarConfig setBackIcon(int resId) {


            if(defaultDefineView==null){
                return this;
            }else{
                ((ImageView)defaultDefineView.findViewById(R.id.btnBack)).setImageResource(resId);
                if(resId != 0){
                    defaultDefineView.findViewById(R.id.btnBack).setVisibility(View.VISIBLE);
                }
                return this;
            }
        }

        public DefaultDefineActionBarConfig setTitleColor(Context context, int color) {

            if(defaultDefineView==null){
                return this;
            }else{
                ((TextView)defaultDefineView.findViewById(R.id.titleTv)).setTextColor((ContextCompat.getColor(context,color)));
                return this;
            }
        }

        public DefaultDefineActionBarConfig setTitleSize(Float size) {

            if(defaultDefineView==null){
                return this;
            }else{
                ((TextView)defaultDefineView.findViewById(R.id.titleTv)).setTextSize(size);
                return this;
            }
        }

        public DefaultDefineActionBarConfig setTitle(String con) {
            if(defaultDefineView==null){
                return this;
            }else{
                ((TextView)defaultDefineView.findViewById(R.id.titleTv)).setText(con);
                return this;
            }
        }

        public DefaultDefineActionBarConfig setBackClick(View.OnClickListener onListener){
            if(defaultDefineView==null){
                return this;
            }else{
                defaultDefineView.findViewById(R.id.btnBack).setOnClickListener(onListener);
                return this;
            }
        }
    }

    /**
     * add the children layout
     *
     * @param layoutResID
     */
    private void addContainerFrame(int layoutResID) {
        View view = LayoutInflater.from(getActivity()).inflate(layoutResID, frame_content_container, false);
        frame_content_container.addView(view);
    }

    /**
     * 显示默认顶部Title栏
     */

    public boolean isShowCustomActionBar() {
        return false;
    }


    /**
     * 设置自定义ActionBar
     */

    public int setCustomActionBar() {
        return 0;
    }

    /**
     *  布局文件加载
     */
    public abstract int getLayoutId();

    /**
     * 初始化参数
     */
    public abstract void init();
    /**
     * 请求数据
     */
    public abstract void requestData();
    

    @Override
    public SupportFragmentDelegate getSupportDelegate() {
        return mDelegate;
    }

    /**
     * Perform some extra transactions.
     * 额外的事务：自定义Tag，添加SharedElement动画，操作非回退栈Fragment
     */
    @Override
    public ExtraTransaction extraTransaction() {
        return mDelegate.extraTransaction();
    }


    /**
     * Causes the Runnable r to be added to the action queue.
     * <p>
     * The runnable will be run after all the previous action has been run.
     * <p>
     * 前面的事务全部执行后 执行该Action
     *
     * @deprecated Use {@link #post(Runnable)} instead.
     */
    @Deprecated
    @Override
    public void enqueueAction(Runnable runnable) {
        mDelegate.enqueueAction(runnable);
    }

    /**
     * Causes the Runnable r to be added to the action queue.
     * <p>
     * The runnable will be run after all the previous action has been run.
     * <p>
     * 前面的事务全部执行后 执行该Action
     */
    @Override
    public void post(Runnable runnable) {
        mDelegate.post(runnable);
    }

    /**
     * Called when the enter-animation end.
     * 入栈动画 结束时,回调
     */
    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        mDelegate.onEnterAnimationEnd(savedInstanceState);
    }


    /**
     * Lazy initial，Called when fragment is first called.
     * <p>
     * 同级下的 懒加载 ＋ ViewPager下的懒加载  的结合回调方法
     */
    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        mDelegate.onLazyInitView(savedInstanceState);
        requestData();
    }

    /**
     * Called when the fragment is visible.
     * 当Fragment对用户可见时回调
     * <p>
     * Is the combination of  [onHiddenChanged() + onResume()/onPause() + setUserVisibleHint()]
     */
    @Override
    public void onSupportVisible() {
        mDelegate.onSupportVisible();
    }

    /**
     * Called when the fragment is invivible.
     * <p>
     * Is the combination of  [onHiddenChanged() + onResume()/onPause() + setUserVisibleHint()]
     */
    @Override
    public void onSupportInvisible() {
        mDelegate.onSupportInvisible();
    }

    /**
     * Return true if the fragment has been supportVisible.
     */
    @Override
    final public boolean isSupportVisible() {
        return mDelegate.isSupportVisible();
    }

    /**
     * Set fragment animation with a higher priority than the ISupportActivity
     * 设定当前Fragmemt动画,优先级比在SupportActivity里高
     */
    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return mDelegate.onCreateFragmentAnimator();
    }

    /**
     * 获取设置的全局动画 copy
     *
     * @return FragmentAnimator
     */
    @Override
    public FragmentAnimator getFragmentAnimator() {
        return mDelegate.getFragmentAnimator();
    }

    /**
     * 设置Fragment内的全局动画
     */
    @Override
    public void setFragmentAnimator(FragmentAnimator fragmentAnimator) {
        mDelegate.setFragmentAnimator(fragmentAnimator);
    }

    /**
     * 按返回键触发,前提是SupportActivity的onBackPressed()方法能被调用
     *
     * @return false则继续向上传递, true则消费掉该事件
     */
    @Override
    public boolean onBackPressedSupport() {
        return mDelegate.onBackPressedSupport();
    }

    /**
     * 类似 {@link Activity#setResult(int, Intent)}
     * <p>
     * Similar to {@link Activity#setResult(int, Intent)}
     *
     * @see #(int)
     */
    @Override
    public void setFragmentResult(int resultCode, Bundle bundle) {
        mDelegate.setFragmentResult(resultCode, bundle);
    }

    /**
     *
     * @see #(, int)
     */
    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        mDelegate.onFragmentResult(requestCode, resultCode, data);
    }

    /**
     * 在start(TargetFragment,LaunchMode)时,启动模式为SingleTask/SingleTop, 回调TargetFragment的该方法
     *
     * @param args putNewBundle(Bundle newBundle)
     * @see #(int)
     */
    @Override
    public void onNewBundle(Bundle args) {
        mDelegate.onNewBundle(args);
    }

    /**
     * 添加NewBundle,用于启动模式为SingleTask/SingleTop时
     *
     * @see , int)
     */
    @Override
    public void putNewBundle(Bundle newBundle) {
        mDelegate.putNewBundle(newBundle);
    }



    /**
     * 加载根Fragment, 即Activity内的第一个Fragment 或 Fragment内的第一个子Fragment
     *
     * @param containerId 容器id
     * @param toFragment  目标Fragment
     */
    public void loadRootFragment(int containerId, BaseSupportFragment toFragment) {
        mDelegate.loadRootFragment(containerId, toFragment);
        SupportHelper.addRootFragment(toFragment);
    }

    public void loadRootFragment(int containerId, BaseSupportFragment toFragment, boolean addToBackStack, boolean allowAnim) {
        mDelegate.loadRootFragment(containerId, toFragment, addToBackStack, allowAnim);
        SupportHelper.addRootFragment(toFragment);
    }

    /**
     * 是否是根Fragment
     */
    public boolean isRootFragment(BaseSupportFragment currentFragment){
        for(BaseSupportFragment baseSupportFragment:SupportHelper.getRootFragment()){
            if(baseSupportFragment==getTopFragment()){
               return true;
            }
        }
        return false;
    }

    /**
     * 加载多个同级根Fragment,类似Wechat, QQ主页的场景
     */
    public void loadMultipleRootFragment(int containerId, int showPosition, BaseSupportFragment... toFragments) {
        mDelegate.loadMultipleRootFragment(containerId, showPosition, toFragments);
    }

    /**
     * show一个Fragment,hide其他同栈所有Fragment
     * 使用该方法时，要确保同级栈内无多余的Fragment,(只有通过loadMultipleRootFragment()载入的Fragment)
     * <p>
     * 建议使用更明确的{@link #showHideFragment(BaseSupportFragment, BaseSupportFragment)}
     *
     * @param showFragment 需要show的Fragment
     */
    public void showHideFragment(BaseSupportFragment showFragment) {
        mDelegate.showHideFragment(showFragment);
    }

    /**
     * show一个Fragment,hide一个Fragment ; 主要用于类似微信主页那种 切换tab的情况
     */
    public void showHideFragment(BaseSupportFragment showFragment, BaseSupportFragment hideFragment) {
        mDelegate.showHideFragment(showFragment, hideFragment);
    }

    public void start(BaseSupportFragment toFragment) {
        mDelegate.start(toFragment);
    }

    /**
     * @param launchMode Similar to Activity's LaunchMode.
     */
    public void start(final BaseSupportFragment toFragment, @LaunchMode int launchMode) {
        mDelegate.start(toFragment, launchMode);
    }

    /**
     * Launch an fragment for which you would like a result when it poped.
     */
    public void startForResult(BaseSupportFragment toFragment, int requestCode) {
        mDelegate.startForResult(toFragment, requestCode);
    }

    /**
     * Start the target Fragment and pop itself
     */
    public void startWithPop(BaseSupportFragment toFragment) {
        mDelegate.startWithPop(toFragment);
    }

    /**
     * @see #popTo(Class, boolean)
     * +
     * @see #start(BaseSupportFragment)
     */
    public void startWithPopTo(BaseSupportFragment toFragment, Class<?> targetFragmentClass, boolean includeTargetFragment) {
        mDelegate.startWithPopTo(toFragment, targetFragmentClass, includeTargetFragment);
    }


    public void replaceFragment(BaseSupportFragment toFragment, boolean addToBackStack) {
        mDelegate.replaceFragment(toFragment, addToBackStack);
    }

    public void pop() {
        mDelegate.pop();
    }

    /**
     * Pop the child fragment.
     */
    public void popChild() {
        mDelegate.popChild();
    }

    /**
     * Pop the last fragment transition from the manager's fragment
     * back stack.
     * <p>
     * 出栈到目标fragment
     *
     * @param targetFragmentClass   目标fragment
     * @param includeTargetFragment 是否包含该fragment
     */
    public void popTo(Class<?> targetFragmentClass, boolean includeTargetFragment) {
        mDelegate.popTo(targetFragmentClass, includeTargetFragment);
    }

    /**
     * If you want to begin another FragmentTransaction immediately after popTo(), use this method.
     * 如果你想在出栈后, 立刻进行FragmentTransaction操作，请使用该方法
     */
    public void popTo(Class<?> targetFragmentClass, boolean includeTargetFragment, Runnable afterPopTransactionRunnable) {
        mDelegate.popTo(targetFragmentClass, includeTargetFragment, afterPopTransactionRunnable);
    }

    public void popTo(Class<?> targetFragmentClass, boolean includeTargetFragment, Runnable afterPopTransactionRunnable, int popAnim) {
        mDelegate.popTo(targetFragmentClass, includeTargetFragment, afterPopTransactionRunnable, popAnim);
    }

    public void popToChild(Class<?> targetFragmentClass, boolean includeTargetFragment) {
        mDelegate.popToChild(targetFragmentClass, includeTargetFragment);
    }

    public void popToChild(Class<?> targetFragmentClass, boolean includeTargetFragment, Runnable afterPopTransactionRunnable) {
        mDelegate.popToChild(targetFragmentClass, includeTargetFragment, afterPopTransactionRunnable);
    }

    public void popToChild(Class<?> targetFragmentClass, boolean includeTargetFragment, Runnable afterPopTransactionRunnable, int popAnim) {
        mDelegate.popToChild(targetFragmentClass, includeTargetFragment, afterPopTransactionRunnable, popAnim);
    }

    /**
     * 得到位于栈顶Fragment
     */
    public BaseSupportFragment getTopFragment() {
        return SupportHelper.getTopFragment(getFragmentManager());
    }

    public BaseSupportFragment getTopChildFragment() {
        return SupportHelper.getTopFragment(getChildFragmentManager());
    }

    /**
     * @return 位于当前Fragment的前一个Fragment
     */
    public BaseSupportFragment getPreFragment() {
        return SupportHelper.getPreFragment(this);
    }

    /**
     * 获取栈内的fragment对象
     */
    public <T extends BaseSupportFragment> T findFragment(Class<T> fragmentClass) {
        return SupportHelper.findFragment(getFragmentManager(), fragmentClass);
    }

    /**
     * 获取栈内的fragment对象
     */
    public <T extends BaseSupportFragment> T findChildFragment(Class<T> fragmentClass) {
        return SupportHelper.findFragment(getChildFragmentManager(), fragmentClass);
    }

}


