package com.youngmanster.collectionlibrary.utils;

import android.content.res.ColorStateList;
import android.graphics.Color;

/**
 * Created by yangy
 * 2020-02-25
 * Describe:
 */
public class ColorUtils {

    public static ColorStateList createColorStateList(String selected, String pressed, String normal) {
        int[] colors = new int[] {Color.parseColor(selected), Color.parseColor(pressed), Color.parseColor(normal) };
        int[][] states = new int[3][];
        states[0] = new int[] { android.R.attr.state_selected};
        states[1] = new int[] { android.R.attr.state_pressed};
        states[2] = new int[] {};
        ColorStateList colorList = new ColorStateList(states, colors);
        return colorList;
    }
}
