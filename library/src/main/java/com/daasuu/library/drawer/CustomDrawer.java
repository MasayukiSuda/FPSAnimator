package com.daasuu.library.drawer;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * drawer that can be drawn on the canvas without restrictions.
 */
public class CustomDrawer extends BaseDrawer {

    private CustomDraw mCustomDraw;

    public CustomDrawer(CustomDraw customDraw) {
        super(null);
        mCustomDraw = customDraw;
    }

    @Override
    protected void draw(Canvas canvas, float x, float y, int alpha) {
        mCustomDraw.draw(canvas, x, y, alpha);
    }

    @Override
    public float getWidth() {
        return mCustomDraw.getWidth();
    }

    @Override
    public float getHeight() {
        return mCustomDraw.getHeight();
    }

    public interface CustomDraw {

        /**
         * Draw the object on the canvas with given properties.
         * This argument, comes in property values ​​in the animation.
         *
         * @param canvas This Canvas acquired by lookCanvas in FPSTextureView or FPSSurfaceView.
         * @param x      The horizontal translation (x position) in pixels
         * @param y      The vertical translation (y position) in pixels
         * @param alpha  The alpha (transparency) ,as a percentage of 255.
         */
        void draw(Canvas canvas, float x, float y, int alpha);

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

    /**
     * The left offset and y offset for this display object's registration point. For example, to make a 100x100px Bitmap scale
     * around its center, you would set regX and regY to 50.
     *
     * @param regX The horizontal rotate registration point in pixels
     * @param regY The vertical rotate registration point in pixels
     * @return this
     */
    public CustomDrawer scaleRegistration(float regX, float regY) {
        setScaleRegistration(regX, regY);
        return this;
    }

    /**
     * The left offset and y offset for this display object's registration point. For example, to make a 100x100px Bitmap rotate
     * around its center, you would set regX and regY to 50.
     *
     * @param regX The horizontal rotate registration point in pixels
     * @param regY The vertical rotate registration point in pixels
     * @return this
     */
    public CustomDrawer rotateRegistration(float regX, float regY) {
        setRotateRegistration(regX, regY);
        return this;
    }
}
