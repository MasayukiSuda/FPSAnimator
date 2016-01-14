package com.daasuu.library;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.SurfaceTexture;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;
import android.view.TextureView;

import com.daasuu.library.constant.Constant;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by sudamasayuki on 15/12/19.
 */
public class FPSTextureView extends TextureView implements TextureView.SurfaceTextureListener {

    private Timer mTimer;
    private long mFps = Constant.DEFAULT_FPS;

    private List<DisplayObject> mDisplayList = new ArrayList<>();

    public FPSTextureView(Context context) {
        this(context, null, 0);
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

            List<DisplayObject> copyDisplayObjectList = new ArrayList<DisplayObject>(mDisplayList);

            Canvas canvas = this.lockCanvas();
            if (canvas == null) return;

            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

            for (DisplayObject displayObject : copyDisplayObjectList) {
                if (displayObject == null) {
                    continue;
                }
                displayObject.draw(canvas);
            }

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
     * @param displayObject DisplayObject
     * @return this
     */
    public FPSTextureView addChild(@NonNull DisplayObject displayObject) {
        displayObject.setUp(mFps);
        mDisplayList.add(displayObject);
        return this;
    }

    /**
     * Adds a child to the display list at the specified index, bumping children at equal or greater indexes up one, and setting its parent to this Container
     *
     * @param location      index
     * @param displayObject DisplayObject
     * @return this
     */
    public FPSTextureView addChildAt(int location, @NonNull DisplayObject displayObject) {
        displayObject.setUp(mFps);
        mDisplayList.add(location, displayObject);
        return this;
    }

    /**
     * Removes the specified child from the display list.
     *
     * @param displayObject DisplayObject
     * @return this
     */
    public FPSTextureView removeChild(@NonNull DisplayObject displayObject) {
        boolean a = mDisplayList.remove(displayObject);
        Log.d("[DEBUG]", "list.size=" + mDisplayList.size() + ", success=" + a + ",displayObject=" + displayObject);
        return this;
    }

    /**
     * Removes the child at the specified index from the display list.
     *
     * @param location index
     * @return this
     */
    public FPSTextureView removeChildAt(int location) {
        mDisplayList.remove(location);
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
    public boolean swapChildren(@NonNull DisplayObject child1, @NonNull DisplayObject child2) {
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
     * Indicates the target frame rate in frames per second.
     *
     * @param fps FPS
     * @return this
     */
    public FPSTextureView setFps(long fps) {
        this.mFps = fps;
        return this;
    }

    /**
     * Getter DisplayList
     *
     * @return DisplayList
     */
    public List<DisplayObject> getDisplayList() {
        return mDisplayList;
    }
}
