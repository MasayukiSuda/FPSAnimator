package com.daasuu.FPSAnimator;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.daasuu.FPSAnimator.UIUtil.UIUtil;
import com.daasuu.library.FPSTextureView;
import com.daasuu.library.parabolicmotion.ParabolicMotionSpriteSheet;
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

        Bitmap spriteBitmapA = BitmapFactory.decodeResource(getResources(), R.drawable.spritesheet_sparkle);
        int spriteBitmapANum = 13;
        TweenSpriteSheet tweenSpriteSheetA = new TweenSpriteSheet(
                spriteBitmapA,
                spriteBitmapA.getWidth() / spriteBitmapANum,
                spriteBitmapA.getHeight(),
                spriteBitmapANum,
                spriteBitmapANum);
        tweenSpriteSheetA
                .frequency(2)
                .transform(300f, 400f)
                .spriteLoop(true);

        Bitmap baseSpriteBitmapB = BitmapFactory.decodeResource(getResources(), R.drawable.spritesheet_grant);
        Bitmap spriteBitmapB = Bitmap.createScaledBitmap(baseSpriteBitmapB, (int) Util.convertDpToPixel(1024f, this), (int) Util.convertDpToPixel(1024f, this), false);

        TweenSpriteSheet tweenSpriteSheetB = new TweenSpriteSheet(
                spriteBitmapB,
                Util.convertDpToPixel(82.875f, this),
                Util.convertDpToPixel(146.25f, this),
                64,
                12);
        tweenSpriteSheetB
                .frequency(1)
                .spriteLoop(true)
                .loop(true)
                .transform(-165.75f, UIUtil.getWindowHeight(this) / 2)
                .toX(3000, UIUtil.getWindowWidth(this));


        ParabolicMotionSpriteSheet parabolicMotionSpriteSheet = new ParabolicMotionSpriteSheet(
                spriteBitmapA,
                spriteBitmapA.getWidth() / spriteBitmapANum,
                spriteBitmapA.getHeight(),
                spriteBitmapANum,
                spriteBitmapANum
        );
        parabolicMotionSpriteSheet
                .transform(800, 800)
                .initialVelocityY(-40);

        mFPSTextureView
                .addChild(parabolicMotionSpriteSheet)
                .addChild(tweenSpriteSheetA)
                .addChild(tweenSpriteSheetB);

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
