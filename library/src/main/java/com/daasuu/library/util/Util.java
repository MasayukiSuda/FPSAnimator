package com.daasuu.library.util;

import android.content.Context;
import android.util.DisplayMetrics;

import com.daasuu.library.constant.Constant;

public class Util {

    /**
     * And then converted to a device-specific pixel density -independent pixels .
     *
     * @param dp
     * @param context
     * @return
     */
    public static float convertDpToPixel(float dp, Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return dp * (metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    /**
     * And then converted to a device-specific pixel density -independent pixels .
     *
     * @param px
     * @param context
     * @return
     */
    public static float convertPixelsToDp(float px, Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return px / (metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    /**
     * @param alphaFloat
     * @return
     */
    public static int convertAlphaFloatToInt(float alphaFloat) {
        if (alphaFloat > 1f) return Constant.DEFAULT_ALPHA;
        if (alphaFloat < 0f) return 0;

        return (int) (alphaFloat * Constant.DEFAULT_ALPHA);
    }

}
