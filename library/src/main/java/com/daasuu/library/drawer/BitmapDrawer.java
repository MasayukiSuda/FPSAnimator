package com.daasuu.library.drawer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.NonNull;

import com.daasuu.library.util.Util;

/**
 * Class for drawing Bitmap on canvas.
 */
public class BitmapDrawer extends BaseDrawer {
    private static final String TAG = BitmapDrawer.class.getSimpleName();

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

    public BitmapDrawer(@NonNull Bitmap bitmap) {
        super(new Paint());
        this.mBitmap = bitmap;
    }

    /**
     * Draw a Bitmap in device-specific pixel density.
     *
     * @param context Activity or view context
     * @return this
     */
    public BitmapDrawer dpSize(@NonNull Context context) {
        mDpSize = true;
        mBitmapDpWidth = Util.convertPixelsToDp(mBitmap.getWidth(), context);
        mBitmapDpHeight = Util.convertPixelsToDp(mBitmap.getHeight(), context);
        mBitmapRect = new Rect(0, 0, mBitmap.getWidth(), mBitmap.getHeight());
        return this;
    }

    public BitmapDrawer scaleRegistration(float regX, float regY) {
        setScaleRegistration(regX, regY);
        return this;
    }

    public BitmapDrawer rotateRegistration(float regX, float regY) {
        setRotateRegistration(regX, regY);
        return this;
    }

    @Override
    public float getWidth() {
        return mDpSize ?
                mBitmapDpWidth :
                mBitmap.getWidth();
    }

    @Override
    public float getHeight() {
        return mDpSize ?
                mBitmapDpHeight :
                mBitmap.getHeight();
    }

    @Override
    protected void draw(Canvas canvas, float x, float y) {
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
            canvas.drawBitmap(mBitmap, mBitmapRect, dpSizeRect, mPaint);
        } else {
            canvas.drawBitmap(mBitmap, x, y, mPaint);
        }
    }
}
