package com.daasuu.FPSAnimator;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.SweepGradient;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.daasuu.FPSAnimator.util.UIUtil;
import com.daasuu.library.DisplayObject;
import com.daasuu.library.FPSTextureView;
import com.daasuu.library.drawer.CircleDrawer;
import com.daasuu.library.drawer.CustomDrawer;
import com.daasuu.library.drawer.RectDrawer;
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


        final Paint sweepGradientPaint = new Paint();
        int[] colors = {0xFFFF0000, 0xFFFFFF00, 0xFFFF00FF};
        SweepGradient sg = new SweepGradient(UIUtil.getWindowWidth(this) / 2, UIUtil.getWindowHeight(this) / 2, colors, null);
        sweepGradientPaint.setShader(sg);

        final Paint paint1 = new Paint();
        paint1.setShader(sg);
        final Paint paint2 = new Paint();
        paint2.setColor(ContextCompat.getColor(getApplicationContext(), R.color.circle2));
        final Paint paint3 = new Paint();
        paint3.setColor(ContextCompat.getColor(getApplicationContext(), R.color.circle3));

        CustomDrawer customDrawer = new CustomDrawer(new CustomDrawer.CustomDraw() {
            @Override
            public void draw(Canvas canvas, float x, float y, int alpha) {

                paint1.setAlpha(alpha);
                paint2.setAlpha(alpha);
                paint3.setAlpha(alpha);

                canvas.drawCircle(x, y, 100, paint3);
                canvas.drawRect(x - 60, y - 60, x + 60, y + 60, paint2);
                canvas.drawCircle(x, y, 35, paint1);
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

        DisplayObject displayObject1 = new DisplayObject();
        displayObject1.with(customDrawer)
                .parabolic()
                .accelerationX(-7)
                .transform(UIUtil.getWindowWidth(this) / 3, UIUtil.getWindowHeight(this) / 6)
                .end();


        CircleDrawer circleDrawer = new CircleDrawer(sweepGradientPaint, 100)
                .scaleRegistration(100, 100);


        DisplayObject displayObject2 = new DisplayObject();
        displayObject2.with(circleDrawer)
                .parabolic()
                .accelerationX(9)
                .transform(UIUtil.getWindowWidth(this) / 2, UIUtil.getWindowHeight(this) / 4)
                .end();

        DisplayObject displayObject3 = new DisplayObject();
        displayObject3.with(circleDrawer)
                .parabolic()
                .transform(UIUtil.getWindowWidth(this) / 2, UIUtil.getWindowHeight(this) / 4)
                .accelerationX(-9)
                .end();


        DisplayObject displayObject4 = new DisplayObject();
        displayObject4.with(circleDrawer)
                .parabolic()
                .end();


        RectDrawer rectDrawer = new RectDrawer(sweepGradientPaint, 200, 300);
        DisplayObject displayObject5 = new DisplayObject();
        displayObject5.with(rectDrawer)
                .parabolic()
                .transform(UIUtil.getWindowWidth(this) / 2, 0)
                .end();

        DisplayObject displayObject6 = new DisplayObject();
        displayObject6.with(rectDrawer)
                .parabolic()
                .transform(UIUtil.getWindowWidth(this) / 2, 0)
                .accelerationX(-10)
                .initialVelocityY(-5)
                .end();

        mFPSTextureView
                .addChild(displayObject1)
                .addChild(displayObject2)
                .addChild(displayObject3)
                .addChild(displayObject4)
                .addChild(displayObject5)
                .addChild(displayObject6)
        ;

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
