package com.daasuu.FPSAnimator.UIUtil;

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
}
