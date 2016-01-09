package com.daasuu.library.parabolicmotion;

import android.graphics.Canvas;

import com.daasuu.library.DisplayObject;
import com.daasuu.library.callback.AnimCallBack;

/**
 * Class for heavy acceleration motion
 */
class ParabolicMotion extends DisplayObject {

    protected static final int DEFAULT_DRAWING_NUM = 1;
    protected static final float DEFAULT_COEFFICIENT_RESTITUTION = 1;
    protected static final float DEFAULT_INITIAL_VELOCITY_Y = 2;
    protected static final float DEFAULT_ACCELERATION_Y = 2;
    protected static final float DEFAULT_ACCELERATION_X = 8;
    protected static final int DEFAULT_FRAME_RATE = 1;


    protected int mDrawingNum = DEFAULT_DRAWING_NUM;

    // The amount of transfer of the y-direction
    protected float mMovementY;

    // Coefficient Of Restitution
    protected float mCoefficientRestitutionY = DEFAULT_COEFFICIENT_RESTITUTION;
    protected float mCoefficientRestitutionX = DEFAULT_COEFFICIENT_RESTITUTION;

    protected float mInitialVelocityY = DEFAULT_INITIAL_VELOCITY_Y;
    protected float mAccelerationY = DEFAULT_ACCELERATION_Y;
    protected float mAccelerationX = DEFAULT_ACCELERATION_X;

    protected int mFrameRate = DEFAULT_FRAME_RATE;

    protected float mBottomBase;
    protected float mRightSide;

    protected boolean mCoefficientBottom = true;
    protected boolean mCoefficientLeft = true;
    protected boolean mCoefficientRight = true;

    // callback when responding to a wall
    protected AnimCallBack mBottomHitCallback;
    protected AnimCallBack mLeftHitCallback;
    protected AnimCallBack mRightHitCallback;

    /**
     * ParabolicMotionPause method.
     */
    private boolean mParabolicMotionPause = false;

    public void parabolicMotionPause(boolean pause) {
        mParabolicMotionPause = pause;
    }

    public boolean isParabolicMotionPause() {
        return mParabolicMotionPause;
    }

    protected void setBottomHitCallback(AnimCallBack bottomCallback) {
        this.mBottomHitCallback = bottomCallback;
    }

    protected void setLeftHitCallback(AnimCallBack leftCallback) {
        this.mLeftHitCallback = leftCallback;
    }

    protected void setRightHitCallback(AnimCallBack rightCallback) {
        this.mRightHitCallback = rightCallback;
    }

    protected synchronized void updatePosition() {
        if (mDrawingNum != mFrameRate) {
            mDrawingNum++;
            return;
        }
        mDrawingNum = DEFAULT_DRAWING_NUM;

        if (mParabolicMotionPause) return;

        this.mMovementY += mAccelerationY;

        y += this.mMovementY;
        x += mAccelerationX;

        if (y > mBottomBase) {

            if (mBottomHitCallback != null) {
                mBottomHitCallback.call();
            }

            if (mCoefficientBottom) {
                y = mBottomBase;
                mMovementY = mMovementY * -1;
            }
        }

        if (x > mRightSide) {
            if (mRightHitCallback != null) {
                mRightHitCallback.call();
            }

            if (mCoefficientRight) {
                x = mRightSide;
                mAccelerationX *= -1;
                mMovementY *= mCoefficientRestitutionY;
            }
        }

        if (x < 0) {
            if (mLeftHitCallback != null) {
                mLeftHitCallback.call();
            }

            if (mCoefficientLeft) {
                x = 0;
                mAccelerationX *= -1;
                mAccelerationX *= mCoefficientRestitutionX;
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        // do nothing
    }

    @Override
    public void setUp(long fps) {
        this.mMovementY = mInitialVelocityY;
    }
}
