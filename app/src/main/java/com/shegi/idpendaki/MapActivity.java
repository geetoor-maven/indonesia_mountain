package com.shegi.idpendaki;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    Button btn_gomaps;

    String lat,lon,namagunung,value;
    private AdView adView;
    private InterstitialAd interstitialAd;
    private GoogleMap mMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);


        btn_gomaps = findViewById(R.id.btn_gomaps);

        value = getIntent().getExtras().getString("value");

        switch (value){
            case "adap":
                lat = getIntent().getExtras().getString("lati");
                lon = getIntent().getExtras().getString("long");
                namagunung = getIntent().getExtras().getString("namagunung");
                break;
            case "prov":
                lat = getIntent().getExtras().getString("lat");
                lon = getIntent().getExtras().getString("lon");
                namagunung = getIntent().getExtras().getString("nama");
                break;
        }

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });

        adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        interstitialAd.loadAd(new AdRequest.Builder().build());

        btn_gomaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (interstitialAd.isLoaded()){
                    interstitialAd.show();
                }else {
                    Intent intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("google.navigation:q="+lat+","+lon+"&mode=1"));
                    intent.setPackage("com.google.android.apps.maps");

                    if (intent.resolveActivity(getPackageManager()) != null){
                        startActivity(intent);
                    }
                }

            }
        });

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng latLng = new LatLng(Double.parseDouble(lat),Double.parseDouble(lon));
        mMap.addMarker(new MarkerOptions().position(latLng).title(namagunung));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,10.0f));

    }
}