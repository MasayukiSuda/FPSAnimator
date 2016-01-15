package com.daasuu.library;

import android.graphics.Canvas;

/**
 *
 */
public interface Anim {

    void updateAnimParam(AnimParameter position);

    void setBaseLine(Canvas canvas, float width, float height);

    AnimParameter getInitialPosition();

    void setUp(long fps);
}
