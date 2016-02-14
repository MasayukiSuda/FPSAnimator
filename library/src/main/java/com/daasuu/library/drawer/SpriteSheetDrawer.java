package com.daasuu.library.drawer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;

import com.daasuu.library.callback.AnimCallBack;
import com.daasuu.library.constant.Constant;
import com.daasuu.library.spritesheet.SpriteSheet;
import com.daasuu.library.util.Util;

import java.util.List;

/**
 * Class for drawing SpriteSheet on canvas.
 */
public class SpriteSheetDrawer extends BaseDrawer {

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
     * The SpriteSheet instance to play back. This includes frame dimensions, and frame data.
     */
    private SpriteSheet mSpriteSheet;

    /**
     * The subset of the SpriteSheet bitmap to be drawn
     */
    private final Rect mBitmapRect;

    /**
     * The rectangle that the SpriteSheet bitmap will be scaled/translated to fit into
     */
    private final Rect mBounds;

    /**
     * member variables needed to skip a tick in mFrequency
     */
    private int mDrawingNum = Constant.DEFAULT_DRAWING_NUM;

    /**
     * By default Sprite instances advance one frame per tick.
     * Specifying a frequency for the Sprite (or its related SpriteSheet) will cause it to
     * advance based on elapsed time between ticks as appropriate to maintain the target frequency.
     * For example, if a Sprite with a frequency of 10 is placed on a Stage being updated at 40fps,
     * then the Sprite will advance roughly one frame every 4 ticks. This will not be exact,
     * because the time between each tick will vary slightly between frames.
     * This feature is dependent on the tick event object being passed into update.
     */
    private int mFrequency = Constant.DEFAULT_FREQUENCY;


    /**
     * Constructor
     *
     * @param bitmap      Bitmap to be drawn in FPSTextureView or FPSSurfaceView.
     * @param frameWidth  The number of width of each frame
     * @param frameHeight The number of height of each frame
     * @param frameNum    the total number of frames in the specified animation
     */
    public SpriteSheetDrawer(@NonNull Bitmap bitmap, float frameWidth, float frameHeight, int frameNum) {
        this(bitmap, new SpriteSheet(frameWidth, frameHeight, frameNum, (int) (bitmap.getWidth() / frameWidth)));
    }

    /**
     * Constructor
     *
     * @param bitmap          Bitmap to be drawn in FPSTextureView or FPSSurfaceView.
     * @param frameWidth      The number of width of each frame
     * @param frameHeight     The number of height of each frame
     * @param frameNum        the total number of frames in the specified animation
     * @param frameNumPerLine The number of which frame, there is about line 1 of side
     */
    public SpriteSheetDrawer(@NonNull Bitmap bitmap, float frameWidth, float frameHeight, int frameNum, int frameNumPerLine) {
        this(bitmap, new SpriteSheet(frameWidth, frameHeight, frameNum, frameNumPerLine));
    }

    /**
     * Constructor
     *
     * @param bitmap      Bitmap to be drawn in FPSTextureView or FPSSurfaceView.
     * @param spriteSheet The SpriteSheet instance to play back. This includes frame dimensions, and frame data.
     */
    public SpriteSheetDrawer(@NonNull Bitmap bitmap, @NonNull SpriteSheet spriteSheet) {
        super(new Paint());
        this.mBitmap = bitmap;
        this.mSpriteSheet = spriteSheet;
        mBitmapRect = new Rect();
        mBounds = new Rect();
    }


    /**
     * Draw a Bitmap in device-specific pixel density.
     *
     * @param context Activity or view context
     * @return this
     */
    public SpriteSheetDrawer dpSize(@NonNull Context context) {
        mDpSize = true;

        mBitmapDpWidth = Util.convertPixelsToDp(mSpriteSheet.frameWidth, context);
        mBitmapDpHeight = Util.convertPixelsToDp(mSpriteSheet.frameHeight, context);
        return this;
    }

    /**
     * If true, the Sprite Animation will loop when it reaches the last frame.
     *
     * @param loop If true, the Sprite Animation will loop when it reaches the last frame.
     * @return this
     */
    public SpriteSheetDrawer spriteLoop(boolean loop) {
        mSpriteSheet.spriteLoop = loop;
        return this;
    }

    /**
     * Set Frequency
     *
     * @param frequency 　The number to be updated in every times of tick
     * @return this
     */
    public SpriteSheetDrawer frequency(int frequency) {
        mFrequency = frequency;
        return this;
    }

    /**
     * It will animation play in the frame number order of the value of this array.
     *
     * @param list Integer List of Frame num.
     * @return this
     */
    public SpriteSheetDrawer customFrameList(List<Integer> list) {
        mSpriteSheet.customFrameList = list;
        return this;
    }

    /**
     * If loopNum is 3, the Sprite Animation will loop 3 times.
     * If loopNum is greater than or equal to 0 , spriteLoop parameter is ignored .
     *
     * @param loopNum Number of SpriteSheet Animation loop
     * @return this
     */
    public SpriteSheetDrawer spriteLoopNum(int loopNum) {
        mSpriteSheet.spriteLoopNum = loopNum;
        return this;
    }

    /**
     * Set function of dispatched when a spriteSheet animation reaches its ends.
     *
     * @param callBack Dispatched when a spriteSheet animation reaches its ends.
     * @return this
     */
    public SpriteSheetDrawer spriteAnimationEndCallBack(AnimCallBack callBack) {
        mSpriteSheet.setSpriteSheetFinishCallback(callBack);
        return this;
    }


    /**
     * The left offset and y offset for this display object's registration point. For example, to make a 100x100px Bitmap scale
     * around its center, you would set regX and regY to 50.
     *
     * @param regX The horizontal rotate registration point in pixels
     * @param regY The vertical rotate registration point in pixels
     * @return this
     */
    public SpriteSheetDrawer scaleRegistration(float regX, float regY) {
        setScaleRegistration(regX, regY);
        return this;
    }

    /**
     * The left offset and y offset for this display object's registration point. For example, to make a 100x100px Bitmap rotate
     * around its center, you would set regX and regY to 50.
     *
     * @param regX The horizontal rotate registration point in pixels
     * @param regY The vertical rotate registration point in pixels
     * @return this
     */
    public SpriteSheetDrawer rotateRegistration(float regX, float regY) {
        setRotateRegistration(regX, regY);
        return this;
    }

    /**
     * indicates whether to start the SpriteAnimation paused.
     *
     * @param pause If true, SpriteAnimation pause.
     */
    public void spritePause(boolean pause) {
        mSpriteSheet.setSpritePause(pause);
    }

    /**
     * Getter SpriteAnimation pause state
     *
     * @return spritePause
     */
    public boolean isSpritePause() {
        return mSpriteSheet.isSpritePause();
    }

    @Override
    protected void draw(Canvas canvas, float x, float y, int alpha) {
        if (mBitmap == null) return;

        if (mBitmap.isRecycled()) {
            mBitmap = null;
            return;
        }

        mPaint.setAlpha(alpha);

        updateSpriteFrame();
        mBitmapRect.set((int) (mSpriteSheet.dx), (int) (mSpriteSheet.dy), (int) (mSpriteSheet.dx + mSpriteSheet.frameWidth), (int) (mSpriteSheet.dy + mSpriteSheet.frameHeight));

        if (mDpSize) {

            mBounds.set(
                    (int) x,
                    (int) y,
                    (int) (x + mBitmapDpWidth),
                    (int) (y + mBitmapDpHeight)
            );
            canvas.drawBitmap(mBitmap, mBitmapRect, mBounds, mPaint);

        } else {

            mBounds.set(
                    (int) x,
                    (int) y,
                    (int) (x + mSpriteSheet.frameWidth),
                    (int) (y + mSpriteSheet.frameHeight)
            );
            canvas.drawBitmap(mBitmap, mBitmapRect, mBounds, mPaint);
        }

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

    @Override
    public float getWidth() {
        if (mDpSize) return mBitmapDpWidth;
        return mSpriteSheet.frameWidth;
    }

    @Override
    public float getHeight() {
        if (mDpSize) return mBitmapDpHeight;
        return mSpriteSheet.frameHeight;
    }
}
