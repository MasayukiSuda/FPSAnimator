package com.daasuu.library.spritesheet;

import com.daasuu.library.callback.AnimCallBack;
import com.daasuu.library.constant.Constant;


public class SpriteSheet {

    public float frameWidth;
    public float frameHeight;
    public int frameNum;

    // The number of which frame, there is about line 1 of side
    public int frameNumPerLine;

    public boolean spriteLoop = false;

    public float dx = 0;
    public float dy = 0;
    public int currentPosition = Constant.DEFAULT_CURRENT_POSITION;

    private AnimCallBack mSpriteSheetFinishCallback;

    /**
     * constructor
     *
     * @param frameWidth
     * @param frameHeight
     * @param frameNum
     * @param frameNumPerLine
     */
    public SpriteSheet(float frameWidth, float frameHeight, int frameNum, int frameNumPerLine) {
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        this.frameNum = frameNum;
        this.frameNumPerLine = frameNumPerLine;
    }


    public void setSpriteSheetFinishCallback(AnimCallBack spriteSheetFinishCallback) {
        this.mSpriteSheetFinishCallback = spriteSheetFinishCallback;
    }

    /**
     * Move position of SpriteSheet
     */
    public void updatePosition() {
        if (currentPosition > frameNum + 2) return;

        boolean edge = currentPosition % frameNumPerLine == 0;
        if (edge) {
            // It falls under the case of the end
            currentPosition++;
            if (currentPosition <= frameNum) {
                dy -= frameHeight;
                dx = 0;
            }
            repeatPosition();
            return;
        }

        currentPosition++;
        if (currentPosition <= frameNum) {
            dx -= frameWidth;
        }
        repeatPosition();

    }

    /**
     * Repeat position of SpriteSheet
     */
    protected void repeatPosition() {
        if (currentPosition != frameNum) return;

        if (spriteLoop) {
            currentPosition = Constant.DEFAULT_CURRENT_POSITION;
            dx = 0;
            dy = 0;
        } else {
            currentPosition = frameNum;
        }

        if (mSpriteSheetFinishCallback != null) {
            mSpriteSheetFinishCallback.call();
            if (!spriteLoop) {
                //　SpriteSheetFinishCallback is called only once.
                mSpriteSheetFinishCallback = null;
            }
        }

    }

}
