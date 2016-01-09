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

    /**
     * Bitmap to be drawn in FPSTextureView or FPSSurfaceView.
     */
    private Bitmap mBitmap;

    /**
     * If true, Draw a Bitmap in device-specific pixel density.
     */
    private boolean mDpSize = false;

    /**
     * Width of Bitmap in device-specific pixel density.
     */
    private float mBitmapDpWidth;

    /**
     * Height of Bitmap in device-specific pixel density.
     */
    private float mBitmapDpHeight;

    /**
     * Bitmap of Rect holds four integer coordinates for a rectangle.
     */
    private Rect mBitmapRect;

    /**
     * The SpriteSheet instance to play back. This includes frame dimensions, and frame data.
     */
    private SpriteSheet mSpriteSheet;

    private int mDrawingNum = Constant.DEFAULT_DRAWING_NUM;

    /**
     * Constructor
     *
     * @param bitmap          Bitmap to be drawn in FPSTextureView or FPSSurfaceView.
     * @param frameWidth      The number of width of each frame
     * @param frameHeight     The number of height of each frame
     * @param frameNum        the total number of frames in the specified animation
     * @param frameNumPerLine The number of which frame, there is about line 1 of side
     */
    public ParabolicMotionSpriteSheet(@NonNull Bitmap bitmap, float frameWidth, float frameHeight, int frameNum, int frameNumPerLine) {
        this.mBitmap = bitmap;
        this.mSpriteSheet = new SpriteSheet(frameWidth, frameHeight, frameNum, frameNumPerLine);
    }

    /**
     * Constructor
     *
     * @param bitmap      Bitmap to be drawn in FPSTextureView or FPSSurfaceView.
     * @param spriteSheet The SpriteSheet instance to play back. This includes frame dimensions, and frame data.
     */
    public ParabolicMotionSpriteSheet(@NonNull Bitmap bitmap, @NonNull SpriteSheet spriteSheet) {
        this.mBitmap = bitmap;
        this.mSpriteSheet = spriteSheet;
    }

    /**
     * Draw a Bitmap in device-specific pixel density.
     *
     * @param context Activity or view context
     * @return this
     */
    public ParabolicMotionSpriteSheet dpSize(@NonNull Context context) {
        mDpSize = true;

        mSpriteSheet.frameWidth = Util.convertPixelsToDp(mSpriteSheet.frameWidth, context);
        mSpriteSheet.frameHeight = Util.convertPixelsToDp(mSpriteSheet.frameHeight, context);

        mBitmapDpWidth = Util.convertPixelsToDp(mBitmap.getWidth(), context);
        mBitmapDpHeight = Util.convertPixelsToDp(mBitmap.getHeight(), context);
        mBitmapRect = new Rect(0, 0, mBitmap.getWidth(), mBitmap.getHeight());
        return this;
    }

    /**
     * Shortcut method to quickly set the transform properties on the display object.
     *
     * @param x The horizontal translation (x position) in pixels
     * @param y The vertical translation (y position) in pixels
     * @return this
     */
    public ParabolicMotionSpriteSheet transform(float x, float y) {
        this.x = x;
        this.y = y;
        return this;
    }

    /**
     * Set the number to be updated in every times of tick
     *
     * @param frequency The number to be updated in every times of tick
     * @return this
     */
    public ParabolicMotionSpriteSheet frequency(int frequency) {
        mFrequency = frequency;
        return this;
    }

    /**
     * If true, the Sprite Animation will loop when it reaches the last frame.
     *
     * @param loop If true, the Sprite Animation will loop when it reaches the last frame.
     * @return this
     */
    public ParabolicMotionSpriteSheet spriteLoop(boolean loop) {
        mSpriteSheet.spriteLoop = loop;
        return this;
    }

    /**
     * Set initial velocity of parabolic movement (y position) in pixels
     *
     * @param velocityY Initial velocity of parabolic movement (y position) in pixels
     * @return this
     */
    public ParabolicMotionSpriteSheet initialVelocityY(float velocityY) {
        mInitialVelocityY = velocityY;
        return this;
    }

    /**
     * Set the number to accelerate (y position) in pixels
     *
     * @param accelerationY By one tick, the number to accelerate (y position) in pixels
     * @return this
     */
    public ParabolicMotionSpriteSheet accelerationY(float accelerationY) {
        mAccelerationY = accelerationY;
        return this;
    }

    /**
     * Set the number to accelerate (x position) in pixels
     *
     * @param accelerationX By one tick, the number to accelerate (x position) in pixels
     * @return this
     */
    public ParabolicMotionSpriteSheet accelerationX(float accelerationX) {
        mAccelerationX = accelerationX;
        return this;
    }

    /**
     * Set coefficient Of Restitution Y, as a percentage of 1
     *
     * @param coefficientRestitutionY Coefficient Of Restitution Y, as a percentage of 1
     * @return this
     */
    public ParabolicMotionSpriteSheet coefficientRestitutionY(float coefficientRestitutionY) {
        mCoefficientRestitutionY = coefficientRestitutionY;
        return this;
    }

    /**
     * Set coefficient Of Restitution X, as a percentage of 1
     *
     * @param coefficientRestitutionX Coefficient Of Restitution X, as a percentage of 1
     * @return this
     */
    public ParabolicMotionSpriteSheet coefficientRestitutionX(float coefficientRestitutionX) {
        mCoefficientRestitutionX = coefficientRestitutionX;
        return this;
    }

    /**
     * Set the flag indicating whether not rebound bottom
     *
     * @param reboundBottom The flag indicating whether not rebound bottom
     * @return this
     */
    public ParabolicMotionSpriteSheet reboundBottom(boolean reboundBottom) {
        mReboundBottom = reboundBottom;
        return this;
    }

    /**
     * Set the flag indicating whether not rebound left
     *
     * @param reboundLeft The flag indicating whether not rebound left
     * @return this
     */
    public ParabolicMotionSpriteSheet reboundLeft(boolean reboundLeft) {
        mReboundLeft = reboundLeft;
        return this;
    }

    /**
     * Set the flag indicating whether not rebound right
     *
     * @param reboundRight The flag indicating whether not rebound right
     * @return this
     */
    public ParabolicMotionSpriteSheet reboundRight(boolean reboundRight) {
        mReboundRight = reboundRight;
        return this;
    }

    /**
     * Set the number to rebound bottom (y position) in pixels
     *
     * @param bottomBase The number to rebound bottom (y position) in pixels
     * @return this
     */
    public ParabolicMotionSpriteSheet bottomBase(float bottomBase) {
        this.mBottomBase = bottomBase;
        return this;
    }

    /**
     * Set the number to rebound right (x position) in pixels
     *
     * @param rightSide The number to rebound right (x position) in pixels
     * @return this
     */
    public ParabolicMotionSpriteSheet rightSide(float rightSide) {
        this.mRightSide = rightSide;
        return this;
    }

    /**
     * Set the number to rebound left (x position) in pixels
     *
     * @param leftSide The number to rebound left (x position) in pixels
     * @return this
     */
    public ParabolicMotionSpriteSheet leftSide(float leftSide) {
        this.mLeftSide = leftSide;
        return this;
    }

    /**
     * Set callback when responding to a bottom base
     *
     * @param animCallBack callback when responding to a bottom base
     * @return this
     */
    public ParabolicMotionSpriteSheet bottomHitCallback(AnimCallBack animCallBack) {
        setBottomHitCallback(animCallBack);
        return this;
    }

    /**
     * Set callback when responding to a left side
     *
     * @param animCallBack callback when responding to a left side
     * @return this
     */
    public ParabolicMotionSpriteSheet leftHitCallback(AnimCallBack animCallBack) {
        setLeftHitCallback(animCallBack);
        return this;
    }

    /**
     * Set callback when responding to a wall of right
     *
     * @param animCallBack callback when responding to a right side
     * @return this
     */
    public ParabolicMotionSpriteSheet rightHitCallback(AnimCallBack animCallBack) {
        setRightHitCallback(animCallBack);
        return this;
    }

    /**
     * Set function of dispatched when an animation reaches its ends.
     *
     * @param callBack Dispatched when an animation reaches its ends.
     * @return this
     */
    public ParabolicMotionSpriteSheet spriteAnimationEndCallBack(AnimCallBack callBack) {
        mSpriteSheet.setSpriteSheetFinishCallback(callBack);
        return this;
    }

    /**
     * indicates whether to start the SpriteAnimation paused.
     *
     * @param pause If true, SpriteAnimation pause.
     */
    @Override
    public void spritePause(boolean pause) {
        mSpriteSheet.setSpritePause(pause);
    }

    /**
     * Getter SpriteAnimation pause state
     *
     * @return spritePause
     */
    @Override
    public boolean isSpritePause() {
        return mSpriteSheet.isSpritePause();
    }

    /**
     * Move frame of SpriteSheet
     */
    private synchronized void updateSpriteFrame() {
        if (mDrawingNum != mFrequency) {
            mDrawingNum++;
            return;
        }
        mDrawingNum = Constant.DEFAULT_DRAWING_NUM;

        mSpriteSheet.updateFrame();
    }

    /**
     * if not set mBottomBase and mRightSide, set Canvas Bottm and Width.
     *
     * @param canvas This Canvas acquired by lookCanvas in FPSTextureView or FPSSurfaceView.
     */
    private void setBaseLength(Canvas canvas) {
        if (mBottomBase < 0) {
            mBottomBase = canvas.getHeight() - mSpriteSheet.frameHeight;
        }
        if (mRightSide < 0) {
            mRightSide = canvas.getWidth() - mSpriteSheet.frameWidth;
        }
    }

    /**
     * Draws the display object into the specified context ignoring its visible, alpha, shadow, and transform.
     *
     * @param canvas This Canvas acquired by lookCanvas in FPSTextureView or FPSSurfaceView.
     */
    @Override
    public void draw(Canvas canvas) {
        if (mBitmap == null) return;
        setBaseLength(canvas);

        updatePosition();
        canvas.save();
        RectF bounds = new RectF(
                x,
                y,
                x + mSpriteSheet.frameWidth,
                y + mSpriteSheet.frameHeight
        );
        canvas.saveLayer(bounds, null, Canvas.ALL_SAVE_FLAG);
        updateSpriteFrame();

        if (mDpSize) {
            RectF dpSizeRect = new RectF(
                    x + mSpriteSheet.dx,
                    y + mSpriteSheet.dy,
                    x + mSpriteSheet.dx + mBitmapDpWidth,
                    y + mSpriteSheet.dy + mBitmapDpHeight
            );
            canvas.drawBitmap(mBitmap, mBitmapRect, dpSizeRect, paint);
        } else {
            canvas.drawBitmap(mBitmap, x + mSpriteSheet.dx, y + mSpriteSheet.dy, paint);
        }
        canvas.restore();
    }

}
