package com.daasuu.library.drawer;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;

/**
 * Class for drawing Text on canvas.
 */
public class TextDrawer extends BaseDrawer {

    private static final String TAG = TextDrawer.class.getSimpleName();

    /**
     * String text to be drawn in FPSTextureView or FPSSurfaceView.
     */
    private String mText;

    /**
     * Adjustment values ​​for drawing the canvas coordinates ( 0, 0 )
     */
    private float mAdjustTextMesureY = -1;

    public TextDrawer(@NonNull String text) {
        this(text, new Paint());
    }


    public TextDrawer(@NonNull String text, Paint paint) {
        super(paint);
        this.mText = text;
        mAdjustTextMesureY = paint.getTextSize();
    }

    @Override
    protected void draw(Canvas canvas, float x, float y) {
        if (mText != null) {
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

    public TextDrawer scaleRegistration(float regX, float regY) {
        setScaleRegistration(regX, regY);
        return this;
    }

    public TextDrawer rotateRegistration(float regX, float regY) {
        setRotateRegistration(regX, regY);
        return this;
    }
}
