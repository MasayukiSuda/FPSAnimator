package com.daasuu.library.parabolicmotion;

import android.graphics.Canvas;

import com.daasuu.library.DisplayObject;
import com.daasuu.library.callback.AnimCallBack;
import com.daasuu.library.constant.Constant;

/**
 * Class for heavy acceleration motion
 */
class ParabolicMotion extends DisplayObject {


    protected int mDrawingNum = Constant.DEFAULT_DRAWING_NUM;

    /**
     * The amount of transfer of the y-direction in pixels
     */
    protected float mMovementY;

    /**
     * Coefficient Of Restitution Y, as a percentage of 1
     */
    protected float mCoefficientRestitutionY = Constant.DEFAULT_COEFFICIENT_RESTITUTION;

    /**
     * Coefficient Of Restitution X, as a percentage of 1
     */
    protected float mCoefficientRestitutionX = Constant.DEFAULT_COEFFICIENT_RESTITUTION;

    /**
     * Initial velocity of parabolic movement (y position) in pixels
     */
    protected float mInitialVelocityY = Constant.DEFAULT_INITIAL_VELOCITY_Y;

    /**
     * By one tick, the number to accelerate (y position) in pixels
     */
    protected float mAccelerationY = Constant.DEFAULT_ACCELERATION_Y;

    /**
     * By one tick, the number to accelerate (x position) in pixels
     */
    protected float mAccelerationX = Constant.DEFAULT_ACCELERATION_X;

    /**
     * For example, if a ParabolicMotion with a frequency of 10 is placed on a Stage being updated at 40fps,
     * then the ParabolicMotion will advance roughly one frame every 4 ticks. This will not be exact,
     * because the time between each tick will vary slightly between frames.
     * This feature is dependent on the tick event object being passed into update.
     */
    protected int mFrequency = Constant.DEFAULT_FREQUENCY;

    /**
     * The number to rebound bottom (y position) in pixels
     */
    protected float mBottomBase = -1;

    /**
     * The number to rebound right (x position) in pixels
     */
    protected float mRightSide = -1;

    /**
     * The number to rebound left (x position) in pixels
     */
    protected float mLeftSide = 0;

    /**
     * The flag indicating whether not rebound bottom
     */
    protected boolean mReboundBottom = true;

    /**
     * The flag indicating whether not rebound left
     */
    protected boolean mReboundLeft = true;

    /**
     * The flag indicating whether not rebound right
     */
    protected boolean mReboundRight = true;

    /**
     * callback when responding to a wall of bottom
     */
    protected AnimCallBack mBottomHitCallback;

    /**
     * callback when responding to a wall of left
     */
    protected AnimCallBack mLeftHitCallback;

    /**
     * callback when responding to a rightSide
     */
    protected AnimCallBack mRightHitCallback;

    /**
     * indicates whether to start the parabolicMotion paused.
     */
    private boolean mParabolicMotionPause = false;

    /**
     * Setter mParabolicMotionPause
     *
     * @param pause indicates whether to start the parabolicMotion paused.
     */
    public void parabolicMotionPause(boolean pause) {
        mParabolicMotionPause = pause;
    }

    /**
     * Getter mParabolicMotionPause
     *
     * @return mParabolicMotionPause
     */
    public boolean isParabolicMotionPause() {
        return mParabolicMotionPause;
    }

    /**
     * Setter mBottomHitCallback
     *
     * @param bottomCallback callback when responding to a bottom base
     */
    protected void setBottomHitCallback(AnimCallBack bottomCallback) {
        this.mBottomHitCallback = bottomCallback;
    }

    /**
     * Setter mLeftHitCallback
     *
     * @param leftCallback callback when responding to a left side
     */
    protected void setLeftHitCallback(AnimCallBack leftCallback) {
        this.mLeftHitCallback = leftCallback;
    }

    /**
     * Setter mRightHitCallback
     *
     * @param rightCallback callback when responding to a right side
     */
    protected void setRightHitCallback(AnimCallBack rightCallback) {
        this.mRightHitCallback = rightCallback;
    }

    /**
     * advance next frame position
     */
    protected synchronized void updatePosition() {
        if (mDrawingNum != mFrequency) {
            mDrawingNum++;
            return;
        }
        mDrawingNum = Constant.DEFAULT_DRAWING_NUM;

        if (mParabolicMotionPause) return;

        mMovementY += mAccelerationY;

        y += mMovementY;
        x += mAccelerationX;

        if (y > mBottomBase) {

            if (mBottomHitCallback != null) {
                mBottomHitCallback.call();
            }

            if (mReboundBottom) {
                y = mBottomBase;
                mMovementY = mMovementY * -1;
            }
        }

        if (x > mRightSide) {
            if (mRightHitCallback != null) {
                mRightHitCallback.call();
            }

            if (mReboundRight) {
                x = mRightSide;
                mAccelerationX *= -1;
                mMovementY *= mCoefficientRestitutionY;
            }
        }

        if (x < mLeftSide) {
            if (mLeftHitCallback != null) {
                mLeftHitCallback.call();
            }

            if (mReboundLeft) {
                x = mLeftSide;
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
        // set Default MovementY
        this.mMovementY = mInitialVelocityY;
    }
}
