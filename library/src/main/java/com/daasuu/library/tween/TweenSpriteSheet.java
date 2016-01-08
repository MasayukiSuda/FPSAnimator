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
import com.daasuu.library.spritesheet.SpritePause;
import com.daasuu.library.spritesheet.SpriteSheet;
import com.daasuu.library.util.Util;

/**
 * Created by sudamasayuki on 15/12/19.
 */
public class TweenSpriteSheet extends Tween implements SpritePause {

    private boolean mDpSize = false;
    private float mBitmapDpWidth;
    private float mBitmapDpHeight;
    private Rect mBitmapRect;
    private Bitmap mBitmap;

    private int mFrequency = 1;

    private SpriteSheet mSpriteSheet;

    private int mDrawingNum = Constant.DEFAULT_DRAWING_NUM;


    public TweenSpriteSheet(@NonNull Bitmap bitmap, float frameWidth, float frameHeight, int frameNum, int frameNumPerLine) {
        this.mBitmap = bitmap;
        this.mSpriteSheet = new SpriteSheet(frameWidth, frameHeight, frameNum, frameNumPerLine);
    }

    public TweenSpriteSheet(@NonNull Bitmap bitmap, @NonNull SpriteSheet spriteSheet) {
        this.mBitmap = bitmap;
        this.mSpriteSheet = spriteSheet;
    }

    public TweenSpriteSheet dpSize(@NonNull Context context) {
        mDpSize = true;

        mSpriteSheet.frameWidth = Util.convertPixelsToDp(mSpriteSheet.frameWidth, context);
        mSpriteSheet.frameHeight = Util.convertPixelsToDp(mSpriteSheet.frameHeight, context);

        mBitmapDpWidth = Util.convertPixelsToDp(mBitmap.getWidth(), context);
        mBitmapDpHeight = Util.convertPixelsToDp(mBitmap.getHeight(), context);
        mBitmapRect = new Rect(0, 0, mBitmap.getWidth(), mBitmap.getHeight());
        return this;
    }

    public TweenSpriteSheet frequency(int frequency) {
        mFrequency = frequency;
        return this;
    }

    public TweenSpriteSheet spriteLoop(boolean loop) {
        mSpriteSheet.spriteLoop = loop;
        return this;
    }

    public TweenSpriteSheet loop(boolean loop) {
        setTweenLoop(loop);
        return this;
    }

    public TweenSpriteSheet paint(Paint paint) {
        this.paint = paint;
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
        mSpriteSheet.setSpriteSheetFinishCallback(callBack);
        return this;
    }

    @Override
    public void spritePause(boolean pause) {
        mSpriteSheet.setSpritePause(pause);
    }

    @Override
    public boolean isSpritePause() {
        return mSpriteSheet.isSpritePause();
    }

    private synchronized void updateSpritePosition() {
        if (mDrawingNum != mFrequency) {
            mDrawingNum++;
            return;
        }
        mDrawingNum = Constant.DEFAULT_DRAWING_NUM;

        mSpriteSheet.updatePosition();

    }

    @Override
    public void draw(Canvas canvas) {

        if (mBitmap == null || mAnimParameters.size() == 0) return;

        AnimParameter animParameter = getDrawAnimParameter();
        if (animParameter == null) return;

        if (paint == null) paint = new Paint();
        alpha = animParameter.alpha;
        paint.setAlpha(alpha);

        canvas.save();
        scaleX = animParameter.scaleX;
        scaleY = animParameter.scaleY;
        rotation = animParameter.rotation;
        canvas.scale(scaleX, scaleY, animParameter.x + mScaleRegistrationX, animParameter.y + mScaleRegistrationY);
        canvas.rotate(rotation, animParameter.x + mRotateRegistrationX, animParameter.y + mRotateRegistrationY);

        RectF bounds = new RectF(
                animParameter.x,
                animParameter.y,
                animParameter.x + mSpriteSheet.frameWidth,
                animParameter.y + mSpriteSheet.frameHeight
        );
        canvas.saveLayer(bounds, null, Canvas.ALL_SAVE_FLAG);
        updateSpritePosition();

        x = animParameter.x + mSpriteSheet.dx;
        y = animParameter.y + mSpriteSheet.dy;

        if (mDpSize) {
            RectF dpSizeRect = new RectF(
                    x,
                    y,
                    x + mBitmapDpWidth,
                    y + mBitmapDpHeight
            );
            canvas.drawBitmap(mBitmap, mBitmapRect, dpSizeRect, paint);
        } else {
            canvas.drawBitmap(mBitmap, x, y, paint);
        }

        canvas.restore();
        runAnimCallBack(animParameter);

    }

}