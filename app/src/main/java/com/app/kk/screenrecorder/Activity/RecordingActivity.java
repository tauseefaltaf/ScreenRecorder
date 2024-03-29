package com.app.kk.screenrecorder.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.kk.screenrecorder.R;
import com.app.kk.screenrecorder.SharedPref;
import com.google.android.ads.nativetemplates.NativeTemplateStyle;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdOptions;


import java.io.File;

public class RecordingActivity extends AppCompatActivity  {

    private Toolbar toolbar;
    LinearLayout folder;
    File file;
    final int currentMax = 10;
    SwitchCompat audioSwitch;
    TextView address, desc3, desc1, timertxt;
    LinearLayout audio, count;
    SeekBar seekBar;
    SharedPref sharedPref;
    int currentProgress;

    TemplateView template;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_recording);

        sharedPref = new SharedPref(this);

        toolbar = findViewById(R.id.toolbar2);
        toolbar.setTitle("Settings");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        address = findViewById(R.id.address);
        audioSwitch = findViewById(R.id.sAudio);
        audio = findViewById(R.id.Audio);
        folder = findViewById(R.id.folder);
        count = findViewById(R.id.count);
        desc3 = findViewById(R.id.desc3);
        desc1 = findViewById(R.id.desc1);

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


        file = new File(Environment.getExternalStorageDirectory() + "/Screen Recording/");
        String add = file.getAbsolutePath();
        address.setText("" + add);

        if (sharedPref.loadTimerText() != 0) {
            desc3.setText(sharedPref.loadTimerText() + " second before recording begins");
        } else {
            desc3.setText(sharedPref.loadTimerText() + " second before recording begins");
        }

        if (sharedPref.loadMic()) {
            audioSwitch.setChecked(true);
            desc1.setText("Audio will be recorded from microphone during capture. Google does not allow apps to record internal audio");
        } else {
            audioSwitch.setChecked(false);
            desc1.setText("Audio will not be recorded during capture. Note that Google does not allow internal audio to be recorded, only from your microphone.");
        }

        audioSwitch.setClickable(false);
        audio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                audioPermission();
            }
        });

        folder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFolder();

            }
        });

        count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDialog();
            }
        });
    }

    private void countDialog() {
        final Dialog dialog = new Dialog(this);
        View mylayout = LayoutInflater.from(this).inflate(R.layout.custom_dialog_countdown, null);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(mylayout);

        Button btnNo = (Button) dialog.findViewById(R.id.btnNo);
        Button btnYes = (Button) dialog.findViewById(R.id.btnYes);

        timertxt = dialog.findViewById(R.id.timertxt);
        seekBar = dialog.findViewById(R.id.seekBar);

        seekBar.setMax(currentMax / 1);
        if (sharedPref.loadTimerText() != 0) {
            seekBar.setProgress(sharedPref.loadTimerText());
            timertxt.setText("" + sharedPref.loadTimerText());
        } else {
            seekBar.setProgress(0);
            timertxt.setText("0");
        }

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                currentProgress = progress * 1;
                timertxt.setText("" + currentProgress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPref.setTimerText(currentProgress);
                Toast.makeText(getApplicationContext(), "" + currentProgress, Toast.LENGTH_LONG).show();
                desc3.setText(sharedPref.loadTimerText() + " second before recording begins");
                dialog.dismiss();
            }
        });

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation3;
        dialog.show();
    }

    private void audioPermission() {
        if (!audioSwitch.isChecked()) {
            sharedPref.setMic(true);
            audioSwitch.setChecked(true);
            Toast.makeText(this, "Mic On", Toast.LENGTH_LONG).show();
            desc1.setText("Audio will be recorded from microphone during capture. Google does not allow apps to record internal audio");
        } else {
            audioSwitch.setChecked(false);
            sharedPref.setMic(false);
            Toast.makeText(this, "Mic Off", Toast.LENGTH_LONG).show();
            desc1.setText("Audio will not be recorded during capture. Note that Google does not allow internal audio to be recorded, only from your microphone.");
        }
    }

    public void openFolder() {
        Intent i = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        startActivityForResult(Intent.createChooser(i, "Choose directory"), 9999);
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
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

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
