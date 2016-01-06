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
import com.daasuu.library.FPSTextureView;
import com.daasuu.library.callback.AnimCallBack;
import com.daasuu.library.easing.Ease;
import com.daasuu.library.tween.TweenBitmap;
import com.daasuu.library.tween.TweenText;
import com.daasuu.library.util.Util;

public class TweenSampleActivity extends AppCompatActivity {

    private FPSTextureView mFPSTextureView;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, TweenSampleActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tween_sample);
        mFPSTextureView = (FPSTextureView) findViewById(R.id.animation_texture_view);

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
                        Snackbar.make(mFPSTextureView, "Animation Callback", Snackbar.LENGTH_LONG)
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
                .transform(300, 400, 1, 1f, 1f, 0)
                .waitTime(300);


        Paint paint = new Paint();
        paint.setColor(ContextCompat.getColor(this, R.color.colorAccent));
        paint.setTextSize(Util.convertDpToPixel(16, this));

        String tweenTxt = "TweenText";
        final TweenText tweenText = new TweenText(tweenTxt, paint);
        tweenText
                .rotateRegistration(paint.measureText(tweenTxt) / 2, paint.getTextSize() / 2)
                .loop(true)
                .transform(0, 800)
                .waitTime(300)
                .to(1000, UIUtil.getWindowWidth(this) - paint.measureText(tweenTxt), 800, Ease.SINE_OUT, 720)
                .waitTime(300)
                .to(1000, 0, 800, Ease.SINE_IN, 0);

        mFPSTextureView
                .addChild(tweenBitmapA)
                .addChild(tweenBitmapB)
                .addChild(tweenText);

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
