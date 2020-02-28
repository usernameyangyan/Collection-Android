package com.youngmanster.collectionlibrary.customview.tagview.layout;


import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by yangy
 * 2020-02-25
 * Describe:
 */
@IntDef({AlignContent.FLEX_START, AlignContent.FLEX_END, AlignContent.CENTER,
        AlignContent.SPACE_BETWEEN, AlignContent.SPACE_AROUND, AlignContent.STRETCH})
@Retention(RetentionPolicy.SOURCE)
public @interface AlignContent {

    /** Flex lines are packed to the start of the flex container. */
    int FLEX_START = 0;

    /** Flex lines are packed to the end of the flex container. */
    int FLEX_END = 1;

    /** Flex lines are centered in the flex container. */
    int CENTER = 2;

    /**
     * Flex lines are evenly distributed in the flex container. The first flex line is
     * placed at the start of the flex container, the last flex line is placed at the
     * end of the flex container.
     */
    int SPACE_BETWEEN = 3;

    /**
     * Flex lines are evenly distributed in the flex container with the same amount of spaces
     * between the flex lines.
     */
    int SPACE_AROUND = 4;

    /**
     * Flex lines are stretched to fill the remaining space along the cross axis.
     */
    int STRETCH = 5;
}
