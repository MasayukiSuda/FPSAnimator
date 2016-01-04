package com.daasuu.library.tween;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.daasuu.library.callback.AnimCallBack;
import com.daasuu.library.easing.Ease;
import com.daasuu.library.parameter.AnimParameter;
import com.daasuu.library.util.Util;

/**
 * Created by sudamasayuki on 15/12/19.
 */
public class TweenText extends Tween {

    private String mText;
    private float mAdjustTextMesureY = 0f;

    public TweenText(String text, Paint paint) {
        this.mText = text;
        this.mPaint = paint;
        if (mPaint != null) mAdjustTextMesureY = mPaint.getTextSize();
    }

    public TweenText transform(float x, float y) {
        setTransform(x, y);
        return this;
    }

    public TweenText transform(float x, float y, float alpha, float scaleX, float scaleY, float rotation) {
        setTransform(x, y, Util.convertAlphaFloatToInt(alpha), scaleX, scaleY, rotation);
        return this;
    }

    public TweenText loop(boolean loop) {
        setTweenLoop(loop);
        return this;
    }

    public TweenText to(long animDuration, float x, float y) {
        to(animDuration, x, y, Ease.LINEAR);
        return this;
    }

    public TweenText to(long animDuration, float x, float y, Ease ease) {
        setTo(animDuration, x, y, ease);
        return this;
    }

    public TweenText to(long animDuration, float x, float y, float alpha, Ease ease) {
        setTo(animDuration, x, y, Util.convertAlphaFloatToInt(alpha), ease);
        return this;
    }

    public TweenText to(long animDuration, float x, float y, float scaleX, float scaleY, Ease ease) {
        setTo(animDuration, x, y, scaleX, scaleY, ease);
        return this;
    }

    public TweenText to(long animDuration, float x, float y, Ease ease, float rotation) {
        setTo(animDuration, x, y, rotation, ease);
        return this;
    }

    public TweenText to(long animDuration, float x, float y, float alpha, float scaleX, float scaleY, float rotation, Ease ease) {
        setTo(animDuration, x, y, Util.convertAlphaFloatToInt(alpha), scaleX, scaleY, rotation, ease);
        return this;
    }

    public TweenText toX(long animDuration, float x) {
        toX(animDuration, x, Ease.LINEAR);
        return this;
    }

    public TweenText toX(long animDuration, float x, Ease ease) {
        setToX(animDuration, x, ease);
        return this;
    }

    public TweenText toY(long animDuration, float y) {
        toY(animDuration, y, Ease.LINEAR);
        return this;
    }

    public TweenText toY(long animDuration, float y, Ease ease) {
        setToY(animDuration, y, ease);
        return this;
    }

    public TweenText alpha(long animDuration, float alpha) {
        setAlpha(animDuration, Util.convertAlphaFloatToInt(alpha), Ease.LINEAR);
        return this;
    }

    public TweenText alpha(long animDuration, float alpha, Ease ease) {
        setAlpha(animDuration, Util.convertAlphaFloatToInt(alpha), ease);
        return this;
    }

    public TweenText scale(long animDuration, float scaleX, float scaleY) {
        setScale(animDuration, scaleX, scaleY, Ease.LINEAR);
        return this;
    }

    public TweenText scale(long animDuration, float scaleX, float scaleY, Ease ease) {
        setScale(animDuration, scaleX, scaleY, ease);
        return this;
    }

    public TweenText scaleRegistration(float regX, float regY) {
        setScaleRegistration(regX, regY);
        return this;
    }

    public TweenText rotation(long animDuration, float rotation) {
        setRotation(animDuration, rotation, Ease.LINEAR);
        return this;
    }

    public TweenText rotation(long animDuration, float rotation, Ease ease) {
        setRotation(animDuration, rotation, ease);
        return this;
    }

    public TweenText rotateRegistration(float regX, float regY) {
        setRotateRegistration(regX, regY);
        return this;
    }

    public TweenText waitTime(long animDuration) {
        setWaitTime(animDuration);
        return this;
    }

    public TweenText call(AnimCallBack callBack) {
        setAnimCallBack(callBack);
        return this;
    }

    @Override
    public void draw(Canvas canvas) {
        if (mText == null || mText.length() == 0 || mAnimParameters.size() == 0) return;

        AnimParameter animParameter = getDrawAnimParameter();
        if (animParameter == null) return;

        if (mPaint == null) mPaint = new Paint();
        mPaint.setAlpha(animParameter.alpha);

        canvas.save();
        canvas.scale(animParameter.scaleX, animParameter.scaleX, animParameter.x + mScaleRegistrationX, animParameter.y + mScaleRegistrationY);
        canvas.rotate(animParameter.rotation, animParameter.x + mRotateRegistrationX, animParameter.y + mRotateRegistrationY);
        canvas.drawText(mText, animParameter.x, animParameter.y + mAdjustTextMesureY, mPaint);

        canvas.restore();

        runAnimCallBack(animParameter);

    }

}
