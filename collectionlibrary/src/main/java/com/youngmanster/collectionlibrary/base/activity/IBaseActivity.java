package com.youngmanster.collectionlibrary.base.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.youngmanster.collectionlibrary.R;
import com.youngmanster.collectionlibrary.base.fragmet.IBaseFragment;
import com.youngmanster.collectionlibrary.mvp.BasePresenter;
import com.youngmanster.collectionlibrary.mvp.ClassGetUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by yangyan
 * on 2018/3/18.
 */

public abstract class IBaseActivity<T extends BasePresenter> extends AppCompatActivity{
	public T mPresenter;

	private boolean isFirst = false;
	private FrameLayout frame_caption_container;
	private FrameLayout frame_content_container;
	public View customBar;

	public DefaultDefineActionBarConfig defineActionBarConfig;



	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mFManager = getSupportFragmentManager();
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
			if(defaultDefineView==null){
				return this;
			}else{
				defaultDefineView.findViewById(R.id.lineView).getLayoutParams().height=height;
				return this;
			}

		}

		public DefaultDefineActionBarConfig setBarDividingLineBackground(int bgId){
			if(defaultDefineView==null){
				return this;
			}else{
				defaultDefineView.findViewById(R.id.lineView).setBackgroundResource(bgId);
				return this;
			}

		}

		public DefaultDefineActionBarConfig setBarDividingLineShowStatus(boolean isShow){

			if(defaultDefineView==null){
				return this;
			}

			if(isShow){
				defaultDefineView.findViewById(R.id.lineView).setVisibility(View.VISIBLE);
			}else{
				defaultDefineView.findViewById(R.id.lineView).setVisibility(View.GONE);
			}

			return this;
		}

		public DefaultDefineActionBarConfig setBarPadding(int left,int top,int right,int bottom){
			if(defaultDefineView==null){
				return this;
			}else{
				((LinearLayout.LayoutParams)defaultDefineView.findViewById(R.id.inRootRel).getLayoutParams()).setMargins(left,top,right,bottom);
				return this;
			}

		}

		public DefaultDefineActionBarConfig setBarBackground(int bgId) {
			if(defaultDefineView==null){
				return this;
			}else{
				defaultDefineView.findViewById(R.id.common_bar_panel).setBackgroundResource(bgId);
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




	/***************************Fragment设置**************************************/

	public static int  REQUEST_CODE_INVALID=-1;
	public static class FragmentStackEntity {
		boolean isSticky = false;
		int requestCode = REQUEST_CODE_INVALID;

		@ResultCode
		public int resultCode = IBaseFragment.RESULT_CANCELED;
		public Bundle result;
	}

	private FragmentManager mFManager;
	private List<IBaseFragment> mFragmentStack=new ArrayList<>();
	private Map fragmentStack = new ArrayMap <String,IBaseFragment>();
	private Map mFragmentEntityMap=new HashMap<IBaseFragment, FragmentStackEntity>();


	/**
	 * 是否是根Fragment
	 * @return
	 */
	public boolean isRootFragment(){
		if(mFragmentStack.size() > 1){
			return false;
		}

		return true;
	}

	/**
	 * When the back off.
	 */
	private boolean onBackStackFragment() {
		if (mFragmentStack.size() > 1) {
			mFManager.popBackStack();
			IBaseFragment inFragment = mFragmentStack.get(mFragmentStack.size() - 2);
			FragmentTransaction fragmentTransaction = mFManager.beginTransaction();
			fragmentTransaction.show(inFragment);
			fragmentTransaction.commit();
			IBaseFragment outFragment= mFragmentStack.get(mFragmentStack.size() - 1);
			inFragment.onResume();
			FragmentStackEntity stackEntity = (FragmentStackEntity) mFragmentEntityMap.get(outFragment);
			mFragmentStack.remove(outFragment);
			mFragmentEntityMap.remove(outFragment);
			fragmentStack.remove(outFragment.getClass().getSimpleName());
			if (stackEntity!=null&&stackEntity.requestCode != REQUEST_CODE_INVALID) {
				inFragment.onFragmentResult(
						stackEntity.requestCode,
						stackEntity.resultCode,
						stackEntity.result
				);
			}
			return true;
		}
		return false;
	}

	@Override
	public void onBackPressed() {
		if (!onBackStackFragment()) {
			finish();
		}
	}
	/**
	 * Show a fragment.
	 *
	 * @param clazz fragment class.
	 */
	public  <T extends IBaseFragment> void startFragment(Class<T> clazz) {
		try {
			IBaseFragment targetFragment = clazz.newInstance();
			startFragment(
					null,
					targetFragment,
					true,
					REQUEST_CODE_INVALID,
					false
            );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Show a fragment.
	 *
	 * @param clazz       fragment class.
	 */
	public  <T extends IBaseFragment> void startFragment(
			Class<T> clazz,
			boolean isSkipAnimation
	) {
		try {
			IBaseFragment targetFragment= clazz.newInstance();
			startFragment(
					null,
					targetFragment,
					true,
					REQUEST_CODE_INVALID,
					isSkipAnimation
			);
		} catch (Exception e ) {
			e.printStackTrace();
		}
	}

	/**
	 * Show a fragment.
	 *
	 * @param targetFragment fragment to display.
	</T> */
	public  <T extends IBaseFragment> void startFragment(T targetFragment) {
		startFragment(
				null,
				targetFragment,
				true,
				REQUEST_CODE_INVALID,
				false
        );
	}

	/**
	 * Show a fragment.
	 *
	 * @param targetFragment fragment to display.
	</T> */
	public  <T extends IBaseFragment> void startFragment(
			T targetFragment,
			boolean isSkipAnimation
	) {
		startFragment(
				null,
				targetFragment,
				true,
				REQUEST_CODE_INVALID,
				isSkipAnimation
        );
	}

	/**
	 * Show a fragment for result.
	 *
	 * @param clazz       fragment to display.
	 * @param requestCode requestCode.
	</T> */
	public  <T extends IBaseFragment> void startFragmentForResult(
			Class<T> clazz,
			int requestCode
	) {
		if(requestCode != REQUEST_CODE_INVALID){
			throw new IllegalStateException("The requestCode must be positive integer.");
		}
		try {
			IBaseFragment targetFragment = clazz.newInstance();
			startFragment(null, targetFragment, true, requestCode,false);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Show a fragment for result.
	 *
	 * @param clazz       fragment to display.
	 * @param requestCode requestCode.
	</T> */
	public  <T extends IBaseFragment> void startFragmentForResult(
			Class<T> clazz,
			int requestCode,
			boolean isSkipAnimation
	) {
		if(requestCode != REQUEST_CODE_INVALID){
			throw new IllegalStateException("The requestCode must be positive integer.");
		}
		try {
			IBaseFragment targetFragment= clazz.newInstance();
			startFragment(null, targetFragment, true, requestCode,isSkipAnimation);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Show a fragment for result.
	 *
	 * @param targetFragment fragment to display.
	 * @param requestCode    requestCode.

	</T> */
	public  <T extends IBaseFragment> void startFragmentForResult(T targetFragment, int requestCode) {
		if(requestCode != REQUEST_CODE_INVALID){
			throw new IllegalStateException("The requestCode must be positive integer.");
		}
		startFragment(null, targetFragment, true, requestCode,false);
	}


	/**
	 * Show a fragment for result.
	 *
	 * @param targetFragment fragment to display.
	 * @param requestCode    requestCode.

	</T> */
	public  <T extends IBaseFragment> void startFragmentForResult(T targetFragment, int requestCode,boolean isSkipAnimation) {
		if(requestCode != REQUEST_CODE_INVALID){
			throw new IllegalStateException("The requestCode must be positive integer.");
		}
		startFragment(null, targetFragment, true, requestCode,isSkipAnimation);
	}

	/**
	 * Show a fragment.
	 *
	 * @param thisFragment Now show fragment, can be null.
	 * @param thatFragment fragment to display.
	 * @param stickyStack  sticky back stack.
	 * @param requestCode  requestCode.
	</T> */
	public  <T extends IBaseFragment> void startFragment(
			T thisFragment, T thatFragment,
			boolean stickyStack, int requestCode,
			boolean isSkipAnimation
	) {
		FragmentTransaction fragmentTransaction = mFManager.beginTransaction();
		if (thisFragment != null) {
			FragmentStackEntity thisStackEntity = (FragmentStackEntity) mFragmentEntityMap.get(thisFragment);
			if (thisStackEntity != null) {
				if (thisStackEntity.isSticky) {
					thisFragment.onPause();
					thisFragment.onStop();
					fragmentTransaction.hide(thisFragment);
					if(isSkipAnimation){
						fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
					}

				} else {
					fragmentTransaction.remove(thisFragment).commit();
					if(isSkipAnimation){
						fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
					}
					fragmentTransaction.commitNow();
					fragmentTransaction = mFManager.beginTransaction();
					mFragmentEntityMap.remove(thisFragment);
					mFragmentStack.remove(thisFragment);
					fragmentStack.remove(thisFragment.getClass().getSimpleName());
				}
			}
		}
		String fragmentTag =
				thatFragment.getClass().getSimpleName();
		fragmentTransaction.add(fragmentLayoutId(), thatFragment, fragmentTag);
		if(isSkipAnimation){
			fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		}
		fragmentTransaction.addToBackStack(fragmentTag);
		fragmentTransaction.commit();
		mFManager.executePendingTransactions();
		FragmentStackEntity fragmentStackEntity = new FragmentStackEntity();
		fragmentStackEntity.isSticky = stickyStack;
		fragmentStackEntity.requestCode = requestCode;
		thatFragment.setStackEntity(fragmentStackEntity);
		mFragmentEntityMap.put(thatFragment,fragmentStackEntity);
		mFragmentStack.add(thatFragment);
		fragmentStack.put(fragmentTag,thatFragment);
	}


	public  <T extends IBaseFragment> T findFragment(Class<T> clazz){
		String fragmentTag =
				clazz.getSimpleName();

		if(fragmentStack.get(fragmentTag)!=null){
			return (T) fragmentStack.get(fragmentTag);
		}
		return null;
	}



	public int fragmentLayoutId(){
		return 0;
	}
}
