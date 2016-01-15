package com.daasuu.library;

import android.graphics.Canvas;

/**
 */
public class DisplayObject2 extends DisplayObject {
    private static final String TAG = DisplayObject2.class.getSimpleName();

    private AnimParameter mPosition;

    private Anim mAnim;

    private Painter mPainter;

    /**
     * Unique ID for this display object. Makes display objects easier for some uses.
     */
    private String id;

    public DisplayObject2() {
    }

    public DisplayObject2 anim(Anim anim) {
        mAnim = anim;
        mPosition = mAnim.getInitialPosition();
        return this;
    }

    public DisplayObject2 painter(Painter painter) {
        this.mPainter = painter;
        return this;
    }

    @Override
    public void draw(Canvas canvas) {
        mAnim.setBaseLine(canvas, mPainter.getWidth(), mPainter.getHeight());
        mAnim.updateAnimParam(mPosition);
        mPainter.draw(canvas, mPosition.x(), mPosition.y(), mPosition.alpha(), mPosition.scaleX(), mPosition.scaleY(), mPosition.rotation());
    }

    @Override
    public void setUp(long fps) {
        mAnim.setUp(fps);
    }

    @Override
    public String toString() {
        return "DisplayObject2{" +
                "mPosition=" + mPosition +
                ", mAnim=" + mAnim +
                ", mPainter=" + mPainter +
                ", id='" + id + '\'' +
                "} " + super.toString();
    }
}

