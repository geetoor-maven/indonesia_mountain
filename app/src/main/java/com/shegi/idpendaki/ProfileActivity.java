package com.shegi.idpendaki;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashMap;

public class ProfileActivity extends AppCompatActivity{

    BottomNavigationView bottomNavigationView;

    SessionManager sessionManager;
    TextView txt_namalengkap,txt_email;
    Button btn_logout,btn_ok;
    Dialog dialog;
    RelativeLayout relativabout,relativkompas,relativcuaca;
    Perminternet perminternet;
    boolean gpsstatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        sessionManager = new SessionManager(this);
        perminternet = new Perminternet();

        txt_namalengkap = findViewById(R.id.txt_profile);
        txt_email = findViewById(R.id.txt_email);

        btn_logout = findViewById(R.id.btn_logout);

        relativabout = findViewById(R.id.relativeabout);
        relativkompas = findViewById(R.id.rtv_compas);
        relativcuaca = findViewById(R.id.rtv_cuaca);
        
        HashMap<String,String> user = sessionManager.getUserDetail();
        String namalengkap = user.get(sessionManager.NAME);
        String email = user.get(sessionManager.EMAIL);

        txt_namalengkap.setText(namalengkap);
        txt_email.setText(email);

        bottomNavigationView = findViewById(R.id.bottomnavigationview);

        bottomNavigationView.setSelectedItemId(R.id.profile);

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
                        startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                        overridePendingTransition(0,0);
                        finish();
                        return true;
                    case R.id.profile:
                        return true;
                }

                return false;
            }
        });


        relativabout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Munculabout();
            }
        });

        relativkompas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this,KompasActivity.class);
                startActivity(intent);
            }
        });

        relativcuaca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gpsStatus();
                switch (String.valueOf(gpsstatus)){
                    case "true":
                        if (!perminternet.isConnect(ProfileActivity.this)){
                            perminternet.Showdialog(ProfileActivity.this);
                        }else{
                            Intent intent = new Intent(ProfileActivity.this,CuacaActivity.class);
                            startActivity(intent);
                        }
                        break;
                    case "false":
                        AlertDialog.Builder dialog = new AlertDialog.Builder(ProfileActivity.this);
                        dialog.setTitle("Gps is off");
                        dialog.setMessage("Please activate first");
                        dialog.setCancelable(true);
                        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(intent);
                            }
                        });
                        AlertDialog alertDialog = dialog.create();
                        alertDialog.show();
                        break;
                }

            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
                builder.setTitle("Seriously You Want To Log Out?");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sessionManager.Logout();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(ProfileActivity.this, "Welcome Again", Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

    }


        @Override
    public void onBackPressed() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("click yes if you want to exit");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ProfileActivity.super.onBackPressed();
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

    private void Munculabout(){
        dialog = new Dialog(ProfileActivity.this);
        dialog.setContentView(R.layout.dialog_apk);

        btn_ok = dialog.findViewById(R.id.btn_dilaogok);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

    }

    private void gpsStatus(){
        LocationManager locationManager = (LocationManager)getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        gpsstatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }


}