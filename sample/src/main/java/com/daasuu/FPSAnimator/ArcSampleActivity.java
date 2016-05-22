package com.daasuu.FPSAnimator;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.daasuu.library.DisplayObject;
import com.daasuu.library.FPSTextureView;
import com.daasuu.library.drawer.BitmapDrawer;

public class ArcSampleActivity extends AppCompatActivity {

    private FPSTextureView mFPSTextureView;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, ArcSampleActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arc_sample);
        mFPSTextureView = (FPSTextureView) findViewById(R.id.animation_texture_view);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);

        DisplayObject bitmapDisplayA = new DisplayObject();
        bitmapDisplayA
                .with(new BitmapDrawer(bitmap))
                .tween()
                .tweenLoop(true)
                .transform(100, 400)
                .waitTime(300)
                .arc(1000, 200, 100, 140)
                .arc(1000, 400, 600, 140)
                .arc(1000, 100, 120, 140)
                .arc(1000, 800, 1000, 140)
                .arc(1000, 600, 500, 140)
                .waitTime(1300)
                .end();

        DisplayObject bitmapDisplayB = new DisplayObject();
        bitmapDisplayB
                .with(new BitmapDrawer(bitmap))
                .tween()
                .tweenLoop(true)
                .transform(100, 400)
                .waitTime(300)
                .to(1000, 200, 100)
                .to(1000, 400, 600)
                .to(1000, 100, 120)
                .to(1000, 800, 1000)
                .to(1000, 600, 500)
                .waitTime(300)
                .end();


        mFPSTextureView
                //.addChild(bitmapDisplayB)
                .addChild(bitmapDisplayA);

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
