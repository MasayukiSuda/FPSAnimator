package com.daasuu.library.util;

import android.content.Context;
import android.graphics.PointF;
import android.util.DisplayMetrics;

import com.daasuu.library.constant.Constant;

public class Util {

    /**
     * And then converted to a device-specific pixel density -independent pixels .
     *
     * @param dp      in device-specific pixel density
     * @param context Activity or view context
     * @return in pixel number
     */
    public static float convertDpToPixel(float dp, Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return dp * (metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    /**
     * And then converted to a device-specific pixel density -independent pixels .
     *
     * @param px      in pixel number
     * @param context Activity or view context
     * @return in device-specific pixel density
     */
    public static float convertPixelsToDp(float px, Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return px / (metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    /**
     * The alpha (transparency) ,from as a percentage of 1 to as a percentage of 255.
     *
     * @param alphaFloat from as a percentage of 1
     * @return int as a percentage of 255.
     */
    public static int convertAlphaFloatToInt(float alphaFloat) {
        if (alphaFloat > 1f) return Constant.DEFAULT_ALPHA;
        if (alphaFloat < 0f) return 0;

        return (int) (alphaFloat * Constant.DEFAULT_ALPHA);
    }

    /**
     * The alpha (transparency) ,from as a percentage of 255 to as a percentage of 1.
     *
     * @param alphaInt int as a percentage of 255.
     * @return float as a percentage of 1
     */
    public static float convertAlphaIntToFloat(int alphaInt) {
        if (alphaInt >= 255) return 1f;
        if (alphaInt <= 0f) return 0;

        return (float) alphaInt / Constant.DEFAULT_ALPHA;
    }

    public static PointF getPointByDistanceAndDegree(double distance, double degree) {

        double radian = Math.PI / 180 * degree;
        float x = (float) ((float) Math.cos(radian) * distance);
        float y = (float) ((float) Math.sin(radian) * distance);

        return new PointF(x, y);
    }

}
