package com.daasuu.FPSAnimator;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.daasuu.FPSAnimator.util.UIUtil;
import com.daasuu.library.DisplayObject;
import com.daasuu.library.FPSTextureView;
import com.daasuu.library.drawer.SpriteSheetDrawer;
import com.daasuu.library.drawer.TextDrawer;
import com.daasuu.library.easing.Ease;
import com.daasuu.library.util.Util;

public class AnimationPauseSampleActivity extends AppCompatActivity {

    private FPSTextureView mFPSTextureView;
    private Button mTweenPauseBtn;
    private Button mSpritePauseBtn;
    private Button mParabolicPauseBtn;

    private DisplayObject mTweenText;
    private DisplayObject mTweenSpriteSheet;
    private DisplayObject mParabolicMotionText;
    private SpriteSheetDrawer mSpriteSheetDrawer;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, AnimationPauseSampleActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_pause_sample);

        setUpBtns();
        mFPSTextureView = (FPSTextureView) findViewById(R.id.animation_texture_view);

        Paint paint = new Paint();
        paint.setColor(ContextCompat.getColor(this, R.color.colorAccent));
        paint.setTextSize(Util.convertDpToPixel(16, this));
        String tweenTxt = "TweenPause";

        TextDrawer textDrawer = new TextDrawer(tweenTxt, paint)
                .rotateRegistration(paint.measureText(tweenTxt) / 2, paint.getTextSize() / 2)
                .scaleRegistration(paint.measureText(tweenTxt) / 2, paint.getTextSize() / 2);

        mTweenText = new DisplayObject();
        mTweenText.with(textDrawer)
                .tween()
                .tweenLoop(true)
                .transform(0, 300, Util.convertAlphaFloatToInt(0.3f), 1f, 1f, 0)
                .waitTime(300)
                .to(1500, UIUtil.getWindowWidth(this) - paint.measureText(tweenTxt) * 1.5f, 300, Util.convertAlphaFloatToInt(1f), 2f, 2f, 720, Ease.LINEAR)
                .waitTime(300)
                .to(1500, 0, 300, Util.convertAlphaFloatToInt(0.3f), 1f, 1f, 0, Ease.LINEAR)
                .end();


        float frameWidth = Util.convertDpToPixel(82.875f, this);
        float frameHeight = Util.convertDpToPixel(146.25f, this);

        Bitmap baseSpriteBitmapB = BitmapFactory.decodeResource(getResources(), R.drawable.spritesheet_grant);
        Bitmap spriteBitmapB = Bitmap.createScaledBitmap(
                baseSpriteBitmapB,
                (int) Util.convertDpToPixel(1024f, this),
                (int) Util.convertDpToPixel(1024f, this),
                false);

        mSpriteSheetDrawer = new SpriteSheetDrawer(spriteBitmapB,
                frameWidth,
                frameHeight,
                64,
                12)
                .spriteLoop(true);

        mTweenSpriteSheet = new DisplayObject();
        mTweenSpriteSheet.with(mSpriteSheetDrawer)
                .tween()
                .tweenLoop(true)
                .transform(-Util.convertDpToPixel(82.875f, this), UIUtil.getWindowHeight(this) / 3)
                .toX(3000, UIUtil.getWindowWidth(this))
                .end();

        Paint paint2 = new Paint();
        paint2.setColor(ContextCompat.getColor(this, R.color.colorPrimary));
        paint2.setTextSize(Util.convertDpToPixel(16, this));
        mParabolicMotionText = new DisplayObject();
        mParabolicMotionText.with(new TextDrawer("ParabolicMotion", paint2))
                .parabolic()
                .transform(UIUtil.getWindowWidth(this) / 2, UIUtil.getWindowHeight(this) / 2)
                .initialVelocityY(-30)
                .end();

        mFPSTextureView
                .addChild(mTweenText)
                .addChild(mParabolicMotionText)
                .addChild(mTweenSpriteSheet);
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

    private void setUpBtns() {
        mTweenPauseBtn = (Button) findViewById(R.id.tween_pause);
        mSpritePauseBtn = (Button) findViewById(R.id.sprite_sheet_pause);
        mParabolicPauseBtn = (Button) findViewById(R.id.parabolic_pause);

        mTweenPauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTweenText.isPause()) {
                    mTweenText.pause(false);
                    mTweenPauseBtn.setText("Tween Pause");
                } else {
                    mTweenText.pause(true);
                    mTweenPauseBtn.setText("Tween Play");
                }
            }
        });

        mSpritePauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSpriteSheetDrawer.isSpritePause()) {
                    mSpriteSheetDrawer.spritePause(false);
                    mSpritePauseBtn.setText("SpriteSheet Pause");
                } else {
                    mSpriteSheetDrawer.spritePause(true);
                    mSpritePauseBtn.setText("SpriteSheet Play");
                }
            }
        });

        mParabolicPauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mParabolicMotionText.isPause()) {
                    mParabolicMotionText.pause(false);
                    mParabolicPauseBtn.setText("ParabolicMotion Pause");
                } else {
                    mParabolicMotionText.pause(true);
                    mParabolicPauseBtn.setText("ParabolicMotion Play");
                }
            }
        });
    }

}
