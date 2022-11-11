package com.app.kk.screenrecorder;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;


import com.app.kk.screenrecorder.Activity.Constant;
import com.app.kk.screenrecorder.Activity.MainActivity;
import com.app.kk.screenrecorder.Activity.MyApplication;
import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdFormat;
import com.applovin.mediation.MaxAdViewAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.ads.MaxAdView;
import com.applovin.sdk.AppLovinSdk;
import com.applovin.sdk.AppLovinSdkConfiguration;
import com.applovin.sdk.AppLovinSdkUtils;

public class OnboardingScreenActivity extends AppCompatActivity{
    ImageView imageView;
    TextView heading, description;
    int currentPosition = 1;
    Button next_button;
    private MaxAdView MRECAdview;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding_screen);
        Window window = this.getWindow();


        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);


        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);


        window.setStatusBarColor(ContextCompat.getColor(OnboardingScreenActivity.this, R.color.yellow));
        AppLovinSdk.getInstance(this).setMediationProvider("max");

        AppLovinSdk.initializeSdk(this, new AppLovinSdk.SdkInitializationListener() {
            @Override
            public void onSdkInitialized(final AppLovinSdkConfiguration configuration) {

            }
        });

        imageView = findViewById(R.id.imageView);
        heading = findViewById(R.id.title_text);
        description = findViewById(R.id.slider_desc);
        createMrecAd();


        if (currentPosition == 1) {
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.rec_add));
            heading.setText("Screen Recorder");
            description.setText("Record Your Screen On One Tab");
            currentPosition++;

        }

        next_button = findViewById(R.id.next_button);

        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentPosition == 2) {
                    imageView.setImageDrawable(getResources().getDrawable(R.drawable.recording_add));
                    heading.setText("Screen Recorder Unlimited");
                    description.setText("Screen recorder with internal audio.Record video game & screen without lag.");
                    next_button.setText("Finish");
                    next_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            startActivity(new Intent(OnboardingScreenActivity.this, MainActivity.class));
                            finish();
                        }
                    });
                }
                //     currentPosition++;

//                } else if (currentPosition == 3) {
//                    imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_launcher_background));
//                    heading.setText("Scan Image Qr Code");
//                    description.setText("You can select the image from gallery and scan qr code from it");
//                    next_button.setText("Finish");
//                    next_button.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            startActivity(new Intent(OnboardingScreenActivity.this, Level.class));
//                            finish();
//                        }
//                    });
//                }
            }
        });
    }
    private void createMrecAd() {
        MRECAdview = new MaxAdView(Constant.MREC_ADD_KEY, MaxAdFormat.MREC, this);
        MRECAdview.setListener(new MaxAdViewAdListener() {
            @Override
            public void onAdExpanded(MaxAd ad) {

            }

            @Override
            public void onAdCollapsed(MaxAd ad) {

            }

            @Override
            public void onAdLoaded(MaxAd ad) {
                Log.d("onAdLoaded", "onAdLoaded: ");
            }

            @Override
            public void onAdDisplayed(MaxAd ad) {
                Log.d("onAdLoaded", "onAdDisplayed: ");
            }

            @Override
            public void onAdHidden(MaxAd ad) {
                Log.d("onAdLoaded", "onAdHidden: ");
            }

            @Override
            public void onAdClicked(MaxAd ad) {
                Log.d("onAdLoaded", "onAdClicked: ");
            }

            @Override
            public void onAdLoadFailed(String adUnitId, MaxError error) {
                Log.d("onAdLoaded", "onAdLoadFailed: ");
            }

            @Override
            public void onAdDisplayFailed(MaxAd ad, MaxError error) {

                Log.d("onAdLoaded", "onAdDisplayFailed: ");
            }
        });

        int width = AppLovinSdkUtils.dpToPx(this, 300);
        int height = AppLovinSdkUtils.dpToPx(this, 250);
        MRECAdview.setLayoutParams(new FrameLayout.LayoutParams(width, height, Gravity.CENTER));

        MRECAdview.setBackgroundColor(Color.WHITE);

        FrameLayout layout = findViewById(R.id.mrec);
        layout.addView(MRECAdview);
        MRECAdview.loadAd();
        MRECAdview.startAutoRefresh();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MyApplication.isFirstTime=true;
    }
}