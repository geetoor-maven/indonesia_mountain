package com.shegi.idpendaki;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.shegi.idpendaki.API.Apiclient;
import com.shegi.idpendaki.API.Apiinterface;
import com.shegi.idpendaki.Adapter.Provadapter;
import com.shegi.idpendaki.Model.Adapter;
import com.shegi.idpendaki.Modelprov.Modelpro;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProvActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<Modelpro> modelprovs;
    private Provadapter provadapter;
    private Apiinterface apiinterface;
    ProgressDialog progressDialog;
    TextView txt_namaprov;
    String value;
    Munculdialog munculdialog;
    AdView adView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prov);

        txt_namaprov = findViewById(R.id.txt_namaprov);
        recyclerView = findViewById(R.id.rcl_viewprov);

        munculdialog = new Munculdialog();

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        value = getIntent().getExtras().getString("value");

        switch (value){
            case "sulsel":
                txt_namaprov.setText("Sulawesi selatan");
                GetProv("Sulawesi Selatan");
                break;
            case "sulteng":
                txt_namaprov.setText("Sulawesi Tengah");
                GetProv("Sulawesi Tengah");
                break;
            case "aceh":
                txt_namaprov.setText("Aceh");
                GetProv("Aceh");
                break;
            case "lampung":
                txt_namaprov.setText("Lampung");
                GetProv("Lampung");
                break;
            case "riau":
                txt_namaprov.setText("Riau");
                GetProv("Riau");
                break;
            case "jawatimur":
                txt_namaprov.setText("Jawa Timur");
                GetProv("Jawa Timur");
                break;
            case "jawabarat":
                txt_namaprov.setText("Jawa Barat");
                GetProv("Jawa Barat");
                break;
            case "jawatengah":
                txt_namaprov.setText("Jawa Tengah");
                GetProv("Jawa Tengah");
                break;
            case "sumut":
                txt_namaprov.setText("Sumatra Utara");
                GetProv("Sumatra Utara");
                break;
            case "sumsel":
                txt_namaprov.setText("Sumatra Selatan");
                GetProv("Sumatra Selatan");
                break;
            case "sulbar":
                txt_namaprov.setText("Sulawesi Barat");
                GetProv("Sulawesi Barat");
                break;
            case "sulut":
                txt_namaprov.setText("Sulawesi Utara");
                GetProv("Sulawesi Utara");
                break;
            case "papua":
                txt_namaprov.setText("Papua");
                GetProv("Papua");
                break;
            case "bali":
                txt_namaprov.setText("Bali");
                GetProv("Bali");
                break;
            case "kaltim":
                txt_namaprov.setText("Kalimantan Timur");
                GetProv("Kalimantan Timur");
                break;
            case "kalbar":
                txt_namaprov.setText("Kalimantan Barat");
                GetProv("Kalimantan Barat");
                break;
            case "kalsel":
                txt_namaprov.setText("Kalimantan Selatan");
                GetProv("Kalimantan Selatan");
                break;
            case "kalteng":
                txt_namaprov.setText("Kalimantan Tengah");
                GetProv("Kalimantan Tengah");
                break;
            case "kalut":
                txt_namaprov.setText("Kalimantan Utara");
                GetProv("Kalimantan Utara");
                break;
        }

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });
        adView = findViewById(R.id.adViewprov);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }

    public void GetProv(String nilai){
        progressDialog = new ProgressDialog(ProvActivity.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_bar);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        apiinterface = Apiclient.getApiclient().create(Apiinterface.class);
        Call<List<Modelpro>> get = apiinterface.getProv(nilai);
        get.enqueue(new Callback<List<Modelpro>>() {
            @Override
            public void onResponse(Call<List<Modelpro>> call, final Response<List<Modelpro>> response) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        modelprovs = response.body();
                        provadapter = new Provadapter(modelprovs,ProvActivity.this);
                        recyclerView.setAdapter(provadapter);
                        provadapter.notifyDataSetChanged();
                    }
                },700);


            }

            @Override
            public void onFailure(Call<List<Modelpro>> call, Throwable t) {
                progressDialog.dismiss();
                munculdialog.mDialog("The server is being maintained\nplease come back next time",ProvActivity.this);
            }
        });
    }

}