package com.github.jzyu.common.utils;

import android.content.res.Resources;
import android.util.TypedValue;

/**
 * Date  : 2019/4/10.
 */
public class Utils {
    private Utils() {
    }

    public static float dp2px(int dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                Resources.getSystem().getDisplayMetrics());
    }
}
