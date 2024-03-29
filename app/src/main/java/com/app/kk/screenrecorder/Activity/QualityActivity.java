package com.app.kk.screenrecorder.Activity;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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


import java.text.NumberFormat;
import java.text.ParseException;

public class QualityActivity extends AppCompatActivity  {

    private Toolbar toolbar;

    final String KEY_SAVED_RADIO_BUTTON_INDEX = "SAVED_RADIO_BUTTON_INDEX";
    LinearLayout frate, bitRate;
    //    int nbits = nMbps * 1000000;
    public int checkIDFR;
    TextView desc1;
    SharedPref sharedPref;
    int nfps = 25;
    int nMbps = 4;
    TextView desc2;
    int checkIDVBR;
    private String fps = "25 FPS";

    TemplateView template;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPref = new SharedPref(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_quality);

        toolbar = findViewById(R.id.toolbar2);
        toolbar.setTitle("Settings");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        desc1 = findViewById(R.id.des1);
        frate = findViewById(R.id.fRate);
        bitRate = findViewById(R.id.bitRate);
        desc2 = findViewById(R.id.desc2);

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



        frate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frateDialog();
            }
        });

        bitRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bitRateDialog();
            }
        });

        if (sharedPref.loadfDesc() == null) {
            desc1.setText("Screen Capture will record at " + nfps + " frame per second");
        } else {
            desc1.setText(sharedPref.loadfDesc());
        }
        if (sharedPref.loadVDesc() == null) {
            desc2.setText("Screen Capture will record at " + nMbps + "mbps");
        } else {
            desc2.setText(sharedPref.loadVDesc());
        }
    }

    private void frateDialog() {

        final Dialog dialog = new Dialog(this);
        View mylayout = LayoutInflater.from(this).inflate(R.layout.custom_dialog_framerate, null);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(mylayout);

        final RadioGroup radioGroup = (RadioGroup) dialog.findViewById(R.id.rg);
        Button btnNo = (Button) dialog.findViewById(R.id.btnNo);
        Button btnYes = (Button) dialog.findViewById(R.id.btnYes);

        if (sharedPref.loadFrate() != 0) {
            radioGroup.check(sharedPref.loadFrate());
        } else {
            radioGroup.check(R.id.b);
        }
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                checkedId = radioGroup.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) dialog.findViewById(checkedId);
                try {
                    nfps = NumberFormat.getInstance().parse(radioButton.getText().toString()).intValue();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                checkIDFR = checkedId;
                Toast.makeText(getApplicationContext(), "" + nfps + " FPS", Toast.LENGTH_LONG).show();
            }
        });

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPref.setFrate(checkIDFR);
                sharedPref.frateValue(nfps);
                desc1.setText("Screen Capture will record at " + nfps + " frame per second");

                String desc = desc1.getText().toString();
                sharedPref.frameDesc(desc);

                dialog.dismiss();
            }
        });

        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation3;

        dialog.show();
    }

    private void bitRateDialog() {

        final Dialog dialog = new Dialog(this);
        View mylayout = LayoutInflater.from(this).inflate(R.layout.custom_dialog_bitrate, null);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(mylayout);

        final RadioGroup radioGroup = (RadioGroup) dialog.findViewById(R.id.rg);
        Button btnNo = (Button) dialog.findViewById(R.id.btnNo);
        Button btnYes = (Button) dialog.findViewById(R.id.btnYes);

        if (sharedPref.loadVrate() != 0) {
            radioGroup.check(sharedPref.loadVrate());
        } else {
            radioGroup.check(R.id.r10);
        }
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                checkedId = radioGroup.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) dialog.findViewById(checkedId);
                try {
                    nMbps = NumberFormat.getInstance().parse(radioButton.getText().toString()).intValue();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                checkIDVBR = checkedId;
                Toast.makeText(getApplicationContext(), "" + nMbps + " Mbps", Toast.LENGTH_LONG).show();
            }
        });

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPref.setVrate(checkIDVBR);
                sharedPref.VrateValue(nMbps);
                desc2.setText("Screen Capture will record at " + nMbps + "mbps");

                String desc1 = desc2.getText().toString();
                sharedPref.bitrateDesc(desc1);

                dialog.dismiss();
            }
        });

        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation3;

        dialog.show();
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
