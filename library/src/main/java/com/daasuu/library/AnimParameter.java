package com.daasuu.library;

import com.daasuu.library.constant.Constant;

/**
 * Created by a13587 on 16/01/14.
 */
public class AnimParameter {

    private float x;

    private float y;

    private int alpha = Constant.DEFAULT_ALPHA;

    private float scaleX = Constant.DEFAULT_SCALE;

    private float scaleY = Constant.DEFAULT_SCALE;

    private float rotation = Constant.DEFAULT_ROTATION;

    public AnimParameter(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public AnimParameter(float x, float y, int alpha, float scaleX, float scaleY, float rotation) {
        this.x = x;
        this.y = y;
        this.alpha = alpha;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.rotation = rotation;
    }

    public float x() {
        return this.x;
    }

    public float y() {
        return this.y;
    }

    public int alpha() {
        return this.alpha;
    }

    public float scaleX() {
        return this.scaleX;
    }

    public float scaleY() {
        return this.scaleY;
    }

    public float rotation() {
        return this.rotation;
    }

    public AnimParameter updatePosition(float x, float y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public AnimParameter updateAlpha(int alpha) {
        this.alpha = alpha;
        return this;
    }

    public AnimParameter updateScale(float scaleX, float scaleY) {
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        return this;
    }

    public AnimParameter updateRotation(float rotation) {
        this.rotation = rotation;
        return this;
    }

    @Override
    public String toString() {
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
