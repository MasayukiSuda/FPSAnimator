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

    private Bitmap mBitmap;

    private boolean mDpSize = false;

    private float mBitmapDpWidth;
    private float mBitmapDpHeight;
    private Rect mBitmapRect;

    public ParabolicMotionBitmap(@NonNull Bitmap bitmap) {
        this.mBitmap = bitmap;
    }

    public ParabolicMotionBitmap dpSize(@NonNull Context context) {
        mDpSize = true;
        mBitmapDpWidth = Util.convertPixelsToDp(mBitmap.getWidth(), context);
        mBitmapDpHeight = Util.convertPixelsToDp(mBitmap.getHeight(), context);
        mBitmapRect = new Rect(0, 0, mBitmap.getWidth(), mBitmap.getHeight());
        return this;
    }


    public ParabolicMotionBitmap transform(float x, float y) {
        mAnimParameter.x = x;
        mAnimParameter.y = y;
        return this;
    }

    public ParabolicMotionBitmap frequency(int frequency) {
        mFrequency = frequency;
        return this;
    }

    public ParabolicMotionBitmap initialVelocityY(float velocityY) {
        mInitialVelocityY = velocityY;
        return this;
    }

    public ParabolicMotionBitmap accelerationY(float accelerationY) {
        mAccelerationY = accelerationY;
        return this;
    }

    public ParabolicMotionBitmap accelerationX(float accelerationX) {
        mAccelerationX = accelerationX;
        return this;
    }

    public ParabolicMotionBitmap coefficientRestitutionY(float coefficientRestitutionY) {
        mCoefficientRestitutionY = coefficientRestitutionY;
        return this;
    }

    public ParabolicMotionBitmap coefficientRestitutionX(float coefficientRestitutionX) {
        mCoefficientRestitutionX = coefficientRestitutionX;
        return this;
    }

    public ParabolicMotionBitmap coefficientBottom(boolean coefficientBottom) {
        mCoefficientBottom = coefficientBottom;
        return this;
    }

    public ParabolicMotionBitmap coefficientLeft(boolean coefficientLeft) {
        mCoefficientLeft = coefficientLeft;
        return this;
    }

    public ParabolicMotionBitmap coefficientRight(boolean coefficientRight) {
        mCoefficientRight = coefficientRight;
        return this;
    }

    public ParabolicMotionBitmap bottomHitCallback(AnimCallBack animCallBack) {
        setBottomHitCallback(animCallBack);
        return this;
    }

    public ParabolicMotionBitmap leftHitCallback(AnimCallBack animCallBack) {
        setLeftHitCallback(animCallBack);
        return this;
    }

    public ParabolicMotionBitmap rightHitCallback(AnimCallBack animCallBack) {
        setRightHitCallback(animCallBack);
        return this;
    }

    private void setBaseLength(Canvas canvas) {
        if (mDpSize) {
            mBottomBase = canvas.getHeight() - mBitmapDpHeight;
            mRightSide = canvas.getWidth() - mBitmapDpWidth;
        } else {
            mBottomBase = canvas.getHeight() - mBitmap.getHeight();
            mRightSide = canvas.getWidth() - mBitmap.getWidth();
        }
    }

    @Override
    public void draw(Canvas canvas) {
        if (mBitmap == null) return;
        setBaseLength(canvas);
        updatePosition();
        if (mDpSize) {
            RectF dpSizeRect = new RectF(
                    mAnimParameter.x,
                    mAnimParameter.y,
                    mAnimParameter.x + mBitmapDpWidth,
                    mAnimParameter.y + mBitmapDpHeight
            );
            canvas.drawBitmap(mBitmap, mBitmapRect, dpSizeRect, mPaint);
        } else {
            canvas.drawBitmap(mBitmap, mAnimParameter.x, mAnimParameter.y, mPaint);
        }
    }

}
