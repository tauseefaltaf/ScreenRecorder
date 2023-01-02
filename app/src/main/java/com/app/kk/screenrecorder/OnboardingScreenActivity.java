package com.app.kk.screenrecorder;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;


import com.app.kk.screenrecorder.Activity.Constant;
import com.app.kk.screenrecorder.Activity.MainActivity;
import com.app.kk.screenrecorder.Activity.MyApplication;
import com.google.android.ads.nativetemplates.NativeTemplateStyle;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.nativead.MediaView;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdOptions;
import com.google.android.gms.ads.nativead.NativeAdView;

import java.util.Objects;


public class OnboardingScreenActivity extends AppCompatActivity  {
    ImageView imageView;
    TextView description,heading;
    LinearLayout adsLayout;
    int currentPosition = 1;
    Button next_button;
    TemplateView template;
    NativeAd nativeAd;

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding_screen);

        Window window = OnboardingScreenActivity.this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(OnboardingScreenActivity.this, R.color.yellow));

        template = findViewById(R.id.my_template);

        adsLayout = findViewById(R.id.adsLayout);
        imageView = findViewById(R.id.imageView);
        description = findViewById(R.id.slider_desc);
        next_button = findViewById(R.id.next_button);

        heading = findViewById(R.id.title_text);
        if (InternetConnection.checkConnection(this)) {
            AdLoader adLoader = new AdLoader.Builder(OnboardingScreenActivity.this, Constant.NativeAd)
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





        if (currentPosition == 1) {
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.rec_add));
            description.setText("Record Your Screen On One Tab");
            currentPosition++;

        }


        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentPosition == 2) {
                    imageView.setImageDrawable(getResources().getDrawable(R.drawable.recording_add));
                    heading.setText("Screen Recorder Unlimited");
                    description.setText("Screen recorder with audio, You can record games too.");
                    next_button.setText("Finish");
                    next_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(OnboardingScreenActivity.this, MainActivity.class));
                            finish();
                        }
                    });
                }
            }
        });


    }
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
