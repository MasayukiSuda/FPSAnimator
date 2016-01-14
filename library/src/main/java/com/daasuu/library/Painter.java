package com.daasuu.library;

import android.graphics.Canvas;

/**
 * Created by a13587 on 16/01/14.
 */
public interface Painter {

    void draw(Canvas canvas, float x, float y);

    float getWidth();

    float getHeight();
}
