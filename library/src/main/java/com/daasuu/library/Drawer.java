package com.daasuu.library;

import android.graphics.Canvas;

/**
 * Interface which defines the functions to be implemented drawer class.
 * Drawer class responsible for drawing process of objects(such as bitmap, text and others) to the canvas.
 */
public interface Drawer {

    /**
     * Draw the object on the canvas with given properties.
     *
     * @param canvas   This Canvas acquired by lookCanvas in FPSTextureView or FPSSurfaceView.
     * @param x        The horizontal translation (x position) in pixels
     * @param y        The vertical translation (y position) in pixels
     * @param alpha    The alpha (transparency) ,as a percentage of 255.
     * @param scaleX   The horizontal scale, as a percentage of 1
     * @param scaleY   The vertical scale, as a percentage of 1
     * @param rotation rotation angle
     */
    void draw(Canvas canvas, float x, float y, int alpha, float scaleX, float scaleY, float rotation);

    /**
     * return the width of the object to be drawn.
     *
     * @return width
     */
    float getWidth();

    /**
     * return the height of the object to be drawn.
     *
     * @return height
     */
    float getHeight();
}
