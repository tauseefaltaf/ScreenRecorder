package com.app.kk.screenrecorder;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.app.kk.screenrecorder.Activity.Constant;
import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdFormat;
import com.applovin.mediation.MaxAdViewAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.ads.MaxAdView;
import com.applovin.sdk.AppLovinSdk;
import com.applovin.sdk.AppLovinSdkConfiguration;
import com.applovin.sdk.AppLovinSdkUtils;

public class App_info extends AppCompatActivity implements MaxAdViewAdListener {

    private MaxAdView MRECAdview;
    private FrameLayout layout;
    private ImageView icBack;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_info);
        AppLovinSdk.getInstance(this).setMediationProvider("max");
        AppLovinSdk.initializeSdk(this, new AppLovinSdk.SdkInitializationListener() {
            @Override
            public void onSdkInitialized(final AppLovinSdkConfiguration configuration) {
                createBannerAd();
            }
        });




        layout=findViewById(R.id.mrec);
        icBack=findViewById(R.id.icBack);
        icBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        createMrecAd();
//        getSupportActionBar().hide();
        Window window = this.getWindow();


        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);


        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);


        window.setStatusBarColor(ContextCompat.getColor(App_info.this, R.color.yellow));


    }
    private void createMrecAd() {
        MRECAdview = new MaxAdView(getResources().getString(R.string.mrec), MaxAdFormat.MREC, this);

        MRECAdview.setListener(this);
        int width = AppLovinSdkUtils.dpToPx(this, 300);
        int height = AppLovinSdkUtils.dpToPx(this, 250);
        MRECAdview.setLayoutParams(new FrameLayout.LayoutParams(width, height, Gravity.CENTER));

        MRECAdview.setBackgroundColor(Color.WHITE);


        layout.addView(MRECAdview);
        MRECAdview.loadAd();
        MRECAdview.startAutoRefresh();

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
    private void createBannerAd() {
        MRECAdview = new MaxAdView(Constant.Banner,this);
        MRECAdview.setListener(new MaxAdViewAdListener() {
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
        });



        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        int heightPx = getResources().getDimensionPixelSize( R.dimen.banner_height );
        MRECAdview.setLayoutParams( new FrameLayout.LayoutParams( width, heightPx ) );
        MRECAdview.setBackgroundColor(Color.WHITE);

        FrameLayout rootView = findViewById(R.id.bannerAd);
        rootView.addView( MRECAdview );

        MRECAdview.loadAd();
    }
}