package com.daasuu.library;

import android.graphics.Canvas;

import com.daasuu.library.animator.ParabolicAnimator;
import com.daasuu.library.animator.TweenAnimator;

/**
 * DisplayObject class.
 * When you only use default animation and drawing class which is provided by this library,
 * you can use simple interface to compose DisplayObject by calling {@link #with(Drawer)}.
 * If you need to use your custom animation or drawing class which implements {@link Animator} or {@link Drawer},
 * you prepare their instances and simply set by calling {@link #animator(Animator)}.
 */
public class DisplayObject {

    private AnimParameter mAnimParameter;

    private Animator mAnimator;

    private Drawer mDrawer;

    /**
     * Return Composer instance to setup this DisplayObject instance.
     * This method is useful when you use only default class of animation.
     *
     * @param drawer drawing object
     * @return composer
     */
    public DisplayObjectComposer with(Drawer drawer) {
        mDrawer = drawer;
        return new DisplayObjectComposer();
    }

    /**
     * Set animation class.
     * Use this method only when there is need to your own custom class of animation,
     * in other cases, use {@link #with(Drawer)} instead.
     *
     * @param animator Animator instance
     */
    public DisplayObject animator(Animator animator) {
        this.mAnimator = animator;
        mAnimParameter = mAnimator.getInitialAnimParameter();
        return this;
    }

    /**
     * Draws the display object into the specified context ignoring its visible, alpha, shadow, and transform.
     *
     * @param canvas This Canvas acquired by lookCanvas in FPSTextureView or FPSSurfaceView.
     */
    public void draw(Canvas canvas) {
        mAnimator.setBaseLine(canvas, mDrawer.getWidth(), mDrawer.getHeight());
        mAnimator.updateAnimParam(mAnimParameter);
        mDrawer.draw(canvas, mAnimParameter.x, mAnimParameter.y, mAnimParameter.alpha, mAnimParameter.scaleX, mAnimParameter.scaleY, mAnimParameter.rotation);
    }

    /**
     * call from FPSTextureView or FPSSurfaceView when it is addChild.
     *
     * @param fps Set in FPSTextureView or FPSSurfaceView.
     */
    public void setUp(long fps) {
        mAnimator.setUp(fps);
    }

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
     * Composer provide simple composing interface.
     */
    public class DisplayObjectComposer {
        /**
         * @return tween composer
         */
        public TweenAnimator.Composer tween() {
            return TweenAnimator.composer(DisplayObject.this);
        }

        /**
         * @return parabolic composer
         */
        public ParabolicAnimator.Composer parabolic() {
            return ParabolicAnimator.composer(DisplayObject.this);
        }
    }
}

