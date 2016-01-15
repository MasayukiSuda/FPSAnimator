package com.daasuu.library;

import android.graphics.Canvas;

import com.daasuu.library.anim.ParabolicAnim;
import com.daasuu.library.anim.TweenAnim;

/**
 * DisplayObject class.
 *
 * When you only use default animation and drawing class which is provided by this library,
 * you can use simple interface to compose DisplayObject by calling {@link #with(Painter)}.
 *
 * If you need to use your custom animation or drawing class which implements {@link Anim} or {@link Painter},
 * you prepare their instances and simply set by calling {@link #setAnim(Anim)} or {@link #setPainter(Painter)}.
 */
public class DisplayObject2 extends DisplayObject {
    private static final String TAG = DisplayObject2.class.getSimpleName();

    private AnimParameter mAnimParameter;

    private Anim mAnim;

    private Painter mPainter;

    public DisplayObject2() {
    }

    /**
     * Return Composer instance to setup this DisplayObject instance.
     * This method is useful when you use only default class of animation.
     *
     * @param painter
     * @return
     */
    public DisplayObjectComposer with(Painter painter) {
        mPainter = painter;
        return new DisplayObjectComposer();
    }

    /**
     * Set animation class.
     * Use this method only when there is need to your own custom class of animation,
     * in other cases, use {@link #with(Painter)} instead.
     *
     * @param anim
     */
    public void setAnim(Anim anim) {
        this.mAnim = anim;
        mAnimParameter = mAnim.getInitialAnimParameter();
    }

    /**
     * Set drawing class.
     * Use this method only when there is need to your own custom class of animation,
     * in other cases, use {@link #with(Painter)} instead.
     *
     * @param painter
     */
    public void setPainter(Painter painter) {
        this.mPainter = painter;
    }

    @Override
    public void draw(Canvas canvas) {
        mAnim.setBaseLine(canvas, mPainter.getWidth(), mPainter.getHeight());
        mAnim.updateAnimParam(mAnimParameter);
        mPainter.draw(canvas, mAnimParameter.x(), mAnimParameter.y(), mAnimParameter.alpha(), mAnimParameter.scaleX(), mAnimParameter.scaleY(), mAnimParameter.rotation());
    }

    @Override
    public void setUp(long fps) {
        mAnim.setUp(fps);
    }


    /**
     * Composer provide simple composing interface.
     */
    public class DisplayObjectComposer {
        /**
         * @return tween composer
         */
        public TweenAnim.Builder tween() {
            return TweenAnim.builder(DisplayObject2.this);
        }

        /**
         * @return parabolic composer
         */
        public ParabolicAnim.Builder parabolic() {
            return ParabolicAnim.builder(DisplayObject2.this);
        }
    }
}

