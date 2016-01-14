package com.daasuu.library.painter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.NonNull;

import com.daasuu.library.Painter;
import com.daasuu.library.constant.Constant;
import com.daasuu.library.spritesheet.SpriteSheet;
import com.daasuu.library.util.Util;

/**
 * Created by a13587 on 16/01/14.
 */
public class SpriteSheetPainter implements Painter {
    private static final String TAG = SpriteSheetPainter.class.getSimpleName();

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

    private int mFrequency = Constant.DEFAULT_FREQUENCY;

    private final Paint mPaint;


    /**
     * Constructor
     *
     * @param bitmap      Bitmap to be drawn in FPSTextureView or FPSSurfaceView.
     * @param frameWidth  The number of width of each frame
     * @param frameHeight The number of height of each frame
     * @param frameNum    the total number of frames in the specified animation
     */
    public SpriteSheetPainter(@NonNull Bitmap bitmap, float frameWidth, float frameHeight, int frameNum) {
        this(bitmap, frameWidth, frameHeight, frameNum, (int) (bitmap.getWidth() / frameWidth));
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
    public SpriteSheetPainter(@NonNull Bitmap bitmap, float frameWidth, float frameHeight, int frameNum, int frameNumPerLine) {
        this(bitmap, new SpriteSheet(frameWidth, frameHeight, frameNum, frameNumPerLine));
    }

    /**
     * Constructor
     *
     * @param bitmap      Bitmap to be drawn in FPSTextureView or FPSSurfaceView.
     * @param spriteSheet The SpriteSheet instance to play back. This includes frame dimensions, and frame data.
     */
    public SpriteSheetPainter(@NonNull Bitmap bitmap, @NonNull SpriteSheet spriteSheet) {
        this.mBitmap = bitmap;
        this.mPaint = new Paint();
        this.mSpriteSheet = spriteSheet;
    }


    /**
     * Draw a Bitmap in device-specific pixel density.
     *
     * @param context Activity or view context
     * @return this
     */
    public SpriteSheetPainter dpSize(@NonNull Context context) {
        mDpSize = true;

        mSpriteSheet.frameWidth = Util.convertPixelsToDp(mSpriteSheet.frameWidth, context);
        mSpriteSheet.frameHeight = Util.convertPixelsToDp(mSpriteSheet.frameHeight, context);

        mBitmapDpWidth = Util.convertPixelsToDp(mBitmap.getWidth(), context);
        mBitmapDpHeight = Util.convertPixelsToDp(mBitmap.getHeight(), context);
        mBitmapRect = new Rect(0, 0, mBitmap.getWidth(), mBitmap.getHeight());
        return this;
    }

    /**
     * If true, the Sprite Animation will loop when it reaches the last frame.
     *
     * @param loop If true, the Sprite Animation will loop when it reaches the last frame.
     * @return this
     */
    public SpriteSheetPainter spriteLoop(boolean loop) {
        mSpriteSheet.spriteLoop = loop;
        return this;
    }

    @Override
    public void draw(Canvas canvas, float x, float y) {
        if (mBitmap == null) {
            return;
        }

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
            canvas.drawBitmap(mBitmap, mBitmapRect, dpSizeRect, mPaint);
        } else {
            canvas.drawBitmap(mBitmap, x + mSpriteSheet.dx, y + mSpriteSheet.dy, mPaint);
        }
        canvas.restore();
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
        return mSpriteSheet.frameWidth;
    }

    @Override
    public float getHeight() {
        return mSpriteSheet.frameHeight;
    }
}
