package com.daasuu.library.parabolicmotion;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;

import com.daasuu.library.callback.AnimCallBack;

public class ParabolicMotionText extends ParabolicMotion {

    /**
     * String text to be drawn in FPSTextureView or FPSSurfaceView.
     */
    private String mText;

    /**
     * Adjustment values ​​for drawing the canvas coordinates ( 0, 0 )
     */
    private float mAdjustTextMesureY;

    /**
     * Constructor
     *
     * @param text  String text to be drawn in FPSTextureView or FPSSurfaceView.
     * @param paint Style and color and typeface .. etc information
     */
    public ParabolicMotionText(@NonNull String text, Paint paint) {
        this.mText = text;
        this.paint = paint;
        mAdjustTextMesureY = this.paint.getTextSize();
    }

    /**
     * Shortcut method to quickly set the transform properties on the display object.
     *
     * @param x The horizontal translation (x position) in pixels
     * @param y The vertical translation (y position) in pixels
     * @return this
     */
    public ParabolicMotionText transform(float x, float y) {
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
    public ParabolicMotionText frequency(int frequency) {
        mFrequency = frequency;
        return this;
    }

    /**
     * Set initial velocity of parabolic movement (y position) in pixels
     *
     * @param velocityY Initial velocity of parabolic movement (y position) in pixels
     * @return this
     */
    public ParabolicMotionText initialVelocityY(float velocityY) {
        mInitialVelocityY = velocityY;
        return this;
    }

    /**
     * Set the number to accelerate (y position) in pixels
     *
     * @param accelerationY By one tick, the number to accelerate (y position) in pixels
     * @return this
     */
    public ParabolicMotionText accelerationY(float accelerationY) {
        mAccelerationY = accelerationY;
        return this;
    }

    /**
     * Set the number to accelerate (x position) in pixels
     *
     * @param accelerationX By one tick, the number to accelerate (x position) in pixels
     * @return this
     */
    public ParabolicMotionText accelerationX(float accelerationX) {
        mAccelerationX = accelerationX;
        return this;
    }

    /**
     * Set coefficient Of Restitution Y, as a percentage of 1
     *
     * @param coefficientRestitutionY Coefficient Of Restitution Y, as a percentage of 1
     * @return this
     */
    public ParabolicMotionText coefficientRestitutionY(float coefficientRestitutionY) {
        mCoefficientRestitutionY = coefficientRestitutionY;
        return this;
    }

    /**
     * Set coefficient Of Restitution X, as a percentage of 1
     *
     * @param coefficientRestitutionX Coefficient Of Restitution X, as a percentage of 1
     * @return this
     */
    public ParabolicMotionText coefficientRestitutionX(float coefficientRestitutionX) {
        mCoefficientRestitutionX = coefficientRestitutionX;
        return this;
    }

    /**
     * Set the flag indicating whether not rebound bottom
     *
     * @param reboundBottom The flag indicating whether not rebound bottom
     * @return this
     */
    public ParabolicMotionText reboundBottom(boolean reboundBottom) {
        mReboundBottom = reboundBottom;
        return this;
    }

    /**
     * Set the flag indicating whether not rebound left
     *
     * @param reboundLeft The flag indicating whether not rebound left
     * @return this
     */
    public ParabolicMotionText reboundLeft(boolean reboundLeft) {
        mReboundLeft = reboundLeft;
        return this;
    }

    /**
     * Set the flag indicating whether not rebound right
     *
     * @param reboundRight The flag indicating whether not rebound right
     * @return this
     */
    public ParabolicMotionText reboundRight(boolean reboundRight) {
        mReboundRight = reboundRight;
        return this;
    }

    /**
     * Set the number to rebound bottom (y position) in pixels
     *
     * @param bottomBase The number to rebound bottom (y position) in pixels
     * @return this
     */
    public ParabolicMotionText bottomBase(float bottomBase) {
        this.mBottomBase = bottomBase;
        return this;
    }

    /**
     * Set the number to rebound right (x position) in pixels
     *
     * @param rightSide The number to rebound right (x position) in pixels
     * @return this
     */
    public ParabolicMotionText rightSide(float rightSide) {
        this.mRightSide = rightSide;
        return this;
    }

    /**
     * Set the number to rebound left (x position) in pixels
     *
     * @param leftSide The number to rebound left (x position) in pixels
     * @return this
     */
    public ParabolicMotionText leftSide(float leftSide) {
        this.mLeftSide = leftSide;
        return this;
    }

    /**
     * Set callback when responding to a bottom base
     *
     * @param animCallBack callback when responding to a bottom base
     * @return this
     */
    public ParabolicMotionText bottomHitCallback(AnimCallBack animCallBack) {
        setBottomHitCallback(animCallBack);
        return this;
    }

    /**
     * Set callback when responding to a left side
     *
     * @param animCallBack callback when responding to a left side
     * @return this
     */
    public ParabolicMotionText leftHitCallback(AnimCallBack animCallBack) {
        setLeftHitCallback(animCallBack);
        return this;
    }

    /**
     * Set callback when responding to a wall of right
     *
     * @param animCallBack callback when responding to a right side
     * @return this
     */
    public ParabolicMotionText rightHitCallback(AnimCallBack animCallBack) {
        setRightHitCallback(animCallBack);
        return this;
    }

    /**
     * if not set mBottomBase and mRightSide, set Canvas Bottom and Width.
     *
     * @param canvas This Canvas acquired by lookCanvas in FPSTextureView or FPSSurfaceView.
     */
    private void setBaseLength(Canvas canvas) {
        if (mBottomBase < 0) {
            mBottomBase = canvas.getHeight() - mAdjustTextMesureY;
        }
        if (mRightSide < 0) {
            mRightSide = canvas.getWidth() - paint.measureText(mText);
        }
    }

    /**
     * Draws the display object into the specified context ignoring its visible, alpha, shadow, and transform.
     *
     * @param canvas This Canvas acquired by lookCanvas in FPSTextureView or FPSSurfaceView.
     */
    @Override
    public void draw(Canvas canvas) {
        if (mText == null) return;
        setBaseLength(canvas);
        updatePosition();
        canvas.drawText(mText, x, y + mAdjustTextMesureY, paint);
    }


}
