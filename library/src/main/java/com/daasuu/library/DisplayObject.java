package com.daasuu.library;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.daasuu.library.constant.Constant;

/**
 * DisplayObject is the base class for all display classes in this library. It defines the core properties and
 * methods that are shared between all display objects, such as transformation properties (x, y, scaleX, scaleY, etc).
 */
public abstract class DisplayObject {

    /**
     * The x (horizontal) position of the display object, relative to its parent.
     */
    public float x = 0f;

    /**
     * The y (vertical) position of the display object, relative to its parent.
     */
    public float y = 0f;

    /**
     * The factor to stretch this display object horizontally. For example, setting scaleX to 2 will stretch the display
     * object to twice its nominal width. To horizontally flip an object, set the scale to a negative number.
     */
    public float scaleX = Constant.DEFAULT_SCALE;

    /**
     * The factor to stretch this display object vertically. For example, setting scaleY to 0.5 will stretch the display
     * object to half its nominal height. To vertically flip an object, set the scale to a negative number.
     */
    public float scaleY = Constant.DEFAULT_SCALE;

    /**
     * The alpha (transparency) for this display object. 0 is fully transparent, 255 is fully opaque.
     * default 255.
     */
    public int alpha = Constant.DEFAULT_ALPHA;

    /**
     * The rotation in degrees for this display object.
     */
    public float rotation = Constant.DEFAULT_ROTATION;

    /**
     * Unique ID for this display object. Makes display objects easier for some uses.
     */
    public String id;

    /**
     * The Paint instance for this display object.
     */
    public Paint paint;

    /**
     * Draws the display object into the specified context ignoring its visible, alpha, shadow, and transform.
     *
     * @param canvas This Canvas acquired by lookCanvas in FPSTextureView or FPSSurfaceView.
     */
    public abstract void draw(Canvas canvas);

    /**
     * call from FPSTextureView or FPSSurfaceView when it is addChild.
     *
     * @param fps Set in FPSTextureView or FPSSurfaceView.
     */
    public abstract void setUp(long fps);

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
