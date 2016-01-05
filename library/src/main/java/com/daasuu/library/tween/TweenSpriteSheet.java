package com.daasuu.library.tween;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.NonNull;

import com.daasuu.library.callback.AnimCallBack;
import com.daasuu.library.constant.Constant;
import com.daasuu.library.easing.Ease;
import com.daasuu.library.parameter.AnimParameter;
import com.daasuu.library.spritesheet.UpdatePositionListener;
import com.daasuu.library.util.Util;

/**
 * Created by sudamasayuki on 15/12/19.
 */
public class TweenSpriteSheet extends Tween {

    private boolean mDpSize = false;
    private float mBitmapDpWidth;
    private float mBitmapDpHeight;
    private Rect mBitmapRect;
    private Bitmap mBitmap;

    private UpdatePositionListener mUpdatePositionListener;
    private float mFrameWidth;
    private float mFrameHeight;
    private int mFrameNum;
    private int mFrequency = 1;
    // The number of which frame, there is about line 1 of side
    private int mFrameNumPerLine;
    private boolean mSpriteLoop = false;


    private float dx = 0;
    private float dy = 0;
    private int mDrawingNum = Constant.DEFAULT_DRAWING_NUM;
    private int mCurrentPosition = Constant.DEFAULT_CURRENT_POSITION;

    private AnimCallBack mSpriteSheetFinishCallback;

    public TweenSpriteSheet(@NonNull Bitmap bitmap, float frameWidth, float frameHeight, int frameNum, int frameNumPerLine) {
        this.mBitmap = bitmap;
        this.mFrameWidth = frameWidth;
        this.mFrameHeight = frameHeight;
        this.mFrameNum = frameNum;
        this.mFrameNumPerLine = frameNumPerLine;
    }

    public TweenSpriteSheet dpSize(@NonNull Context context) {
        mDpSize = true;

        mFrameWidth = Util.convertPixelsToDp(mFrameWidth, context);
        mFrameHeight = Util.convertPixelsToDp(mFrameHeight, context);

        mBitmapDpWidth = Util.convertPixelsToDp(mBitmap.getWidth(), context);
        mBitmapDpHeight = Util.convertPixelsToDp(mBitmap.getHeight(), context);
        mBitmapRect = new Rect(0, 0, mBitmap.getWidth(), mBitmap.getHeight());
        return this;
    }

    public TweenSpriteSheet frequency(int frequency) {
        mFrequency = frequency;
        return this;
    }

    public TweenSpriteSheet updatePositionListener(UpdatePositionListener listener) {
        mUpdatePositionListener = listener;
        return this;
    }

    public TweenSpriteSheet spriteLoop(boolean loop) {
        mSpriteLoop = loop;
        return this;
    }

    public TweenSpriteSheet loop(boolean loop) {
        setTweenLoop(loop);
        return this;
    }

    public TweenSpriteSheet paint(Paint paint) {
        mPaint = paint;
        return this;
    }

    public TweenSpriteSheet transform(float x, float y) {
        setTransform(x, y);
        return this;
    }

    public TweenSpriteSheet transform(float x, float y, float alpha, float scaleX, float scaleY, float rotation) {
        setTransform(x, y, Util.convertAlphaFloatToInt(alpha), scaleX, scaleY, rotation);
        return this;
    }

    public TweenSpriteSheet to(long animDuration, float x, float y) {
        to(animDuration, x, y, Ease.LINEAR);
        return this;
    }

    public TweenSpriteSheet to(long animDuration, float x, float y, Ease ease) {
        setTo(animDuration, x, y, ease);
        return this;
    }

    public TweenSpriteSheet to(long animDuration, float x, float y, float alpha, Ease ease) {
        setTo(animDuration, x, y, Util.convertAlphaFloatToInt(alpha), ease);
        return this;
    }

    public TweenSpriteSheet to(long animDuration, float x, float y, float scaleX, float scaleY, Ease ease) {
        setTo(animDuration, x, y, scaleX, scaleY, ease);
        return this;
    }

    public TweenSpriteSheet to(long animDuration, float x, float y, Ease ease, float rotation) {
        setTo(animDuration, x, y, rotation, ease);
        return this;
    }

    public TweenSpriteSheet to(long animDuration, float x, float y, float alpha, float scaleX, float scaleY, float rotation, Ease ease) {
        setTo(animDuration, x, y, Util.convertAlphaFloatToInt(alpha), scaleX, scaleY, rotation, ease);
        return this;
    }

    public TweenSpriteSheet toX(long animDuration, float x) {
        toX(animDuration, x, Ease.LINEAR);
        return this;
    }

    public TweenSpriteSheet toX(long animDuration, float x, Ease ease) {
        setToX(animDuration, x, ease);
        return this;
    }

    public TweenSpriteSheet toY(long animDuration, float y) {
        toY(animDuration, y, Ease.LINEAR);
        return this;
    }

    public TweenSpriteSheet toY(long animDuration, float y, Ease ease) {
        setToY(animDuration, y, ease);
        return this;
    }

    public TweenSpriteSheet alpha(long animDuration, float alpha) {
        setAlpha(animDuration, Util.convertAlphaFloatToInt(alpha), Ease.LINEAR);
        return this;
    }

    public TweenSpriteSheet alpha(long animDuration, float alpha, Ease ease) {
        setAlpha(animDuration, Util.convertAlphaFloatToInt(alpha), ease);
        return this;
    }

    public TweenSpriteSheet scale(long animDuration, float scaleX, float scaleY) {
        setScale(animDuration, scaleX, scaleY, Ease.LINEAR);
        return this;
    }

    public TweenSpriteSheet scale(long animDuration, float scaleX, float scaleY, Ease ease) {
        setScale(animDuration, scaleX, scaleY, ease);
        return this;
    }

    public TweenSpriteSheet scaleRegistration(float regX, float regY) {
        setScaleRegistration(regX, regY);
        return this;
    }

    public TweenSpriteSheet rotation(long animDuration, float rotation) {
        setRotation(animDuration, rotation, Ease.LINEAR);
        return this;
    }

    public TweenSpriteSheet rotation(long animDuration, float rotation, Ease ease) {
        setRotation(animDuration, rotation, ease);
        return this;
    }

    public TweenSpriteSheet rotateRegistration(float regX, float regY) {
        setRotateRegistration(regX, regY);
        return this;
    }

    public TweenSpriteSheet waitTime(long animDuration) {
        setWaitTime(animDuration);
        return this;
    }

    public TweenSpriteSheet call(AnimCallBack callBack) {
        setAnimCallBack(callBack);
        return this;
    }

    public TweenSpriteSheet spriteAnimationEndCallBack(AnimCallBack callBack) {
        mSpriteSheetFinishCallback = callBack;
        return this;
    }

    private synchronized void updateSpritePosition() {
        if (mDrawingNum != mFrequency) {
            mDrawingNum++;
            return;
        }
        mDrawingNum = Constant.DEFAULT_DRAWING_NUM;

        if (mUpdatePositionListener != null) {
            mUpdatePositionListener.update(dx, dy, mCurrentPosition);
            repeatPosition();
            return;
        }

        boolean edge = mCurrentPosition % mFrameNumPerLine == 0;
        if (edge) {
            // It falls under the case of the end
            dy -= mFrameHeight;
            dx = 0;
            mCurrentPosition++;
            repeatPosition();
            return;
        }

        dx -= mFrameWidth;
        mCurrentPosition++;
        repeatPosition();
    }

    private void repeatPosition() {
        if (mCurrentPosition != mFrameNum) return;

        if (mSpriteLoop) {
            mCurrentPosition = Constant.DEFAULT_CURRENT_POSITION;
            dx = 0;
            dy = 0;
        }

        if (mSpriteSheetFinishCallback != null) {
            mSpriteSheetFinishCallback.call();
        }

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

        RectF bounds = new RectF(
                animParameter.x,
                animParameter.y,
                animParameter.x + mFrameWidth,
                animParameter.y + mFrameHeight
        );
        canvas.saveLayer(bounds, null, Canvas.ALL_SAVE_FLAG);
        updateSpritePosition();

        if (mDpSize) {
            RectF dpSizeRect = new RectF(
                    animParameter.x + dx,
                    animParameter.y + dy,
                    animParameter.x + dx + mBitmapDpWidth,
                    animParameter.y + dy + mBitmapDpHeight
            );
            canvas.drawBitmap(mBitmap, mBitmapRect, dpSizeRect, mPaint);
        } else {
            canvas.drawBitmap(mBitmap, animParameter.x + dx, animParameter.y + dy, mPaint);
        }

        canvas.restore();
        runAnimCallBack(animParameter);

    }

}