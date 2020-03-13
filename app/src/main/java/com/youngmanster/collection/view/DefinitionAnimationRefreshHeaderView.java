package com.youngmanster.collection.view;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.youngmanster.collection.R;
import com.youngmanster.collectionlibrary.refreshrecyclerview.base.refreshview.BasePullToRefreshView;


/**
 * 自定义默认动画
 * Created by yangyan on 2018/3/10.
 */

public class DefinitionAnimationRefreshHeaderView extends BasePullToRefreshView implements BasePullToRefreshView.OnStateChangeListener{


    private ImageView ivPullBgOne,ivPullBgTwo;
    private ImageView ivWheelOne,ivWheelTwo;
    private ImageView ivSun;

    private Animation wheelAnimation,sunAnimation;  //轮子、太阳动画
    private Animation backAnimationOne,backAnimationTwo;    //两张背景图动画

    private boolean isDestroy = false;

    public DefinitionAnimationRefreshHeaderView(Context context) {
        super(context);
        onStateChangeListener=this;
    }
    
    @Override
    public void initView(Context context){
        mContainer = LayoutInflater.from(context).inflate(R.layout.layout_definition_animation_refresh, null);

        ivPullBgOne=mContainer.findViewById(R.id.iv_pull_bg_one);
        ivPullBgTwo=mContainer.findViewById(R.id.iv_pull_bg_two);
        ivWheelOne=mContainer.findViewById(R.id.ivWheelOne);
        ivWheelTwo=mContainer.findViewById(R.id.ivWheelTwo);
        ivSun=mContainer.findViewById(R.id.ivSun);

        //获取动画
        wheelAnimation = AnimationUtils.loadAnimation(context, R.anim.wheel_animation);
        sunAnimation = AnimationUtils.loadAnimation(context, R.anim.sun_animation);
        LinearInterpolator lir = new LinearInterpolator();
        wheelAnimation.setInterpolator(lir);
        sunAnimation.setInterpolator(lir);
        backAnimationOne = AnimationUtils.loadAnimation(context, R.anim.back_animation_one);
        backAnimationTwo = AnimationUtils.loadAnimation(context, R.anim.back_animation_two);

        //把刷新头部的高度初始化为0
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 0, 0, 0);
        this.setLayoutParams(lp);
        this.setPadding(0, 0, 0, 0);
        addView(mContainer, new LayoutParams(LayoutParams.MATCH_PARENT, 0));
        setGravity(Gravity.BOTTOM);

        //测量高度
        measure(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        mMeasuredHeight = getMeasuredHeight();

    }

    @Override
    public void setRefreshTimeVisible(boolean show) {
    }

    /**
     * 开启动画
     */
    public void startAnim(){
        ivPullBgOne.startAnimation(backAnimationOne);
        ivPullBgTwo.startAnimation(backAnimationTwo);
        ivSun.startAnimation(sunAnimation);
        ivWheelOne.startAnimation(wheelAnimation);
        ivWheelTwo.startAnimation(wheelAnimation);
    }

    /**
     * 清除动画
     */
    public void clearAnim(){
        ivPullBgOne.clearAnimation();
        ivPullBgTwo.clearAnimation();
        ivSun.clearAnimation();
        ivWheelOne.clearAnimation();
        ivWheelTwo.clearAnimation();
    }

    @Override
    public void destroy() {

        isDestroy=true;

        if(backAnimationOne!=null){
            backAnimationOne.cancel();
            backAnimationOne=null;
        }

        if(backAnimationTwo!=null){
            backAnimationTwo.cancel();
            backAnimationTwo=null;
        }

        if(sunAnimation!=null){
            sunAnimation.cancel();
            sunAnimation=null;
        }

        if(wheelAnimation!=null){
            wheelAnimation.cancel();
            wheelAnimation=null;
        }

    }

    @Override
    public void onStateChange(int state) {

        if(isDestroy){
            return;
        }
        //下拉时状态相同不做继续保持原有的状态
        if (state == mState) return ;
        //根据状态进行动画显示
        switch (state){
            case STATE_PULL_DOWN:
                clearAnim();
                startAnim();
                break;
            case STATE_RELEASE_REFRESH:
                break;
            case STATE_REFRESHING:
                clearAnim();
                startAnim();
                scrollTo(mMeasuredHeight);
                break;
            case STATE_DONE:
                break;
        }
        mState = state;
    }
}
