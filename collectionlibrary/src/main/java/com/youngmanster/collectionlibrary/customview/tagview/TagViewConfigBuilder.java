package com.youngmanster.collectionlibrary.customview.tagview;

import com.youngmanster.collectionlibrary.R;

/**
 * Created by yangy
 * 2020-02-28
 * Describe:
 */
public class TagViewConfigBuilder {

    public enum TagViewAlign{
        LEFT,RIGHT,CENTER
    }

    private String[] titles;
    private float textSize=14f;
    private String textColor="#D81B60";
    private String textSelectColor="#000000";
    private float paddingLeftAndRight=10f;
    private float paddingTopAndBottom=6f;
    private float marginLeftAndRight=6f;
    private float marginTopAndBottom=8f;
    private int backgroudRes= R.drawable.collection_library_selector_tagview;
    private TagViewAlign tagViewAlign=TagViewAlign.CENTER;

    public TagViewConfigBuilder setTitles(String[] titles){
        this.titles=titles;
        return this;
    }

    public String[] getTitles(){
        return titles;
    }

    public TagViewConfigBuilder setTextSize(float textSize){
        this.textSize=textSize;
        return this;
    }

    public float getTextSize(){
        return textSize;
    }

    public TagViewConfigBuilder setTextColor(String textColor){
        this.textColor=textColor;
        return this;
    }

    public String getTextColor(){
        return textColor;
    }

    public TagViewConfigBuilder setTextSelectColor(String textColor){
        this.textSelectColor=textColor;
        return this;
    }

    public String getTextSelectColor(){
        return textSelectColor;
    }

    public TagViewConfigBuilder setPaddingLeftAndRight(float padding){
        this.paddingLeftAndRight=padding;
        return this;
    }

    public float getPaddingLeftAndRight(){
        return paddingLeftAndRight;
    }

    public TagViewConfigBuilder setPaddingTopAndBottom(float padding){
        this.paddingTopAndBottom=padding;
        return this;
    }

    public float getPaddingTopAndBottom(){
        return paddingTopAndBottom;
    }

    public TagViewConfigBuilder setMarginAndTopBottom(float margin){
        this.marginTopAndBottom=margin;
        return this;
    }

    public float getMarginTopAndBottom(){
        return marginTopAndBottom;
    }

    public TagViewConfigBuilder setMarginLeftAndRight(float margin){
        this.marginLeftAndRight=margin;
        return this;
    }

    public float getMarginLeftAndRight(){
        return marginLeftAndRight;
    }

    public TagViewConfigBuilder setackgroudRes(int backgroudRes){
        this.backgroudRes=backgroudRes;
        return this;
    }

    public int getBackgroudRes(){
        return backgroudRes;
    }

    public TagViewConfigBuilder setTagViewAlign(TagViewAlign tagViewAlign){
        this.tagViewAlign=tagViewAlign;
        return this;
    }

    public TagViewAlign getTagViewAlign(){
        return tagViewAlign;
    }

}
