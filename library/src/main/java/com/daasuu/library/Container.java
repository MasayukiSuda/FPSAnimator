package com.daasuu.library;


import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;

import com.daasuu.library.drawer.BaseDrawer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A Container is a nestable display list that allows you to work with compound display elements.
 * For example you could group arm, leg, torso and head Bitmap instances together into a Person Container,
 * and transform them as a group, while still being able to move the individual parts relative to each other.
 * Children of containers have their transform and alpha properties concatenated with their parent Container.
 */
public class Container extends DisplayBase {

    private static final long DEFAULT_FPS = -1;

    private long mFps = DEFAULT_FPS;

    private final List<DisplayObject> mDrawingList = new ArrayList<>();

    /**
     * constructor
     */
    public Container() {
        this(0);
    }

    public Container(int priority) {
        super(priority);
        drawer(new ContainerDrawer()).tween().end();
    }

    /**
     * Return Composer instance to setup this Container instance.
     * This method is useful when you use only default class of animation.
     *
     * @return composer
     */
    public DisplayComposer with() {
        return new DisplayComposer();
    }

    @Override
    void draw(Canvas canvas) {
        super.draw(canvas);

        mDrawingList.addAll(mDisplayList);
        mDrawingList.removeAll(Collections.singleton(null));
        Collections.sort(mDrawingList);
        for (DisplayObject DisplayObject : mDrawingList) {
            if (DisplayObject == null) {
                continue;
            }
            DisplayObject.draw(canvas, mAnimParameter);
        }
        mDrawingList.clear();

    }

    @Override
    void setUp(long fps) {
        super.setUp(fps);
        mFps = fps;
        for (DisplayObject DisplayObject : mDisplayList) {
            if (DisplayObject == null) {
                continue;
            }
            DisplayObject.setUp(fps);
        }
    }

    private List<DisplayObject> mDisplayList = new ArrayList<>();

    /**
     * Adds a child to the top of the display list.
     *
     * @param DisplayObject DisplayObject2
     * @return this
     */
    public Container addChild(@NonNull DisplayObject DisplayObject) {
        if (mFps != DEFAULT_FPS) {
            DisplayObject.setUp(mFps);
        }
        mDisplayList.add(DisplayObject);
        return this;
    }

    /**
     * Adds a child to the display list at the specified index, bumping children at equal or greater indexes up one, and setting its parent to this Container
     *
     * @param location      index
     * @param DisplayObject DisplayObject
     * @return this
     */
    public Container addChildAt(int location, @NonNull DisplayObject DisplayObject) {
        if (mFps != DEFAULT_FPS) {
            DisplayObject.setUp(mFps);
        }
        mDisplayList.add(location, DisplayObject);
        return this;
    }

    /**
     * Removes the specified child from the display list.
     *
     * @param displayObject DisplayObject
     * @return this
     */
    public Container removeChild(@NonNull DisplayObject displayObject) {
        displayObject.disable();
        mDisplayList.remove(displayObject);
        return this;
    }

    /**
     * Removes the child at the specified index from the display list.
     *
     * @param location index
     * @return this
     */
    public Container removeChildAt(int location) {
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
    public Container removeAllChildren() {
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
        child1.swapPriority(child2);
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
    public List<DisplayObject> getDisplayList() {
        return mDisplayList;
    }


    private class ContainerDrawer extends BaseDrawer {

        public ContainerDrawer() {
            super(new Paint());
        }

        @Override
        protected void draw(Canvas canvas, float x, float y, int alpha) {
            // Do nothing
        }

        @Override
        public float getWidth() {
            return 0;
        }

        @Override
        public float getHeight() {
            return 0;
        }
    }

}
