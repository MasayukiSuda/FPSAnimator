package com.daasuu.library.drawer;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;

/**
 * Class for drawing Text on canvas.
 */
public class TextDrawer extends BaseDrawer {

    /**
     * String text to be drawn in FPSTextureView or FPSSurfaceView.
     */
    private String mText;

    /**
     * Adjustment values ​​for drawing the canvas coordinates ( 0, 0 )
     */
    private float mAdjustTextMesureY = -1;

    /**
     * Constructor
     *
     * @param text String text to be drawn in FPSTextureView or FPSSurfaceView.
     */
    public TextDrawer(@NonNull String text) {
        this(text, new Paint());
    }

    /**
     * Constructor
     *
     * @param text  String text to be drawn in FPSTextureView or FPSSurfaceView.
     * @param paint Style and color and typeface .. etc information
     */
    public TextDrawer(@NonNull String text, Paint paint) {
        super(paint);
        this.mText = text;
        mAdjustTextMesureY = paint.getTextSize();
    }

    @Override
    protected void draw(Canvas canvas, float x, float y, int alpha) {
        if (mText != null) {
            mPaint.setAlpha(alpha);
            canvas.drawText(mText, x, y + mAdjustTextMesureY, mPaint);
        }
    }

    @Override
    public float getWidth() {
        return mPaint.measureText(mText);
    }

    @Override
    public float getHeight() {
        return mAdjustTextMesureY;
    }

    /**
     * The left offset and y offset for this display object's registration point. For example, to make a 100x100px Bitmap scale
     * around its center, you would set regX and regY to 50.
     *
     * @param regX The horizontal rotate registration point in pixels
     * @param regY The vertical rotate registration point in pixels
     * @return this
     */
    public TextDrawer scaleRegistration(float regX, float regY) {
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
    public TextDrawer rotateRegistration(float regX, float regY) {
        setRotateRegistration(regX, regY);
        return this;
    }
}
