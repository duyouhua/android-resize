# 安卓RECT缩放
使用场景:图片剪裁,地图围栏等

## 样式设置
````
    <style tools:ignore="NewApi" name="CustomTheme" parent="AppTheme">
        <item name="highlightViewStyle">@style/Widget.HighlightViewStyle</item>
    </style>

    <style name="Widget.HighlightViewStyle" parent="android:Widget">
        <item name="showThirds">false</item>
        <item name="showCircle">true</item>
        <item name="showHandles">always</item>
        <item name="highlightColor">@color/highlight</item>
    </style>
````

## 示例
HighlightFrame

## 截图
![alt text](https://github.com/haiyangwu/android-resize/blob/master/screenshot/android-resize.png?raw=true "android-resize")