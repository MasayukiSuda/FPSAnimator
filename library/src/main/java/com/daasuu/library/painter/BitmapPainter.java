package com.daasuu.library.painter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.NonNull;

import com.daasuu.library.Painter;
import com.daasuu.library.util.Util;

/**
 * Created by a13587 on 16/01/14.
 */
public class BitmapPainter implements Painter {
    private static final String TAG = BitmapPainter.class.getSimpleName();

    /**
     * Bitmap to be drawn in FPSTextureView or FPSSurfaceView.
     */
    private Bitmap mBitmap;

    /**
     * If true, Draw a Bitmap in device-specific pixel density.
     */
    private boolean mDpSize = false;

    /**
     * Width of Bitmap in device-specific pixel density.
     */
    private float mBitmapDpWidth;

    /**
     * Height of Bitmap in device-specific pixel density.
     */
    private float mBitmapDpHeight;

    /**
     * Bitmap of Rect holds four integer coordinates for a rectangle.
     */
    private Rect mBitmapRect;

    public BitmapPainter(Bitmap bitmap) {
        this.mBitmap = bitmap;
    }

    /**
     * Draw a Bitmap in device-specific pixel density.
     *
     * @param context Activity or view context
     * @return this
     */
    public BitmapPainter dpSize(@NonNull Context context) {
        mDpSize = true;
        mBitmapDpWidth = Util.convertPixelsToDp(mBitmap.getWidth(), context);
        mBitmapDpHeight = Util.convertPixelsToDp(mBitmap.getHeight(), context);
        mBitmapRect = new Rect(0, 0, mBitmap.getWidth(), mBitmap.getHeight());
        return this;
    }

    @Override
    public float getWidth(Paint paint) {
        return mDpSize ?
                mBitmapDpWidth :
                mBitmap.getWidth();
    }

    @Override
    public float getHeight(Paint paint) {
        return mDpSize ?
                mBitmapDpHeight :
                mBitmap.getHeight();
    }

    @Override
    public void draw(Canvas canvas, float x, float y, Paint paint) {
        if (mBitmap == null) {
            return;
        }

        if (mDpSize) {
            RectF dpSizeRect = new RectF(
                    x,
                    y,
                    x + mBitmapDpWidth,
                    y + mBitmapDpHeight
            );
            canvas.drawBitmap(mBitmap, mBitmapRect, dpSizeRect, paint);
        } else {
            canvas.drawBitmap(mBitmap, x, y, paint);
        }
    }
}
