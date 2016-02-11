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
import android.util.Log;

import com.daasuu.FPSAnimator.util.UIUtil;
import com.daasuu.library.Container;
import com.daasuu.library.DisplayObject;
import com.daasuu.library.FPSTextureView;
import com.daasuu.library.callback.AnimCallBack;
import com.daasuu.library.drawer.BitmapDrawer;
import com.daasuu.library.drawer.SpriteSheetDrawer;
import com.daasuu.library.drawer.TextDrawer;
import com.daasuu.library.easing.Ease;
import com.daasuu.library.util.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ContainerSampleActivity extends AppCompatActivity {

    private FPSTextureView mFPSTextureView;

    private Container container1 = new Container();
    private Container container2 = new Container();

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, ContainerSampleActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container_sample);
        mFPSTextureView = (FPSTextureView) findViewById(R.id.animation_texture_view);

        float container2Height = UIUtil.getWindowHeight(this) / 2;
        container2
                .with()
                .tween()
                .waitTime(3000)
                .toY(2000, container2Height)
                .waitTime(3000)
                .toY(2000, 0)
                .tweenLoop(true)
                .end();

        mFPSTextureView.addChild(container1);
        mFPSTextureView.addChild(container2);

        setUpContainerChild(container1);
        setUpContainerChild(container2);
    }

    private void setUpContainerChild(Container container) {

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);

        float bitmapX = 300;
        float bitmapY = 400;
        DisplayObject bitmapDisplay = new DisplayObject();
        bitmapDisplay.with(new BitmapDrawer(bitmap)
                .dpSize(this)
                .scaleRegistration(Util.convertPixelsToDp(bitmap.getWidth(), this) / 2, Util.convertPixelsToDp(bitmap.getHeight(), this) / 2))
                .tween()
                .tweenLoop(true)
                .transform(bitmapX, bitmapY)
                .to(500, bitmapX, bitmapY, 0, 6f, 6f, 0, Ease.SINE_IN_OUT)
                .waitTime(300)
                .transform(bitmapX, bitmapY, Util.convertAlphaFloatToInt(1f), 1f, 1f, 0)
                .waitTime(300)
                .end();


        float frameWidth = Util.convertDpToPixel(82.875f, this);
        float frameHeight = Util.convertDpToPixel(146.25f, this);

        Bitmap baseSpriteBitmapB = BitmapFactory.decodeResource(getResources(), R.drawable.spritesheet_grant);
        Bitmap spriteBitmapB = Bitmap.createScaledBitmap(
                baseSpriteBitmapB,
                (int) Util.convertDpToPixel(1024f, this),
                (int) Util.convertDpToPixel(1024f, this),
                false);

        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i <= 26; i++) list.add(i);

        SpriteSheetDrawer spriteSheetDrawer = new SpriteSheetDrawer(spriteBitmapB,
                frameWidth,
                frameHeight,
                64,
                12)
                .dpSize(this)
                .customFrameList(list)
                .spriteLoop(true);

        DisplayObject tweenSprite = new DisplayObject();
        tweenSprite.with(spriteSheetDrawer)
                .tween()
                .tweenLoop(true)
                .toX(2000, 400)
                .scale(0, -1f, 1)
                .toX(2000, 0)
                .scale(0, 1f, 1)
                .end();


        Paint paint = new Paint();
        paint.setColor(ContextCompat.getColor(this, R.color.colorAccent));
        paint.setTextSize(Util.convertDpToPixel(16, this));

        String tweenTxt = "container sample";
        DisplayObject tweenText = new DisplayObject();
        tweenText.with(new TextDrawer(tweenTxt, paint)
                .rotateRegistration(paint.measureText(tweenTxt) / 2, paint.getTextSize() / 2))
                .tween()
                .tweenLoop(true)
                .transform(UIUtil.getWindowWidth(this) / 2, 0)
                .to(2000, UIUtil.getWindowWidth(this) - paint.measureText(tweenTxt), 500, 0f, Ease.SINE_OUT)
                .to(2000, UIUtil.getWindowWidth(this) / 2, 0, 0f, Ease.SINE_IN)
                .end();


        container
                .addChild(tweenSprite)
                .addChild(bitmapDisplay)
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
        mFPSTextureView.tickStop();
    }
}
