package com.daasuu.FPSAnimator;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.daasuu.FPSAnimator.util.UIUtil;
import com.daasuu.library.FPSTextureView;
import com.daasuu.library.easing.Ease;
import com.daasuu.library.parabolicmotion.ParabolicMotionBitmap;
import com.daasuu.library.tween.TweenSpriteSheet;
import com.daasuu.library.tween.TweenText;
import com.daasuu.library.util.Util;

public class AnimationPauseSampleActivity extends AppCompatActivity {

    private FPSTextureView mFPSTextureView;
    private Button mTweenPauseBtn;
    private Button mSpritePauseBtn;
    private Button mParabolicPauseBtn;

    private TweenText mTweenText;
    private TweenSpriteSheet mTweenSpriteSheet;
    private ParabolicMotionBitmap mParabolicMotionBitmap;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, AnimationPauseSampleActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_stop_sample);

        setUpBtns();
        mFPSTextureView = (FPSTextureView) findViewById(R.id.animation_texture_view);


        Paint paint = new Paint();
        paint.setColor(ContextCompat.getColor(this, R.color.colorAccent));
        paint.setTextSize(Util.convertDpToPixel(16, this));
        String tweenTxt = "TweenPause";

        mTweenText = new TweenText(tweenTxt, paint)
                .rotateRegistration(paint.measureText(tweenTxt) / 2, paint.getTextSize() / 2)
                .scaleRegistration(paint.measureText(tweenTxt) / 2, paint.getTextSize() / 2)
                .loop(true)
                .transform(0, 300, 0.5f, 1f, 1f, 0)
                .waitTime(300)
                .to(1500, UIUtil.getWindowWidth(this) - paint.measureText(tweenTxt) * 1.5f, 300, 1f, 2f, 2f, 720, Ease.LINEAR)
                .waitTime(300)
                .to(1500, 0, 300, 0.5f, 1f, 1f, 0, Ease.LINEAR);

        mFPSTextureView
                .addChild(mTweenText);
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

    private void setUpBtns() {
        mTweenPauseBtn = (Button) findViewById(R.id.tween_pause);
        mSpritePauseBtn = (Button) findViewById(R.id.sprite_sheet_pause);
        mParabolicPauseBtn = (Button) findViewById(R.id.parabolic_pause);

        mTweenPauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTweenText.isTweenPause()) {
                    mTweenText.tweenPause(false);
                    mTweenPauseBtn.setText("Tween Pause");
                } else {
                    mTweenText.tweenPause(true);
                    mTweenPauseBtn.setText("Tween Play");
                }
            }
        });
    }

}
