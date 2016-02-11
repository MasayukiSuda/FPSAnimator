package com.daasuu.library.drawer;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;

/**
 * Draw the specified Rect using the specified paint. The rectangle will
 * be filled or framed based on the Style in the paint.
 */
public class RectDrawer extends BaseDrawer {

    private float mWide;

    private float mLong;

    public RectDrawer(@NonNull Paint paint, float wide, float mLong) {
        super(paint);
        this.mWide = wide;
        this.mLong = mLong;
    }

    @Override
    protected void draw(Canvas canvas, float x, float y, int alpha) {
        mPaint.setAlpha(alpha);
        canvas.drawRect(x, y, x + mWide, y + mLong, mPaint);
    }

    @Override
    public float getWidth() {
        return mWide;
    }

    @Override
    public float getHeight() {
        return mLong;
    }

    /**
     * The left offset and y offset for this display object's registration point. For example, to make a 100x100px Bitmap scale
     * around its center, you would set regX and regY to 50.
     *
     * @param regX The horizontal rotate registration point in pixels
     * @param regY The vertical rotate registration point in pixels
     * @return this
     */
    public RectDrawer scaleRegistration(float regX, float regY) {
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
    public RectDrawer rotateRegistration(float regX, float regY) {
        setRotateRegistration(regX, regY);
        return this;
    }

}
