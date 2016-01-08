package com.daasuu.library;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.daasuu.library.constant.Constant;
import com.daasuu.library.parameter.AnimParameter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sudamasayuki on 15/12/19.
 */
public abstract class DisplayObject {

    public float mInitialX = 0f;
    public float mInitialY = 0f;

    public float mInitialScaleX = Constant.DEFAULT_SCALE;
    public float mInitialScaleY = Constant.DEFAULT_SCALE;
    public int mInitialAlpha = Constant.DEFAULT_ALPHA;
    protected float mInitialRotation = Constant.DEFAULT_ROTATION;

    protected Paint mPaint;

    protected List<AnimParameter> mAnimParameters = new ArrayList<AnimParameter>() {
        {
            add(new AnimParameter(mInitialX, mInitialY));
        }
    };

    protected long mFps = 1000 / Constant.DEFAULT_FPS;

    void setFps(long fps) {
        mFps = 1000 / fps;
    }

    // Draw
    public abstract void draw(Canvas canvas);

    public abstract void setUp();

}
