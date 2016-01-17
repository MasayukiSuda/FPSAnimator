package com.daasuu.library;

import android.graphics.Canvas;

/**
 * Interface which defines the functions to be implemented animation class.
 * The main role of the animation class is to determine the information
 * about position and deformation ({@link AnimParameter}) to draw on canvas.
 */
public interface Animator {

    /**
     * Sets a next drawing value to the given animation parameter instance.
     *
     * @param animParameter
     */
    void updateAnimParam(AnimParameter animParameter);

    /**
     * Setup the base drawing position.
     *
     * @param canvas
     * @param width
     * @param height
     */
    void setBaseLine(Canvas canvas, float width, float height);

    /**
     * return the initial animation parameter.
     *
     * @return initial animation parameter
     */
    AnimParameter getInitialAnimParameter();

    /**
     * call from FPSTextureView or FPSSurfaceView when it is addChild.
     *
     * @param fps Set in FPSTextureView or FPSSurfaceView.
     */
    void setUp(long fps);
}
