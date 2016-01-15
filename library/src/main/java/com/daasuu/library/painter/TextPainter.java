package com.daasuu.library.painter;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 */
public class TextPainter extends DeformablePainter {

    private static final String TAG = TextPainter.class.getSimpleName();

    /**
     * String text to be drawn in FPSTextureView or FPSSurfaceView.
     */
    private String mText;

    /**
     * Adjustment values ​​for drawing the canvas coordinates ( 0, 0 )
     */
    private float mAdjustTextMesureY = -1;

    public TextPainter(String text) {
        this(text, new Paint());
    }


    public TextPainter(String text, Paint paint) {
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

    public TextPainter scaleRegistration(float regX, float regY) {
        setScaleRegistration(regX, regY);
        return this;
    }

    public TextPainter rotateRegistration(float regX, float regY) {
        setRotateRegistration(regX, regY);
        return this;
    }
}
