package com.daasuu.library;

import android.graphics.Canvas;

import com.daasuu.library.anim.ParabolicAnim;
import com.daasuu.library.anim.TweenAnim;

/**
 */
public class DisplayObject2 extends DisplayObject {
    private static final String TAG = DisplayObject2.class.getSimpleName();

    private AnimParameter mAnimParameter;

    private Anim mAnim;

    private Painter mPainter;

    public DisplayObject2() {
    }

    public DisplayObjectComposer with(Painter painter) {
        mPainter = painter;
        return new DisplayObjectComposer();
    }

    public void setAnim(Anim anim) {
        this.mAnim = anim;
        mAnimParameter = mAnim.getInitialPosition();
    }

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
     *
     */
    public class DisplayObjectComposer {
        /**
         * @return
         */
        public TweenAnim.Builder tween() {
            return TweenAnim.builder(DisplayObject2.this);
        }

        /**
         * @return
         */
        public ParabolicAnim.Builder parabolic() {
            return ParabolicAnim.builder(DisplayObject2.this);
        }
    }
}

