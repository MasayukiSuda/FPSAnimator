package com.daasuu.library.animator;

import android.graphics.Canvas;
import android.support.annotation.NonNull;

import com.daasuu.library.Animator;
import com.daasuu.library.AnimParameter;
import com.daasuu.library.DisplayBase;
import com.daasuu.library.callback.AnimCallBack;
import com.daasuu.library.constant.Constant;

/**
 * Class for heavy acceleration motion
 */
public class ParabolicAnimator implements Animator {
    private static final String TAG = ParabolicAnimator.class.getSimpleName();

    private int mDrawingNum = Constant.DEFAULT_DRAWING_NUM;

    private final AnimParameter mInitialPosition;

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
     * indicates whether to start the parabolicMotion paused.
     */
    private boolean mParabolicMotionPause = false;

    /**
     * create composer instance.
     *
     * @param DisplayBase DisplayBase
     * @return composer
     */
    public static Composer composer(DisplayBase DisplayBase) {
        return new Composer(DisplayBase);
    }


    private ParabolicAnimator(AnimParameter initialPosition, int mDrawingNum, float mMovementY, float mCoefficientRestitutionY, float mCoefficientRestitutionX, float mInitialVelocityY, float mAccelerationY, float mAccelerationX, int mFrequency, float mBottomBase, float mRightSide, float mLeftSide, boolean mReboundBottom, boolean mReboundLeft, boolean mReboundRight, AnimCallBack mBottomHitCallback, AnimCallBack mLeftHitCallback, AnimCallBack mRightHitCallback) {
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
    }

    @Override
    public AnimParameter getInitialAnimParameter() {
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
    public void pause(boolean pause) {
        mParabolicMotionPause = pause;
    }

    @Override
    public boolean isPause() {
        return mParabolicMotionPause;
    }

    @Override
    public void updateAnimParam(AnimParameter position) {
        if (mDrawingNum != mFrequency) {
            mDrawingNum++;
            return;
        }

        mDrawingNum = Constant.DEFAULT_DRAWING_NUM;

        if (mParabolicMotionPause) return;

        mMovementY += mAccelerationY;

        float y = position.y;
        float x = position.x;

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

        position.x = x;
        position.y = y;
    }

    @Override
    public void setUp(long fps) {
        this.mMovementY = mInitialVelocityY;
    }

    /**
     * Builder for {@link ParabolicAnimator}
     */
    public static final class Composer {

        private final DisplayBase mDisplayBase;

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

        private Composer(DisplayBase DisplayBase) {
            mDisplayBase = DisplayBase;
        }


        public void end() {
            mDisplayBase.animator(new ParabolicAnimator(
                    new AnimParameter(x, y),
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
                    mRightHitCallback
            ));
        }

        /**
         * Shortcut method to quickly set the transform properties on the display object.
         *
         * @param x The horizontal translation (x position) in pixels
         * @param y The vertical translation (y position) in pixels
         * @return this
         */
        public Composer transform(float x, float y) {
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
        public Composer frequency(int frequency) {
            mFrequency = frequency;
            return this;
        }

        /**
         * Set initial velocity of parabolic movement (y position) in pixels
         *
         * @param velocityY Initial velocity of parabolic movement (y position) in pixels
         * @return this
         */
        public Composer initialVelocityY(float velocityY) {
            mInitialVelocityY = velocityY;
            return this;
        }

        /**
         * Set the number to accelerate (y position) in pixels
         *
         * @param accelerationY By one tick, the number to accelerate (y position) in pixels
         * @return this
         */
        public Composer accelerationY(float accelerationY) {
            mAccelerationY = accelerationY;
            return this;
        }

        /**
         * Set the number to accelerate (x position) in pixels
         *
         * @param accelerationX By one tick, the number to accelerate (x position) in pixels
         * @return this
         */
        public Composer accelerationX(float accelerationX) {
            mAccelerationX = accelerationX;
            return this;
        }

        /**
         * Set coefficient Of Restitution Y, as a percentage of 1
         *
         * @param coefficientRestitutionY Coefficient Of Restitution Y, as a percentage of 1
         * @return this
         */
        public Composer coefficientRestitutionY(float coefficientRestitutionY) {
            mCoefficientRestitutionY = coefficientRestitutionY;
            return this;
        }

        /**
         * Set coefficient Of Restitution X, as a percentage of 1
         *
         * @param coefficientRestitutionX Coefficient Of Restitution X, as a percentage of 1
         * @return this
         */
        public Composer coefficientRestitutionX(float coefficientRestitutionX) {
            mCoefficientRestitutionX = coefficientRestitutionX;
            return this;
        }

        /**
         * Set the flag indicating whether not rebound bottom
         *
         * @param reboundBottom The flag indicating whether not rebound bottom
         * @return this
         */
        public Composer reboundBottom(boolean reboundBottom) {
            mReboundBottom = reboundBottom;
            return this;
        }

        /**
         * Set the flag indicating whether not rebound left
         *
         * @param reboundLeft The flag indicating whether not rebound left
         * @return this
         */
        public Composer reboundLeft(boolean reboundLeft) {
            mReboundLeft = reboundLeft;
            return this;
        }

        /**
         * Set the flag indicating whether not rebound right
         *
         * @param reboundRight The flag indicating whether not rebound right
         * @return this
         */
        public Composer reboundRight(boolean reboundRight) {
            mReboundRight = reboundRight;
            return this;
        }

        /**
         * Set the number to rebound bottom (y position) in pixels
         *
         * @param bottomBase The number to rebound bottom (y position) in pixels
         * @return this
         */
        public Composer bottomBase(float bottomBase) {
            this.mBottomBase = bottomBase;
            return this;
        }

        /**
         * Set the number to rebound right (x position) in pixels
         *
         * @param rightSide The number to rebound right (x position) in pixels
         * @return this
         */
        public Composer rightSide(float rightSide) {
            this.mRightSide = rightSide;
            return this;
        }

        /**
         * Set the number to rebound left (x position) in pixels
         *
         * @param leftSide The number to rebound left (x position) in pixels
         * @return this
         */
        public Composer leftSide(float leftSide) {
            this.mLeftSide = leftSide;
            return this;
        }

        /**
         * Set callback when responding to a bottom base
         *
         * @param animCallBack callback when responding to a bottom base
         * @return this
         */
        public Composer bottomHitCallback(@NonNull AnimCallBack animCallBack) {
            mBottomHitCallback = animCallBack;
            return this;
        }

        /**
         * Set callback when responding to a left side
         *
         * @param animCallBack callback when responding to a left side
         * @return this
         */
        public Composer leftHitCallback(@NonNull AnimCallBack animCallBack) {
            mLeftHitCallback = animCallBack;
            return this;
        }

        /**
         * Set callback when responding to a wall of right
         *
         * @param animCallBack callback when responding to a right side
         * @return this
         */
        public Composer rightHitCallback(@NonNull AnimCallBack animCallBack) {
            mRightHitCallback = animCallBack;
            return this;
        }
    }
}
