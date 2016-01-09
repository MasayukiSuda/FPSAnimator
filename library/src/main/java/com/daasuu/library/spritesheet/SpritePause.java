package com.daasuu.library.spritesheet;

/**
 * Interface to force the SpritePause
 */
public interface SpritePause {

    /**
     * indicates whether to start the SpriteAnimation paused.
     *
     * @param pause If true, SpriteAnimation pause.
     */
    public void spritePause(boolean pause);

    /**
     * Getter SpriteAnimation pause state
     *
     * @return spritePause
     */
    public boolean isSpritePause();
}
