package com.youngmanster.collectionlibrary.customview.tagview;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.youngmanster.collectionlibrary.customview.tagview.layout.FlexWrap;
import com.youngmanster.collectionlibrary.customview.tagview.layout.FlexboxLayout;
import com.youngmanster.collectionlibrary.customview.tagview.layout.JustifyContent;
import com.youngmanster.collectionlibrary.utils.ColorUtils;
import com.youngmanster.collectionlibrary.utils.DisplayUtils;

/**
 * Created by yangy
 * 2020-02-28
 * Describe:
 */
public class TagView extends FlexboxLayout {

    public interface TagViewPressListener{
        void onPress(View view, String title, int position);
    }

    private Context mContext;
    private TagViewPressListener onTagViewPressListener;
    private TagViewConfigBuilder tagViewBuilder;

    public TagView(Context context) {
        super(context);
        this.mContext = context;
    }

    public TagView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }

    public TagView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
    }


    public void create(TagViewConfigBuilder tagViewBuilder,TagViewPressListener onTagViewPressListener) {
        this.tagViewBuilder = tagViewBuilder;
        this.onTagViewPressListener=onTagViewPressListener;
        this.setFlexWrap(FlexWrap.WRAP);

        if(tagViewBuilder.getTagViewAlign()==TagViewConfigBuilder.TagViewAlign.CENTER){
            this.setJustifyContent(JustifyContent.CENTER);
        }else if(tagViewBuilder.getTagViewAlign()==TagViewConfigBuilder.TagViewAlign.LEFT){
            this.setJustifyContent(JustifyContent.FLEX_START);
        }else if(tagViewBuilder.getTagViewAlign()==TagViewConfigBuilder.TagViewAlign.RIGHT){
            this.setJustifyContent(JustifyContent.FLEX_END);
        }

        if (tagViewBuilder.getTitles() != null) {

            for(int i=0;i<tagViewBuilder.getTitles().length;i++){
                this.addView(createNewFlexItemTextView(tagViewBuilder.getTitles()[i], i));
            }
        }
    }


    /**
     * TagView
     * 动态创建TextView
     * @return
     */
    private TextView createNewFlexItemTextView(String str, final int pos) {
        TextView textView = new TextView(mContext);
        textView.setGravity(Gravity.CENTER);
        textView.setText(str);
        textView.setTextSize(tagViewBuilder.getTextSize());
        textView.setTextColor(ColorUtils.createColorStateList(tagViewBuilder.getTextSelectColor(),tagViewBuilder.getTextSelectColor(),tagViewBuilder.getTextColor()));
        textView.setTag(pos);

        textView.setBackgroundResource(tagViewBuilder.getBackgroudRes());
        textView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int position= (int) v.getTag();
                onTagViewPressListener.onPress(v,tagViewBuilder.getTitles()[position],position);
            }
        });

        float padding = DisplayUtils.dip2px(mContext, tagViewBuilder.getPaddingTopAndBottom());
        float paddingLeftAndRight =
                DisplayUtils.dip2px(mContext, tagViewBuilder.getPaddingLeftAndRight());
        ViewCompat.setPaddingRelative(
                textView,
                (int)paddingLeftAndRight,
                (int)padding,
                (int)paddingLeftAndRight,
                (int)padding
        );
        LayoutParams layoutParams = new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        float margin = DisplayUtils.dip2px(mContext, tagViewBuilder.getMarginLeftAndRight());
        float marginTopAndBottom =
                DisplayUtils.dip2px(mContext, tagViewBuilder.getMarginTopAndBottom());
        layoutParams.setMargins((int)margin, (int)marginTopAndBottom, (int)margin, (int)marginTopAndBottom);
        layoutParams.setMargins(10,10,10,10);
        textView.setLayoutParams(layoutParams);
        return textView;
    }
}
