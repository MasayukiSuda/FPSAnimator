# FPSAnimator
[![License](https://img.shields.io/badge/license-Apache%202-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0)
[![Platform](https://img.shields.io/badge/platform-android-green.svg)](http://developer.android.com/index.html)

A simple but powerful Tween / SpriteSheet / ParabolicMotion / animation library for Android TextureView and SurfaceView.

# Features
* The controls Fps possible to animation.
* Possible for animation of Tween and SpriteSheet and ParabolicMotion in one of TextureView.
* Possible to animate with low load on the terminal.
* 28 types of Easing

# Basic Usage
Gradle
```groovy
    dependencies {
        // jCenter
        compile 'com.daasuu:FPSAnimator:0.0.8'
    }
```
Include the FPSTextureView widget in your layout.
```xml
    <com.daasuu.library.FPSTextureView
        android:id="@+id/animation_texture_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />
```
In your onCreate method (or onCreateView for a fragment), bind the widget.
```JAVA
    private FPSTextureView mFPSTextureView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easing_sample);
        mFPSTextureView = (FPSTextureView) findViewById(R.id.animation_texture_view);
    }
```
Create an instance of the Tween, please add it to the FPSTextureView.
```JAVA
    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
    
    TweenBitmap tweenBitmap = new TweenBitmap(bitmap);
    tweenBitmap
            .toX(1600, windowWidth - bitmap.getWidth(), Ease.BACK_IN_OUT)
            .waitTime(1000)
            .alpha(1000, 0f)
            .alpha(1000, 1f);
            
    mFPSTextureView
                .setFps(24)
                .addChild(tweenBitmap)
                .tickStart();
```
<img src="art/tweenBitmapSampleDemo.gif" width="32%">

The example above will create a new tween instance that:
* tweens the target to an x value of rightSide over 1600ms
* waits 1000 ms
* tweens the target's alpha from  1 to 0 over 1s
* tweens the target's alpha from 0 to 1 over 1s

# Example
This is the implementation of the sample application.

#### SpriteSheet
<img src="art/spriteSheet.gif" width="50%">


```JAVA
    
    TweenSpriteSheet tweenSpriteSheet = new TweenSpriteSheet(
            spriteBitmap,
            frameWidth,
            frameHeight,
            frameNum,
            frameNumPerLine)
            .spriteLoop(true)
            .loop(true)
            .transform(frameWidth, windowHeight / 2)
            .toX(3000, windowWidth);

    mFPSTextureView
                .setFps(24)
                .addChild(tweenSpriteSheet);
```


#### TweenText
<img src="art/tweenTextDemo.gif" width="50%">
```JAVA
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
            .to(1000, windowWidth - textWidth, 800, Ease.SINE_OUT, 720)
            .waitTime(300)
            .to(1000, 0, 800, Ease.SINE_IN, 0);
```

#### TweenBitmap
<img src="art/scaleAndAlpha.gif" width="16%">
```JAVA
    TweenBitmap tweenBitmap = new TweenBitmap(bitmap);
    tweenBitmap
            .dpSize(context)
            .scaleRegistration(bitmap.getWidth() / 2, bitmap.getHeight() / 2)
            .loop(true)
            .transform(300, 400)
            .to(500, 300, 400, 0, 6f, 6f, 0, Ease.SINE_IN_OUT)
            .waitTime(300)
            .transform(300, 400, 1, 1f, 1f, 0)
            .waitTime(300);
```

#### ParabolicMotion
<img src="art/parabolicDemo.gif" width="50%">

```JAVA
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easing_sample);
        mFPSTextureView = (FPSTextureView) findViewById(R.id.animation_texture_view);
        
        // ParabolicMotionText
        Paint paint = new Paint();
        paint.setColor(ContextCompat.getColor(context, R.color.colorPrimary));
        paint.setTextSize(Util.convertDpToPixel(20, context));
        
        ParabolicMotionText parabolicMotionText = new ParabolicMotionText("Text", paint);
        parabolicMotionText
                .transform(800, 800)
                .initialVelocityY(-40);

        mFPSTextureView.addChild(parabolicMotionText);
    }

    private void createParabolicMotionBitmap() {
        ParabolicMotionBitmap parabolicMotionBitmap = new ParabolicMotionBitmap(mBitmap);
        parabolicMotionBitmap
                .transform(0, mFPSTextureView.getHeight())
                .dpSize(context)
                .reboundBottom(false)
                .accelerationX((float) (15 + Math.random() * 7))
                .initialVelocityY((float) (-65 + Math.random() * 15))
                .bottomHitCallback(new AnimCallBack() {
                    @Override
                    public void call() {
                        mFPSTextureView.removeChild(parabolicMotionBitmap);
                    }
                });

        mFPSTextureView.addChild(parabolicMotionBitmap);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFPSTextureView.tickStart();

        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    createParabolicMotionBitmap();
                }
            }
        }, 0, 100);
    }

```

# Easing
<img src="art/easingDemo.gif" width="80%">

## License
    Copyright 2016 MasayukiSuda

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
