package com.daasuu.library.tween;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;


import com.daasuu.library.callback.AnimCallBack;
import com.daasuu.library.easing.Ease;
import com.daasuu.library.parameter.AnimParameter;
import com.daasuu.library.util.Util;

public class TweenBitmap extends Tween {

    private Bitmap mBitmap;
    private boolean mDpSize = false;

    private float mBitmapDpWidth;
    private float mBitmapDpHeight;
    private Rect mBitmapRect;

    public TweenBitmap(Bitmap bitmap) {
        this(bitmap, null);
    }

    public TweenBitmap(Bitmap bitmap, Paint paint) {
        this.mBitmap = bitmap;
        this.mPaint = paint;
    }

    public TweenBitmap transform(float x, float y) {
        setTransform(x, y);
        return this;
    }

    public TweenBitmap transform(float x, float y, float alpha, float scaleX, float scaleY, float rotation) {
        setTransform(x, y, Util.convertAlphaFloatToInt(alpha), scaleX, scaleY, rotation);
        return this;
    }

    public TweenBitmap dpSize(Context context) {
        mDpSize = true;
        mBitmapDpWidth = Util.convertPixelsToDp(mBitmap.getWidth(), context);
        mBitmapDpHeight = Util.convertPixelsToDp(mBitmap.getHeight(), context);
        mBitmapRect = new Rect(0, 0, mBitmap.getWidth(), mBitmap.getHeight());
        return this;
    }


    public TweenBitmap loop(boolean loop) {
        setTweenLoop(loop);
        return this;
    }

    public TweenBitmap to(long animDuration, float x, float y) {
        to(animDuration, x, y, Ease.LINEAR);
        return this;
    }

    public TweenBitmap to(long animDuration, float x, float y, Ease ease) {
        setTo(animDuration, x, y, ease);
        return this;
    }

    public TweenBitmap to(long animDuration, float x, float y, float alpha, Ease ease) {
        setTo(animDuration, x, y, Util.convertAlphaFloatToInt(alpha), ease);
        return this;
    }

    public TweenBitmap to(long animDuration, float x, float y, float scaleX, float scaleY, Ease ease) {
        setTo(animDuration, x, y, scaleX, scaleY, ease);
        return this;
    }

    public TweenBitmap to(long animDuration, float x, float y, Ease ease, float rotation) {
        setTo(animDuration, x, y, rotation, ease);
        return this;
    }

    public TweenBitmap to(long animDuration, float x, float y, float alpha, float scaleX, float scaleY, float rotation, Ease ease) {
        setTo(animDuration, x, y, Util.convertAlphaFloatToInt(alpha), scaleX, scaleY, rotation, ease);
        return this;
    }

    public TweenBitmap toX(long animDuration, float x) {
        toX(animDuration, x, Ease.LINEAR);
        return this;
    }

    public TweenBitmap toX(long animDuration, float x, Ease ease) {
        setToX(animDuration, x, ease);
        return this;
    }

    public TweenBitmap toY(long animDuration, float y) {
        toY(animDuration, y, Ease.LINEAR);
        return this;
    }

    public TweenBitmap toY(long animDuration, float y, Ease ease) {
        setToY(animDuration, y, Ease.LINEAR);
        return this;
    }

    public TweenBitmap alpha(long animDuration, float alpha) {
        setAlpha(animDuration, Util.convertAlphaFloatToInt(alpha), Ease.LINEAR);
        return this;
    }

    public TweenBitmap alpha(long animDuration, float alpha, Ease ease) {
        setAlpha(animDuration, Util.convertAlphaFloatToInt(alpha), ease);
        return this;
    }

    public TweenBitmap scale(long animDuration, float scaleX, float scaleY) {
        setScale(animDuration, scaleX, scaleY, Ease.LINEAR);
        return this;
    }

    public TweenBitmap scale(long animDuration, float scaleX, float scaleY, Ease ease) {
        setScale(animDuration, scaleX, scaleY, ease);
        return this;
    }

    public TweenBitmap scaleRegistration(float regX, float regY) {
        setScaleRegistration(regX, regY);
        return this;
    }

    public TweenBitmap rotation(long animDuration, float rotation) {
        setRotation(animDuration, rotation, Ease.LINEAR);
        return this;
    }

    public TweenBitmap rotation(long animDuration, float rotation, Ease ease) {
        setRotation(animDuration, rotation, ease);
        return this;
    }

    public TweenBitmap rotateRegistration(float regX, float regY) {
        setRotateRegistration(regX, regY);
        return this;
    }

    public TweenBitmap waitTime(long animDuration) {
        setWaitTime(animDuration);
        return this;
    }

    public TweenBitmap call(AnimCallBack callBack) {
        setAnimCallBack(callBack);
        return this;
    }

    @Override
    public void draw(Canvas canvas) {
        if (mBitmap == null || mAnimParameters.size() == 0) return;

        AnimParameter animParameter = getDrawAnimParameter();
        if (animParameter == null) return;

        if (mPaint == null) mPaint = new Paint();
        mPaint.setAlpha(animParameter.alpha);

        canvas.save();
        canvas.scale(animParameter.scaleX, animParameter.scaleX, animParameter.x + mScaleRegistrationX, animParameter.y + mScaleRegistrationY);
        canvas.rotate(animParameter.rotation, animParameter.x + mRotateRegistrationX, animParameter.y + mRotateRegistrationY);

        if (mDpSize) {
            RectF dpSizeRect = new RectF(
                    animParameter.x,
                    animParameter.y,
                    animParameter.x + mBitmapDpWidth,
                    animParameter.y + mBitmapDpHeight
            );
            canvas.drawBitmap(mBitmap, mBitmapRect, dpSizeRect, mPaint);
        } else {
            canvas.drawBitmap(mBitmap, animParameter.x, animParameter.y, mPaint);
        }

        canvas.restore();

        runAnimCallBack(animParameter);
    }

}
