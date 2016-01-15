package com.daasuu.library;

import android.graphics.Canvas;

/**
 * Interface which defines the functions to be implemented drawer class.
 * Drawer class responsible for drawing process of objects(such as bitmap, text and others) to the canvas.
 */
public interface Painter {

    /**
     * Draw the object on the canvas with given properties.
     *
     * @param canvas
     * @param x        horizontal position
     * @param y        vertical position
     * @param alpha    object alpha value (0 ~ 255)
     * @param scaleX   horizontal scale value  (0 ~ 1)
     * @param scaleY   vertical scale value (0 ~ 1)
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
