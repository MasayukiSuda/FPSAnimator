package com.daasuu.library.drawer;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;

/**
 * Draw the specified circle using the specified paint. If radius is less 0,
 * then nothing will be drawn. The circle will be filled or framed based
 * on the Style in the paint.
 */
public class CircleDrawer extends BaseDrawer {

    private float mRadius;

    /**
     * Constructor
     *
     * @param paint  The paint used to draw the circle
     * @param radius The radius of the circle to be drawn
     */
    public CircleDrawer(@NonNull Paint paint, float radius) {
        super(paint);
        mRadius = radius;
    }

    @Override
    protected void draw(Canvas canvas, float x, float y, int alpha) {
        mPaint.setAlpha(alpha);
        canvas.drawCircle(x + mRadius, y + mRadius, mRadius, mPaint);
    }

    @Override
    public float getWidth() {
        return mRadius * 2;
    }

    @Override
    public float getHeight() {
        return mRadius * 2;
    }

    /**
     * The left offset and y offset for this display object's registration point. For example, to make a 100x100px Bitmap scale
     * around its center, you would set regX and regY to 50.
     *
     * @param regX The horizontal rotate registration point in pixels
     * @param regY The vertical rotate registration point in pixels
     * @return this
     */
    public CircleDrawer scaleRegistration(float regX, float regY) {
        setScaleRegistration(regX, regY);
        return this;
    }

    /**
     * The left offset and y offset for this display object's registration point. For example, to make a 100x100px Bitmap rotate
     * around its center, you would set regX and regY to 50.
     *
     * @param regX The horizontal rotate registration point in pixels
     * @param regY The vertical rotate registration point in pixels
     * @return this
     */
    public CircleDrawer rotateRegistration(float regX, float regY) {
        setRotateRegistration(regX, regY);
        return this;
    }
}
