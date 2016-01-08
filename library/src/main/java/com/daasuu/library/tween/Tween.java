package com.daasuu.library.tween;

import android.graphics.Canvas;

import com.daasuu.library.DisplayObject;
import com.daasuu.library.callback.AnimCallBack;
import com.daasuu.library.easing.Ease;
import com.daasuu.library.easing.EaseProvider;
import com.daasuu.library.parameter.AnimParameter;

import java.util.ArrayList;
import java.util.List;

/**
 * Tween BaseClass. It summarizes the common processing required when to Tween animation.
 */
class Tween extends DisplayObject {

    protected boolean mTweenLoop = false;
    private boolean mTweenPause = false;


    public void kill() {
        mTweenParameterList.clear();
        mAnimParameters.clear();
    }

    public void tweenPause(boolean pause) {
        mTweenPause = pause;
    }

    public boolean isTweenPause() {
        return mTweenPause;
    }

    protected void setTweenLoop(boolean repeat) {
        mTweenLoop = repeat;
    }

    protected void setTransform(float x, float y) {
        if (mTweenParameterList.size() > 0) {
            setTo(0, x, y, Ease.NONE);
        } else {
            mInitialX = x;
            mInitialY = y;
            AnimParameter animParameter = mAnimParameters.get(0);
            animParameter.x = x;
            animParameter.y = y;
        }
    }

    protected void setTransform(float x, float y, int alpha, float scaleX, float scaleY, float rotation) {
        if (mTweenParameterList.size() > 0) {
            setTo(0, x, y, alpha, scaleX, scaleY, rotation, Ease.NONE);
        } else {
            mInitialX = x;
            mInitialY = y;
            mInitialAlpha = alpha;
            mInitialScaleX = scaleX;
            mInitialScaleY = scaleY;
            mInitialRotation = rotation;

            AnimParameter animParameter = mAnimParameters.get(0);
            animParameter.x = x;
            animParameter.y = y;
            animParameter.alpha = alpha;
            animParameter.scaleX = scaleX;
            animParameter.scaleY = scaleY;
            animParameter.rotation = rotation;
        }

    }

    protected float mRotateRegistrationX = 0f;
    protected float mRotateRegistrationY = 0f;
    protected float mScaleRegistrationX = 0f;
    protected float mScaleRegistrationY = 0f;

    protected void setRotateRegistration(float regX, float regY) {
        mRotateRegistrationX = regX;
        mRotateRegistrationY = regY;
    }

    protected void setScaleRegistration(float regX, float regY) {
        mScaleRegistrationX = regX;
        mScaleRegistrationY = regY;
    }


    /**
     * animation array set
     *
     * @param animDuration
     * @param x
     * @param y
     * @param ease
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
            alpha = mInitialAlpha;
            scaleX = mInitialScaleX;
            scaleY = mInitialScaleY;
            rotation = mInitialRotation;
        }
        setTo(animDuration, x, y, alpha, scaleX, scaleY, rotation, ease);
    }


    /**
     * animation array set
     *
     * @param animDuration
     * @param x
     * @param y
     * @param alpha
     * @param ease
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
            scaleX = mInitialScaleX;
            scaleY = mInitialScaleY;
            rotation = mInitialRotation;
        }

        setTo(animDuration, x, y, alpha, scaleX, scaleY, rotation, ease);
    }

    /**
     * animation array set
     *
     * @param animDuration
     * @param x
     * @param y
     * @param scaleX
     * @param scaleY
     * @param ease
     */
    protected void setTo(long animDuration, float x, float y, float scaleX, float scaleY, Ease ease) {
        int alpha;
        float rotation;
        if (mTweenParameterList.size() > 0) {
            TweenParameter beforeTweenParameter = mTweenParameterList.get(mTweenParameterList.size() - 1);
            alpha = beforeTweenParameter.alpha;
            rotation = beforeTweenParameter.rotation;
        } else {
            alpha = mInitialAlpha;
            rotation = mInitialRotation;
        }

        setTo(animDuration, x, y, alpha, scaleX, scaleY, rotation, ease);
    }

    /**
     * animation array set
     *
     * @param animDuration
     * @param x
     * @param y
     * @param rotation
     * @param ease
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
            alpha = mInitialAlpha;
            scaleX = mInitialScaleX;
            scaleY = mInitialScaleY;
        }

        setTo(animDuration, x, y, alpha, scaleX, scaleY, rotation, ease);
    }

    /**
     * animation array set
     *
     * @param animDuration
     * @param x
     * @param y
     * @param alpha
     * @param scaleX
     * @param scaleY
     * @param rotation
     * @param ease
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
     * animation array set x only
     *
     * @param animDuration
     * @param x
     * @param ease
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
            y = mInitialY;
            alpha = mInitialAlpha;
            scaleX = mInitialScaleX;
            scaleY = mInitialScaleY;
            rotation = mInitialRotation;
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
     * animation array set y only
     *
     * @param animDuration
     * @param y
     * @param ease
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
            x = mInitialX;
            alpha = mInitialAlpha;
            scaleX = mInitialScaleX;
            scaleY = mInitialScaleY;
            rotation = mInitialRotation;
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
     * @param animDuration
     * @param alpha
     * @param ease
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
            x = mInitialX;
            y = mInitialY;
            scaleX = mInitialScaleX;
            scaleY = mInitialScaleY;
            rotation = mInitialRotation;
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
     * @param animDuration
     * @param scaleX
     * @param scaleY
     * @param ease
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
            x = mInitialX;
            y = mInitialY;
            alpha = mInitialAlpha;
            rotation = mInitialRotation;
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
     * @param animDuration
     * @param rotation
     * @param ease
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
            x = mInitialX;
            y = mInitialY;
            alpha = mInitialAlpha;
            scaleX = mInitialScaleX;
            scaleY = mInitialScaleY;
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
     * waitTime Animation set
     *
     * @param animDuration
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
            x = mInitialX;
            y = mInitialY;
            alpha = mInitialAlpha;
            scaleX = mInitialScaleX;
            scaleY = mInitialScaleY;
            rotation = mInitialRotation;
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
     * @param animCallBack
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
     * mAnimParametersの配列を生成する際に
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

    protected void runAnimCallBack(AnimParameter animParameter) {
        if (animParameter.animCallBack != null) {
            animParameter.animCallBack.call();
            if (!mTweenLoop) animParameter.animCallBack = null;
        }
    }

    @Override
    public void setUp() {
        // FPS、到達ポイント、からAnimParameterを生成し、1TweenParameterあたり、mAnimParametersをいくつ作成するかを決める。
        for (int i = 0, n = mTweenParameterList.size(); i < n; i++) {
            setUpAnimParamList(mTweenParameterList.get(i), i);
        }
    }

    /**
     * @param tweenParameter
     * @param position
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

        float beforeParameterX = mInitialX;
        float beforeParameterY = mInitialY;
        int beforeParameterAlpha = mInitialAlpha;
        float beforeParameterRotation = mInitialRotation;
        float beforeParameterScaleX = mInitialScaleX;
        float beforeParameterScaleY = mInitialScaleY;

        if (position == 0) {

            cntX = (tweenParameter.x - mInitialX) / animParamNum;
            cntY = (tweenParameter.y - mInitialY) / animParamNum;
            cntAlpha = (int) ((tweenParameter.alpha - mInitialAlpha) / animParamNum);
            cntRotation = (tweenParameter.rotation - mInitialRotation) / animParamNum;
            cntScaleX = (tweenParameter.scaleX - mInitialScaleX) / animParamNum;
            cntScaleY = (tweenParameter.scaleY - mInitialScaleY) / animParamNum;

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
