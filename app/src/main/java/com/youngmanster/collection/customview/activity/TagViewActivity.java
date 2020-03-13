package com.youngmanster.collection.customview.activity;

import android.view.View;

import com.youngmanster.collection.R;
import com.youngmanster.collection.base.BaseActivity;
import com.youngmanster.collectionlibrary.customview.tagview.TagView;
import com.youngmanster.collectionlibrary.customview.tagview.TagViewConfigBuilder;
import com.youngmanster.collectionlibrary.utils.ToastUtils;

import butterknife.BindView;

/**
 * Created by yangy
 * 2020-02-28
 * Describe:
 */
public class TagViewActivity  extends BaseActivity {

    @BindView(R.id.tagView)
    TagView tagView;


    @Override
    public int getLayoutId() {
        return R.layout.activity_tagview;
    }

    @Override
    public void init() {
        defineActionBarConfig.setTitle("TagView");

        String[] list={"werwrw","4545465","金浩","风和日丽",
                "一只蜜蜂叮在挂历上","阳光","灿烂","1+1","浏览器","玲珑骰子安红豆，入骨相思知不知"};

        TagViewConfigBuilder builder=new TagViewConfigBuilder()
                .setTitles(list);

        tagView.create(builder, new TagView.TagViewPressListener() {
            @Override
            public void onPress(View view, String title, int position) {
                ToastUtils.showToast(TagViewActivity.this,title);
            }
        });
    }

    @Override
    public void requestData() {

    }
}
