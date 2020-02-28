package com.youngmanster.collectionlibrary.customview.tagview.layout;


import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by yangy
 * 2020-02-25
 * Describe:
 */
@IntDef({AlignItems.FLEX_START, AlignItems.FLEX_END, AlignItems.CENTER,
        AlignItems.BASELINE, AlignItems.STRETCH})
@Retention(RetentionPolicy.SOURCE)
public @interface AlignItems {

    /** Flex item's edge is placed on the cross start line. */
    int FLEX_START = 0;

    /** Flex item's edge is placed on the cross end line. */
    int FLEX_END = 1;

    /** Flex item's edge is centered along the cross axis. */
    int CENTER = 2;

    /** Flex items are aligned based on their text's baselines. */
    int BASELINE = 3;

    /** Flex items are stretched to fill the flex line's cross size. */
    int STRETCH = 4;
}