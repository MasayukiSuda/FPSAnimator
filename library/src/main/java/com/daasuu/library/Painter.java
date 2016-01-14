package com.daasuu.library;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by a13587 on 16/01/14.
 */
public interface Painter {

    void draw(Canvas canvas, float x, float y, Paint paint);

    float getWidth(Paint paint);

    float getHeight(Paint paint);
}
