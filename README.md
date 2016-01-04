# FPSAnimator
[![License](https://img.shields.io/badge/license-Apache%202-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0)
[![Platform](https://img.shields.io/badge/platform-android-green.svg)](http://developer.android.com/index.html)

A simple but powerful tweening / SpriteSheet / ParabolicMotion / animation library for Android TextureView.

# Usage
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
Create Tween instance and Add FPSTextureView.
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
