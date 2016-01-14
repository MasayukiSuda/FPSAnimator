package com.daasuu.library.anim;

import android.graphics.Canvas;
import android.support.annotation.NonNull;

import com.daasuu.library.Anim;
import com.daasuu.library.Position;
import com.daasuu.library.callback.AnimCallBack;
import com.daasuu.library.constant.Constant;

/**
 * Created by a13587 on 16/01/14.
 */
public class ParabolicAnim implements Anim {
    private static final String TAG = ParabolicAnim.class.getSimpleName();

    private int mDrawingNum = Constant.DEFAULT_DRAWING_NUM;

    private final Position mInitialPosition;

    /**
     * The amount of transfer of the y-direction in pixels
     */
    private float mMovementY;

    /**
     * Coefficient Of Restitution Y, as a percentage of 1
     */
    private float mCoefficientRestitutionY = Constant.DEFAULT_COEFFICIENT_RESTITUTION;

    /**
     * Coefficient Of Restitution X, as a percentage of 1
     */
    private float mCoefficientRestitutionX = Constant.DEFAULT_COEFFICIENT_RESTITUTION;

    /**
     * Initial velocity of parabolic movement (y position) in pixels
     */
    private float mInitialVelocityY = Constant.DEFAULT_INITIAL_VELOCITY_Y;

    /**
     * By one tick, the number to accelerate (y position) in pixels
     */
    private float mAccelerationY = Constant.DEFAULT_ACCELERATION_Y;

    /**
     * By one tick, the number to accelerate (x position) in pixels
     */
    private float mAccelerationX = Constant.DEFAULT_ACCELERATION_X;

    /**
     * For example, if a ParabolicMotion with a frequency of 10 is placed on a Stage being updated at 40fps,
     * then the ParabolicMotion will advance roughly one frame every 4 ticks. This will not be exact,
     * because the time between each tick will vary slightly between frames.
     * This feature is dependent on the tick event object being passed into update.
     */
    private int mFrequency = Constant.DEFAULT_FREQUENCY;

    /**
     * The number to rebound bottom (y position) in pixels
     */
    private float mBottomBase = -1;

    /**
     * The number to rebound right (x position) in pixels
     */
    private float mRightSide = -1;

    /**
     * The number to rebound left (x position) in pixels
     */
    private float mLeftSide = 0;

    /**
     * The flag indicating whether not rebound bottom
     */
    private boolean mReboundBottom = true;

    /**
     * The flag indicating whether not rebound left
     */
    private boolean mReboundLeft = true;

    /**
     * The flag indicating whether not rebound right
     */
    private boolean mReboundRight = true;

    /**
     * callback when responding to a wall of bottom
     */
    private AnimCallBack mBottomHitCallback;

    /**
     * callback when responding to a wall of left
     */
    private AnimCallBack mLeftHitCallback;

    /**
     * callback when responding to a rightSide
     */
    private AnimCallBack mRightHitCallback;

    /**
     * indicates whether to start the tween paused.
     */
    private boolean mParabolicMotionPause = false;

    public static Builder builder() {
        return new Builder();
    }


    private ParabolicAnim(Position initialPosition, int mDrawingNum, float mMovementY, float mCoefficientRestitutionY, float mCoefficientRestitutionX, float mInitialVelocityY, float mAccelerationY, float mAccelerationX, int mFrequency, float mBottomBase, float mRightSide, float mLeftSide, boolean mReboundBottom, boolean mReboundLeft, boolean mReboundRight, AnimCallBack mBottomHitCallback, AnimCallBack mLeftHitCallback, AnimCallBack mRightHitCallback, boolean mParabolicMotionPause) {
        this.mInitialPosition = initialPosition;
        this.mDrawingNum = mDrawingNum;
        this.mMovementY = mMovementY;
        this.mCoefficientRestitutionY = mCoefficientRestitutionY;
        this.mCoefficientRestitutionX = mCoefficientRestitutionX;
        this.mInitialVelocityY = mInitialVelocityY;
        this.mAccelerationY = mAccelerationY;
        this.mAccelerationX = mAccelerationX;
        this.mFrequency = mFrequency;
        this.mBottomBase = mBottomBase;
        this.mRightSide = mRightSide;
        this.mLeftSide = mLeftSide;
        this.mReboundBottom = mReboundBottom;
        this.mReboundLeft = mReboundLeft;
        this.mReboundRight = mReboundRight;
        this.mBottomHitCallback = mBottomHitCallback;
        this.mLeftHitCallback = mLeftHitCallback;
        this.mRightHitCallback = mRightHitCallback;
        this.mParabolicMotionPause = mParabolicMotionPause;
    }

    @Override
    public Position getInitialPosition() {
        return mInitialPosition;
    }

    @Override
    public void setBaseLine(Canvas canvas, float width, float height) {
        if (mBottomBase < 0) {
            mBottomBase = canvas.getHeight() - height;
        }
        if (mRightSide < 0) {
            mRightSide = canvas.getWidth() - width;
        }
    }

    @Override
    public void updatePosition(Position position) {
        if (mDrawingNum != mFrequency) {
            mDrawingNum++;
            return;
        }

        mDrawingNum = Constant.DEFAULT_DRAWING_NUM;

        if (mParabolicMotionPause) return;

        mMovementY += mAccelerationY;

        float y = position.y();
        float x = position.x();

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

        position.update(x, y);
    }

    @Override
    public void setUp(long fps) {
        this.mMovementY = mInitialVelocityY;
    }

    /**
     * Builder for {@link ParabolicAnim}
     */
    public static final class Builder {

        private float x;

        private float y;

        private int mDrawingNum = Constant.DEFAULT_DRAWING_NUM;

        /**
         * The amount of transfer of the y-direction in pixels
         */
        private float mMovementY;

        /**
         * Coefficient Of Restitution Y, as a percentage of 1
         */
        private float mCoefficientRestitutionY = Constant.DEFAULT_COEFFICIENT_RESTITUTION;

        /**
         * Coefficient Of Restitution X, as a percentage of 1
         */
        private float mCoefficientRestitutionX = Constant.DEFAULT_COEFFICIENT_RESTITUTION;

        /**
         * Initial velocity of parabolic movement (y position) in pixels
         */
        private float mInitialVelocityY = Constant.DEFAULT_INITIAL_VELOCITY_Y;

        /**
         * By one tick, the number to accelerate (y position) in pixels
         */
        private float mAccelerationY = Constant.DEFAULT_ACCELERATION_Y;

        /**
         * By one tick, the number to accelerate (x position) in pixels
         */
        private float mAccelerationX = Constant.DEFAULT_ACCELERATION_X;

        /**
         * For example, if a ParabolicMotion with a frequency of 10 is placed on a Stage being updated at 40fps,
         * then the ParabolicMotion will advance roughly one frame every 4 ticks. This will not be exact,
         * because the time between each tick will vary slightly between frames.
         * This feature is dependent on the tick event object being passed into update.
         */
        private int mFrequency = Constant.DEFAULT_FREQUENCY;

        /**
         * The number to rebound bottom (y position) in pixels
         */
        private float mBottomBase = -1;

        /**
         * The number to rebound right (x position) in pixels
         */
        private float mRightSide = -1;

        /**
         * The number to rebound left (x position) in pixels
         */
        private float mLeftSide = 0;

        /**
         * The flag indicating whether not rebound bottom
         */
        private boolean mReboundBottom = true;

        /**
         * The flag indicating whether not rebound left
         */
        private boolean mReboundLeft = true;

        /**
         * The flag indicating whether not rebound right
         */
        private boolean mReboundRight = true;

        /**
         * callback when responding to a wall of bottom
         */
        private AnimCallBack mBottomHitCallback;

        /**
         * callback when responding to a wall of left
         */
        private AnimCallBack mLeftHitCallback;

        /**
         * callback when responding to a rightSide
         */
        private AnimCallBack mRightHitCallback;

        /**
         * indicates whether to start the tween paused.
         */
        private boolean mParabolicMotionPause = false;


        private Builder() {
        }


        public ParabolicAnim build() {
            ParabolicAnim anim = new ParabolicAnim(
                    new Position(x, y),
                    mDrawingNum,
                    mMovementY,
                    mCoefficientRestitutionY,
                    mCoefficientRestitutionX,
                    mInitialVelocityY,
                    mAccelerationY,
                    mAccelerationX,
                    mFrequency,
                    mBottomBase,
                    mRightSide,
                    mLeftSide,
                    mReboundBottom,
                    mReboundLeft,
                    mReboundRight,
                    mBottomHitCallback,
                    mLeftHitCallback,
                    mRightHitCallback,
                    mParabolicMotionPause
            );

            return anim;
        }

        /**
         * Shortcut method to quickly set the transform properties on the display object.
         *
         * @param x The horizontal translation (x position) in pixels
         * @param y The vertical translation (y position) in pixels
         * @return this
         */
        public Builder transform(float x, float y) {
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
        public Builder frequency(int frequency) {
            mFrequency = frequency;
            return this;
        }

        /**
         * Set initial velocity of parabolic movement (y position) in pixels
         *
         * @param velocityY Initial velocity of parabolic movement (y position) in pixels
         * @return this
         */
        public Builder initialVelocityY(float velocityY) {
            mInitialVelocityY = velocityY;
            return this;
        }

        /**
         * Set the number to accelerate (y position) in pixels
         *
         * @param accelerationY By one tick, the number to accelerate (y position) in pixels
         * @return this
         */
        public Builder accelerationY(float accelerationY) {
            mAccelerationY = accelerationY;
            return this;
        }

        /**
         * Set the number to accelerate (x position) in pixels
         *
         * @param accelerationX By one tick, the number to accelerate (x position) in pixels
         * @return this
         */
        public Builder accelerationX(float accelerationX) {
            mAccelerationX = accelerationX;
            return this;
        }

        /**
         * Set coefficient Of Restitution Y, as a percentage of 1
         *
         * @param coefficientRestitutionY Coefficient Of Restitution Y, as a percentage of 1
         * @return this
         */
        public Builder coefficientRestitutionY(float coefficientRestitutionY) {
            mCoefficientRestitutionY = coefficientRestitutionY;
            return this;
        }

        /**
         * Set coefficient Of Restitution X, as a percentage of 1
         *
         * @param coefficientRestitutionX Coefficient Of Restitution X, as a percentage of 1
         * @return this
         */
        public Builder coefficientRestitutionX(float coefficientRestitutionX) {
            mCoefficientRestitutionX = coefficientRestitutionX;
            return this;
        }

        /**
         * Set the flag indicating whether not rebound bottom
         *
         * @param reboundBottom The flag indicating whether not rebound bottom
         * @return this
         */
        public Builder reboundBottom(boolean reboundBottom) {
            mReboundBottom = reboundBottom;
            return this;
        }

        /**
         * Set the flag indicating whether not rebound left
         *
         * @param reboundLeft The flag indicating whether not rebound left
         * @return this
         */
        public Builder reboundLeft(boolean reboundLeft) {
            mReboundLeft = reboundLeft;
            return this;
        }

        /**
         * Set the flag indicating whether not rebound right
         *
         * @param reboundRight The flag indicating whether not rebound right
         * @return this
         */
        public Builder reboundRight(boolean reboundRight) {
            mReboundRight = reboundRight;
            return this;
        }

        /**
         * Set the number to rebound bottom (y position) in pixels
         *
         * @param bottomBase The number to rebound bottom (y position) in pixels
         * @return this
         */
        public Builder bottomBase(float bottomBase) {
            this.mBottomBase = bottomBase;
            return this;
        }

        /**
         * Set the number to rebound right (x position) in pixels
         *
         * @param rightSide The number to rebound right (x position) in pixels
         * @return this
         */
        public Builder rightSide(float rightSide) {
            this.mRightSide = rightSide;
            return this;
        }

        /**
         * Set the number to rebound left (x position) in pixels
         *
         * @param leftSide The number to rebound left (x position) in pixels
         * @return this
         */
        public Builder leftSide(float leftSide) {
            this.mLeftSide = leftSide;
            return this;
        }

        /**
         * Set callback when responding to a bottom base
         *
         * @param animCallBack callback when responding to a bottom base
         * @return this
         */
        public Builder bottomHitCallback(@NonNull AnimCallBack animCallBack) {
            mBottomHitCallback = animCallBack;
            return this;
        }

        /**
         * Set callback when responding to a left side
         *
         * @param animCallBack callback when responding to a left side
         * @return this
         */
        public Builder leftHitCallback(@NonNull AnimCallBack animCallBack) {
            mLeftHitCallback = animCallBack;
            return this;
        }

        /**
         * Set callback when responding to a wall of right
         *
         * @param animCallBack callback when responding to a right side
         * @return this
         */
        public Builder rightHitCallback(@NonNull AnimCallBack animCallBack) {
            mRightHitCallback = animCallBack;
            return this;
        }
    }
}
