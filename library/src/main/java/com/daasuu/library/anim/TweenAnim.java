package com.daasuu.library.anim;

import android.graphics.Canvas;

import com.daasuu.library.Anim;
import com.daasuu.library.AnimParameter;
import com.daasuu.library.callback.AnimCallBack;
import com.daasuu.library.constant.Constant;
import com.daasuu.library.easing.Ease;
import com.daasuu.library.easing.EaseProvider;
import com.daasuu.library.util.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 */
public class TweenAnim implements Anim {
    private static final String TAG = TweenAnim.class.getSimpleName();

    /**
     * If true, the tween will loop when it reaches the end. Can be set via the props param.
     */
    private boolean mTweenLoop = false;

    /**
     * indicates whether to start the tween paused.
     */
    private boolean mTweenPause = false;

    private AnimParameter mInitialParam;

    protected long mFps = 1000 / Constant.DEFAULT_FPS;

    private int mDrawCount = -1;

    /**
     * Array of parameters required when to Tween animation DisplayObject.
     * TODO 初期化
     */
    protected final List<AnimParameter> mAnimParameters;

    protected final List<TweenParameter> mTweenParameters;

    private final Map<AnimParameter, AnimCallBack> mCallbacks;

    public static Builder builder() {
        return new Builder();
    }

    public TweenAnim(boolean tweenLoop, AnimParameter initialParam, List<TweenParameter> tweenParameters) {
        this.mTweenLoop = tweenLoop;
        this.mInitialParam = initialParam;
        mTweenParameters = tweenParameters;
        mAnimParameters = new ArrayList<>();
        mCallbacks = new HashMap<>();
    }

    @Override
    public void updateAnimParam(AnimParameter parameter) {
        AnimParameter nextParam = getNextAnimParameter();

        // update parameters
        parameter.updatePosition(nextParam.x(), nextParam.y());
        parameter.updateAlpha(nextParam.alpha());
        parameter.updateRotation(nextParam.rotation());
        parameter.updateScale(nextParam.scaleX(), nextParam.scaleY());

        // run callback. delete callback if tween animation will not be loop.
        AnimCallBack callBack;
        synchronized (this) {
            callBack = mCallbacks.get(nextParam);
            if (!mTweenLoop) {
                mCallbacks.remove(nextParam);
            }
        }
        if (callBack != null) {
            callBack.call();
        }
    }

    @Override
    public void setBaseLine(Canvas canvas, float width, float height) {
        // Do nothing
    }

    @Override
    public AnimParameter getInitialPosition() {
        return mInitialParam;
    }

    @Override
    public void setUp(long fps) {
        mFps = 1000 / fps;
        mAnimParameters.clear();
        mCallbacks.clear();
        for (int i = 0; i < mTweenParameters.size(); i++) {
            TweenParameter tp = mTweenParameters.get(i);
            // set anim parameter
            mAnimParameters.addAll(createAnimParamList(tp, i));
            // set callback
            if (tp.callBack != null) {
                mCallbacks.put(mAnimParameters.get(mAnimParameters.size() - 1), tp.callBack);
            }
        }
    }

    protected AnimParameter getNextAnimParameter() {
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
     * It generates a AnimParam sequences required in Tween.
     *
     * @param tweenParameter Arrangement for generating AnimPrameter
     * @param position       The number of times that Queues a tween was set.
     */
    private List<AnimParameter> createAnimParamList(TweenParameter tweenParameter, int position) {

        List<AnimParameter> params = new ArrayList<>();

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

        AnimParameter beforeParam = position == 0 ?
                mInitialParam :
                mTweenParameters.get(position - 1);

        cntX = (tweenParameter.x() - beforeParam.x()) / animParamNum;
        cntY = (tweenParameter.y() - beforeParam.y()) / animParamNum;
        cntAlpha = (int) ((tweenParameter.alpha() - beforeParam.alpha()) / animParamNum);
        cntRotation = (tweenParameter.rotation() - beforeParam.rotation()) / animParamNum;
        cntScaleX = (tweenParameter.scaleX() - beforeParam.scaleX()) / animParamNum;
        cntScaleY = (tweenParameter.scaleY() - beforeParam.scaleY()) / animParamNum;

        for (int i = 0; i <= animParamNum; i++) {
            float elapsedTimeRate = (float) i / (float) animParamNum;
            float valueChangeRate = EaseProvider.get(tweenParameter.ease, elapsedTimeRate);

            AnimParameter animParameter = new AnimParameter(
                    beforeParam.x() + cntX * i * valueChangeRate,
                    beforeParam.y() + cntY * i * valueChangeRate,
                    (int) (beforeParam.alpha() + cntAlpha * i * valueChangeRate),
                    beforeParam.scaleX() + cntScaleX * i * valueChangeRate,
                    beforeParam.scaleY() + cntScaleY * i * valueChangeRate,
                    beforeParam.rotation() + cntRotation * i * valueChangeRate
            );

            params.add(animParameter);
        }

        return params;
    }

    /**
     * Arrangement for generating AnimPrameter
     */
    private static class TweenParameter extends AnimParameter {
        private long animDuration;
        private Ease ease = Ease.NONE;
        private AnimCallBack callBack;

        private TweenParameter(float x, float y, long animDuration) {
            super(x, y);
            this.animDuration = animDuration;
        }

        public TweenParameter(float x, float y, int alpha, float scaleX, float scaleY, float rotation, long animDuration, Ease ease) {
            super(x, y, alpha, scaleX, scaleY, rotation);
            this.animDuration = animDuration;
            this.ease = ease;
        }

        @Override
        public String toString() {
            return "TweenParameter{" +
                    "animDuration=" + animDuration +
                    ", ease=" + ease +
                    "} " + super.toString();
        }
    }


    /**
     * Builder for {@link TweenAnim}
     */
    public static class Builder {

        protected List<TweenParameter> mTweenParameterList = new ArrayList<>();

        /**
         * If true, the tween will loop when it reaches the end. Can be set via the props param.
         */
        private boolean mTweenLoop = false;

        protected long mFps = 1000 / Constant.DEFAULT_FPS;

        private AnimParameter mInitialParam = new AnimParameter(0, 0);


        public TweenAnim build() {
            return new TweenAnim(mTweenLoop, mInitialParam, mTweenParameterList);
        }

        /**
         * Setter mTweenLoop
         *
         * @param repeat If true, the tween will loop when it reaches the end.
         */
        public Builder tweenLoop(boolean repeat) {
            mTweenLoop = repeat;
            return this;
        }

        /**
         * see {@link #transform(float, float, int, float, float, float)}
         */
        public Builder transform(float x, float y) {
            AnimParameter beforeParam = mTweenParameterList.size() > 0 ?
                    mTweenParameterList.get(mTweenParameterList.size() - 1) :
                    mInitialParam;
            return transform(x, y, beforeParam.alpha(), beforeParam.scaleX(), beforeParam.scaleY(), beforeParam.rotation());
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
        public Builder transform(float x, float y, int alpha, float scaleX, float scaleY, float rotation) {
            if (mTweenParameterList.size() > 0) {
                to(0, x, y, alpha, scaleX, scaleY, rotation, Ease.NONE);
            } else {
                mInitialParam = new AnimParameter(x, y, alpha, scaleX, scaleY, rotation);
            }

            return this;
        }

        /**
         * see {@link #to(long, float, float, int, float, float, float, Ease)}
         */
        public Builder to(long animDuration, float x, float y, Ease ease) {
            AnimParameter beforeParam = mTweenParameterList.size() > 0 ?
                    mTweenParameterList.get(mTweenParameterList.size() - 1) :
                    mInitialParam;
            return to(animDuration, x, y, beforeParam.alpha(), beforeParam.scaleX(), beforeParam.scaleY(), beforeParam.rotation(), ease);
        }

        /**
         * see {@link #to(long, float, float, int, float, float, float, Ease)}
         */
        public Builder to(long animDuration, float x, float y, int alpha, Ease ease) {
            AnimParameter beforeParam = mTweenParameterList.size() > 0 ?
                    mTweenParameterList.get(mTweenParameterList.size() - 1) :
                    mInitialParam;

            return to(animDuration, x, y, alpha, beforeParam.scaleX(), beforeParam.scaleY(), beforeParam.rotation(), ease);
        }

        /**
         * see {@link #to(long, float, float, int, float, float, float, Ease)}
         */
        public Builder to(long animDuration, float x, float y, float scaleX, float scaleY, Ease ease) {
            AnimParameter beforeParam = mTweenParameterList.size() > 0 ?
                    mTweenParameterList.get(mTweenParameterList.size() - 1) :
                    mInitialParam;

            return to(animDuration, x, y, beforeParam.alpha(), scaleX, scaleY, beforeParam.rotation(), ease);
        }

        /**
         * see {@link #to(long, float, float, int, float, float, float, Ease)}
         */
        public Builder to(long animDuration, float x, float y, float rotation, Ease ease) {
            AnimParameter beforeParam = mTweenParameterList.size() > 0 ?
                    mTweenParameterList.get(mTweenParameterList.size() - 1) :
                    mInitialParam;

            return to(animDuration, x, y, beforeParam.alpha(), beforeParam.scaleX(), beforeParam.scaleY(), rotation, ease);
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
        public Builder to(long animDuration, float x, float y, int alpha, float scaleX, float scaleY, float rotation, Ease ease) {
            mTweenParameterList.add(new TweenParameter(
                    x,
                    y,
                    alpha,
                    scaleX,
                    scaleY,
                    rotation,
                    animDuration,
                    ease
            ));
            return this;
        }

        /**
         * Queues a tween from the current values to the target properties. Set duration to 0 to jump to these value.
         * Numeric properties will be tweened from their current value in the tween to the target value.
         *
         * @param animDuration The duration of the tween in milliseconds
         * @param x            The horizontal translation (x position) in pixels
         * @param ease         The easing function to use for this tween.
         */
        public Builder toX(long animDuration, float x, Ease ease) {
            AnimParameter beforeParam = mTweenParameterList.size() > 0 ?
                    mTweenParameterList.get(mTweenParameterList.size() - 1) :
                    mInitialParam;

            mTweenParameterList.add(new TweenParameter(
                    x,
                    beforeParam.y(),
                    beforeParam.alpha(),
                    beforeParam.scaleX(),
                    beforeParam.scaleY(),
                    beforeParam.rotation(),
                    animDuration,
                    ease
            ));

            return this;
        }

        /**
         * Queues a tween from the current values to the target properties. Set duration to 0 to jump to these value.
         * Numeric properties will be tweened from their current value in the tween to the target value.
         *
         * @param animDuration The duration of the tween in milliseconds
         * @param y            The vertical translation (y position) in pixels
         * @param ease         The easing function to use for this tween.
         */
        public Builder toY(long animDuration, float y, Ease ease) {
            AnimParameter beforeParam = mTweenParameterList.size() > 0 ?
                    mTweenParameterList.get(mTweenParameterList.size() - 1) :
                    mInitialParam;

            mTweenParameterList.add(new TweenParameter(
                    beforeParam.x(),
                    y,
                    beforeParam.alpha(),
                    beforeParam.scaleX(),
                    beforeParam.scaleY(),
                    beforeParam.rotation(),
                    animDuration,
                    ease
            ));

            return this;
        }

        /**
         * see {@link #alpha(long, float, Ease)}
         */
        public Builder alpha(long animDuration, float alpha) {
            return alpha(animDuration, alpha, Ease.NONE);
        }

        /**
         * Queues a tween from the current values to the target properties. Set duration to 0 to jump to these value.
         * Numeric properties will be tweened from their current value in the tween to the target value.
         *
         * @param animDuration The duration of the tween in milliseconds
         * @param alpha        The alpha (transparency) ,as a percentage of 255.
         * @param ease         The easing function to use for this tween.
         */
        public Builder alpha(long animDuration, float alpha, Ease ease) {
            AnimParameter beforeParam = mTweenParameterList.size() > 0 ?
                    mTweenParameterList.get(mTweenParameterList.size() - 1) :
                    mInitialParam;

            mTweenParameterList.add(new TweenParameter(
                    beforeParam.x(),
                    beforeParam.y(),
                    Util.convertAlphaFloatToInt(alpha),
                    beforeParam.scaleX(),
                    beforeParam.scaleY(),
                    beforeParam.rotation(),
                    animDuration,
                    ease
            ));

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
         */
        public Builder scale(long animDuration, float scaleX, float scaleY, Ease ease) {
            AnimParameter beforeParam = mTweenParameterList.size() > 0 ?
                    mTweenParameterList.get(mTweenParameterList.size() - 1) :
                    mInitialParam;

            mTweenParameterList.add(new TweenParameter(
                    beforeParam.x(),
                    beforeParam.y(),
                    beforeParam.alpha(),
                    scaleX,
                    scaleY,
                    beforeParam.rotation(),
                    animDuration,
                    ease
            ));

            return this;
        }

        /**
         * Queues a tween from the current values to the target properties. Set duration to 0 to jump to these value.
         * Numeric properties will be tweened from their current value in the tween to the target value.
         *
         * @param animDuration The duration of the tween in milliseconds
         * @param rotation     The rotation, in degrees
         * @param ease         The easing function to use for this tween.
         */
        public Builder rotation(long animDuration, float rotation, Ease ease) {
            AnimParameter beforeParam = mTweenParameterList.size() > 0 ?
                    mTweenParameterList.get(mTweenParameterList.size() - 1) :
                    mInitialParam;

            mTweenParameterList.add(new TweenParameter(
                    beforeParam.x(),
                    beforeParam.y(),
                    beforeParam.alpha(),
                    beforeParam.scaleX(),
                    beforeParam.scaleY(),
                    rotation,
                    animDuration,
                    ease
            ));

            return this;
        }

        /**
         * Queues a wait (essentially an empty tween).
         *
         * @param animDuration The duration of the wait in milliseconds
         */
        public Builder waitTime(long animDuration) {

            AnimParameter beforeParam = mTweenParameterList.size() > 0 ?
                    mTweenParameterList.get(mTweenParameterList.size() - 1) :
                    mInitialParam;

            mTweenParameterList.add(new TweenParameter(
                    beforeParam.x(),
                    beforeParam.y(),
                    beforeParam.alpha(),
                    beforeParam.scaleX(),
                    beforeParam.scaleY(),
                    beforeParam.rotation(),
                    animDuration,
                    Ease.NONE
            ));

            return this;
        }

        public Builder call(AnimCallBack callBack) {
            if (!mTweenParameterList.isEmpty()) {
                mTweenParameterList.get(mTweenParameterList.size() - 1).callBack = callBack;
            }
            return this;
        }
    }

}
