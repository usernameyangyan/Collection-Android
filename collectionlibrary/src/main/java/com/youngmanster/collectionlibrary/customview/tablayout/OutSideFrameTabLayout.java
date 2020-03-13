package com.youngmanster.collectionlibrary.customview.tablayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.youngmanster.collectionlibrary.R;
import com.youngmanster.collectionlibrary.utils.DisplayUtils;

/**
 * Created by yangyan on 2018/7/9.
 */

public class OutSideFrameTabLayout extends FrameLayout implements ViewPager.OnPageChangeListener{

    private Context mContext;
    private LinearLayout mTabsContainer;
    private ViewPager mViewPager;
    private int mTabCount;
    private int mCurrentTab;


    /**
     * indicator
     */
    private int mIndicatorColor;
    private float mTabPadding;

    private float mIndicatorCornerRadius;
    private float mIndicatorMarginLeft;
    private float mIndicatorMarginTop;
    private float mIndicatorMarginRight;
    private float mIndicatorMarginBottom;
    private int mTextSelectColor;
    private int mTextUnSelectColor;
    private float mTextSize;
    private boolean mTextAllCaps;
    private float tabWidth;
    private int mBarColor;
    private int mBarStrokeColor;
    private float mBarStrokeWidth;

    /** 用于绘制显示器 */
    private Rect mIndicatorRect = new Rect();
    private GradientDrawable mIndicatorDrawable = new GradientDrawable();
    private GradientDrawable mRectDrawable = new GradientDrawable();
    private float[] mRadiusArr = new float[8];

    private OnTabSelectListener mListener;

    public void setOnTabSelectListener(OnTabSelectListener listener) {
        this.mListener = listener;
    }

    public OutSideFrameTabLayout(Context context) {
        this(context, null);
    }

    public OutSideFrameTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OutSideFrameTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);//重写onDraw方法,需要调用这个方法来清除flag
        setClipChildren(false);
        setClipToPadding(false);
        this.mContext = context;
        mTabsContainer = new LinearLayout(context);
        super.addView(mTabsContainer, 0, new LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));


        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.OutSideFrameTabLayout);

        mIndicatorColor = ta.getColor(R.styleable.OutSideFrameTabLayout_tab_tabIndicatorColor, Color.parseColor("#222831"));
        mIndicatorCornerRadius = ta.getDimension(R.styleable.OutSideFrameTabLayout_tab_indicator_corner, -1);
        mIndicatorMarginLeft = ta.getDimension(R.styleable.OutSideFrameTabLayout_tab_indicator_marginLeft, 0);
        mIndicatorMarginTop = ta.getDimension(R.styleable.OutSideFrameTabLayout_tab_indicator_marginTop, 0);
        mIndicatorMarginRight = ta.getDimension(R.styleable.OutSideFrameTabLayout_tab_indicator_marginRight, 0);
        mIndicatorMarginBottom = ta.getDimension(R.styleable.OutSideFrameTabLayout_tab_indicator_marginBottom, 0);

        mTextSelectColor = ta.getColor(R.styleable.OutSideFrameTabLayout_tab_tabSelectedTextColor, 0);
        mTextUnSelectColor = ta.getColor(R.styleable.OutSideFrameTabLayout_tab_tabTextColor, 0);
        mTextSize = ta.getDimension(R.styleable.OutSideFrameTabLayout_tab_tabTextSize, DisplayUtils.dip2px(context, 14));
        mTextAllCaps = ta.getBoolean(R.styleable.OutSideFrameTabLayout_tab_textAllCaps, false);

        tabWidth = ta.getDimension(R.styleable.OutSideFrameTabLayout_tab_width, 0);
        mTabPadding = ta.getDimension(R.styleable.OutSideFrameTabLayout_tab_padding, DisplayUtils.dip2px(context,10));

        mBarColor = ta.getColor(R.styleable.OutSideFrameTabLayout_tab_bar_color, Color.TRANSPARENT);
        mBarStrokeColor = ta.getColor(R.styleable.OutSideFrameTabLayout_tab_bar_stroke_color, mIndicatorColor);
        mBarStrokeWidth = ta.getDimension(R.styleable.OutSideFrameTabLayout_tab_bar_stroke_width, DisplayUtils.dip2px(context, 1));

        ta.recycle();
    }


    public void setupWithViewPager(@Nullable ViewPager vp) {
        if (vp == null || vp.getAdapter() == null) {
            throw new IllegalStateException("ViewPager or ViewPager adapter can not be null!");
        }

        this.mViewPager = vp;

        this.mViewPager.removeOnPageChangeListener(this);
        this.mViewPager.addOnPageChangeListener(this);
        notifyDataSetChanged();
    }

    public void notifyDataSetChanged() {
        mTabsContainer.removeAllViews();

        this.mTabCount = mViewPager.getAdapter().getCount();
        View tabView;
        for (int i = 0; i < mTabCount; i++) {
            tabView = View.inflate(mContext, R.layout.collection_library_design_layout_tab_text, null);
            CharSequence pageTitle = mViewPager.getAdapter().getPageTitle(i);
            addTab(i, pageTitle.toString(), tabView);
        }
        updateTabStates();
    }

    /**
     * 创建并添加tab
     */
    private void addTab(final int position, String title, View tabView) {
        TextView tv_tab_title = tabView.findViewById(R.id.tv_tab_title);
        if (tv_tab_title != null) {
            if (title != null) tv_tab_title.setText(title);
        }

        tabView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = mTabsContainer.indexOfChild(v);
                if (position != -1) {
                    if (mViewPager.getCurrentItem() != position) {
                        mViewPager.setCurrentItem(position, false);

                        if (mListener != null) {
                            mListener.onTabSelect(position);
                        }
                    }
                }
            }
        });

        /** 每一个Tab的布局参数 */
        LinearLayout.LayoutParams lp_tab;
        if (tabWidth > 0) {
            lp_tab = new LinearLayout.LayoutParams((int) tabWidth, LayoutParams.MATCH_PARENT);
        }else{
            lp_tab = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        }

        mTabsContainer.addView(tabView, position, lp_tab);
    }


    /**
     * 创建并添加tab
     */
    private void addCustomTab(final int position, View tabView) {

        tabView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = mTabsContainer.indexOfChild(v);
                if (position != -1) {
                    if (mViewPager.getCurrentItem() != position) {
                        mViewPager.setCurrentItem(position, false);

                        if (mListener != null) {
                            mListener.onTabSelect(position);
                        }
                    }
                }
            }
        });

        /** 每一个Tab的布局参数 */
        LinearLayout.LayoutParams lp_tab;
        if (tabWidth > 0) {
            lp_tab = new LinearLayout.LayoutParams((int) tabWidth, LayoutParams.MATCH_PARENT);
        }else{
            lp_tab = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        }
        mTabsContainer.addView(tabView, position, lp_tab);
    }


    private void updateTabStates() {

        for (int i = 0; i < mTabCount; i++) {
            View v = mTabsContainer.getChildAt(i);
            TextView tv_tab_title = v.findViewById(R.id.tv_tab_title);
            if (tv_tab_title != null) {
                tv_tab_title.setTextColor(i == mCurrentTab ? mTextSelectColor : mTextUnSelectColor);
                tv_tab_title.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
                tv_tab_title.setPadding((int) mTabPadding, 0, (int) mTabPadding, 0);
                if (mTextAllCaps) {
                    tv_tab_title.setText(tv_tab_title.getText().toString().toUpperCase());
                }
            }
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (isInEditMode() || mTabCount <= 0) {
            return;
        }

        int height = getHeight();
        int paddingLeft = getPaddingLeft();

        float mIndicatorHeight = height - mIndicatorMarginTop - mIndicatorMarginBottom;

        if (mIndicatorCornerRadius < 0 || mIndicatorCornerRadius > mIndicatorHeight / 2) {
            mIndicatorCornerRadius = mIndicatorHeight / 2;
        }
        mRectDrawable.setColor(mBarColor);
        mRectDrawable.setStroke((int) mBarStrokeWidth, mBarStrokeColor);
        mRectDrawable.setCornerRadius(mIndicatorCornerRadius);
        mRectDrawable.setBounds(getPaddingLeft(), getPaddingTop(), getWidth() - getPaddingRight(), getHeight() - getPaddingBottom());
        mRectDrawable.draw(canvas);


        calcIndicatorRect();

        mIndicatorDrawable.setColor(mIndicatorColor);
        mIndicatorDrawable.setBounds(paddingLeft + (int) mIndicatorMarginLeft + mIndicatorRect.left,
                (int) mIndicatorMarginTop, (int) (paddingLeft + mIndicatorRect.right - mIndicatorMarginRight),
                (int) (mIndicatorMarginTop + mIndicatorHeight));

        mIndicatorDrawable.setCornerRadii(mRadiusArr);
        mIndicatorDrawable.draw(canvas);

    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//        this.mCurrentTab = position;
    }

    @Override
    public void onPageSelected(int position) {
        this.mCurrentTab = position;
        updateTabSelection(position);
        if (mListener != null) {
            mListener.onTabSelect(position);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void updateTabSelection(int position) {

        for (int i = 0; i < mTabCount; ++i) {
            View tabView = mTabsContainer.getChildAt(i);
            final boolean isSelect = i == position;


            TextView tab_title = tabView.findViewById(R.id.tv_tab_title);
            if (tab_title != null) {
                tab_title.setTextColor(isSelect ? mTextSelectColor : mTextUnSelectColor);
            }
        }

        invalidate();
    }

    private void calcIndicatorRect() {
        View currentTabView = mTabsContainer.getChildAt(this.mCurrentTab);
        float left = currentTabView.getLeft();
        float right = currentTabView.getRight();

        mIndicatorRect.left = (int) left;
        mIndicatorRect.right = (int) right;

        /**The corners are ordered top-left, top-right, bottom-right, bottom-left*/
        mRadiusArr[0] = mIndicatorCornerRadius;
        mRadiusArr[1] = mIndicatorCornerRadius;
        mRadiusArr[2] = mIndicatorCornerRadius;
        mRadiusArr[3] = mIndicatorCornerRadius;
        mRadiusArr[4] = mIndicatorCornerRadius;
        mRadiusArr[5] = mIndicatorCornerRadius;
        mRadiusArr[6] = mIndicatorCornerRadius;
        mRadiusArr[7] = mIndicatorCornerRadius;
    }

}
