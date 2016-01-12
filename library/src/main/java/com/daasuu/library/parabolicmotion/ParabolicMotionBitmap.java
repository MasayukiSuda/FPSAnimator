package com.daasuu.library.parabolicmotion;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.NonNull;

import com.daasuu.library.callback.AnimCallBack;
import com.daasuu.library.util.Util;


public class ParabolicMotionBitmap extends ParabolicMotion {

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
     * Constructor
     *
     * @param bitmap Bitmap to be drawn in FPSTextureView or FPSSurfaceView.
     */
    public ParabolicMotionBitmap(@NonNull Bitmap bitmap) {
        this.mBitmap = bitmap;
    }

    /**
     * Draw a Bitmap in device-specific pixel density.
     *
     * @param context Activity or view context
     * @return this
     */
    public ParabolicMotionBitmap dpSize(@NonNull Context context) {
        mDpSize = true;
        mBitmapDpWidth = Util.convertPixelsToDp(mBitmap.getWidth(), context);
        mBitmapDpHeight = Util.convertPixelsToDp(mBitmap.getHeight(), context);
        mBitmapRect = new Rect(0, 0, mBitmap.getWidth(), mBitmap.getHeight());
        return this;
    }

    /**
     * Shortcut method to quickly set the transform properties on the display object.
     *
     * @param x The horizontal translation (x position) in pixels
     * @param y The vertical translation (y position) in pixels
     * @return this
     */
    public ParabolicMotionBitmap transform(float x, float y) {
        this.x = x;
        this.y = y;
        return this;
    }

    /**
     * Set the number to be updated in every times of tick
     *
     * @param frequency The number to be updated in every times of tick
     * @return this
     */
    public ParabolicMotionBitmap frequency(int frequency) {
        mFrequency = frequency;
        return this;
    }

    /**
     * Set initial velocity of parabolic movement (y position) in pixels
     *
     * @param velocityY Initial velocity of parabolic movement (y position) in pixels
     * @return this
     */
    public ParabolicMotionBitmap initialVelocityY(float velocityY) {
        mInitialVelocityY = velocityY;
        return this;
    }

    /**
     * Set the number to accelerate (y position) in pixels
     *
     * @param accelerationY By one tick, the number to accelerate (y position) in pixels
     * @return this
     */
    public ParabolicMotionBitmap accelerationY(float accelerationY) {
        mAccelerationY = accelerationY;
        return this;
    }

    /**
     * Set the number to accelerate (x position) in pixels
     *
     * @param accelerationX By one tick, the number to accelerate (x position) in pixels
     * @return this
     */
    public ParabolicMotionBitmap accelerationX(float accelerationX) {
        mAccelerationX = accelerationX;
        return this;
    }

    /**
     * Set coefficient Of Restitution Y, as a percentage of 1
     *
     * @param coefficientRestitutionY Coefficient Of Restitution Y, as a percentage of 1
     * @return this
     */
    public ParabolicMotionBitmap coefficientRestitutionY(float coefficientRestitutionY) {
        mCoefficientRestitutionY = coefficientRestitutionY;
        return this;
    }

    /**
     * Set coefficient Of Restitution X, as a percentage of 1
     *
     * @param coefficientRestitutionX Coefficient Of Restitution X, as a percentage of 1
     * @return this
     */
    public ParabolicMotionBitmap coefficientRestitutionX(float coefficientRestitutionX) {
        mCoefficientRestitutionX = coefficientRestitutionX;
        return this;
    }

    /**
     * Set the flag indicating whether not rebound bottom
     *
     * @param reboundBottom The flag indicating whether not rebound bottom
     * @return this
     */
    public ParabolicMotionBitmap reboundBottom(boolean reboundBottom) {
        mReboundBottom = reboundBottom;
        return this;
    }

    /**
     * Set the flag indicating whether not rebound left
     *
     * @param reboundLeft The flag indicating whether not rebound left
     * @return this
     */
    public ParabolicMotionBitmap reboundLeft(boolean reboundLeft) {
        mReboundLeft = reboundLeft;
        return this;
    }

    /**
     * Set the flag indicating whether not rebound right
     *
     * @param reboundRight The flag indicating whether not rebound right
     * @return this
     */
    public ParabolicMotionBitmap reboundRight(boolean reboundRight) {
        mReboundRight = reboundRight;
        return this;
    }

    /**
     * Set the number to rebound bottom (y position) in pixels
     *
     * @param bottomBase The number to rebound bottom (y position) in pixels
     * @return this
     */
    public ParabolicMotionBitmap bottomBase(float bottomBase) {
        this.mBottomBase = bottomBase;
        return this;
    }

    /**
     * Set the number to rebound right (x position) in pixels
     *
     * @param rightSide The number to rebound right (x position) in pixels
     * @return this
     */
    public ParabolicMotionBitmap rightSide(float rightSide) {
        this.mRightSide = rightSide;
        return this;
    }

    /**
     * Set the number to rebound left (x position) in pixels
     *
     * @param leftSide The number to rebound left (x position) in pixels
     * @return this
     */
    public ParabolicMotionBitmap leftSide(float leftSide) {
        this.mLeftSide = leftSide;
        return this;
    }

    /**
     * Set callback when responding to a bottom base
     *
     * @param animCallBack callback when responding to a bottom base
     * @return this
     */
    public ParabolicMotionBitmap bottomHitCallback(@NonNull AnimCallBack animCallBack) {
        setBottomHitCallback(animCallBack);
        return this;
    }

    /**
     * Set callback when responding to a left side
     *
     * @param animCallBack callback when responding to a left side
     * @return this
     */
    public ParabolicMotionBitmap leftHitCallback(@NonNull AnimCallBack animCallBack) {
        setLeftHitCallback(animCallBack);
        return this;
    }

    /**
     * Set callback when responding to a wall of right
     *
     * @param animCallBack callback when responding to a right side
     * @return this
     */
    public ParabolicMotionBitmap rightHitCallback(@NonNull AnimCallBack animCallBack) {
        setRightHitCallback(animCallBack);
        return this;
    }

    /**
     * if not set mBottomBase and mRightSide, set Canvas Bottom and Width.
     *
     * @param canvas This Canvas acquired by lookCanvas in FPSTextureView or FPSSurfaceView.
     */
    private void setDefaultBaseLength(Canvas canvas) {
        if (mDpSize) {
            if (mBottomBase < 0) {
                mBottomBase = canvas.getHeight() - mBitmapDpHeight;
            }
            if (mRightSide < 0) {
                mRightSide = canvas.getWidth() - mBitmapDpWidth;
            }
            return;
        }
        if (mBottomBase < 0) {
            mBottomBase = canvas.getHeight() - mBitmap.getHeight();
        }
        if (mRightSide < 0) {
            mRightSide = canvas.getWidth() - mBitmap.getWidth();
        }
    }

    /**
     * Draws the display object into the specified context ignoring its visible, alpha, shadow, and transform.
     *
     * @param canvas This Canvas acquired by lookCanvas in FPSTextureView or FPSSurfaceView.
     */
    @Override
    public void draw(Canvas canvas) {
        if (mBitmap == null) return;
        setDefaultBaseLength(canvas);
        updatePosition();
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
