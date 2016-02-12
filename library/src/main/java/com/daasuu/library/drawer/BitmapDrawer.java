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

    /**
     * The rectangle that the bitmap will be scaled/translated to fit into
     */
    private RectF mDpSizeRect;

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
        mDpSizeRect = new RectF();
        return this;
    }

    /**
     * The left offset and y offset for this display object's registration point. For example, to make a 100x100px Bitmap scale
     * around its center, you would set regX and regY to 50.
     *
     * @param regX The horizontal rotate registration point in pixels
     * @param regY The vertical rotate registration point in pixels
     * @return this
     */
    public BitmapDrawer scaleRegistration(float regX, float regY) {
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
    public BitmapDrawer rotateRegistration(float regX, float regY) {
        setRotateRegistration(regX, regY);
        return this;
    }

    @Override
    public float getWidth() {
        return mBitmap == null ?
                0 :
                mDpSize ? mBitmapDpWidth : mBitmap.getWidth();
    }

    @Override
    public float getHeight() {
        return mBitmap == null ?
                0 :
                mDpSize ? mBitmapDpHeight : mBitmap.getHeight();
    }

    @Override
    protected void draw(Canvas canvas, float x, float y, int alpha) {
        if (mBitmap == null) {
            return;
        }

        if (mBitmap.isRecycled()) {
            mBitmap = null;
            return;
        }

        mPaint.setAlpha(alpha);

        if (mDpSize) {
            mDpSizeRect.set(
                    x,
                    y,
                    x + mBitmapDpWidth,
                    y + mBitmapDpHeight
            );
            canvas.drawBitmap(mBitmap, mBitmapRect, mDpSizeRect, mPaint);
        } else {
            canvas.drawBitmap(mBitmap, x, y, mPaint);
        }
    }
}
