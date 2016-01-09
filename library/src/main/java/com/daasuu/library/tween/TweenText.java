package com.daasuu.library.tween;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;

import com.daasuu.library.callback.AnimCallBack;
import com.daasuu.library.easing.Ease;
import com.daasuu.library.parameter.AnimParameter;
import com.daasuu.library.util.Util;

/**
 * A Tween class tweens properties for a single String.
 */
public class TweenText extends Tween {

    /**
     * String text to be drawn in FPSTextureView or FPSSurfaceView.
     */
    private String mText;

    /**
     * Adjustment values ​​for drawing the canvas coordinates ( 0, 0 )
     */
    private float mAdjustTextMesureY = 0f;

    /**
     * Constructor
     *
     * @param text  String text to be drawn in FPSTextureView or FPSSurfaceView.
     * @param paint Style and color and typeface .. etc information
     */
    public TweenText(@NonNull String text, Paint paint) {
        this.mText = text;
        this.paint = paint;
        if (this.paint != null) mAdjustTextMesureY = this.paint.getTextSize();
    }

    /**
     * Shortcut method to quickly set the transform properties on the display object.
     *
     * @param x The horizontal translation (x position) in pixels
     * @param y The vertical translation (y position) in pixels
     * @return this
     */
    public TweenText transform(float x, float y) {
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
    public TweenText transform(float x, float y, float alpha, float scaleX, float scaleY, float rotation) {
        setTransform(x, y, Util.convertAlphaFloatToInt(alpha), scaleX, scaleY, rotation);
        return this;
    }

    /**
     * Set the tween loop. If true, the tween will loop when it reaches the end.
     *
     * @param loop If true, the tween will loop when it reaches the end.
     * @return this
     */
    public TweenText loop(boolean loop) {
        setTweenLoop(loop);
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
    public TweenText to(long animDuration, float x, float y) {
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
    public TweenText to(long animDuration, float x, float y, Ease ease) {
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
    public TweenText to(long animDuration, float x, float y, float alpha, Ease ease) {
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
    public TweenText to(long animDuration, float x, float y, float scaleX, float scaleY, Ease ease) {
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
    public TweenText to(long animDuration, float x, float y, Ease ease, float rotation) {
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
    public TweenText to(long animDuration, float x, float y, float alpha, float scaleX, float scaleY, float rotation, Ease ease) {
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
    public TweenText toX(long animDuration, float x) {
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
    public TweenText toX(long animDuration, float x, Ease ease) {
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
    public TweenText toY(long animDuration, float y) {
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
    public TweenText toY(long animDuration, float y, Ease ease) {
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
    public TweenText alpha(long animDuration, float alpha) {
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
    public TweenText alpha(long animDuration, float alpha, Ease ease) {
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
    public TweenText scale(long animDuration, float scaleX, float scaleY) {
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
    public TweenText scale(long animDuration, float scaleX, float scaleY, Ease ease) {
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
    public TweenText scaleRegistration(float regX, float regY) {
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
    public TweenText rotation(long animDuration, float rotation) {
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
    public TweenText rotation(long animDuration, float rotation, Ease ease) {
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
    public TweenText rotateRegistration(float regX, float regY) {
        setRotateRegistration(regX, regY);
        return this;
    }

    /**
     * Queues a wait (essentially an empty tween).
     *
     * @param animDuration The duration of the wait in milliseconds
     * @return this
     */
    public TweenText waitTime(long animDuration) {
        setWaitTime(animDuration);
        return this;
    }

    /**
     * Set queues an action to call the specified function.
     *
     * @param callBack callback The function to call.
     * @return this
     */
    public TweenText call(AnimCallBack callBack) {
        setAnimCallBack(callBack);
        return this;
    }

    /**
     * Draws the display object into the specified context ignoring its visible, alpha, shadow, and transform.
     *
     * @param canvas This Canvas acquired by lookCanvas in FPSTextureView or FPSSurfaceView.
     */
    @Override
    public void draw(Canvas canvas) {
        if (mText == null || mText.length() == 0 || mAnimParameters.size() == 0) return;

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

        x = animParameter.x;
        y = animParameter.y + mAdjustTextMesureY;
        canvas.drawText(mText, x, y, paint);

        canvas.restore();

        runAnimCallBack(animParameter);

    }

}
