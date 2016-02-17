package com.daasuu.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.SurfaceTexture;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.TextureView;

import com.daasuu.library.constant.Constant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 */
public class FPSTextureView extends TextureView implements TextureView.SurfaceTextureListener {

    private Timer mTimer;
    private int mFps = Constant.DEFAULT_FPS;

    private List<DisplayBase> mDisplayList = new ArrayList<>();
    private final List<DisplayBase> mDrawingList = new ArrayList<>();

    public FPSTextureView(Context context) {
        this(context, null, 0);
    }

    public FPSTextureView(Context context, int fps) {
        this(context, null, 0);
        mFps = fps;
    }

    public FPSTextureView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * Constructor
     *
     * @param context      The Context the view is running in, through which it can
     *                     access the current theme, resources, etc.
     * @param attrs        The attributes of the XML tag that is inflating the view.
     * @param defStyleAttr An attribute in the current theme that contains a
     *                     reference to a style resource that supplies default values for
     *                     the view. Can be 0 to not look for defaults.
     */
    public FPSTextureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOpaque(false);
        setSurfaceTextureListener(this);

        // FPS set
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.FPSAnimator);
        mFps = ta.getInteger(R.styleable.FPSAnimator_FPSAnimator_fps, Constant.DEFAULT_FPS);
        ta.recycle();
    }

    /**
     * Start tick
     *
     * @return this
     */
    public FPSTextureView tickStart() {
        tickStop();
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                onTick();
            }
        }, 0, 1000 / mFps);
        return this;
    }

    /**
     * Stop tick
     */
    public void tickStop() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }

    private void onTick() {

        synchronized (this) {
            Canvas canvas = this.lockCanvas();
            if (canvas == null) return;

            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

            mDrawingList.addAll(mDisplayList);
            mDrawingList.removeAll(Collections.singleton(null));
            Collections.sort(mDrawingList);
            for (DisplayBase displayBase : mDrawingList) {
                if (displayBase == null) {
                    continue;
                }
                displayBase.draw(canvas);
            }
            mDrawingList.clear();

            this.unlockCanvasAndPost(canvas);
        }

    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        synchronized (this) {
            Canvas canvas = this.lockCanvas();
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            this.unlockCanvasAndPost(canvas);
        }

    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        // do nothing
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        synchronized (this) {
            tickStop();
            return false;
        }
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        // do nothing
    }

    /**
     * Adds a child to the top of the display list.
     *
     * @param DisplayObject DisplayObject2
     * @return this
     */
    public FPSTextureView addChild(@NonNull DisplayBase DisplayObject) {
        DisplayObject.setUp(mFps);
        mDisplayList.add(DisplayObject);
        return this;
    }

    /**
     * Adds a child to the display list at the specified index, bumping children at equal or greater indexes up one, and setting its parent to this Container
     *
     * @param location    index
     * @param displayBase DisplayObject2
     * @return this
     */
    public FPSTextureView addChildAt(int location, @NonNull DisplayBase displayBase) {
        displayBase.setUp(mFps);
        mDisplayList.add(location, displayBase);
        return this;
    }

    /**
     * Removes the specified child from the display list.
     *
     * @param displayBase DisplayBase
     * @return this
     */
    public FPSTextureView removeChild(@NonNull DisplayBase displayBase) {
        displayBase.disable();
        boolean a = mDisplayList.remove(displayBase);
        return this;
    }

    /**
     * Removes the child at the specified index from the display list.
     *
     * @param location index
     * @return this
     */
    public FPSTextureView removeChildAt(int location) {
        DisplayBase removed = mDisplayList.remove(location);
        if (removed != null) {
            removed.disable();
        }
        return this;
    }

    /**
     * Removes all children from the display list.
     *
     * @return this
     */
    public FPSTextureView removeAllChildren() {
        mDisplayList.clear();
        return this;
    }

    /**
     * Swaps the specified children's depth in the display list. If either child is not a child of this Container, return false.
     *
     * @param child1 DisplayObject
     * @param child2 DisplayObject
     * @return if true, success to swapChildren
     */
    public boolean swapChildren(@NonNull DisplayBase child1, @NonNull DisplayBase child2) {
        int childIndex1 = mDisplayList.indexOf(child1);
        int childIndex2 = mDisplayList.indexOf(child2);

        if (childIndex1 == -1 || childIndex2 == -1) {
            return false;
        }

        removeChildAt(childIndex1);
        addChildAt(childIndex1, child2);
        removeChildAt(childIndex2);
        addChildAt(childIndex2, child1);
        return true;
    }

    /**
     * Getter DisplayList
     *
     * @return DisplayList
     */
    public List<DisplayBase> getDisplayList() {
        return mDisplayList;
    }
}
