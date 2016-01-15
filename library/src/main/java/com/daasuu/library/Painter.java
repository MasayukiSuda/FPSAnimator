package com.daasuu.library;

import android.graphics.Canvas;

/**
 */
public interface Painter {

    void draw(Canvas canvas, float x, float y, int alpha, float scaleX, float scaleY, float rotation);

    float getWidth();

    float getHeight();
}
