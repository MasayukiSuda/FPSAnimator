package com.daasuu.library.spritesheet;

import com.daasuu.library.callback.AnimCallBack;
import com.daasuu.library.constant.Constant;

import java.util.List;

/**
 * Encapsulates the properties and methods associated with a sprite sheet. A sprite sheet is a series of images (usually
 * animation frames) combined into a larger image (or images). For example, an animation consisting of eight 100x100
 * images could be combined into a single 400x200 sprite sheet (4 frames across by 2 high).
 */
public class SpriteSheet {

    private static final int DEFAULT_SPRITE_SHEET_NUM = -1;

    /**
     * The number of width of each frame
     */
    public float frameWidth;

    /**
     * The number of height of each frame
     */
    public float frameHeight;

    /**
     * the total number of frames in the specified animation
     */
    public int frameNum;

    /**
     * The number of which frame, there is about line 1 of side
     */
    public int frameNumPerLine;

    /**
     * If true, the Sprite Animation will loop when it reaches the last frame.
     */
    public boolean spriteLoop = false;

    /**
     * Movement of frame horizontal translation (x position) in pixels
     */
    public float dx = 0;

    /**
     * Movement of frame horizontal translation (y position) in pixels
     */
    public float dy = 0;

    /**
     * The frame index that will be drawn when draw is called.
     * This will always be an integer value.
     */
    public int currentFrame = Constant.DEFAULT_CURRENT_FRAME;

    /**
     * It will animation play in the frame number order of the value of this array.
     */
    public List<Integer> customFrameList;

    /**
     * Dispatched when an animation reaches its ends.
     */
    private AnimCallBack mSpriteSheetFinishCallback;

    /**
     * Number of SpriteSheet Animation loop
     */
    public int spriteLoopNum = DEFAULT_SPRITE_SHEET_NUM;

    private int currentLoopNum = 0;

    /**
     * indicates whether to start the SpriteAnimation paused.
     */
    protected boolean mSpritePause = false;

    /**
     * Getter mSpritePause
     *
     * @return mSpritePause
     */
    public boolean isSpritePause() {
        return mSpritePause;
    }

    /**
     * Setter mSpritePause
     *
     * @param mSpritePause indicates whether to start the SpriteAnimation paused.
     */
    public void setSpritePause(boolean mSpritePause) {
        this.mSpritePause = mSpritePause;
    }

    /**
     * constructor
     *
     * @param frameWidth      The number of width of each frame
     * @param frameHeight     The number of height of each frame
     * @param frameNum        the total number of frames in the specified animation
     * @param frameNumPerLine The number of which frame, there is about line 1 of side
     */
    public SpriteSheet(float frameWidth, float frameHeight, int frameNum, int frameNumPerLine) {
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        this.frameNum = frameNum;
        this.frameNumPerLine = frameNumPerLine;
    }

    /**
     * Setter mSpriteSheetFinishCallback
     *
     * @param spriteSheetFinishCallback Dispatched when an animation reaches its ends.
     */
    public void setSpriteSheetFinishCallback(AnimCallBack spriteSheetFinishCallback) {
        this.mSpriteSheetFinishCallback = spriteSheetFinishCallback;
    }

    /**
     * Move frame of SpriteSheet
     */
    public void updateFrame() {
        if (mSpritePause) return;

        if (customFrameList != null) {
            updateCustomFrame();
            return;
        }

        if (currentFrame > frameNum + 2) return;

        boolean edge = currentFrame % frameNumPerLine == 0;
        if (edge) {
            // It falls under the case of the end
            currentFrame++;
            if (currentFrame <= frameNum) {
                dy += frameHeight;
                dx = 0;
            }
            repeatFrame();
            return;
        }

        currentFrame++;
        if (currentFrame <= frameNum) {
            dx += frameWidth;
        }
        repeatFrame();
    }

    /**
     * Repeat frame of SpriteSheet
     */
    protected void repeatFrame() {
        if (currentFrame != frameNum) return;

        if (spriteLoopNum > 0) {
            currentLoopNum++;
            if (spriteLoopNum == currentLoopNum) {
                dispatchCallback();
                return;
            }
            resetFrame();
            return;
        }

        if (spriteLoop) {
            resetFrame();
        } else {
            currentFrame = frameNum;
        }

        dispatchCallback();

    }

    private void resetFrame() {
        currentFrame = Constant.DEFAULT_CURRENT_FRAME;
        dx = 0;
        dy = 0;
    }

    private void dispatchCallback() {
        if (mSpriteSheetFinishCallback != null) {
            mSpriteSheetFinishCallback.call();
            if (!spriteLoop || spriteLoopNum > 0) {
                //　SpriteSheetFinishCallback is called only once.
                mSpriteSheetFinishCallback = null;
            }
        }
    }

    /**
     * Move frame of SpriteSheet by customFrameList
     */
    protected void updateCustomFrame() {

        if (currentFrame > customFrameList.size()) {
            if (!spriteLoop) return;
            currentFrame = Constant.DEFAULT_CURRENT_FRAME;
        }

        int currentNum = customFrameList.get(currentFrame - 1);

        dx = frameWidth * (currentNum % frameNumPerLine);
        dy = frameHeight * (currentNum / frameNumPerLine);

        currentFrame++;

    }

}
