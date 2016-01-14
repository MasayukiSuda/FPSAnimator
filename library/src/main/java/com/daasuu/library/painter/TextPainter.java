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

    public TextPainter(String text) {
        this.mText = text;
    }

    @Override
    public void draw(Canvas canvas, float x, float y, Paint paint) {
        if (mText != null) {
            setAdjustTextMesureY(paint);
            canvas.drawText(mText, x, y + mAdjustTextMesureY, paint);
        }
    }

    @Override
    public float getWidth(Paint paint) {
        setAdjustTextMesureY(paint);
        return paint.measureText(mText);
    }

    @Override
    public float getHeight(Paint paint) {
        setAdjustTextMesureY(paint);
        return mAdjustTextMesureY;
    }

    private void setAdjustTextMesureY(Paint paint) {
        if (mAdjustTextMesureY == -1) {
            mAdjustTextMesureY = paint.getTextSize();
        }
    }
}
