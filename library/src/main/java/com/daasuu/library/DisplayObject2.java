package com.daasuu.library;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.daasuu.library.constant.Constant;

/**
 * Created by a13587 on 16/01/14.
 */
public class DisplayObject2 extends DisplayObject {
    private static final String TAG = DisplayObject2.class.getSimpleName();

    private Position mPosition;

    private Anim mAnim;

    private Painter mPainter;

    /**
     * The factor to stretch this display object horizontally. For example, setting scaleX to 2 will stretch the display
     * object to twice its nominal width. To horizontally flip an object, set the scale to a negative number.
     */
    private float scaleX = Constant.DEFAULT_SCALE;

    /**
     * The factor to stretch this display object vertically. For example, setting scaleY to 0.5 will stretch the display
     * object to half its nominal height. To vertically flip an object, set the scale to a negative number.
     */
    private float scaleY = Constant.DEFAULT_SCALE;

    /**
     * The alpha (transparency) for this display object. 0 is fully transparent, 255 is fully opaque.
     * default 255.
     */
    private int alpha = Constant.DEFAULT_ALPHA;

    /**
     * The rotation in degrees for this display object.
     */
    private float rotation = Constant.DEFAULT_ROTATION;

    /**
     * Unique ID for this display object. Makes display objects easier for some uses.
     */
    private String id;

    /**
     * The Paint instance for this display object.
     */
    public Paint paint;

    public DisplayObject2() {
        paint = new Paint();
    }

    public DisplayObject2(Paint paint) {
        this.paint = paint;
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
        mAnim.setBaseLine(canvas, mPainter.getWidth(paint), mPainter.getHeight(paint));
        mAnim.updatePosition(mPosition);
        mPainter.draw(canvas, mPosition.x(), mPosition.y(), paint);
    }

    @Override
    public void setUp(long fps) {
        mAnim.setUp(fps);
    }

    /**
     * for debug.
     *
     * @return this display object Properties
     */
    public String toString() {
        return "DisplayObject{" +
                "x='" + this.x + '\n' +
                "y='" + this.y + '\n' +
                "alpha='" + this.alpha + '\n' +
                "scaleX='" + this.scaleX + '\n' +
                "scaleY='" + this.scaleY + '\n' +
                "rotation='" + this.rotation + '\n' +
                "paint='" + this.paint + '\n' +
                "id='" + this.id + '\n' +
                '}';
    }
}
