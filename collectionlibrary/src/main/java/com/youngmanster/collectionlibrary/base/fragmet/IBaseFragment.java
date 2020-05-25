package com.youngmanster.collectionlibrary.base.fragmet;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.youngmanster.collectionlibrary.R;
import com.youngmanster.collectionlibrary.base.activity.IBaseActivity;
import com.youngmanster.collectionlibrary.base.activity.ResultCode;
import com.youngmanster.collectionlibrary.mvp.BasePresenter;
import com.youngmanster.collectionlibrary.mvp.ClassGetUtil;

/**
 * Created by yangyan
 * on 2018/3/18.
 */

public abstract class IBaseFragment<T extends BasePresenter> extends Fragment {
    public View mainView;
    public T mPresenter;
    /**
     * 是否加载过数据
     */
    private boolean isDataInitiated = false;
    private FrameLayout frame_caption_container;
    private FrameLayout frame_content_container;
    public View customBar;
    public DefaultDefineActionBarConfig defineActionBarConfig;


    /***********************************************************生命周期回调**************************************************/

    
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
        if(!isDataInitiated){
            requestData();
            isDataInitiated=true;
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(mPresenter!=null){
            mPresenter.onDestroy();
        }

        isDataInitiated=false;
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

        public DefaultDefineActionBarConfig setBarDividingLineBackground(int color){
            if(defaultDefineView==null){
                return this;
            }else{
                defaultDefineView.findViewById(R.id.lineView).setBackgroundResource(color);
                return this;
            }

        }

        public DefaultDefineActionBarConfig setBarDividingLineShowStatus(boolean isShow){
            if(isShow){
                defaultDefineView.findViewById(R.id.lineView).setVisibility(View.VISIBLE);
            }else{
                defaultDefineView.findViewById(R.id.lineView).setVisibility(View.GONE);
            }

            return this;
        }

        public DefaultDefineActionBarConfig setBarBackground(int bgColor) {
            if(defaultDefineView==null){
                return this;
            }else{
                defaultDefineView.findViewById(R.id.common_bar_panel).setBackgroundResource(bgColor);
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

        public DefaultDefineActionBarConfig setTitleColor( int color) {

            if(defaultDefineView==null){
                return this;
            }else{
                ((TextView)defaultDefineView.findViewById(R.id.titleTv)).setTextColor(color);
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




    /******************************Fragment**********************************/

    /**
     * IBaseActivity.
     */
    private IBaseActivity mActivity;

    public final static int REQUEST_CODE_INVALID = IBaseActivity.REQUEST_CODE_INVALID;
    public final static int RESULT_OK = Activity.RESULT_OK;
    public final static int RESULT_CANCELED = Activity.RESULT_CANCELED;

    // ------------------------- Stack ------------------------- //
    /**
     * Stack info.
     */
    private IBaseActivity.FragmentStackEntity mStackEntity;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity =  (IBaseActivity)context ;
    }


    /**
     * Get the resultCode for requestCode.
     */
    public void setStackEntity(@NonNull IBaseActivity.FragmentStackEntity stackEntity) {
        this.mStackEntity = stackEntity;
    }

    /**
     * Set result.
     *
     * @param resultCode result code, one of [IBaseFragment.RESULT_OK], [IBaseFragment.RESULT_CANCELED].
     */
    protected void setResult(@ResultCode int resultCode) {
        mStackEntity.resultCode = resultCode;
    }

    /**
     * Set result.
     *
     * @param resultCode resultCode, use [].
     * @param result     [Bundle].
     */
    protected void setResult(@ResultCode int resultCode, @NonNull Bundle result) {
        mStackEntity.resultCode = resultCode;
        mStackEntity.result = result;
    }


    /**
     * You should override it.
     *
     * @param resultCode resultCode.
     * @param result     [Bundle].
     */
    public void onFragmentResult(
            int requestCode,
            @ResultCode int resultCode,
            @Nullable Bundle result
    ) {
    }


    /**
     * Show a fragment.
     *
     * @param clazz fragment class.
    </T> */
    public  <T extends IBaseFragment> void startFragment(Class<T> clazz) {
        try {
            IBaseFragment targetFragment= clazz.newInstance();
            startFragment(
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
    </T> */
    public  <T extends IBaseFragment> void startFragment(
            Class<T> clazz,
            boolean isSkipAnimation
    ) {
        try {
            IBaseFragment targetFragment = clazz.newInstance();
            startFragment(
                    targetFragment,
                    true,
                    REQUEST_CODE_INVALID,
                    isSkipAnimation
            );
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Show a fragment.
     *
     * @param targetFragment fragment to display.
    </T> */
    public  <T extends IBaseFragment> void startFragment(T targetFragment) {
        startFragment(targetFragment, true, REQUEST_CODE_INVALID,false);
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
        try {
            IBaseFragment targetFragment= clazz.newInstance();
            startFragment(targetFragment, true, requestCode,false);
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
        try {
            IBaseFragment targetFragment = clazz.newInstance();
            startFragment(targetFragment, true, requestCode,isSkipAnimation);
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
    public  <T extends IBaseFragment> void startFragmentForResult(
            T targetFragment,
            int requestCode
    ) {
        startFragment(targetFragment, true, requestCode,false);
    }

    /**
     * Show a fragment for result.
     *
     * @param targetFragment fragment to display.
     * @param requestCode    requestCode.
    </T> */
    public  <T extends IBaseFragment> void startFragmentForResult(
            T targetFragment,
            int requestCode,
            boolean isSkipAnimation
    ) {
        startFragment(targetFragment, true, requestCode,isSkipAnimation);
    }

    /**
     * Show a fragment.
     *
     * @param targetFragment fragment to display.
     * @param stickyStack    sticky back stack.
     * @param requestCode    requestCode.
    </T> */
    private  <T extends IBaseFragment> void startFragment(
            T targetFragment,
            boolean stickyStack,
            int requestCode,
            boolean isSkipAnimation
    ) {
        mActivity.startFragment(this, targetFragment, stickyStack, requestCode,isSkipAnimation);
    }


    public  <T extends IBaseFragment> T findFragment(Class<T> clazz ){
        return (T) mActivity.findFragment(clazz);
    }


    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        if (transit == FragmentTransaction.TRANSIT_FRAGMENT_OPEN) { //表示是一个进入动作，比如add.show等
            if (enter) { //普通的进入的动作
                return AnimationUtils.loadAnimation(
                        getActivity(),
                        R.anim.slide_right_in
                );
            } else { //比如一个已经Fragmen被另一个replace，是一个进入动作，被replace的那个就是false
                return AnimationUtils.loadAnimation(
                        getActivity(),
                        R.anim.slide_left_out
                );
            }
        } else if (transit == FragmentTransaction.TRANSIT_FRAGMENT_CLOSE) { //表示一个退出动作，比如出栈，hide，detach等
            if (enter) { //之前被replace的重新进入到界面或者Fragment回到栈顶
                return AnimationUtils.loadAnimation(
                        getActivity(),
                        R.anim.slide_left_in
                );
            } else { //Fragment退出，出栈
                return AnimationUtils.loadAnimation(
                        getActivity(),
                        R.anim.slide_right_out
                );
            }
        }
        return null;
    }

}


