package com.shegi.idpendaki;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class PermissionActivity extends AppCompatActivity {

    Button btn_aktifkan;
    TextView txt_lat,txt_long;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);



        btn_aktifkan = findViewById(R.id.btn_aktifkanlokasi);

        txt_lat = findViewById(R.id.txt_lat);
        txt_long = findViewById(R.id.txt_long);

        String lat = getIntent().getExtras().getString("latitude");
        txt_lat.setText(lat);
        String lon = getIntent().getExtras().getString("longtitude");
        txt_long.setText(lon);

        btn_aktifkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cekpermission();
            }
        });

    }

    public void Cekpermission(){
        Dexter.withActivity(PermissionActivity.this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                       Intent intent = new Intent(PermissionActivity.this, MapActivity.class);
                       intent.putExtra("latitude",txt_lat.getText().toString().trim());
                       intent.putExtra("longtitude",txt_long.getText().toString().trim());
                       startActivity(intent);
                       finish();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        if (permissionDeniedResponse.isPermanentlyDenied()){
                            AlertDialog.Builder builder = new AlertDialog.Builder(PermissionActivity.this);
                            builder.setTitle("Permission danied")
                                    .setMessage("Tes Tes")
                                    .setNegativeButton("Cancel",null)
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent();
                                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                            intent.setData(Uri.fromParts("package",getPackageName(),null));
                                        }
                                    })
                                    .show();
                        }else {
                            Toast.makeText(PermissionActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                })
                .check();
    }
}