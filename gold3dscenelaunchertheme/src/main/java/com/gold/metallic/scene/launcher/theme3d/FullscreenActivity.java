package com.gold.metallic.scene.launcher.theme3d;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.VideoView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


public class FullscreenActivity extends Activity {
    VideoView video_view;
    private AdView adsWorkspaceMenuBannerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.theme);
        video_view=findViewById(R.id.video_view);
        String uri="android.resource://"+getPackageName()+"/"+R.raw.video;
        video_view.setVideoURI(Uri.parse(uri));
        video_view.start();
        video_view.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
                mp.setLooping(true);
            }
        });
        adsWorkspaceMenuBannerView=findViewById(R.id.adView);
        initAds();
    }
    private void initAds() {
        adsWorkspaceMenuBannerView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("C4AB88E6B69668DF585FA8B83138BCF0").build();
        adsWorkspaceMenuBannerView.loadAd(adRequest);
        adsWorkspaceMenuBannerView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
                // Toast.makeText(getContext(),"error:"+errorCode,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when when the interstitial ad is closed.
                // Load the next interstitial.
                adsWorkspaceMenuBannerView.loadAd(new AdRequest.Builder().addTestDevice("C4AB88E6B69668DF585FA8B83138BCF0").build());
            }

        });

//        Context thdContext = null;
//        try {
//            thdContext = createPackageContext(
//                    "com.example.testdatabase",
//                    Context.CONTEXT_IGNORE_SECURITY);
//            Resources res = thdContext.getResources();
//            int menuIconId = res.getIdentifier("send_bg", "drawable",
//                    "com.example.testdatabase");
//            Drawable drawable = res.getAssets().open()
//            mButton.setBackgroundDrawable(drawable);
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }

    }
    @Override
    protected void onResume() {
        super.onResume();
        adsWorkspaceMenuBannerView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        adsWorkspaceMenuBannerView.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        adsWorkspaceMenuBannerView.destroy();
    }
}
