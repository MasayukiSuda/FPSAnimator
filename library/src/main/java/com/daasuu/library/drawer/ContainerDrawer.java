package com.daasuu.library.drawer;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by sudamasayuki on 16/01/25.
 */
public class ContainerDrawer extends BaseDrawer {

    public ContainerDrawer(Paint paint) {
        super(paint);
    }

    @Override
    protected void draw(Canvas canvas, float x, float y) {

    }

    @Override
    public float getWidth() {
        return 0;
    }

    @Override
    public float getHeight() {
        return 0;
    }
}
