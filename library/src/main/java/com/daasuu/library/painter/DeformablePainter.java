package com.daasuu.library.painter;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.daasuu.library.Painter;

/**
 * Created by m1noon on 16/01/15.
 */
public abstract class DeformablePainter implements Painter {
    private static final String TAG = DeformablePainter.class.getSimpleName();

    protected final Paint mPaint;

    /**
     * The left offset for this display object's registration point. For example, to make a 100x100px Bitmap scale
     * around its center, you would set mRotateRegistrationX and mRotateRegistrationY to 50.
     */
    protected float mScaleRegistrationX = 0f;

    /**
     * The y offset for this display object's registration point. For example, to make a 100x100px Bitmap scale
     * around its center, you would set mRotateRegistrationX and mRotateRegistrationY to 50.
     */
    protected float mScaleRegistrationY = 0f;

    /**
     * The left offset for this display object's registration point. For example, to make a 100x100px Bitmap rotate
     * around its center, you would set mRotateRegistrationX and mRotateRegistrationY to 50.
     */
    protected float mRotateRegistrationX = 0f;

    /**
     * The y offset for this display object's registration point. For example, to make a 100x100px Bitmap rotate
     * around its center, you would set mRotateRegistrationX and mRotateRegistrationY to 50.
     */
    protected float mRotateRegistrationY = 0f;


    public DeformablePainter(Paint paint) {
        this.mPaint = paint;
    }

    protected void setScaleRegistration(float regX, float regY) {
        mScaleRegistrationX = regX;
        mScaleRegistrationY = regY;
    }

    /**
     * Setter mRotateRegistrationX and mRotateRegistrationY
     *
     * @param regX The horizontal rotate registration point in pixels
     * @param regY The vertical rotate registration point in pixels
     */
    protected void setRotateRegistration(float regX, float regY) {
        mRotateRegistrationX = regX;
        mRotateRegistrationY = regY;
    }

    @Override
    public void draw(Canvas canvas, float x, float y, int alpha, float scaleX, float scaleY, float rotation) {
        mPaint.setAlpha(alpha);

        canvas.save();
        canvas.scale(scaleX, scaleY, x + mScaleRegistrationX, y + mScaleRegistrationY);
        canvas.rotate(rotation, x + mRotateRegistrationX, y + mRotateRegistrationY);

        draw(canvas, x, y);

        canvas.restore();
    }

    protected abstract void draw(Canvas canvas, float x, float y);
}
