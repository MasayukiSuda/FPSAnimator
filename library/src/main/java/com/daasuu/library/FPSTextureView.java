package com.daasuu.library;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.SurfaceTexture;
import android.util.AttributeSet;
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

    private List<Anim> mAnimList = new ArrayList<>();

    public FPSTextureView(Context context) {
        this(context, null, 0);
    }

    public FPSTextureView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FPSTextureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setOpaque(false);
        setSurfaceTextureListener(this);
    }


    public FPSTextureView tickStart() {
        stop();
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                onTick();
            }
        }, 0, 1000 / mFps);
        return this;
    }

    public void stop() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        // do nothing
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
            stop();
            return false;
        }
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        // do nothing
    }

    private void onTick() {

        synchronized (this) {

            List<Anim> copyAnimList = new ArrayList<Anim>(mAnimList);

            Canvas canvas = this.lockCanvas();
            if (canvas == null) return;

            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

            for (Anim anim : copyAnimList) {
                if (anim == null) {
                    continue;
                }
                anim.draw(canvas);
            }

            this.unlockCanvasAndPost(canvas);
        }

    }

    public FPSTextureView addChild(Anim anim) {
        anim.setFps(mFps);
        anim.setUp();
        mAnimList.add(anim);
        return this;
    }

    public FPSTextureView removeChild(Anim anim) {
        mAnimList.remove(anim);
        return this;
    }

    public FPSTextureView setFps(long fps) {
        this.mFps = fps;
        return this;
    }

    public List<Anim> getAnimList() {
        return mAnimList;
    }
}
