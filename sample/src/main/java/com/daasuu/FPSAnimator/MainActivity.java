package com.daasuu.FPSAnimator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button tweenSample = (Button) findViewById(R.id.tween_sample);
        tweenSample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TweenSampleActivity.startActivity(v.getContext());
            }
        });

        Button easingSample = (Button) findViewById(R.id.easing_sample);
        easingSample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EasingSampleActivity.startActivity(v.getContext());
            }
        });

        Button spriteSheetSample = (Button) findViewById(R.id.sprite_sheet_sample);
        spriteSheetSample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpriteSheetSampleActivity.startActivity(v.getContext());
            }
        });

        Button parabolicMotionSample = (Button) findViewById(R.id.parabolic_motion_sample);
        parabolicMotionSample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParabolicMotionSampleActivity.startActivity(v.getContext());
            }
        });

        Button animationStopSample = (Button) findViewById(R.id.animation_stop_sample);
        animationStopSample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimationPauseSampleActivity.startActivity(v.getContext());
            }
        });

        Button surfaceSample = (Button) findViewById(R.id.surface_sample);
        surfaceSample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FPSSurfaceViewSampleActivity.startActivity(v.getContext());
            }
        });

    }


}
