package com.shegi.idpendaki;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashMap;

public class HomeActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    SessionManager sessionManager;

    TextView txt_totalgunung;
    RelativeLayout relativeprov,relativmdpl;
    Perminternet perminternet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        txt_totalgunung = findViewById(R.id.txt_totalgunung);

        relativeprov = findViewById(R.id.relativeprovensi);
        relativmdpl = findViewById(R.id.relativmdpl);

        perminternet = new Perminternet();
        if (!perminternet.isConnect(HomeActivity.this)){
            perminternet.Showdialog(HomeActivity.this);
        }
        sessionManager = new SessionManager(this);
        sessionManager.CheckLogin();
        HashMap<String,String> user = sessionManager.getUserDetail();
        String totalgunung = user.get(sessionManager.TOTALGN);
        txt_totalgunung.setText(totalgunung);

        relativeprov.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!perminternet.isConnect(HomeActivity.this)){
                    perminternet.Showdialog(HomeActivity.this);
                }else{
                    new Fragmentdialog().show(getSupportFragmentManager(),"fragmentDialog");
                }
            }
        });

        relativmdpl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!perminternet.isConnect(HomeActivity.this)){
                    perminternet.Showdialog(HomeActivity.this);
                }else{
                    new Fragmentmdpl().show(getSupportFragmentManager(),"fragmentmdpl");
                }
            }
        });

        bottomNavigationView = findViewById(R.id.bottomnavigationview);
        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.dashboard:
                        startActivity(new Intent(getApplicationContext(),Dashboard.class));
                        overridePendingTransition(0,0);
                        finish();
                        return true;
                    case R.id.home:
                        return true;
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                        overridePendingTransition(0,0);
                        finish();
                        return true;
                }

                return false;
            }
        });

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });
        AdLoader.Builder builder = new AdLoader.Builder(this,getString(R.string.idnativeadvence));
        builder.forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
            @Override
            public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                TemplateView templateView = findViewById(R.id.my_template);
                templateView.setNativeAd(unifiedNativeAd);
            }
        });
        AdLoader adLoader = builder.build();
        AdRequest adRequest = new AdRequest.Builder().build();
        adLoader.loadAd(adRequest);
    }

    @Override
    public void onBackPressed() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("click yes if you want to exit");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                HomeActivity.super.onBackPressed();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}