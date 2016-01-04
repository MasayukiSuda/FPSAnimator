# FPSAnimator
[![License](https://img.shields.io/badge/license-Apache%202-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0)
[![Platform](https://img.shields.io/badge/platform-android-green.svg)](http://developer.android.com/index.html)

A simple but powerful tweening / SpriteSheet / ParabolicMotion / animation library for Android TextureView.

# Basic Usage
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
    TweenBitmap tweenBitmapA = new TweenBitmap(bitmap);
    tweenBitmapA
            .toX(1600, UIUtil.getWindowWidth(this) - bitmap.getWidth(), Ease.BACK_IN_OUT)
            .waitTime(1000)
            .alpha(1000, 0f)
            .alpha(1000, 1f);
            
    mFPSTextureView.addChild(tweenBitmapA);
    mFPSTextureView.tickStart();
```
<img src="art/tweenBitmapSampleDemo.gif" width="32%">

The example above will create a new tween instance that:
* tweens the target to an x value of rightSide over 1600ms
* waits 1000 ms
* tweens the target's alpha from  1 to 0 over 1s
* tweens the target's alpha from 0 to 1 over 1s

# Easing Demo
<img src="art/easingDemo.gif" width="50%">


# Example

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
            .to(1000, UIUtil.getWindowWidth(this) - paint.measureText(tweenTxt), 800, Ease.SINE_OUT, 720)
            .waitTime(300)
            .to(1000, 0, 800, Ease.SINE_IN, 0);
```

#### TweenBitmap
<img src="art/scaleAndAlpha.gif" width="16%">
```JAVA
    TweenBitmap tweenBitmapB = new TweenBitmap(bitmap);
    tweenBitmapB
            .dpSize(this)
            .scaleRegistration(Util.convertPixelsToDp(bitmap.getWidth(), this) / 2, Util.convertPixelsToDp(bitmap.getHeight(), this) / 2)
            .loop(true)
            .transform(300, 400)
            .to(500, 300, 400, 0, 6f, 6f, 0, Ease.SINE_IN_OUT)
            .waitTime(300)
            .to(0, 300, 400, 1, 1f, 1f, 0, Ease.LINEAR)
            .waitTime(300);
```



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
