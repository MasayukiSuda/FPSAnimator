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
 * A Tween class tweens properties for a single SpriteSheet.
 */
public class TweenSpriteSheet extends Tween implements SpritePause {

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
     * By default Sprite instances advance one frame per tick.
     * Specifying a frequency for the Sprite (or its related SpriteSheet) will cause it to
     * advance based on elapsed time between ticks as appropriate to maintain the target frequency.
     * For example, if a Sprite with a frequency of 10 is placed on a Stage being updated at 40fps,
     * then the Sprite will advance roughly one frame every 4 ticks. This will not be exact,
     * because the time between each tick will vary slightly between frames.
     * This feature is dependent on the tick event object being passed into update.
     */
    private int mFrequency = 1;

    /**
     * The SpriteSheet instance to play back. This includes frame dimensions, and frame data.
     */
    private SpriteSheet mSpriteSheet;

    /**
     * member variables needed to skip a tick in mFrequency
     */
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
    public TweenSpriteSheet(@NonNull Bitmap bitmap, float frameWidth, float frameHeight, int frameNum, int frameNumPerLine) {
        this.mBitmap = bitmap;
        this.mSpriteSheet = new SpriteSheet(frameWidth, frameHeight, frameNum, frameNumPerLine);
    }

    /**
     * Constructor
     *
     * @param bitmap      Bitmap to be drawn in FPSTextureView or FPSSurfaceView.
     * @param spriteSheet The SpriteSheet instance to play back. This includes frame dimensions, and frame data.
     */
    public TweenSpriteSheet(@NonNull Bitmap bitmap, @NonNull SpriteSheet spriteSheet) {
        this.mBitmap = bitmap;
        this.mSpriteSheet = spriteSheet;
    }

    /**
     * Draw a Bitmap in device-specific pixel density.
     *
     * @param context Activity or view context
     * @return this
     */
    public TweenSpriteSheet dpSize(@NonNull Context context) {
        mDpSize = true;

        mSpriteSheet.frameWidth = Util.convertPixelsToDp(mSpriteSheet.frameWidth, context);
        mSpriteSheet.frameHeight = Util.convertPixelsToDp(mSpriteSheet.frameHeight, context);

        mBitmapDpWidth = Util.convertPixelsToDp(mBitmap.getWidth(), context);
        mBitmapDpHeight = Util.convertPixelsToDp(mBitmap.getHeight(), context);
        mBitmapRect = new Rect(0, 0, mBitmap.getWidth(), mBitmap.getHeight());
        return this;
    }

    /**
     * Set Frequency
     *
     * @param frequency 　The number to be updated in every times of tick
     * @return this
     */
    public TweenSpriteSheet frequency(int frequency) {
        mFrequency = frequency;
        return this;
    }

    /**
     * If true, the Sprite Animation will loop when it reaches the last frame.
     *
     * @param loop If true, the Sprite Animation will loop when it reaches the last frame.
     * @return this
     */
    public TweenSpriteSheet spriteLoop(boolean loop) {
        mSpriteSheet.spriteLoop = loop;
        return this;
    }

    /**
     * Set the tween loop. If true, the tween will loop when it reaches the end.
     *
     * @param loop If true, the tween will loop when it reaches the end.
     * @return this
     */
    public TweenSpriteSheet loop(boolean loop) {
        setTweenLoop(loop);
        return this;
    }

    /**
     * Shortcut method to quickly set the transform properties on the display object.
     *
     * @param x The horizontal translation (x position) in pixels
     * @param y The vertical translation (y position) in pixels
     * @return this
     */
    public TweenSpriteSheet transform(float x, float y) {
        setTransform(x, y);
        return this;
    }

    /**
     * Shortcut method to quickly set the transform properties on the display object.
     *
     * @param x        The horizontal translation (x position) in pixels
     * @param y        The vertical translation (y position) in pixels
     * @param alpha    The alpha (transparency) ,as a percentage of 1.
     * @param scaleX   The horizontal scale, as a percentage of 1
     * @param scaleY   The vertical scale, as a percentage of 1
     * @param rotation The rotation, in degrees
     * @return this
     */
    public TweenSpriteSheet transform(float x, float y, float alpha, float scaleX, float scaleY, float rotation) {
        setTransform(x, y, Util.convertAlphaFloatToInt(alpha), scaleX, scaleY, rotation);
        return this;
    }

    /**
     * Queues a tween from the current values to the target properties. Set duration to 0 to jump to these value.
     * Numeric properties will be tweened from their current value in the tween to the target value.
     *
     * @param animDuration The duration of the tween in milliseconds
     * @param x            The horizontal translation (x position) in pixels
     * @param y            The vertical translation (y position) in pixels
     * @return this
     */
    public TweenSpriteSheet to(long animDuration, float x, float y) {
        to(animDuration, x, y, Ease.LINEAR);
        return this;
    }

    /**
     * Queues a tween from the current values to the target properties. Set duration to 0 to jump to these value.
     * Numeric properties will be tweened from their current value in the tween to the target value.
     *
     * @param animDuration The duration of the tween in milliseconds
     * @param x            The horizontal translation (x position) in pixels
     * @param y            The vertical translation (y position) in pixels
     * @param ease         The easing function to use for this tween.
     * @return this
     */
    public TweenSpriteSheet to(long animDuration, float x, float y, Ease ease) {
        setTo(animDuration, x, y, ease);
        return this;
    }

    /**
     * Queues a tween from the current values to the target properties. Set duration to 0 to jump to these value.
     * Numeric properties will be tweened from their current value in the tween to the target value.
     *
     * @param animDuration The duration of the tween in milliseconds
     * @param x            The horizontal translation (x position) in pixels
     * @param y            The vertical translation (y position) in pixels
     * @param alpha        The alpha (transparency) ,as a percentage of 1.
     * @param ease         The easing function to use for this tween.
     * @return this
     */
    public TweenSpriteSheet to(long animDuration, float x, float y, float alpha, Ease ease) {
        setTo(animDuration, x, y, Util.convertAlphaFloatToInt(alpha), ease);
        return this;
    }

    /**
     * Queues a tween from the current values to the target properties. Set duration to 0 to jump to these value.
     * Numeric properties will be tweened from their current value in the tween to the target value.
     *
     * @param animDuration The duration of the tween in milliseconds
     * @param x            The horizontal translation (x position) in pixels
     * @param y            The vertical translation (y position) in pixels
     * @param scaleX       The horizontal scale, as a percentage of 1
     * @param scaleY       The vertical scale, as a percentage of 1
     * @param ease         The easing function to use for this tween.
     * @return this
     */
    public TweenSpriteSheet to(long animDuration, float x, float y, float scaleX, float scaleY, Ease ease) {
        setTo(animDuration, x, y, scaleX, scaleY, ease);
        return this;
    }

    /**
     * Queues a tween from the current values to the target properties. Set duration to 0 to jump to these value.
     * Numeric properties will be tweened from their current value in the tween to the target value.
     *
     * @param animDuration The duration of the tween in milliseconds
     * @param x            The horizontal translation (x position) in pixels
     * @param y            The vertical translation (y position) in pixels
     * @param ease         The easing function to use for this tween.
     * @param rotation     The rotation, in degrees
     * @return this
     */
    public TweenSpriteSheet to(long animDuration, float x, float y, Ease ease, float rotation) {
        setTo(animDuration, x, y, rotation, ease);
        return this;
    }

    /**
     * Queues a tween from the current values to the target properties. Set duration to 0 to jump to these value.
     * Numeric properties will be tweened from their current value in the tween to the target value.
     *
     * @param animDuration The duration of the tween in milliseconds
     * @param x            The horizontal translation (x position) in pixels
     * @param y            The vertical translation (y position) in pixels
     * @param alpha        The alpha (transparency) ,as a percentage of 1.
     * @param scaleX       The horizontal scale, as a percentage of 1
     * @param scaleY       The vertical scale, as a percentage of 1
     * @param rotation     The rotation, in degrees
     * @param ease         The easing function to use for this tween.
     * @return this
     */
    public TweenSpriteSheet to(long animDuration, float x, float y, float alpha, float scaleX, float scaleY, float rotation, Ease ease) {
        setTo(animDuration, x, y, Util.convertAlphaFloatToInt(alpha), scaleX, scaleY, rotation, ease);
        return this;
    }

    /**
     * Queues a tween from the current values to the target properties. Set duration to 0 to jump to these value.
     * Numeric properties will be tweened from their current value in the tween to the target value.
     *
     * @param animDuration The duration of the tween in milliseconds
     * @param x            The horizontal translation (x position) in pixels
     * @return this
     */
    public TweenSpriteSheet toX(long animDuration, float x) {
        toX(animDuration, x, Ease.LINEAR);
        return this;
    }

    /**
     * Queues a tween from the current values to the target properties. Set duration to 0 to jump to these value.
     * Numeric properties will be tweened from their current value in the tween to the target value.
     *
     * @param animDuration The duration of the tween in milliseconds
     * @param x            The horizontal translation (x position) in pixels
     * @param ease         The easing function to use for this tween.
     * @return this
     */
    public TweenSpriteSheet toX(long animDuration, float x, Ease ease) {
        setToX(animDuration, x, ease);
        return this;
    }

    /**
     * Queues a tween from the current values to the target properties. Set duration to 0 to jump to these value.
     * Numeric properties will be tweened from their current value in the tween to the target value.
     *
     * @param animDuration The duration of the tween in milliseconds
     * @param y            The vertical translation (y position) in pixels
     * @return this
     */
    public TweenSpriteSheet toY(long animDuration, float y) {
        toY(animDuration, y, Ease.LINEAR);
        return this;
    }

    /**
     * Queues a tween from the current values to the target properties. Set duration to 0 to jump to these value.
     * Numeric properties will be tweened from their current value in the tween to the target value.
     *
     * @param animDuration The duration of the tween in milliseconds
     * @param y            The vertical translation (y position) in pixels
     * @param ease         The easing function to use for this tween.
     * @return this
     */
    public TweenSpriteSheet toY(long animDuration, float y, Ease ease) {
        setToY(animDuration, y, ease);
        return this;
    }

    /**
     * Queues a tween from the current values to the target properties. Set duration to 0 to jump to these value.
     * Numeric properties will be tweened from their current value in the tween to the target value.
     *
     * @param animDuration The duration of the tween in milliseconds
     * @param alpha        The alpha (transparency) ,as a percentage of 1.
     * @return this
     */
    public TweenSpriteSheet alpha(long animDuration, float alpha) {
        setAlpha(animDuration, Util.convertAlphaFloatToInt(alpha), Ease.LINEAR);
        return this;
    }

    /**
     * Queues a tween from the current values to the target properties. Set duration to 0 to jump to these value.
     * Numeric properties will be tweened from their current value in the tween to the target value.
     *
     * @param animDuration The duration of the tween in milliseconds
     * @param alpha        The alpha (transparency) ,as a percentage of 1.
     * @param ease         The easing function to use for this tween.
     * @return this
     */
    public TweenSpriteSheet alpha(long animDuration, float alpha, Ease ease) {
        setAlpha(animDuration, Util.convertAlphaFloatToInt(alpha), ease);
        return this;
    }

    /**
     * Queues a tween from the current values to the target properties. Set duration to 0 to jump to these value.
     * Numeric properties will be tweened from their current value in the tween to the target value.
     *
     * @param animDuration The duration of the tween in milliseconds
     * @param scaleX       The horizontal scale, as a percentage of 1
     * @param scaleY       The vertical scale, as a percentage of 1
     * @return this
     */
    public TweenSpriteSheet scale(long animDuration, float scaleX, float scaleY) {
        setScale(animDuration, scaleX, scaleY, Ease.LINEAR);
        return this;
    }

    /**
     * Queues a tween from the current values to the target properties. Set duration to 0 to jump to these value.
     * Numeric properties will be tweened from their current value in the tween to the target value.
     *
     * @param animDuration The duration of the tween in milliseconds
     * @param scaleX       The horizontal scale, as a percentage of 1
     * @param scaleY       The vertical scale, as a percentage of 1
     * @param ease         The easing function to use for this tween.
     * @return this
     */
    public TweenSpriteSheet scale(long animDuration, float scaleX, float scaleY, Ease ease) {
        setScale(animDuration, scaleX, scaleY, ease);
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
    public TweenSpriteSheet scaleRegistration(float regX, float regY) {
        setScaleRegistration(regX, regY);
        return this;
    }

    /**
     * Queues a tween from the current values to the target properties. Set duration to 0 to jump to these value.
     * Numeric properties will be tweened from their current value in the tween to the target value.
     *
     * @param animDuration The duration of the tween in milliseconds
     * @param rotation     The rotation, in degrees
     * @return this
     */
    public TweenSpriteSheet rotation(long animDuration, float rotation) {
        setRotation(animDuration, rotation, Ease.LINEAR);
        return this;
    }

    /**
     * Queues a tween from the current values to the target properties. Set duration to 0 to jump to these value.
     * Numeric properties will be tweened from their current value in the tween to the target value.
     *
     * @param animDuration The duration of the tween in milliseconds
     * @param rotation     The rotation, in degrees
     * @param ease         The easing function to use for this tween.
     * @return this
     */
    public TweenSpriteSheet rotation(long animDuration, float rotation, Ease ease) {
        setRotation(animDuration, rotation, ease);
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
    public TweenSpriteSheet rotateRegistration(float regX, float regY) {
        setRotateRegistration(regX, regY);
        return this;
    }

    /**
     * Queues a wait (essentially an empty tween).
     *
     * @param animDuration The duration of the wait in milliseconds
     * @return this
     */
    public TweenSpriteSheet waitTime(long animDuration) {
        setWaitTime(animDuration);
        return this;
    }

    /**
     * Set queues an action to call the specified function.
     *
     * @param callBack callback The function to call.
     * @return this
     */
    public TweenSpriteSheet call(AnimCallBack callBack) {
        setAnimCallBack(callBack);
        return this;
    }

    /**
     * Set function of dispatched when an animation reaches its ends.
     *
     * @param callBack Dispatched when an animation reaches its ends.
     * @return this
     */
    public TweenSpriteSheet spriteAnimationEndCallBack(AnimCallBack callBack) {
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
     * Draws the display object into the specified context ignoring its visible, alpha, shadow, and transform.
     *
     * @param canvas This Canvas acquired by lookCanvas in FPSTextureView or FPSSurfaceView.
     */
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
        updateSpriteFrame();

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