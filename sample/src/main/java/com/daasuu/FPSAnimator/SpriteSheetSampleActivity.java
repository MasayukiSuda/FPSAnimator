package com.daasuu.FPSAnimator;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.daasuu.FPSAnimator.util.UIUtil;
import com.daasuu.library.DisplayObject;
import com.daasuu.library.FPSTextureView;
import com.daasuu.library.callback.AnimCallBack;
import com.daasuu.library.drawer.SpriteSheetDrawer;
import com.daasuu.library.spritesheet.SpriteSheet;
import com.daasuu.library.util.Util;

public class SpriteSheetSampleActivity extends AppCompatActivity {

    private FPSTextureView mFPSTextureView;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, SpriteSheetSampleActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sprite_sheet_sample);
        mFPSTextureView = (FPSTextureView) findViewById(R.id.animation_texture_view);

        createSparkles();

        float frameWidth = Util.convertDpToPixel(82.875f, this);
        float frameHeight = Util.convertDpToPixel(146.25f, this);

        Bitmap baseSpriteBitmapB = BitmapFactory.decodeResource(getResources(), R.drawable.spritesheet_grant);
        Bitmap spriteBitmapB = Bitmap.createScaledBitmap(
                baseSpriteBitmapB,
                (int) Util.convertDpToPixel(1024f, this),
                (int) Util.convertDpToPixel(1024f, this),
                false);

        DisplayObject displayObject = new DisplayObject();
        displayObject
                .with(new SpriteSheetDrawer(spriteBitmapB, frameWidth, frameHeight, 64, 12).spriteLoop(true))
                .tween()
                .tweenLoop(true)
                .transform(-Util.convertDpToPixel(82.875f, this), UIUtil.getWindowHeight(this) / 2)
                .toX(3000, UIUtil.getWindowWidth(this))
                .end();


        OverrideSpriteSheet overrideSpriteSheet = new OverrideSpriteSheet(
                frameWidth,
                frameHeight,
                64,
                12
        );

        final DisplayObject parabolicDisplay = new DisplayObject();
        parabolicDisplay
                .with(
                        new SpriteSheetDrawer(spriteBitmapB, overrideSpriteSheet)
                                .dpSize(this)
                                .spriteLoop(true)
                )
                .parabolic()
                .transform(0, UIUtil.getWindowHeight(this) / 2)
                .initialVelocityY(-30)
                .reboundRight(false)
                .rightHitCallback(new AnimCallBack() {
                    @Override
                    public void call() {
                        mFPSTextureView.removeChild(parabolicDisplay);
                    }
                })
                .end();

        mFPSTextureView
                .setFps(24)
                .addChild(parabolicDisplay)
                .addChild(displayObject);
    }

    private class OverrideSpriteSheet extends SpriteSheet {

        /**
         * constructor
         *
         * @param frameWidth
         * @param frameHeight
         * @param frameNum
         * @param frameNumPerLine
         */
        public OverrideSpriteSheet(float frameWidth, float frameHeight, int frameNum, int frameNumPerLine) {
            super(frameWidth, frameHeight, frameNum, frameNumPerLine);
        }

        @Override
        public void updateFrame() {
            if (currentFrame > frameNum + 2) return;

            if (currentFrame < 26) {
                currentFrame = 26;
            }

            boolean edge = currentFrame % 12 == 0;
            if (edge) {
                currentFrame++;
                if (currentFrame <= frameNum) {
                    dy -= frameHeight;
                    dx = 0;
                }
                repeatFrame();
                return;
            }
            currentFrame++;
            if (currentFrame <= frameNum) {
                dx -= frameWidth;
            }
            repeatFrame();
        }
    }


    private void createSparkles() {


        Bitmap spriteBitmapA = BitmapFactory.decodeResource(getResources(), R.drawable.spritesheet_sparkle);
        int spriteBitmapANum = 13;

        float displayWidth = UIUtil.getWindowWidth(this);
        float displayHeight = UIUtil.getWindowHeight(this) - UIUtil.getStatusBarHeight(this);
        int interval = (int) Util.convertDpToPixel(60, this);
        int intervalWidth = (int) (displayWidth / interval);
        int intervalHeight = (int) (displayHeight / interval);


        for (int i = 0; i <= intervalWidth; i++) {

            for (int j = 0; j <= intervalHeight; j++) {

                SpriteSheetDrawer spriteSheetDrawer = new SpriteSheetDrawer(spriteBitmapA,
                        spriteBitmapA.getWidth() / spriteBitmapANum,
                        spriteBitmapA.getHeight(),
                        spriteBitmapANum,
                        spriteBitmapANum)
                        .spriteLoop(true)
                        .frequency((int) (1 + Math.random() * 3));

                DisplayObject displayObject = new DisplayObject();
                displayObject.with(spriteSheetDrawer)
                        .tween()
                        .transform(i * interval, j * interval)
                        .end();

                mFPSTextureView.addChild(displayObject);

            }

        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        mFPSTextureView.tickStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mFPSTextureView.tickStop();
    }
}
