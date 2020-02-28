package com.youngmanster.collectionlibrary.customview.tagview.layout;


import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by yangy
 * 2020-02-25
 * Describe:
 */
@IntDef({JustifyContent.FLEX_START, JustifyContent.FLEX_END, JustifyContent.CENTER,
        JustifyContent.SPACE_BETWEEN, JustifyContent.SPACE_AROUND, JustifyContent.SPACE_EVENLY})
@Retention(RetentionPolicy.SOURCE)
public @interface JustifyContent {

    /** Flex items are packed toward the start line. */
    int FLEX_START = 0;

    /** Flex items are packed toward the end line. */
    int FLEX_END = 1;

    /** Flex items are centered along the flex line where the flex items belong. */
    int CENTER = 2;

    /**
     * Flex items are evenly distributed along the flex line, first flex item is on the
     * start line, the last flex item is on the end line.
     */
    int SPACE_BETWEEN = 3;

    /**
     * Flex items are evenly distributed along the flex line with the same amount of spaces between
     * the flex lines.
     */
    int SPACE_AROUND = 4;

    /**
     * Flex items are evenly distributed along the flex line. The difference between
     * {@link #SPACE_AROUND} is that all the spaces between items should be the same as the
     * space before the first item and after the last item.
     * See
     * <a href="https://developer.mozilla.org/en-US/docs/Web/CSS/justify-content">the document on MDN</a>
     * for more details.
     */
    int SPACE_EVENLY = 5;
}
