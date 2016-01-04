package com.daasuu.library.parameter;

import com.daasuu.library.callback.AnimCallBack;
import com.daasuu.library.constant.Constant;

/**
 * Is actually used when it is drawn on the canvas, the parameter holds class
 */
public class AnimParameter {
    public float x;
    public float y;
    public int alpha = Constant.DEFAULT_ALPHA;
    public float scaleX = Constant.DEFAULT_SCALE;
    public float scaleY = Constant.DEFAULT_SCALE;
    public float rotation = Constant.DEFAULT_ROTATION;
    public AnimCallBack animCallBack;

    public AnimParameter(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public AnimParameter setAlpha(int alpha) {
        this.alpha = alpha;
        return this;
    }

    public AnimParameter setScaleX(float scaleX) {
        this.scaleX = scaleX;
        return this;
    }

    public AnimParameter setScaleY(float scaleY) {
        this.scaleY = scaleY;
        return this;
    }


    public AnimParameter setRotation(float rotation) {
        this.rotation = rotation;
        return this;
    }

    public AnimParameter setAnimCallback(AnimCallBack callBack) {
        this.animCallBack = callBack;
        return this;
    }

    // for debug
    public String toString() {
        return "AnimParameter{" +
                "x='" + this.x + '\n' +
                "y='" + this.y + '\n' +
                "alpha='" + this.alpha + '\n' +
                "scaleX='" + this.scaleX + '\n' +
                "scaleY='" + this.scaleY + '\n' +
                "rotation='" + this.rotation + '\n' +
                "animCallBack='" + this.animCallBack + '\n' +
                '}';
    }
}
