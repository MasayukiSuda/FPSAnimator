package com.daasuu.FPSAnimator;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.daasuu.FPSAnimator.util.UIUtil;
import com.daasuu.library.FPSTextureView;
import com.daasuu.library.callback.AnimCallBack;
import com.daasuu.library.parabolicmotion.ParabolicMotionSpriteSheet;
import com.daasuu.library.spritesheet.UpdatePositionListener;
import com.daasuu.library.tween.TweenSpriteSheet;
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

        Bitmap baseSpriteBitmapB = BitmapFactory.decodeResource(getResources(), R.drawable.spritesheet_grant);
        Bitmap spriteBitmapB = Bitmap.createScaledBitmap(
                baseSpriteBitmapB,
                (int) Util.convertDpToPixel(1024f, this),
                (int) Util.convertDpToPixel(1024f, this),
                false);

        TweenSpriteSheet tweenSpriteSheetB = new TweenSpriteSheet(
                spriteBitmapB,
                Util.convertDpToPixel(82.875f, this),
                Util.convertDpToPixel(146.25f, this),
                64,
                12)
                .spriteLoop(true)
                .loop(true)
                .transform(-Util.convertDpToPixel(82.875f, this), UIUtil.getWindowHeight(this) / 2)
                .toX(3000, UIUtil.getWindowWidth(this));


        float frameWidth = Util.convertDpToPixel(82.875f, this);
        float frameHeight = Util.convertDpToPixel(146.25f, this);


        final ParabolicMotionSpriteSheet parabolicMotion = new ParabolicMotionSpriteSheet(
                spriteBitmapB,
                frameWidth,
                frameHeight,
                64,
                12
        );
        parabolicMotion
                .dpSize(this)
                .transform(0, UIUtil.getWindowHeight(this) / 2)
                .initialVelocityY(-30)
                .coefficientRight(false)
                .rightHitCallback(new AnimCallBack() {
                    @Override
                    public void call() {
                        mFPSTextureView.removeChild(parabolicMotion);
                    }
                })
                .updatePositionListener(new UpdatePositionListener() {
                    @Override
                    public void update(float dx, float dy, int currentPosition) {

                        if (parabolicMotion.currentPosition < 26) {
                            parabolicMotion.currentPosition = 26;
                        }

                        boolean edge = parabolicMotion.currentPosition % 12 == 0;
                        if (edge) {
                            parabolicMotion.dy -= parabolicMotion.frameHeight;
                            parabolicMotion.dx = 0;
                            parabolicMotion.currentPosition++;
                            return;
                        }
                        parabolicMotion.dx -= parabolicMotion.frameWidth;
                        parabolicMotion.currentPosition++;
                    }
                });


        mFPSTextureView
                .setFps(24)
                .addChild(parabolicMotion)
                .addChild(tweenSpriteSheetB);
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

                final TweenSpriteSheet tweenSpriteSheetA = new TweenSpriteSheet(
                        spriteBitmapA,
                        spriteBitmapA.getWidth() / spriteBitmapANum,
                        spriteBitmapA.getHeight(),
                        spriteBitmapANum,
                        spriteBitmapANum)
                        .frequency((int) (1 + Math.random() * 3))
                        .transform(i * interval, j * interval)
                        .spriteLoop(true);

                mFPSTextureView.addChild(tweenSpriteSheetA);

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
        mFPSTextureView.stop();
    }
}
