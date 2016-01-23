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
import com.daasuu.library.DisplayObject;
import com.daasuu.library.FPSTextureView;
import com.daasuu.library.callback.AnimCallBack;
import com.daasuu.library.drawer.BitmapDrawer;
import com.daasuu.library.drawer.TextDrawer;
import com.daasuu.library.easing.Ease;
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
        DisplayObject bitmapDisplayA = new DisplayObject();
        bitmapDisplayA
                .with(new BitmapDrawer(bitmap))
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
                .end();

        BitmapDrawer bitmapDrawer = new BitmapDrawer(bitmap)
                .dpSize(this)
                .scaleRegistration(Util.convertPixelsToDp(bitmap.getWidth(), this) / 2, Util.convertPixelsToDp(bitmap.getHeight(), this) / 2);

        DisplayObject bitmapDisplayB = new DisplayObject();
        bitmapDisplayB.with(bitmapDrawer)
                .tween()
                .tweenLoop(true)
                .transform(300, 400)
                .to(500, 300, 400, 0, 6f, 6f, 0, Ease.SINE_IN_OUT)
                .waitTime(300)
                .transform(300, 400, Util.convertAlphaFloatToInt(1f), 1f, 1f, 0)
                .waitTime(300)
                .end();


        Paint paint = new Paint();
        paint.setColor(ContextCompat.getColor(this, R.color.colorAccent));
        paint.setTextSize(Util.convertDpToPixel(16, this));

        String tweenTxt = "TweenText";
        TextDrawer textDrawer = new TextDrawer(tweenTxt, paint)
                .rotateRegistration(paint.measureText(tweenTxt) / 2, paint.getTextSize() / 2);

        DisplayObject textDisplay = new DisplayObject();
        textDisplay.with(textDrawer)
                .tween()
                .tweenLoop(true)
                .transform(0, 800)
                .waitTime(300)
                .to(1000, UIUtil.getWindowWidth(this) - paint.measureText(tweenTxt), 800, 720f, Ease.SINE_OUT)
                .waitTime(300)
                .to(1000, 0, 800, 0f, Ease.SINE_IN)
                .end();

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
