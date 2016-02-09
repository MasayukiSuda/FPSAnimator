package com.daasuu.FPSAnimator;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.daasuu.library.DisplayObject;
import com.daasuu.library.FPSTextureView;
import com.daasuu.library.drawer.CustomDrawer;
import com.daasuu.library.easing.Ease;
import com.daasuu.library.util.Util;

public class CustomDrawerSampleActivity extends AppCompatActivity {

    private FPSTextureView mFPSTextureView;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, CustomDrawerSampleActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_drawer_sample);
        mFPSTextureView = (FPSTextureView) findViewById(R.id.animation_texture_view);

        CustomDrawer customDrawer = new CustomDrawer(new CustomDrawer.CustomDraw() {
            @Override
            public void draw(Canvas canvas, float x, float y, int alpha) {
                Paint paint = new Paint();
                paint.setAlpha(alpha);
                paint.setStyle(Paint.Style.STROKE);
                canvas.drawCircle(x, y, 25, paint); // 半径25
                canvas.drawCircle(x, y, 50, paint); // 半径50
                canvas.drawCircle(x, y, 100, paint); // 半径100
            }

            @Override
            public float getWidth() {
                return 100;
            }

            @Override
            public float getHeight() {
                return 100;
            }
        });

        DisplayObject displayObject = new DisplayObject();
        displayObject.with(customDrawer)
                .tween()
                .tweenLoop(true)
                .transform(400, 400)
                .to(500, 400, 400, 0, 6f, 6f, 0, Ease.SINE_IN_OUT)
                .waitTime(300)
                .transform(400, 400, Util.convertAlphaFloatToInt(1f), 1f, 1f, 0)
                .waitTime(300)
                .end();

        DisplayObject displayObject2 = new DisplayObject();
        displayObject2.with(customDrawer)
                .parabolic()
                .leftSide(100)
                .end();

        mFPSTextureView
                .addChild(displayObject2)
                .addChild(displayObject);

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
