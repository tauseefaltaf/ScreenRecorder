package com.app.kk.screenrecorder;

import static com.app.kk.screenrecorder.Activity.MyApplication.showAdIfReady;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.kk.screenrecorder.Activity.Constant;
import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.ads.MaxAppOpenAd;
import com.applovin.sdk.AppLovinSdk;
import com.github.ybq.android.spinkit.SpinKitView;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.FadingCircle;

public class SplashScreen extends AppCompatActivity {
    ImageView imageView;
    TextView text;
    Animation topAnim, bottonAnim;
    public static String PackageName;
    private SpinKitView progressBar;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        PackageName=getApplicationContext().getPackageName();
        Log.d("tttttt", "onCreate: "+PackageName);

        final Handler handler = new Handler(Looper.getMainLooper());
        Window window = this.getWindow();


        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);


        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);


        window.setStatusBarColor(ContextCompat.getColor(SplashScreen.this, R.color.yellow));


        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottonAnim = AnimationUtils.loadAnimation(this, R.anim.botton_animation);

        imageView = findViewById(R.id.img);
        text = findViewById(R.id.txt);

        imageView.setAnimation(topAnim);
        text.setAnimation(bottonAnim);
        progressBar = findViewById(R.id.spin_kit);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                showAdIfReady();
                startActivity(new Intent(SplashScreen.this, OnboardingScreenActivity.class));
                finish();
            }
        }, 3000);

        ProgressBar progressBar = (ProgressBar) findViewById(R.id.spin_kit);
        Sprite fadingCircle = new FadingCircle();
        progressBar.setIndeterminateDrawable(fadingCircle);

    }

}