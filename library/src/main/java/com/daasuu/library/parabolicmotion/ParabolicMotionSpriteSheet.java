package com.daasuu.library.parabolicmotion;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.NonNull;

import com.daasuu.library.callback.AnimCallBack;
import com.daasuu.library.constant.Constant;
import com.daasuu.library.spritesheet.SpritePause;
import com.daasuu.library.spritesheet.SpriteSheet;
import com.daasuu.library.util.Util;


public class ParabolicMotionSpriteSheet extends ParabolicMotion implements SpritePause {

    private Bitmap mBitmap;

    private boolean mDpSize = false;

    private float mBitmapDpWidth;
    private float mBitmapDpHeight;
    private Rect mBitmapRect;

    private SpriteSheet mSpriteSheet;

    private int mDrawingNum = Constant.DEFAULT_DRAWING_NUM;

    public ParabolicMotionSpriteSheet(@NonNull Bitmap bitmap, float frameWidth, float frameHeight, int frameNum, int frameNumPerLine) {
        this.mBitmap = bitmap;
        this.mSpriteSheet = new SpriteSheet(frameWidth, frameHeight, frameNum, frameNumPerLine);
    }

    public ParabolicMotionSpriteSheet(@NonNull Bitmap bitmap, @NonNull SpriteSheet spriteSheet) {
        this.mBitmap = bitmap;
        this.mSpriteSheet = spriteSheet;
    }

    public ParabolicMotionSpriteSheet dpSize(@NonNull Context context) {
        mDpSize = true;

        mSpriteSheet.frameWidth = Util.convertPixelsToDp(mSpriteSheet.frameWidth, context);
        mSpriteSheet.frameHeight = Util.convertPixelsToDp(mSpriteSheet.frameHeight, context);

        mBitmapDpWidth = Util.convertPixelsToDp(mBitmap.getWidth(), context);
        mBitmapDpHeight = Util.convertPixelsToDp(mBitmap.getHeight(), context);
        mBitmapRect = new Rect(0, 0, mBitmap.getWidth(), mBitmap.getHeight());
        return this;
    }

    public ParabolicMotionSpriteSheet transform(float x, float y) {
        mAnimParameter.x = x;
        mAnimParameter.y = y;
        return this;
    }

    public ParabolicMotionSpriteSheet frequency(int frequency) {
        mFrequency = frequency;
        return this;
    }

    public ParabolicMotionSpriteSheet spriteLoop(boolean loop) {
        mSpriteSheet.spriteLoop = loop;
        return this;
    }

    public ParabolicMotionSpriteSheet initialVelocityY(float velocityY) {
        mInitialVelocityY = velocityY;
        return this;
    }

    public ParabolicMotionSpriteSheet accelerationY(float accelerationY) {
        mAccelerationY = accelerationY;
        return this;
    }

    public ParabolicMotionSpriteSheet accelerationX(float accelerationX) {
        mAccelerationX = accelerationX;
        return this;
    }

    public ParabolicMotionSpriteSheet coefficientRestitutionY(float coefficientRestitutionY) {
        mCoefficientRestitutionY = coefficientRestitutionY;
        return this;
    }

    public ParabolicMotionSpriteSheet coefficientRestitutionX(float coefficientRestitutionX) {
        mCoefficientRestitutionX = coefficientRestitutionX;
        return this;
    }

    public ParabolicMotionSpriteSheet coefficientBottom(boolean coefficientBottom) {
        mCoefficientBottom = coefficientBottom;
        return this;
    }

    public ParabolicMotionSpriteSheet coefficientLeft(boolean coefficientLeft) {
        mCoefficientLeft = coefficientLeft;
        return this;
    }

    public ParabolicMotionSpriteSheet coefficientRight(boolean coefficientRight) {
        mCoefficientRight = coefficientRight;
        return this;
    }

    public ParabolicMotionSpriteSheet bottomHitCallback(AnimCallBack animCallBack) {
        setBottomHitCallback(animCallBack);
        return this;
    }

    public ParabolicMotionSpriteSheet leftHitCallback(AnimCallBack animCallBack) {
        setLeftHitCallback(animCallBack);
        return this;
    }

    public ParabolicMotionSpriteSheet rightHitCallback(AnimCallBack animCallBack) {
        setRightHitCallback(animCallBack);
        return this;
    }

    public ParabolicMotionSpriteSheet spriteAnimationEndCallBack(AnimCallBack callBack) {
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

    private void setBaseLength(Canvas canvas) {
        mBottomBase = canvas.getHeight() - mSpriteSheet.frameHeight;
        mRightSide = canvas.getWidth() - mSpriteSheet.frameWidth;
    }

    @Override
    public void draw(Canvas canvas) {
        if (mBitmap == null) return;
        setBaseLength(canvas);

        updatePosition();
        canvas.save();
        RectF bounds = new RectF(
                mAnimParameter.x,
                mAnimParameter.y,
                mAnimParameter.x + mSpriteSheet.frameWidth,
                mAnimParameter.y + mSpriteSheet.frameHeight
        );
        canvas.saveLayer(bounds, null, Canvas.ALL_SAVE_FLAG);
        updateSpritePosition();

        if (mDpSize) {
            RectF dpSizeRect = new RectF(
                    mAnimParameter.x + mSpriteSheet.dx,
                    mAnimParameter.y + mSpriteSheet.dy,
                    mAnimParameter.x + mSpriteSheet.dx + mBitmapDpWidth,
                    mAnimParameter.y + mSpriteSheet.dy + mBitmapDpHeight
            );
            canvas.drawBitmap(mBitmap, mBitmapRect, dpSizeRect, mPaint);
        } else {
            canvas.drawBitmap(mBitmap, mAnimParameter.x + mSpriteSheet.dx, mAnimParameter.y + mSpriteSheet.dy, mPaint);
        }
        canvas.restore();
    }

}
