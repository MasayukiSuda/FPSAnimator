package com.daasuu.library.animator;

import android.graphics.Canvas;
import android.graphics.PointF;

import com.daasuu.library.AnimParameter;
import com.daasuu.library.Animator;
import com.daasuu.library.DisplayBase;
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
 * Class for tween animation
 */
public class TweenAnimator implements Animator {
    private static final String TAG = TweenAnimator.class.getSimpleName();

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
     * Array of parameters required when to Tween animation DisplayBase.
     */
    protected final List<AnimParameter> mAnimParameters;

    protected final List<TweenParameter> mTweenParameters;

    private final Map<AnimParameter, AnimCallBack> mCallbacks;

    public static Composer composer(DisplayBase DisplayBase) {
        return new Composer(DisplayBase);
    }

    private TweenAnimator(boolean tweenLoop, AnimParameter initialParam, List<TweenParameter> tweenParameters) {
        this.mTweenLoop = tweenLoop;
        this.mInitialParam = initialParam;
        mTweenParameters = tweenParameters;
        mAnimParameters = new ArrayList<>();
        mCallbacks = new HashMap<>();
    }

    @Override
    public void updateAnimParam(AnimParameter parameter) {
        AnimParameter nextParam = getNextAnimParameter();

        parameter.x = nextParam.x;
        parameter.y = nextParam.y;
        parameter.alpha = nextParam.alpha;
        parameter.scaleX = nextParam.scaleX;
        parameter.scaleY = nextParam.scaleY;
        parameter.rotation = nextParam.rotation;

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
    public AnimParameter getInitialAnimParameter() {
        return mInitialParam;
    }

    @Override
    public void setUp(long fps) {
        mFps = 1000 / fps;
        mAnimParameters.clear();
        mCallbacks.clear();
        for (int i = 0, n = mTweenParameters.size(); i < n; i++) {
            TweenParameter tp = mTweenParameters.get(i);
            // set anim parameter
            mAnimParameters.addAll(createAnimParamList(tp, i));
            // set callback
            if (tp.callBack != null) {
                mCallbacks.put(mAnimParameters.get(mAnimParameters.size() - 1), tp.callBack);
            }
        }
    }

    @Override
    public void pause(boolean pause) {
        mTweenPause = pause;
    }

    @Override
    public boolean isPause() {
        return mTweenPause;
    }

    protected AnimParameter getNextAnimParameter() {
        if (mAnimParameters.size() == 0) return mInitialParam;

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

        cntX = (tweenParameter.x - beforeParam.x) / animParamNum;
        cntY = (tweenParameter.y - beforeParam.y) / animParamNum;
        cntAlpha = (int) ((tweenParameter.alpha - beforeParam.alpha) / animParamNum);
        cntRotation = (tweenParameter.rotation - beforeParam.rotation) / animParamNum;
        cntScaleX = (tweenParameter.scaleX - beforeParam.scaleX) / animParamNum;
        cntScaleY = (tweenParameter.scaleY - beforeParam.scaleY) / animParamNum;

        if (tweenParameter.angle == TweenParameter.DEFAULT_ANGLE) {

            for (int i = 0; i < animParamNum; i++) {
                float elapsedTimeRate = (float) i / (float) animParamNum;
                float valueChangeRate = EaseProvider.get(tweenParameter.ease, elapsedTimeRate);

                AnimParameter animParameter = new AnimParameter(
                        beforeParam.x + cntX * i * valueChangeRate,
                        beforeParam.y + cntY * i * valueChangeRate,
                        (int) (beforeParam.alpha + cntAlpha * i * valueChangeRate),
                        beforeParam.scaleX + cntScaleX * i * valueChangeRate,
                        beforeParam.scaleY + cntScaleY * i * valueChangeRate,
                        beforeParam.rotation + cntRotation * i * valueChangeRate
                );

                params.add(animParameter);
            }

        } else {


            float differX = tweenParameter.x - beforeParam.x;
            float differY = tweenParameter.y - beforeParam.y;

            float halfDifferX = differX / 2;
            float halfDifferY = differY / 2;


            float baseLineLength = (float) Math.sqrt(halfDifferX * halfDifferX + halfDifferY * halfDifferY);

            double baseRadian = Math.atan2(differY, differX);
            double baseDegree = Math.toDegrees(baseRadian);

            double cosRadius = 90 - (tweenParameter.angle / 2);

            double distance = (baseLineLength / Math.cos(Math.toRadians(cosRadius)));


            float plusAngle = 0;
            float startAngle = 0;

            if (beforeParam.x >= tweenParameter.x && beforeParam.y <= tweenParameter.y) {
                plusAngle = 0;
                startAngle = (float) (plusAngle + (90 - tweenParameter.angle) / 2 + baseDegree - 90 - 45f);

            } else if (beforeParam.x >= tweenParameter.x && beforeParam.y > tweenParameter.y) {
                plusAngle = 90;
                startAngle = (float) (plusAngle + (90 - tweenParameter.angle) / 2 + -(-180 - baseDegree) - 45f);
            } else if (beforeParam.x < tweenParameter.x && beforeParam.y > tweenParameter.y) {
                plusAngle = 180;
                startAngle = (float) (plusAngle + (90 - tweenParameter.angle) / 2 + -(-90 - baseDegree) - 45f);
            } else if (beforeParam.x < tweenParameter.x && beforeParam.y <= tweenParameter.y) {
                plusAngle = 270;
                startAngle = (float) (plusAngle + (90 - tweenParameter.angle) / 2 + baseDegree - 45f);
            }

            PointF differPointF = Util.getPointByDistanceAndDegree(distance, startAngle);

            float differBaseX = beforeParam.x - differPointF.x;
            float differBaseY = beforeParam.y - differPointF.y;


            float cntAngle = tweenParameter.angle / animParamNum;

            for (int i = 0; i < animParamNum; i++) {
                float elapsedTimeRate = (float) i / (float) animParamNum;
                float valueChangeRate = EaseProvider.get(tweenParameter.ease, elapsedTimeRate);

                float angle = startAngle + cntAngle * (i + 1) * valueChangeRate;

                PointF pointF = Util.getPointByDistanceAndDegree(distance, angle);

                AnimParameter animParameter = new AnimParameter(
                        pointF.x + differBaseX,
                        pointF.y + differBaseY,
                        (int) (beforeParam.alpha + cntAlpha * i * valueChangeRate),
                        beforeParam.scaleX + cntScaleX * i * valueChangeRate,
                        beforeParam.scaleY + cntScaleY * i * valueChangeRate,
                        beforeParam.rotation + cntRotation * i * valueChangeRate
                );

                params.add(animParameter);
            }

        }

        return params;
    }

    /**
     * Arrangement for generating AnimParameter
     */
    private static class TweenParameter extends AnimParameter {
        private static final float DEFAULT_ANGLE = -9999;
        private long animDuration;
        private Ease ease = Ease.NONE;
        private AnimCallBack callBack;
        private float angle = DEFAULT_ANGLE;

        private TweenParameter(float x, float y, long animDuration) {
            super(x, y);
            this.animDuration = animDuration;
        }

        public TweenParameter(float x, float y, int alpha, float scaleX, float scaleY, float rotation, long animDuration, Ease ease) {
            this(x, y, alpha, scaleX, scaleY, rotation, animDuration, ease, DEFAULT_ANGLE);
        }

        public TweenParameter(float x, float y, int alpha, float scaleX, float scaleY, float rotation, long animDuration, Ease ease, float angle) {
            super(x, y, alpha, scaleX, scaleY, rotation);
            this.animDuration = animDuration;
            this.ease = ease;
            this.angle = angle;
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
     * Builder for {@link TweenAnimator}
     */
    public static class Composer {

        private final DisplayBase mDisplayBase;

        protected List<TweenParameter> mTweenParameterList = new ArrayList<>();

        /**
         * If true, the tween will loop when it reaches the end. Can be set via the props param.
         */
        private boolean mTweenLoop = false;

        private AnimParameter mInitialParam = new AnimParameter(0, 0);

        private Composer(DisplayBase DisplayBase) {
            mDisplayBase = DisplayBase;
        }

        public void end() {
            mDisplayBase.animator(new TweenAnimator(mTweenLoop, mInitialParam, mTweenParameterList));
        }

        /**
         * Setter mTweenLoop
         *
         * @param repeat If true, the tween will loop when it reaches the end.
         * @return this
         */
        public Composer tweenLoop(boolean repeat) {
            mTweenLoop = repeat;
            return this;
        }

        /**
         * see {@link #transform(float, float, int, float, float, float)}
         *
         * @param x The horizontal translation (x position) in pixels
         * @param y The vertical translation (y position) in pixels
         * @return this
         */
        public Composer transform(float x, float y) {
            AnimParameter beforeParam = mTweenParameterList.size() > 0 ?
                    mTweenParameterList.get(mTweenParameterList.size() - 1) :
                    mInitialParam;
            return transform(x, y, beforeParam.alpha, beforeParam.scaleX, beforeParam.scaleY, beforeParam.rotation);
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
         * @return this
         */
        public Composer transform(float x, float y, int alpha, float scaleX, float scaleY, float rotation) {
            if (mTweenParameterList.size() > 0) {
                to(0, x, y, alpha, scaleX, scaleY, rotation, Ease.NONE);
            } else {
                mInitialParam = new AnimParameter(x, y, alpha, scaleX, scaleY, rotation);
            }

            return this;
        }

        /**
         * see {@link #to(long, float, float, int, float, float, float, Ease)}
         *
         * @param animDuration The duration of the tween in milliseconds
         * @param x            The horizontal translation (x position) in pixels
         * @param y            The vertical translation (y position) in pixels
         * @return this
         */
        public Composer to(long animDuration, float x, float y) {
            return to(animDuration, x, y, Ease.LINEAR);
        }

        /**
         * see {@link #to(long, float, float, int, float, float, float, Ease)}
         *
         * @param animDuration The duration of the tween in milliseconds
         * @param x            The horizontal translation (x position) in pixels
         * @param y            The vertical translation (y position) in pixels
         * @param ease         The easing function to use for this tween.
         * @return this
         */
        public Composer to(long animDuration, float x, float y, Ease ease) {
            AnimParameter beforeParam = mTweenParameterList.size() > 0 ?
                    mTweenParameterList.get(mTweenParameterList.size() - 1) :
                    mInitialParam;
            return to(animDuration, x, y, beforeParam.alpha, beforeParam.scaleX, beforeParam.scaleY, beforeParam.rotation, ease);
        }

        /**
         * see {@link #to(long, float, float, int, float, float, float, Ease)}
         *
         * @param animDuration The duration of the tween in milliseconds
         * @param x            The horizontal translation (x position) in pixels
         * @param y            The vertical translation (y position) in pixels
         * @param alpha        The alpha (transparency) ,as a percentage of 255.
         * @return this
         */
        public Composer to(long animDuration, float x, float y, int alpha) {
            return to(animDuration, x, y, alpha, Ease.LINEAR);
        }

        /**
         * see {@link #to(long, float, float, int, float, float, float, Ease)}
         *
         * @param animDuration The duration of the tween in milliseconds
         * @param x            The horizontal translation (x position) in pixels
         * @param y            The vertical translation (y position) in pixels
         * @param alpha        The alpha (transparency) ,as a percentage of 255.
         * @param ease         The easing function to use for this tween.
         * @return this
         */
        public Composer to(long animDuration, float x, float y, int alpha, Ease ease) {
            AnimParameter beforeParam = mTweenParameterList.size() > 0 ?
                    mTweenParameterList.get(mTweenParameterList.size() - 1) :
                    mInitialParam;

            return to(animDuration, x, y, alpha, beforeParam.scaleX, beforeParam.scaleY, beforeParam.rotation, ease);
        }

        /**
         * see {@link #to(long, float, float, int, float, float, float, Ease)}
         *
         * @param animDuration The duration of the tween in milliseconds
         * @param x            The horizontal translation (x position) in pixels
         * @param y            The vertical translation (y position) in pixels
         * @param scaleX       The horizontal scale, as a percentage of 1
         * @param scaleY       The vertical scale, as a percentage of 1
         * @return this
         */
        public Composer to(long animDuration, float x, float y, float scaleX, float scaleY) {
            return to(animDuration, x, y, scaleX, scaleY, Ease.LINEAR);
        }

        /**
         * see {@link #to(long, float, float, int, float, float, float, Ease)}
         *
         * @param animDuration The duration of the tween in milliseconds
         * @param x            The horizontal translation (x position) in pixels
         * @param y            The vertical translation (y position) in pixels
         * @param scaleX       The horizontal scale, as a percentage of 1
         * @param scaleY       The vertical scale, as a percentage of 1
         * @param ease         The easing function to use for this tween.
         * @return this
         */
        public Composer to(long animDuration, float x, float y, float scaleX, float scaleY, Ease ease) {
            AnimParameter beforeParam = mTweenParameterList.size() > 0 ?
                    mTweenParameterList.get(mTweenParameterList.size() - 1) :
                    mInitialParam;

            return to(animDuration, x, y, beforeParam.alpha, scaleX, scaleY, beforeParam.rotation, ease);
        }

        /**
         * see {@link #to(long, float, float, int, float, float, float, Ease)}
         *
         * @param animDuration The duration of the tween in milliseconds
         * @param x            The horizontal translation (x position) in pixels
         * @param y            The vertical translation (y position) in pixels
         * @param rotation     The rotation, in degrees
         * @return this
         */
        public Composer to(long animDuration, float x, float y, float rotation) {
            return to(animDuration, x, y, rotation, Ease.LINEAR);
        }

        /**
         * see {@link #to(long, float, float, int, float, float, float, Ease)}
         *
         * @param animDuration The duration of the tween in milliseconds
         * @param x            The horizontal translation (x position) in pixels
         * @param y            The vertical translation (y position) in pixels
         * @param rotation     The rotation, in degrees
         * @param ease         The easing function to use for this tween.
         * @return this
         */
        public Composer to(long animDuration, float x, float y, float rotation, Ease ease) {
            AnimParameter beforeParam = mTweenParameterList.size() > 0 ?
                    mTweenParameterList.get(mTweenParameterList.size() - 1) :
                    mInitialParam;

            return to(animDuration, x, y, beforeParam.alpha, beforeParam.scaleX, beforeParam.scaleY, rotation, ease);
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
         * @return this
         */
        public Composer to(long animDuration, float x, float y, int alpha, float scaleX, float scaleY, float rotation, Ease ease) {
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
         * see {@link #toX(long, float, Ease)}
         *
         * @param animDuration The duration of the tween in milliseconds
         * @param x            The horizontal translation (x position) in pixels
         * @return this
         */
        public Composer toX(long animDuration, float x) {
            return toX(animDuration, x, Ease.LINEAR);
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
        public Composer toX(long animDuration, float x, Ease ease) {
            AnimParameter beforeParam = mTweenParameterList.size() > 0 ?
                    mTweenParameterList.get(mTweenParameterList.size() - 1) :
                    mInitialParam;

            mTweenParameterList.add(new TweenParameter(
                    x,
                    beforeParam.y,
                    beforeParam.alpha,
                    beforeParam.scaleX,
                    beforeParam.scaleY,
                    beforeParam.rotation,
                    animDuration,
                    ease
            ));

            return this;
        }

        /**
         * see {@link #toY(long, float, Ease)}
         *
         * @param animDuration The duration of the tween in milliseconds
         * @param y            The vertical translation (y position) in pixels
         * @return this
         */
        public Composer toY(long animDuration, float y) {
            return toY(animDuration, y, Ease.LINEAR);
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
        public Composer toY(long animDuration, float y, Ease ease) {
            AnimParameter beforeParam = mTweenParameterList.size() > 0 ?
                    mTweenParameterList.get(mTweenParameterList.size() - 1) :
                    mInitialParam;

            mTweenParameterList.add(new TweenParameter(
                    beforeParam.x,
                    y,
                    beforeParam.alpha,
                    beforeParam.scaleX,
                    beforeParam.scaleY,
                    beforeParam.rotation,
                    animDuration,
                    ease
            ));

            return this;
        }

        /**
         * see {@link #alpha(long, float, Ease)}
         *
         * @param animDuration The duration of the tween in milliseconds
         * @param alpha        The alpha (transparency) ,as a percentage of 255.
         * @return this
         */
        public Composer alpha(long animDuration, float alpha) {
            return alpha(animDuration, alpha, Ease.LINEAR);
        }

        /**
         * Queues a tween from the current values to the target properties. Set duration to 0 to jump to these value.
         * Numeric properties will be tweened from their current value in the tween to the target value.
         *
         * @param animDuration The duration of the tween in milliseconds
         * @param alpha        The alpha (transparency) ,as a percentage of 255.
         * @param ease         The easing function to use for this tween.
         * @return this
         */
        public Composer alpha(long animDuration, float alpha, Ease ease) {
            AnimParameter beforeParam = mTweenParameterList.size() > 0 ?
                    mTweenParameterList.get(mTweenParameterList.size() - 1) :
                    mInitialParam;

            mTweenParameterList.add(new TweenParameter(
                    beforeParam.x,
                    beforeParam.y,
                    Util.convertAlphaFloatToInt(alpha),
                    beforeParam.scaleX,
                    beforeParam.scaleY,
                    beforeParam.rotation,
                    animDuration,
                    ease
            ));

            return this;
        }

        /**
         * see {@link #scale(long, float, float, Ease)}
         *
         * @param animDuration The duration of the tween in milliseconds
         * @param scaleX       The horizontal scale, as a percentage of 1
         * @param scaleY       The vertical scale, as a percentage of 1
         * @return this
         */
        public Composer scale(long animDuration, float scaleX, float scaleY) {
            return scale(animDuration, scaleX, scaleY, Ease.LINEAR);
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
        public Composer scale(long animDuration, float scaleX, float scaleY, Ease ease) {
            AnimParameter beforeParam = mTweenParameterList.size() > 0 ?
                    mTweenParameterList.get(mTweenParameterList.size() - 1) :
                    mInitialParam;

            mTweenParameterList.add(new TweenParameter(
                    beforeParam.x,
                    beforeParam.y,
                    beforeParam.alpha,
                    scaleX,
                    scaleY,
                    beforeParam.rotation,
                    animDuration,
                    ease
            ));

            return this;
        }

        /**
         * see {@link #rotation(long, float, Ease)}
         *
         * @param animDuration The duration of the tween in milliseconds
         * @param rotation     The rotation, in degrees
         * @return this
         */
        public Composer rotation(long animDuration, float rotation) {
            return rotation(animDuration, rotation, Ease.LINEAR);
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
        public Composer rotation(long animDuration, float rotation, Ease ease) {
            AnimParameter beforeParam = mTweenParameterList.size() > 0 ?
                    mTweenParameterList.get(mTweenParameterList.size() - 1) :
                    mInitialParam;

            mTweenParameterList.add(new TweenParameter(
                    beforeParam.x,
                    beforeParam.y,
                    beforeParam.alpha,
                    beforeParam.scaleX,
                    beforeParam.scaleY,
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
         * @param y            The vertical translation (y position) in pixels
         * @param angle        The curve angle
         * @return this
         */
        public Composer arc(long animDuration, float x, float y, float angle) {
            if (angle < 1f && angle > -1f) {
                return to(animDuration, x, y, Ease.NONE);
            }
            return arc(animDuration, x, y, angle, Ease.NONE);
        }

        /**
         * Queues a tween from the current values to the target properties. Set duration to 0 to jump to these value.
         * Numeric properties will be tweened from their current value in the tween to the target value.
         *
         * @param animDuration The duration of the tween in milliseconds
         * @param x            The horizontal translation (x position) in pixels
         * @param y            The vertical translation (y position) in pixels
         * @param angle        The curve angle
         * @param ease         The easing function to use for this tween.
         * @return this
         */
        public Composer arc(long animDuration, float x, float y, float angle, Ease ease) {
            AnimParameter beforeParam = mTweenParameterList.size() > 0 ?
                    mTweenParameterList.get(mTweenParameterList.size() - 1) :
                    mInitialParam;

            if (angle < 1f && angle > -1f) {
                return to(animDuration, x, y, beforeParam.alpha, beforeParam.scaleX, beforeParam.scaleY, beforeParam.rotation, ease);
            }

            return arc(animDuration, x, y, angle, beforeParam.alpha, beforeParam.scaleX, beforeParam.scaleY, beforeParam.rotation, ease);
        }

        /**
         * Queues a tween from the current values to the target properties. Set duration to 0 to jump to these value.
         * Numeric properties will be tweened from their current value in the tween to the target value.
         *
         * @param animDuration The duration of the tween in milliseconds
         * @param x            The horizontal translation (x position) in pixels
         * @param y            The vertical translation (y position) in pixels
         * @param angle        The curve angle
         * @param alpha        The alpha (transparency) ,as a percentage of 255.
         * @param scaleX       The horizontal scale, as a percentage of 1
         * @param scaleY       The vertical scale, as a percentage of 1
         * @param rotation     The rotation, in degrees
         * @param ease         The easing function to use for this tween.
         * @return this
         */
        public Composer arc(long animDuration, float x, float y, float angle, int alpha, float scaleX, float scaleY, float rotation, Ease ease) {

            if (angle < 1f && angle > -1f) {
                return to(animDuration, x, y, alpha, scaleX, scaleY, rotation, ease);
            }

            mTweenParameterList.add(new TweenParameter(
                    x,
                    y,
                    alpha,
                    scaleX,
                    scaleY,
                    rotation,
                    animDuration,
                    ease,
                    angle
            ));

            return this;
        }


        /**
         * Queues a wait (essentially an empty tween).
         *
         * @param animDuration The duration of the wait in milliseconds
         * @return this
         */
        public Composer waitTime(long animDuration) {

            AnimParameter beforeParam = mTweenParameterList.size() > 0 ?
                    mTweenParameterList.get(mTweenParameterList.size() - 1) :
                    mInitialParam;

            mTweenParameterList.add(new TweenParameter(
                    beforeParam.x,
                    beforeParam.y,
                    beforeParam.alpha,
                    beforeParam.scaleX,
                    beforeParam.scaleY,
                    beforeParam.rotation,
                    animDuration,
                    Ease.NONE
            ));

            return this;
        }

        /**
         * Set queues an action to call the specified function.
         *
         * @param callBack callback The function to call.
         * @return this
         */
        public Composer call(AnimCallBack callBack) {
            if (!mTweenParameterList.isEmpty()) {
                mTweenParameterList.get(mTweenParameterList.size() - 1).callBack = callBack;
            }
            return this;
        }

    }

}
