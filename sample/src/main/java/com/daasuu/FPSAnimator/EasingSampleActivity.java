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
import com.daasuu.library.DisplayObject;
import com.daasuu.library.FPSTextureView;
import com.daasuu.library.drawer.BitmapDrawer;
import com.daasuu.library.drawer.TextDrawer;
import com.daasuu.library.easing.Ease;
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

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        float bitmapInitialX = paint.measureText(Ease.BOUNCE_IN_OUT.name()) + 50;


        for (Ease ease : Ease.values()) {

            float initialY = paint.getTextSize() * cnt * 1.3f;

            DisplayObject easeName = new DisplayObject();
            easeName.with(new TextDrawer(ease.name(), paint))
                    .tween()
                    .transform(0, initialY)
                    .end();


            DisplayObject bitmapTween = new DisplayObject();
            bitmapTween.with(new BitmapDrawer(bitmap).dpSize(this))
                    .tween()
                    .tweenLoop(true)
                    .transform(bitmapInitialX, initialY)
                    .waitTime(1000)
                    .toX(1000, UIUtil.getWindowWidth(this) - Util.convertPixelsToDp(bitmap.getWidth(), this), ease)
                    .waitTime(1000)
                    .toX(1000, bitmapInitialX, ease)
                    .end();


            mFPSTextureView
                    .addChild(easeName)
                    .addChild(bitmapTween);

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
