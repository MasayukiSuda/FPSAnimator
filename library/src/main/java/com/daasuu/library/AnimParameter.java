package com.daasuu.library;

import com.daasuu.library.constant.Constant;

/**
 * Class to hold a parameter related to the drawing on the canvas.
 */
public class AnimParameter {

    /**
     * The x (horizontal) position of the display object, relative to its parent.
     */
    public float x = 0;

    /**
     * The y (vertical) position of the display object, relative to its parent.
     */
    public float y = 0;

    /**
     * The alpha (transparency) for this display object. 0 is fully transparent, 255 is fully opaque.
     * default 255.
     */
    public int alpha = Constant.DEFAULT_ALPHA;

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
     * The rotation in degrees for this display object.
     */
    public float rotation = Constant.DEFAULT_ROTATION;

    /**
     * Constructor
     *
     * @param x The horizontal translation (x position) in pixels
     * @param y The vertical translation (y position) in pixels
     */
    public AnimParameter(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Constructor
     *
     * @param x        The horizontal translation (x position) in pixels
     * @param y        The vertical translation (y position) in pixels
     * @param alpha    The alpha (transparency) ,as a percentage of 255.
     * @param scaleX   The horizontal scale, as a percentage of 1
     * @param scaleY   The vertical scale, as a percentage of 1
     * @param rotation rotation angle
     */
    public AnimParameter(float x, float y, int alpha, float scaleX, float scaleY, float rotation) {
        this.x = x;
        this.y = y;
        this.alpha = alpha;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.rotation = rotation;
    }

    @Override
    public String toString() {
        return "AnimParameter{" +
                "x=" + x +
                ", y=" + y +
                ", alpha=" + alpha +
                ", scaleX=" + scaleX +
                ", scaleY=" + scaleY +
                ", rotation=" + rotation +
                '}';
    }
}
