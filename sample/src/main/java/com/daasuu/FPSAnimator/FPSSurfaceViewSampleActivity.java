package com.daasuu.FPSAnimator;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.daasuu.FPSAnimator.util.UIUtil;
import com.daasuu.library.FPSSurfaceView;
import com.daasuu.library.callback.AnimCallBack;
import com.daasuu.library.easing.Ease;
import com.daasuu.library.parabolicmotion.ParabolicMotionSpriteSheet;
import com.daasuu.library.tween.TweenBitmap;
import com.daasuu.library.tween.TweenSpriteSheet;
import com.daasuu.library.tween.TweenText;
import com.daasuu.library.util.Util;

public class FPSSurfaceViewSampleActivity extends AppCompatActivity {

    private FPSSurfaceView mFpsSurfaceView;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, FPSSurfaceViewSampleActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fpssurface_view_sample);

        mFpsSurfaceView = (FPSSurfaceView) findViewById(R.id.fps_surface);


        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        TweenBitmap tweenBitmapA = new TweenBitmap(bitmap);
        tweenBitmapA
                .toX(1600, UIUtil.getWindowWidth(this) - bitmap.getWidth(), Ease.BACK_IN_OUT)
                .waitTime(1000)
                .alpha(1000, 0f)
                .alpha(1000, 1f)
                .call(new AnimCallBack() {
                    @Override
                    public void call() {
                        Snackbar.make(mFpsSurfaceView, "Animation Callback", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                });


        TweenBitmap tweenBitmapB = new TweenBitmap(bitmap);
        tweenBitmapB
                .dpSize(this)
                .scaleRegistration(Util.convertPixelsToDp(bitmap.getWidth(), this) / 2, Util.convertPixelsToDp(bitmap.getHeight(), this) / 2)
                .loop(true)
                .transform(300, 400)
                .to(500, 300, 400, 0, 6f, 6f, 0, Ease.SINE_IN_OUT)
                .waitTime(300)
                .to(0, 300, 400, 1, 1f, 1f, 0, Ease.LINEAR)
                .waitTime(300);


        Paint paint = new Paint();
        paint.setColor(ContextCompat.getColor(this, R.color.colorAccent));
        paint.setTextSize(Util.convertDpToPixel(16, this));

        String tweenTxt = "Better to use FPSTextureView.";
        final TweenText tweenText = new TweenText(tweenTxt, paint);
        tweenText
                .rotateRegistration(paint.measureText(tweenTxt) / 2, paint.getTextSize() / 2)
                .loop(true)
                .transform(0, 800)
                .waitTime(2000)
                .to(1000, UIUtil.getWindowWidth(this) - paint.measureText(tweenTxt), 800, Ease.SINE_OUT, 720)
                .waitTime(2000)
                .to(1000, 0, 800, Ease.SINE_IN, 0);


        float frameWidth = Util.convertDpToPixel(82.875f, this);
        float frameHeight = Util.convertDpToPixel(146.25f, this);

        Bitmap baseSpriteBitmapB = BitmapFactory.decodeResource(getResources(), R.drawable.spritesheet_grant);
        Bitmap spriteBitmapB = Bitmap.createScaledBitmap(
                baseSpriteBitmapB,
                (int) Util.convertDpToPixel(1024f, this),
                (int) Util.convertDpToPixel(1024f, this),
                false);

        TweenSpriteSheet tweenSpriteSheetB = new TweenSpriteSheet(
                spriteBitmapB,
                frameWidth,
                frameHeight,
                64,
                12)
                .spriteLoop(true)
                .loop(true)
                .transform(-Util.convertDpToPixel(82.875f, this), UIUtil.getWindowHeight(this) / 2)
                .toX(3000, UIUtil.getWindowWidth(this));


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
                .reboundRight(false)
                .rightHitCallback(new AnimCallBack() {
                    @Override
                    public void call() {
                        mFpsSurfaceView.removeChild(parabolicMotion);
                    }
                });

        mFpsSurfaceView
                .addChild(parabolicMotion)
                .addChild(tweenSpriteSheetB)
                .addChild(tweenBitmapA)
                .addChild(tweenBitmapB)
                .addChild(tweenText);
    }


    @Override
    protected void onResume() {
        super.onResume();
        mFpsSurfaceView.tickStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mFpsSurfaceView.stop();
    }

}
