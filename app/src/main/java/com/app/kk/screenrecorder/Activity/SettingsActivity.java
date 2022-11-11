package com.app.kk.screenrecorder.Activity;

import android.app.ActivityOptions;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
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
import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdFormat;
import com.applovin.mediation.MaxAdViewAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.ads.MaxAdView;
import com.applovin.sdk.AppLovinSdk;
import com.applovin.sdk.AppLovinSdkConfiguration;
import com.applovin.sdk.AppLovinSdkUtils;

public class SettingsActivity extends AppCompatActivity implements MaxAdViewAdListener {

    public LinearLayout quality, rSettings, controls;
    public TextView rate, qua, con;

    private Toolbar toolbar;
    private MaxAdView MRECAdview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        createMrecAd();
        AppLovinSdk.getInstance(this).setMediationProvider("max");
        AppLovinSdk.initializeSdk(this, new AppLovinSdk.SdkInitializationListener() {
            @Override
            public void onSdkInitialized(final AppLovinSdkConfiguration configuration) {

            }
        });


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

    @Override
    public void onAdExpanded(MaxAd ad) {

    }

    @Override
    public void onAdCollapsed(MaxAd ad) {

    }

    @Override
    public void onAdLoaded(MaxAd ad) {

    }

    @Override
    public void onAdDisplayed(MaxAd ad) {

    }

    @Override
    public void onAdHidden(MaxAd ad) {

    }

    @Override
    public void onAdClicked(MaxAd ad) {

    }

    @Override
    public void onAdLoadFailed(String adUnitId, MaxError error) {

    }

    @Override
    public void onAdDisplayFailed(MaxAd ad, MaxError error) {

    }
    private void createMrecAd() {
        MRECAdview = new MaxAdView(getResources().getString(R.string.mrec), MaxAdFormat.MREC, this);

        MRECAdview.setListener(this);
        int width = AppLovinSdkUtils.dpToPx(this, 300);
        int height = AppLovinSdkUtils.dpToPx(this, 250);
        MRECAdview.setLayoutParams(new FrameLayout.LayoutParams(width, height, Gravity.CENTER));

        MRECAdview.setBackgroundColor(Color.WHITE);

        FrameLayout layout = findViewById(R.id.mrec);
        layout.addView(MRECAdview);
        MRECAdview.loadAd();
        MRECAdview.startAutoRefresh();

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
}
