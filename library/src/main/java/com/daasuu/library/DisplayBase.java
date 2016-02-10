package com.daasuu.library;

import android.graphics.Canvas;
import android.support.annotation.NonNull;

import com.daasuu.library.animator.ParabolicAnimator;
import com.daasuu.library.animator.TweenAnimator;

/**
 * Basic Class of Object to Add to FPSTextureView or FPSSurfaceView.
 */
public abstract class DisplayBase implements Comparable<DisplayBase> {

    /**
     * hold a parameter related to the drawing on the canvas.
     */
    protected AnimParameter mAnimParameter;

    protected Animator mAnimator;

    protected Drawer mDrawer;

    private boolean mIsEnable;

    private int mPriority;

    protected DisplayBase() {
        this(0);
    }

    protected DisplayBase(int priority) {
        mPriority = priority;
    }

    /**
     * call from FPSTextureView or FPSSurfaceView when it is addChild.
     *
     * @param fps Set in FPSTextureView or FPSSurfaceView.
     */
    void setUp(long fps) {
        synchronized (this) {
            mIsEnable = true;
        }
        mAnimator.setUp(fps);
    }

    /**
     * Return Composer instance to setup this DisplayObject instance.
     * This method is useful when you use only default class of animation.
     *
     * @param drawer drawing object
     * @return composer
     */
    protected DisplayComposer drawer(@NonNull Drawer drawer) {
        mDrawer = drawer;
        return new DisplayComposer();
    }

    /**
     * Set animation class.
     * Use this method only when there is need to your own custom class of animation,
     *
     * @param animator Animator instance
     * @return this
     */
    public DisplayBase animator(@NonNull Animator animator) {
        this.mAnimator = animator;
        mAnimParameter = mAnimator.getInitialAnimParameter();
        return this;
    }


    /**
     * Draws the display object into the specified context ignoring its visible, alpha, shadow, and transform.
     *
     * @param canvas This Canvas acquired by lookCanvas in FPSTextureView or FPSSurfaceView.
     */
    void draw(@NonNull Canvas canvas) {
        synchronized (this) {
            if (mIsEnable) {
                mAnimator.setBaseLine(canvas, mDrawer.getWidth(), mDrawer.getHeight());
                mAnimator.updateAnimParam(mAnimParameter);
                mDrawer.draw(canvas, mAnimParameter.x, mAnimParameter.y, mAnimParameter.alpha, mAnimParameter.scaleX, mAnimParameter.scaleY, mAnimParameter.rotation);
            }
        }
    }

    /**
     * Getter mAnimParameter
     *
     * @return hold a parameter related to the drawing on the canvas.
     */
    public AnimParameter getAnimParameter() {
        return mAnimParameter;
    }

    /**
     * Setter status of pause Motion Animator
     *
     * @param pause indicates whether to start the motion animation paused.
     */
    public void pause(boolean pause) {
        mAnimator.pause(pause);
    }

    /**
     * Getter status of pause Motion Animator
     *
     * @return indicates whether to start the motion animation paused.
     */
    public boolean isPause() {
        return mAnimator.isPause();
    }

    /**
     * disable this display object.
     * This call from FPSTextureView, FPSSurfaceView or Container when it is removeChild.
     */
    void disable() {
        synchronized (this) {
            mIsEnable = false;
        }
    }

    synchronized void swapPriority(DisplayBase another) {
        if (another != null) {
            synchronized (another) {
                int myPriority = this.mPriority;
                this.mPriority = another.mPriority;
                another.mPriority = myPriority;
            }
        }
    }

    @Override
    public int compareTo(DisplayBase another) {
        return this.mPriority - (another == null ? 0 : another.mPriority);
    }

    /**
     * Composer provide simple composing interface.
     */
    public class DisplayComposer {
        /**
         * @return tween composer
         */
        public TweenAnimator.Composer tween() {
            return TweenAnimator.composer(DisplayBase.this);
        }

        /**
         * @return parabolic composer
         */
        public ParabolicAnimator.Composer parabolic() {
            return ParabolicAnimator.composer(DisplayBase.this);
        }
    }

}
