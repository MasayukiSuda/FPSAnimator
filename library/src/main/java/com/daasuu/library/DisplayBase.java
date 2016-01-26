package com.daasuu.library;

import android.graphics.Canvas;
import android.support.annotation.NonNull;

import com.daasuu.library.animator.ParabolicAnimator;
import com.daasuu.library.animator.TweenAnimator;

/**
 * Created by sudamasayuki on 16/01/26.
 */
public abstract class DisplayBase {

    /**
     * hold a parameter related to the drawing on the canvas.
     */
    protected AnimParameter mAnimParameter;

    protected Animator mAnimator;

    protected Drawer mDrawer;

    /**
     * call from FPSTextureView or FPSSurfaceView when it is addChild.
     *
     * @param fps Set in FPSTextureView or FPSSurfaceView.
     */
    void setUp(long fps) {
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
        mAnimator.setBaseLine(canvas, mDrawer.getWidth(), mDrawer.getHeight());
        mAnimator.updateAnimParam(mAnimParameter);
        mDrawer.draw(canvas, mAnimParameter.x, mAnimParameter.y, mAnimParameter.alpha, mAnimParameter.scaleX, mAnimParameter.scaleY, mAnimParameter.rotation);
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
