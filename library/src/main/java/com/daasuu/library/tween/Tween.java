package com.daasuu.library.tween;

import android.graphics.Canvas;

import com.daasuu.library.DisplayObject;
import com.daasuu.library.callback.AnimCallBack;
import com.daasuu.library.constant.Constant;
import com.daasuu.library.easing.Ease;
import com.daasuu.library.easing.EaseProvider;
import com.daasuu.library.parameter.AnimParameter;

import java.util.ArrayList;
import java.util.List;

/**
 * Tween BaseClass. It summarizes the common processing required when to Tween animation.
 */
class Tween extends DisplayObject {

    /**
     * If true, the tween will loop when it reaches the end. Can be set via the props param.
     */
    protected boolean mTweenLoop = false;

    /**
     * Setter mTweenLoop
     *
     * @param repeat If true, the tween will loop when it reaches the end.
     */
    protected void setTweenLoop(boolean repeat) {
        mTweenLoop = repeat;
    }

    /**
     * indicates whether to start the tween paused.
     */
    private boolean mTweenPause = false;

    /**
     * Setter mTweenPause
     *
     * @param pause indicates whether to start the tween paused.
     */
    public void tweenPause(boolean pause) {
        mTweenPause = pause;
    }

    /**
     * Getter mTweenPause
     *
     * @return indicates whether to start the tween paused.
     */
    public boolean isTweenPause() {
        return mTweenPause;
    }


    public void kill() {
        mTweenParameterList.clear();
        mAnimParameters.clear();
    }

    /**
     * Shortcut method to quickly set the transform properties on the display object.
     *
     * @param x The horizontal translation (x position) in pixels
     * @param y The vertical translation (y position) in pixels
     */
    protected void setTransform(float x, float y) {
        if (mTweenParameterList.size() > 0) {
            setTo(0, x, y, Ease.NONE);
        } else {
            this.x = x;
            this.y = y;
            AnimParameter animParameter = mAnimParameters.get(0);
            animParameter.x = x;
            animParameter.y = y;
        }
    }

    /**
     * Shortcut method to quickly set the transform properties on the display object.
     *
     * @param x        The horizontal translation (x position) in pixels
     * @param y        The vertical translation (y position) in pixels
     * @param alpha    The alpha (transparency) ,as a percentage of 255.
     * @param scaleX   The horizontal scale, as a percentage of 1
     * @param scaleY   The vertical scale, as a percentage of 1
     * @param rotation The rotation, in degrees
     */
    protected void setTransform(float x, float y, int alpha, float scaleX, float scaleY, float rotation) {
        if (mTweenParameterList.size() > 0) {
            setTo(0, x, y, alpha, scaleX, scaleY, rotation, Ease.NONE);
        } else {
            this.x = x;
            this.y = y;
            this.alpha = alpha;
            this.scaleX = scaleX;
            this.scaleY = scaleY;
            this.rotation = rotation;

            AnimParameter animParameter = mAnimParameters.get(0);
            animParameter.x = x;
            animParameter.y = y;
            animParameter.alpha = alpha;
            animParameter.scaleX = scaleX;
            animParameter.scaleY = scaleY;
            animParameter.rotation = rotation;
        }

    }

    /**
     * The left offset for this display object's registration point. For example, to make a 100x100px Bitmap rotate
     * around its center, you would set mRotateRegistrationX and mRotateRegistrationY to 50.
     */
    protected float mRotateRegistrationX = 0f;

    /**
     * The y offset for this display object's registration point. For example, to make a 100x100px Bitmap rotate
     * around its center, you would set mRotateRegistrationX and mRotateRegistrationY to 50.
     */
    protected float mRotateRegistrationY = 0f;

    /**
     * Setter mRotateRegistrationX and mRotateRegistrationY
     *
     * @param regX The horizontal rotate registration point in pixels
     * @param regY The vertical rotate registration point in pixels
     */
    protected void setRotateRegistration(float regX, float regY) {
        mRotateRegistrationX = regX;
        mRotateRegistrationY = regY;
    }

    /**
     * The left offset for this display object's registration point. For example, to make a 100x100px Bitmap scale
     * around its center, you would set mRotateRegistrationX and mRotateRegistrationY to 50.
     */
    protected float mScaleRegistrationX = 0f;

    /**
     * The y offset for this display object's registration point. For example, to make a 100x100px Bitmap scale
     * around its center, you would set mRotateRegistrationX and mRotateRegistrationY to 50.
     */
    protected float mScaleRegistrationY = 0f;

    /**
     * Setter mScaleRegistrationX and mScaleRegistrationY
     *
     * @param regX The horizontal scale registration point in pixels
     * @param regY The vertical scale registration point in pixels
     */
    protected void setScaleRegistration(float regX, float regY) {
        mScaleRegistrationX = regX;
        mScaleRegistrationY = regY;
    }

    /**
     * Array of parameters required when to Tween animation DisplayObject.
     */
    protected List<AnimParameter> mAnimParameters = new ArrayList<AnimParameter>() {
        {
            add(new AnimParameter(x, y));
        }
    };


    protected long mFps = 1000 / Constant.DEFAULT_FPS;

    /**
     * Indicates the target frame rate in frames per second (FPS). Effectively just a shortcut to `interval`, where
     * `frequency == 1000/interval`.
     *
     * @param fps frequency
     */
    private void setFps(long fps) {
        mFps = 1000 / fps;
    }

    /**
     * Queues a tween from the current values to the target properties. Set duration to 0 to jump to these value.
     * Numeric properties will be tweened from their current value in the tween to the target value.
     *
     * @param animDuration The duration of the tween in milliseconds
     * @param x            The horizontal translation (x position) in pixels
     * @param y            The vertical translation (y position) in pixels
     * @param ease         The easing function to use for this tween.
     */
    protected void setTo(long animDuration, float x, float y, Ease ease) {
        int alpha;
        float scaleX;
        float scaleY;
        float rotation;
        if (mTweenParameterList.size() > 0) {
            TweenParameter beforeTweenParameter = mTweenParameterList.get(mTweenParameterList.size() - 1);
            alpha = beforeTweenParameter.alpha;
            scaleX = beforeTweenParameter.scaleX;
            scaleY = beforeTweenParameter.scaleY;
            rotation = beforeTweenParameter.rotation;
        } else {
            alpha = this.alpha;
            scaleX = this.scaleX;
            scaleY = this.scaleY;
            rotation = this.rotation;
        }
        setTo(animDuration, x, y, alpha, scaleX, scaleY, rotation, ease);
    }


    /**
     * Queues a tween from the current values to the target properties. Set duration to 0 to jump to these value.
     * Numeric properties will be tweened from their current value in the tween to the target value.
     *
     * @param animDuration The duration of the tween in milliseconds
     * @param x            The horizontal translation (x position) in pixels
     * @param y            The vertical translation (y position) in pixels
     * @param alpha        The alpha (transparency) ,as a percentage of 255.
     * @param ease         The easing function to use for this tween.
     */
    protected void setTo(long animDuration, float x, float y, int alpha, Ease ease) {
        float scaleX;
        float scaleY;
        float rotation;
        if (mTweenParameterList.size() > 0) {
            TweenParameter beforeTweenParameter = mTweenParameterList.get(mTweenParameterList.size() - 1);
            scaleX = beforeTweenParameter.scaleX;
            scaleY = beforeTweenParameter.scaleY;
            rotation = beforeTweenParameter.rotation;
        } else {
            scaleX = this.scaleX;
            scaleY = this.scaleY;
            rotation = this.rotation;
        }

        setTo(animDuration, x, y, alpha, scaleX, scaleY, rotation, ease);
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
     */
    protected void setTo(long animDuration, float x, float y, float scaleX, float scaleY, Ease ease) {
        int alpha;
        float rotation;
        if (mTweenParameterList.size() > 0) {
            TweenParameter beforeTweenParameter = mTweenParameterList.get(mTweenParameterList.size() - 1);
            alpha = beforeTweenParameter.alpha;
            rotation = beforeTweenParameter.rotation;
        } else {
            alpha = this.alpha;
            rotation = this.rotation;
        }

        setTo(animDuration, x, y, alpha, scaleX, scaleY, rotation, ease);
    }

    /**
     * Queues a tween from the current values to the target properties. Set duration to 0 to jump to these value.
     * Numeric properties will be tweened from their current value in the tween to the target value.
     *
     * @param animDuration The duration of the tween in milliseconds
     * @param x            The horizontal translation (x position) in pixels
     * @param y            The vertical translation (y position) in pixels
     * @param rotation     The rotation, in degrees
     * @param ease         The easing function to use for this tween.
     */
    protected void setTo(long animDuration, float x, float y, float rotation, Ease ease) {
        int alpha;
        float scaleX;
        float scaleY;
        if (mTweenParameterList.size() > 0) {
            TweenParameter beforeTweenParameter = mTweenParameterList.get(mTweenParameterList.size() - 1);
            alpha = beforeTweenParameter.alpha;
            scaleX = beforeTweenParameter.scaleX;
            scaleY = beforeTweenParameter.scaleY;
        } else {
            alpha = this.alpha;
            scaleX = this.scaleX;
            scaleY = this.scaleY;
        }

        setTo(animDuration, x, y, alpha, scaleX, scaleY, rotation, ease);
    }

    /**
     * Queues a tween from the current values to the target properties. Set duration to 0 to jump to these value.
     * Numeric properties will be tweened from their current value in the tween to the target value.
     *
     * @param animDuration The duration of the tween in milliseconds
     * @param x            The horizontal translation (x position) in pixels
     * @param y            The vertical translation (y position) in pixels
     * @param alpha        The alpha (transparency) ,as a percentage of 255.
     * @param scaleX       The horizontal scale, as a percentage of 1
     * @param scaleY       The vertical scale, as a percentage of 1
     * @param rotation     The rotation, in degrees
     * @param ease         The easing function to use for this tween.
     */
    protected void setTo(long animDuration, float x, float y, int alpha, float scaleX, float scaleY, float rotation, Ease ease) {
        if (mTweenParameterList.size() == 0) {
            mAnimParameters.clear();
        }

        TweenParameter tweenParameter = new TweenParameter(x, y, animDuration);
        tweenParameter.ease = ease;
        tweenParameter.alpha = alpha;
        tweenParameter.scaleX = scaleX;
        tweenParameter.scaleY = scaleY;
        tweenParameter.rotation = rotation;
        mTweenParameterList.add(tweenParameter);
    }


    /**
     * Queues a tween from the current values to the target properties. Set duration to 0 to jump to these value.
     * Numeric properties will be tweened from their current value in the tween to the target value.
     *
     * @param animDuration The duration of the tween in milliseconds
     * @param x            The horizontal translation (x position) in pixels
     * @param ease         The easing function to use for this tween.
     */
    protected void setToX(long animDuration, float x, Ease ease) {
        int alpha;
        float scaleX;
        float scaleY;
        float rotation;
        float y;
        if (mTweenParameterList.size() > 0) {
            TweenParameter beforeTweenParameter = mTweenParameterList.get(mTweenParameterList.size() - 1);
            y = beforeTweenParameter.y;
            alpha = beforeTweenParameter.alpha;
            scaleX = beforeTweenParameter.scaleX;
            scaleY = beforeTweenParameter.scaleY;
            rotation = beforeTweenParameter.rotation;
        } else {
            y = this.y;
            alpha = this.alpha;
            scaleX = this.scaleX;
            scaleY = this.scaleY;
            rotation = this.rotation;
            mAnimParameters.clear();
        }

        TweenParameter tweenParameter = new TweenParameter(x, y, animDuration);
        tweenParameter.ease = ease;
        tweenParameter.alpha = alpha;
        tweenParameter.scaleX = scaleX;
        tweenParameter.scaleY = scaleY;
        tweenParameter.rotation = rotation;
        mTweenParameterList.add(tweenParameter);
    }

    /**
     * Queues a tween from the current values to the target properties. Set duration to 0 to jump to these value.
     * Numeric properties will be tweened from their current value in the tween to the target value.
     *
     * @param animDuration The duration of the tween in milliseconds
     * @param y            The vertical translation (y position) in pixels
     * @param ease         The easing function to use for this tween.
     */
    protected void setToY(long animDuration, float y, Ease ease) {
        int alpha;
        float x;
        float scaleX;
        float scaleY;
        float rotation;
        if (mTweenParameterList.size() > 0) {
            TweenParameter beforeTweenParameter = mTweenParameterList.get(mTweenParameterList.size() - 1);
            x = beforeTweenParameter.x;
            alpha = beforeTweenParameter.alpha;
            scaleX = beforeTweenParameter.scaleX;
            scaleY = beforeTweenParameter.scaleY;
            rotation = beforeTweenParameter.rotation;
        } else {
            x = this.x;
            alpha = this.alpha;
            scaleX = this.scaleX;
            scaleY = this.scaleY;
            rotation = this.rotation;
            mAnimParameters.clear();
        }

        TweenParameter tweenParameter = new TweenParameter(x, y, animDuration);
        tweenParameter.ease = ease;
        tweenParameter.alpha = alpha;
        tweenParameter.scaleX = scaleX;
        tweenParameter.scaleY = scaleY;
        tweenParameter.rotation = rotation;
        mTweenParameterList.add(tweenParameter);
    }

    /**
     * Queues a tween from the current values to the target properties. Set duration to 0 to jump to these value.
     * Numeric properties will be tweened from their current value in the tween to the target value.
     *
     * @param animDuration The duration of the tween in milliseconds
     * @param alpha        The alpha (transparency) ,as a percentage of 255.
     * @param ease         The easing function to use for this tween.
     */
    protected void setAlpha(long animDuration, int alpha, Ease ease) {
        float x;
        float y;
        float scaleX;
        float scaleY;
        float rotation;

        if (mTweenParameterList.size() > 0) {
            TweenParameter beforeTweenParameter = mTweenParameterList.get(mTweenParameterList.size() - 1);
            x = beforeTweenParameter.x;
            y = beforeTweenParameter.y;
            scaleX = beforeTweenParameter.scaleX;
            scaleY = beforeTweenParameter.scaleY;
            rotation = beforeTweenParameter.rotation;
        } else {
            x = this.x;
            y = this.y;
            scaleX = this.scaleX;
            scaleY = this.scaleY;
            rotation = this.rotation;
            mAnimParameters.clear();
        }

        TweenParameter tweenParameter = new TweenParameter(x, y, animDuration);
        tweenParameter.alpha = alpha;
        tweenParameter.ease = ease;
        tweenParameter.scaleX = scaleX;
        tweenParameter.scaleY = scaleY;
        tweenParameter.rotation = rotation;
        mTweenParameterList.add(tweenParameter);
    }

    /**
     * Queues a tween from the current values to the target properties. Set duration to 0 to jump to these value.
     * Numeric properties will be tweened from their current value in the tween to the target value.
     *
     * @param animDuration The duration of the tween in milliseconds
     * @param scaleX       The horizontal scale, as a percentage of 1
     * @param scaleY       The vertical scale, as a percentage of 1
     * @param ease         The easing function to use for this tween.
     */
    protected void setScale(long animDuration, float scaleX, float scaleY, Ease ease) {
        float x;
        float y;
        int alpha;
        float rotation;

        if (mTweenParameterList.size() > 0) {
            TweenParameter beforeTweenParameter = mTweenParameterList.get(mTweenParameterList.size() - 1);
            x = beforeTweenParameter.x;
            y = beforeTweenParameter.y;
            alpha = beforeTweenParameter.alpha;
            rotation = beforeTweenParameter.rotation;
        } else {
            x = this.x;
            y = this.y;
            alpha = this.alpha;
            rotation = this.rotation;
            mAnimParameters.clear();
        }

        TweenParameter tweenParameter = new TweenParameter(x, y, animDuration);
        tweenParameter.ease = ease;
        tweenParameter.alpha = alpha;
        tweenParameter.scaleX = scaleX;
        tweenParameter.scaleY = scaleY;
        tweenParameter.rotation = rotation;
        mTweenParameterList.add(tweenParameter);
    }

    /**
     * Queues a tween from the current values to the target properties. Set duration to 0 to jump to these value.
     * Numeric properties will be tweened from their current value in the tween to the target value.
     *
     * @param animDuration The duration of the tween in milliseconds
     * @param rotation     The rotation, in degrees
     * @param ease         The easing function to use for this tween.
     */
    protected void setRotation(long animDuration, float rotation, Ease ease) {
        float x;
        float y;
        int alpha;
        float scaleX;
        float scaleY;

        if (mTweenParameterList.size() > 0) {
            TweenParameter beforeTweenParameter = mTweenParameterList.get(mTweenParameterList.size() - 1);
            x = beforeTweenParameter.x;
            y = beforeTweenParameter.y;
            alpha = beforeTweenParameter.alpha;
            scaleX = beforeTweenParameter.scaleX;
            scaleY = beforeTweenParameter.scaleY;
        } else {
            x = this.x;
            y = this.y;
            alpha = this.alpha;
            scaleX = this.scaleX;
            scaleY = this.scaleY;
            mAnimParameters.clear();
        }

        TweenParameter tweenParameter = new TweenParameter(x, y, animDuration);
        tweenParameter.ease = ease;
        tweenParameter.alpha = alpha;
        tweenParameter.scaleX = scaleX;
        tweenParameter.scaleY = scaleY;
        tweenParameter.rotation = rotation;
        mTweenParameterList.add(tweenParameter);

    }

    /**
     * Queues a wait (essentially an empty tween).
     *
     * @param animDuration The duration of the wait in milliseconds
     */
    protected void setWaitTime(long animDuration) {
        float x;
        float y;
        int alpha;
        float scaleX;
        float scaleY;
        float rotation;

        if (mTweenParameterList.size() > 0) {
            TweenParameter beforeTweenParameter = mTweenParameterList.get(mTweenParameterList.size() - 1);
            x = beforeTweenParameter.x;
            y = beforeTweenParameter.y;
            alpha = beforeTweenParameter.alpha;
            scaleX = beforeTweenParameter.scaleX;
            scaleY = beforeTweenParameter.scaleY;
            rotation = beforeTweenParameter.rotation;
        } else {
            x = this.x;
            y = this.y;
            alpha = this.alpha;
            scaleX = this.scaleX;
            scaleY = this.scaleY;
            rotation = this.rotation;
            mAnimParameters.clear();
        }

        TweenParameter tweenParameter = new TweenParameter(x, y, animDuration);
        tweenParameter.alpha = alpha;
        tweenParameter.scaleX = scaleX;
        tweenParameter.scaleY = scaleY;
        tweenParameter.rotation = rotation;
        mTweenParameterList.add(tweenParameter);
    }

    /**
     * Set queues an action to call the specified function.
     *
     * @param animCallBack callback The function to call.
     */
    protected void setAnimCallBack(AnimCallBack animCallBack) {

        int listSize = mTweenParameterList.size();
        if (listSize > 0) {
            mTweenParameterList.get(listSize - 1).animCallBack = animCallBack;
        } else {
            mAnimParameters.get(0).animCallBack = animCallBack;
        }
    }

    protected List<TweenParameter> mTweenParameterList = new ArrayList<>();

    /**
     * Arrangement for generating AnimPrameter
     */
    protected class TweenParameter extends AnimParameter {
        public long animDuration;
        public Ease ease = Ease.NONE;

        public TweenParameter(float x, float y, long animDuration) {
            super(x, y);
            this.animDuration = animDuration;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        // do nothing
    }

    private int mDrawCount = -1;

    protected AnimParameter getDrawAnimParameter() {
        if (mAnimParameters.size() == 0) return null;

        if (mTweenPause) {
            if (mDrawCount == -1) mDrawCount = 0;

            return mAnimParameters.get(mDrawCount);
        }

        mDrawCount++;

        if (mAnimParameters.size() <= mDrawCount) {
            if (mTweenLoop) {
                mDrawCount = 0;
            } else {
                mDrawCount = mAnimParameters.size();
                return mAnimParameters.get(mDrawCount - 1);
            }
        }

        return mAnimParameters.get(mDrawCount);
    }

    /**
     * Queues an action to call the specified function.
     *
     * @param animParameter It might have been holding the call the specified function
     */
    protected void runAnimCallBack(AnimParameter animParameter) {
        if (animParameter.animCallBack != null) {
            animParameter.animCallBack.call();
            if (!mTweenLoop) animParameter.animCallBack = null;
        }
    }

    @Override
    public void setUp(long fps) {
        setFps(fps);
        // FPS, generates a AnimParameter from the arrival point , per 1TweenParameter, to determine how many create a mAnimParameters.
        for (int i = 0, n = mTweenParameterList.size(); i < n; i++) {
            setUpAnimParamList(mTweenParameterList.get(i), i);
        }
    }

    /**
     * It generates a AnimParam sequences required in Tween.
     *
     * @param tweenParameter Arrangement for generating AnimPrameter
     * @param position       The number of times that Queues a tween was set.
     */
    private void setUpAnimParamList(TweenParameter tweenParameter, int position) {

        long animParamNum = tweenParameter.animDuration / mFps;
        if (animParamNum < 1) {
            animParamNum = 1;
        }

        float cntX;
        float cntY;
        int cntAlpha;
        float cntRotation;
        float cntScaleX;
        float cntScaleY;

        float beforeParameterX = x;
        float beforeParameterY = y;
        int beforeParameterAlpha = alpha;
        float beforeParameterRotation = rotation;
        float beforeParameterScaleX = scaleX;
        float beforeParameterScaleY = scaleY;

        if (position == 0) {

            cntX = (tweenParameter.x - x) / animParamNum;
            cntY = (tweenParameter.y - y) / animParamNum;
            cntAlpha = (int) ((tweenParameter.alpha - alpha) / animParamNum);
            cntRotation = (tweenParameter.rotation - rotation) / animParamNum;
            cntScaleX = (tweenParameter.scaleX - scaleX) / animParamNum;
            cntScaleY = (tweenParameter.scaleY - scaleY) / animParamNum;

        } else {
            TweenParameter beforeTweenParameter = mTweenParameterList.get(position - 1);
            cntX = (tweenParameter.x - beforeTweenParameter.x) / animParamNum;
            cntY = (tweenParameter.y - beforeTweenParameter.y) / animParamNum;
            cntAlpha = (int) ((tweenParameter.alpha - beforeTweenParameter.alpha) / animParamNum);
            cntRotation = (tweenParameter.rotation - beforeTweenParameter.rotation) / animParamNum;
            cntScaleX = (tweenParameter.scaleX - beforeTweenParameter.scaleX) / animParamNum;
            cntScaleY = (tweenParameter.scaleY - beforeTweenParameter.scaleY) / animParamNum;

            beforeParameterX = beforeTweenParameter.x;
            beforeParameterY = beforeTweenParameter.y;
            beforeParameterAlpha = beforeTweenParameter.alpha;
            beforeParameterRotation = beforeTweenParameter.rotation;
            beforeParameterScaleX = beforeTweenParameter.scaleX;
            beforeParameterScaleY = beforeTweenParameter.scaleY;
        }

        for (int i = 0; i <= animParamNum; i++) {
            float elapsedTimeRate = (float) i / (float) animParamNum;
            float valueChangeRate = EaseProvider.get(tweenParameter.ease, elapsedTimeRate);

            AnimParameter animParameter = new AnimParameter(
                    beforeParameterX + cntX * i * valueChangeRate,
                    beforeParameterY + cntY * i * valueChangeRate
            )
                    .setAlpha((int) (beforeParameterAlpha + cntAlpha * i * valueChangeRate))
                    .setRotation(beforeParameterRotation + cntRotation * i * valueChangeRate)
                    .setScaleX(beforeParameterScaleX + cntScaleX * i * valueChangeRate)
                    .setScaleY(beforeParameterScaleY + cntScaleY * i * valueChangeRate);


            if (i == animParamNum) {
                animParameter.animCallBack = tweenParameter.animCallBack;
            }
            mAnimParameters.add(animParameter);
        }

    }

}
