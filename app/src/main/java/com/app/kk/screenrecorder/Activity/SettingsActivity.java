package com.app.kk.screenrecorder.Activity;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityOptionsCompat;

import android.util.Pair;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.kk.screenrecorder.R;
import com.google.android.ads.nativetemplates.NativeTemplateStyle;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdOptions;


public class SettingsActivity extends AppCompatActivity  {

    public LinearLayout quality, rSettings, controls;
    public TextView rate, qua, con;

    private Toolbar toolbar;
  TemplateView template;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);



        toolbar = findViewById(R.id.toolbar2);
        toolbar.setTitle("Settings");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        con = findViewById(R.id.con);
        qua = findViewById(R.id.qua);
        quality = findViewById(R.id.quality);
        rSettings = findViewById(R.id.rSetting);
        controls = findViewById(R.id.controls);
//        rate = findViewById(R.id.rate);

        template = findViewById(R.id.my_template);
        if (InternetConnection.checkConnection(this)) {
            AdLoader adLoader = new AdLoader.Builder(this, Constant.NativeAd)
                    .forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                        @Override
                        public void onNativeAdLoaded(NativeAd nativeAd) {
                            NativeTemplateStyle styles = new
                                    NativeTemplateStyle.Builder().build();

                            template.setStyles(styles);
                            template.setNativeAd(nativeAd);
                        }
                    })
                    .withAdListener(new AdListener() {
                        @Override
                        public void onAdFailedToLoad(LoadAdError adError) {
                        }
                    })
                    .withNativeAdOptions(new NativeAdOptions.Builder()

                            .build())
                    .build();

        }else{
            template.setVisibility(View.INVISIBLE);
        }

        quality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**Shared Element Code*/
//                Intent in = new Intent(SettingsActivity.this, QualityActivity.class);
////                ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(SettingsActivity.this, quality,"qua");
//
//                Pair[] pairs = new Pair[1];
//                pairs[0] = new Pair<View, String>(quality, "quality");
//                pairs[0] = new Pair<View, String>(qua, "qua");
//                ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(SettingsActivity.this, pairs);
//                startActivity(in,activityOptions.toBundle());
//
                Intent myIntent = new Intent(SettingsActivity.this, QualityActivity.class);
                startActivity(myIntent);
                overridePendingTransition(0, 0);

            }
        });

        rSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(SettingsActivity.this, RecordingActivity.class);
                startActivity(myIntent);
                overridePendingTransition(0, 0);

            }
        });

        controls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(SettingsActivity.this, ControlsActivity.class);
                startActivity(myIntent);
                overridePendingTransition(0, 0);

            }
        });

//        rate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }




//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        MyApplication.activityResumed();
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        MyApplication.activityPaused();
//    }
public static class InternetConnection {

    /**
     * CHECK WHETHER INTERNET CONNECTION IS AVAILABLE OR NOT
     */
    public static boolean checkConnection(Context context) {
        final ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connMgr != null) {
            NetworkInfo activeNetworkInfo = connMgr.getActiveNetworkInfo();

            if (activeNetworkInfo != null) { // connected to the internet
                // connected to the mobile provider's data plan
                if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    // connected to wifi
                    return true;
                } else return activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE;
            }
        }
        return false;
    }}

}
