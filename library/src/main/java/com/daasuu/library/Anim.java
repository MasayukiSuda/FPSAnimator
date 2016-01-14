package com.daasuu.library;

import android.graphics.Canvas;

/**
 * Created by a13587 on 16/01/14.
 */
public interface Anim {

    void updatePosition(Position position);

    void setBaseLine(Canvas canvas, float width, float height);

    Position getInitialPosition();

    void setUp(long fps);
}
