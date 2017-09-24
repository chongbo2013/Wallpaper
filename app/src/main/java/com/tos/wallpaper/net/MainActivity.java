package com.tos.wallpaper.net;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {
    ImageView iv;
    String img="https://raw.githubusercontent.com/chongbo2013/Wallpaper/master/app/src/main/assets/wallpaper/wallpaper1/wallpaper.png";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv= (ImageView) findViewById(R.id.iv);
        //
        Glide.with(this)
                .load(img)  //可以从本地图片资源读取，也可以从Url去加载。
                .crossFade()//显示渐变效果
                .into(iv);
    }
}
