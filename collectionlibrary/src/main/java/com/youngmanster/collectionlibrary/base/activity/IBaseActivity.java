package com.youngmanster.collectionlibrary.base.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.youngmanster.collectionlibrary.R;
import com.youngmanster.collectionlibrary.base.fragmet.BaseSupportFragment;
import com.youngmanster.collectionlibrary.base.fragmet.FragmentAnimator;
import com.youngmanster.collectionlibrary.base.helper.ExtraTransaction;
import com.youngmanster.collectionlibrary.base.helper.SupportActivityDelegate;
import com.youngmanster.collectionlibrary.base.helper.SupportHelper;
import com.youngmanster.collectionlibrary.mvp.BasePresenter;
import com.youngmanster.collectionlibrary.mvp.ClassGetUtil;

/**
 * Created by yangyan
 * on 2018/3/18.
 */

public abstract class IBaseActivity<T extends BasePresenter> extends AppCompatActivity implements BaseSupportActivity{
	public T mPresenter;

	private boolean isFirst = false;
	private FrameLayout frame_caption_container;
	private FrameLayout frame_content_container;
	public View customBar;

	public DefaultDefineActionBarConfig defineActionBarConfig;

	final SupportActivityDelegate mDelegate = new SupportActivityDelegate(this);


	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mDelegate.onCreate(savedInstanceState);
		setContentView(R.layout.collection_library_default_base_activity);

		frame_caption_container=findViewById(R.id.frame_caption_container);
		frame_content_container=findViewById(R.id.frame_content_container);

		if (getLayoutId() != 0) {
			addContainerFrame(getLayoutId());
		}else{
			throw new IllegalArgumentException("请设置getLayoutId");
		}

		mPresenter = ClassGetUtil.getClass(this, 0);

		if (mPresenter != null) {
			mPresenter.setV(this);
		}

		defineActionBarConfig=new DefaultDefineActionBarConfig();
		boolean isShowActionBar = isShowSystemActionBar();
		boolean isShowCustomActionBar = isShowCustomActionBar();
		int customRes = setCustomActionBar();
		if (!isShowActionBar) {//如果系统ActionBar隐藏并且自定义Bar显示
			getSupportActionBar().hide();
			if (isShowCustomActionBar) {
				if (customRes !=0) {
					customBar=LayoutInflater.from(this)
							.inflate(customRes, frame_caption_container, false);
					frame_caption_container.addView(customBar);
				} else {
					View defaultBar = LayoutInflater.from(this)
							.inflate(R.layout.collection_library_default_common_toolbar, frame_caption_container, false);

					frame_caption_container.addView(defaultBar);

					defineActionBarConfig.defaultDefineView=defaultBar;
				}

				frame_caption_container.setVisibility(View.VISIBLE);
			} else {
				frame_caption_container.setVisibility(View.GONE);
			}
		} else {//如果系统ActionBar显示那么自定义Bar隐藏
			frame_caption_container.setVisibility(View.GONE);
		}
	}

	@Override
	protected void onPostCreate(@Nullable Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDelegate.onPostCreate(savedInstanceState);
	}

	@Override
	protected void onStart() {
		super.onStart();
		if (!isFirst) {
			init();
			requestData();
			isFirst = true;
		}
	}

	@Override
	public void onDestroy() {
		mDelegate.onDestroy();
		super.onDestroy();
		if (mPresenter != null) {
			mPresenter.onDestroy();
		}
	}


	/**
	 * add the children layout
	 *
	 * @param layoutResID
	 */
	private void addContainerFrame(int layoutResID) {
		View view = LayoutInflater.from(this).inflate(layoutResID,frame_content_container , false);
		frame_content_container.addView(view);
	}

	/**
	 * Note： return mDelegate.dispatchTouchEvent(ev) || super.dispatchTouchEvent(ev);
	 */
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		return mDelegate.dispatchTouchEvent(ev) || super.dispatchTouchEvent(ev);
	}

	/**
	 * 不建议复写该方法,请使用 {@link #onBackPressedSupport} 代替
	 */
	@Override
	final public void onBackPressed() {
		mDelegate.onBackPressed();
	}

	/**
	 * 布局文件加载
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


	/**
	 * 是否显示系统ActionBar
	 */
	public boolean isShowSystemActionBar() {
		return false;
	}

	/**
	 * 显示默认顶部Title栏
	 */

	public boolean isShowCustomActionBar() {
		return true;
	}

	/**
	 * 设置自定义ActionBar
	 */

	public int setCustomActionBar() {
		return 0;
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

		public DefaultDefineActionBarConfig setBarDividingLineHeight(int height){
			defaultDefineView.findViewById(R.id.lineView).getLayoutParams().height=height;
			return this;
		}

		public DefaultDefineActionBarConfig setBarDividingLineColor(Context context,int color){
			defaultDefineView.findViewById(R.id.lineView).setBackgroundColor(ContextCompat.getColor(context,color));
			return this;
		}

		public DefaultDefineActionBarConfig setBarDividingLineShowStatus(boolean isShow){
			if(isShow){
				defaultDefineView.findViewById(R.id.lineView).setVisibility(View.VISIBLE);
			}else{
				defaultDefineView.findViewById(R.id.lineView).setVisibility(View.GONE);
			}

			return this;
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



	@Override
	public SupportActivityDelegate getSupportDelegate() {
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
	 * 该方法回调时机为,Activity回退栈内Fragment的数量 小于等于1 时,默认finish Activity
	 * 请尽量复写该方法,避免复写onBackPress(),以保证SupportFragment内的onBackPressedSupport()回退事件正常执行
	 */
	@Override
	public void onBackPressedSupport() {
		mDelegate.onBackPressedSupport();
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
	 * Set all fragments animation.
	 * 设置Fragment内的全局动画
	 */
	@Override
	public void setFragmentAnimator(FragmentAnimator fragmentAnimator) {
		mDelegate.setFragmentAnimator(fragmentAnimator);
	}

	/**
	 * Set all fragments animation.
	 * 构建Fragment转场动画
	 * <p/>
	 * 如果是在Activity内实现,则构建的是Activity内所有Fragment的转场动画,
	 * 如果是在Fragment内实现,则构建的是该Fragment的转场动画,此时优先级 > Activity的onCreateFragmentAnimator()
	 *
	 * @return FragmentAnimator对象
	 */
	@Override
	public FragmentAnimator onCreateFragmentAnimator() {
		return mDelegate.onCreateFragmentAnimator();
	}

	@Override
	public void post(Runnable runnable) {
		mDelegate.post(runnable);
	}

	/****************************************以下为可选方法(Optional methods)******************************************************/

	/**
	 * 加载根Fragment, 即Activity内的第一个Fragment 或 Fragment内的第一个子Fragment
	 *
	 * @param containerId 容器id
	 * @param toFragment  目标Fragment
	 */
	public void loadRootFragment(int containerId, @NonNull BaseSupportFragment toFragment) {
		mDelegate.loadRootFragment(containerId, toFragment);
		SupportHelper.addRootFragment(toFragment);
	}

	public void loadRootFragment(int containerId, BaseSupportFragment toFragment, boolean addToBackStack, boolean allowAnimation) {
		mDelegate.loadRootFragment(containerId, toFragment, addToBackStack, allowAnimation);
		SupportHelper.addRootFragment(toFragment);
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

	/**
	 * It is recommended to use {@link BaseSupportFragment(BaseSupportFragment)}.
	 */
	public void start(BaseSupportFragment toFragment) {
		mDelegate.start(toFragment);
	}

	/**
	 * It is recommended to use {@link BaseSupportFragment(BaseSupportFragment, int)}.
	 *
	 * @param launchMode Similar to Activity's LaunchMode.
	 */
	public void start(BaseSupportFragment toFragment, @BaseSupportFragment.LaunchMode int launchMode) {
		mDelegate.start(toFragment, launchMode);
	}

	/**
	 * It is recommended to use {@link BaseSupportFragment(BaseSupportFragment, int)}.
	 * Launch an fragment for which you would like a result when it poped.
	 */
	public void startForResult(BaseSupportFragment toFragment, int requestCode) {
		mDelegate.startForResult(toFragment, requestCode);
	}

	/**
	 * It is recommended to use {@link BaseSupportFragment#(BaseSupportFragment)}.
	 * Start the target Fragment and pop itself
	 */
	public void startWithPop(BaseSupportFragment toFragment) {
		mDelegate.startWithPop(toFragment);
	}

	/**
	 * It is recommended to use {@link BaseSupportFragment(BaseSupportFragment, Class, boolean)}.
	 *
	 * @see #popTo(Class, boolean)
	 * +
	 * @see #start(BaseSupportFragment)
	 */
	public void startWithPopTo(BaseSupportFragment toFragment, Class<?> targetFragmentClass, boolean includeTargetFragment) {
		mDelegate.startWithPopTo(toFragment, targetFragmentClass, includeTargetFragment);
	}

	/**
	 * It is recommended to use {@link BaseSupportFragment(BaseSupportFragment, boolean)}.
	 */
	public void replaceFragment(BaseSupportFragment toFragment, boolean addToBackStack) {
		mDelegate.replaceFragment(toFragment, addToBackStack);
	}

	/**
	 * Pop the fragment.
	 */
	public void pop() {
		mDelegate.pop();
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

	/**
	 * 当Fragment根布局 没有 设定background属性时,
	 * Fragmentation默认使用Theme的android:windowbackground作为Fragment的背景,
	 * 可以通过该方法改变其内所有Fragment的默认背景。
	 */
	public void setDefaultFragmentBackground(@DrawableRes int backgroundRes) {
		mDelegate.setDefaultFragmentBackground(backgroundRes);
	}

	/**
	 * 得到位于栈顶Fragment
	 */
	public BaseSupportFragment getTopFragment() {
		return SupportHelper.getTopFragment(getSupportFragmentManager());
	}

	/**
	 * 获取栈内的fragment对象
	 */
	public <T extends BaseSupportFragment> T findFragment(Class<T> fragmentClass) {
		return SupportHelper.findFragment(getSupportFragmentManager(), fragmentClass);
	}
}
