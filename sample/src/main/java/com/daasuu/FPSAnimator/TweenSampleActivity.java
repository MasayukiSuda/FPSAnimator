package com.daasuu.FPSAnimator;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.daasuu.FPSAnimator.util.UIUtil;
import com.daasuu.library.DisplayObject2;
import com.daasuu.library.FPSTextureView;
import com.daasuu.library.callback.AnimCallBack;
import com.daasuu.library.easing.Ease;
import com.daasuu.library.painter.BitmapPainter;
import com.daasuu.library.painter.TextPainter;
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
        DisplayObject2 bitmapDisplayA = new DisplayObject2();
        bitmapDisplayA
                .with(new BitmapPainter(bitmap))
                .tween()
                .toX(1600, UIUtil.getWindowWidth(this) - bitmap.getWidth(), Ease.BACK_IN_OUT)
                .waitTime(1000)
                .alpha(1000, 0f)
                .alpha(1000, 1f)
                .call(new AnimCallBack() {
                    @Override
                    public void call() {
                        Snackbar.make(mFPSTextureView, "BitmapA animation finished!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                })
                .build();

        DisplayObject2 bitmapDisplayB = new DisplayObject2();
        bitmapDisplayB.with(new BitmapPainter(bitmap)
                .dpSize(this)
                .scaleRegistration(Util.convertPixelsToDp(bitmap.getWidth(), this) / 2, Util.convertPixelsToDp(bitmap.getHeight(), this) / 2))
                .tween()
                .tweenLoop(true)
                .transform(300, 400)
                .to(500, 300, 400, 0, 6f, 6f, 0, Ease.SINE_IN_OUT)
                .waitTime(300)
                .transform(300, 400, 1, 1f, 1f, 0)
                .waitTime(300)
                .build();


        Paint paint = new Paint();
        paint.setColor(ContextCompat.getColor(this, R.color.colorAccent));
        paint.setTextSize(Util.convertDpToPixel(16, this));

        String tweenTxt = "TweenText";
        DisplayObject2 textDisplay = new DisplayObject2();
        textDisplay.with(new TextPainter(tweenTxt, paint).rotateRegistration(paint.measureText(tweenTxt) / 2, paint.getTextSize() / 2))
                .tween()
                .tweenLoop(true)
                .transform(0, 800)
                .waitTime(300)
                .to(1000, UIUtil.getWindowWidth(this) - paint.measureText(tweenTxt), 800, 720f, Ease.SINE_OUT)
                .waitTime(300)
                .to(1000, 0, 1000, 0f, Ease.SINE_IN)
                .build();

        mFPSTextureView
                .addChild(bitmapDisplayA)
                .addChild(bitmapDisplayB)
                .addChild(textDisplay);

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
