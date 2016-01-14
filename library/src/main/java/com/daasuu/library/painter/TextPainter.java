package com.daasuu.library.painter;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.daasuu.library.Painter;

/**
 * Created by a13587 on 16/01/14.
 */
public class TextPainter implements Painter {

    private static final String TAG = TextPainter.class.getSimpleName();

    /**
     * String text to be drawn in FPSTextureView or FPSSurfaceView.
     */
    private String mText;

    /**
     * Adjustment values ​​for drawing the canvas coordinates ( 0, 0 )
     */
    private float mAdjustTextMesureY = -1;

    private final Paint mPaint;

    public TextPainter(String text) {
        this(text, new Paint());
    }

    public TextPainter(String text, Paint paint) {
        this.mText = text;
        this.mPaint = paint;
        mAdjustTextMesureY = paint.getTextSize();
    }

    @Override
    public void draw(Canvas canvas, float x, float y) {
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
}
