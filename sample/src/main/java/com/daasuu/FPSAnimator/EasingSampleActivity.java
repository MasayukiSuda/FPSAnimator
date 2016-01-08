package com.daasuu.FPSAnimator;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.daasuu.FPSAnimator.util.UIUtil;
import com.daasuu.library.FPSTextureView;
import com.daasuu.library.easing.Ease;
import com.daasuu.library.tween.TweenBitmap;
import com.daasuu.library.tween.TweenText;
import com.daasuu.library.util.Util;

public class EasingSampleActivity extends AppCompatActivity {

    private FPSTextureView mFPSTextureView;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, EasingSampleActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easing_sample);
        mFPSTextureView = (FPSTextureView) findViewById(R.id.animation_texture_view);


        int cnt = 0;
        Paint paint = new Paint();
        paint.setColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        paint.setTextSize(Util.convertDpToPixel(12, this));

        for (Ease ease : Ease.values()) {

            float initialY = paint.getTextSize() * cnt * 1.3f;
            TweenText tweenText = new TweenText(ease.name(), paint)
                    .transform(0, initialY);

            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
            float bitmapInitialX = paint.measureText(Ease.BOUNCE_IN_OUT.name()) + 50;
            TweenBitmap tweenBitmap = new TweenBitmap(bitmap)
                    .dpSize(this)
                    .loop(true)
                    .transform(bitmapInitialX, initialY)
                    .waitTime(1000)
                    .toX(1000, UIUtil.getWindowWidth(this) - Util.convertPixelsToDp(bitmap.getWidth(), this), ease)
                    .waitTime(1000)
                    .toX(1000, bitmapInitialX, ease);

            mFPSTextureView
                    .addChild(tweenText)
                    .addChild(tweenBitmap);

            cnt++;
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
