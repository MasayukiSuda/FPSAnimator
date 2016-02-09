package com.daasuu.library;

import android.graphics.Canvas;
import android.support.annotation.NonNull;

import com.daasuu.library.util.Util;

/**
 * DisplayObject class.
 * When you only use default animation and drawing class which is provided by this library,
 * you can use simple interface to compose DisplayObject by calling {@link #drawer(Drawer)}.
 * If you need to use your custom animation or drawing class which implements {@link Animator} or {@link Drawer},
 * you prepare their instances and simply set by calling {@link #animator(Animator)}.
 */
public class DisplayObject extends DisplayBase {

    public DisplayObject() {
    }

    public DisplayObject(int priority) {
        super(priority);
    }

    public DisplayComposer with(Drawer drawer) {
        return drawer(drawer);
    }

    /**
     * Call from {@link Container} class.
     * Affected by the parameter of Container.
     *
     * @param canvas        This Canvas acquired by lookCanvas in FPSTextureView or FPSSurfaceView.
     * @param animParameter AnimParameter instance
     */
    void draw(@NonNull Canvas canvas, @NonNull AnimParameter animParameter) {
        mAnimator.setBaseLine(canvas, mDrawer.getWidth(), mDrawer.getHeight());
        mAnimator.updateAnimParam(mAnimParameter);

        mDrawer.draw(
                canvas,
                mAnimParameter.x + animParameter.x,
                mAnimParameter.y + animParameter.y,
                (int) (mAnimParameter.alpha * Util.convertAlphaIntToFloat(animParameter.alpha)),
                mAnimParameter.scaleX * animParameter.scaleX,
                mAnimParameter.scaleY * animParameter.scaleY,
                mAnimParameter.rotation + animParameter.rotation);
    }


}

