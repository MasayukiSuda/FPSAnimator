package com.daasuu.FPSAnimator.util;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

public class UIUtil {

    /**
     * @param context
     * @return
     */
    public static int getWindowHeight(Context context) {
        Display disp = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point size = new Point();
        disp.getSize(size);
        return size.y;
    }

    /**
     * @param context
     * @return
     */
    public static int getWindowWidth(Context context) {
        Display disp = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point size = new Point();
        disp.getSize(size);
        return size.x;
    }

    /**
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {

        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
