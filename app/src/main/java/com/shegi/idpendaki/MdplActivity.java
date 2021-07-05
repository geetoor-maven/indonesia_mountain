package com.shegi.idpendaki;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.shegi.idpendaki.API.Apiclient;
import com.shegi.idpendaki.API.Apiinterface;
import com.shegi.idpendaki.Adapter.Provadapter;
import com.shegi.idpendaki.Modelprov.Modelpro;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MdplActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<Modelpro> modelprovs;
    private Provadapter provadapter;
    private Apiinterface apiinterface;
    ProgressDialog progressDialog;
    TextView txt_mdpl;
    String value;
    Munculdialog munculdialog;
    private AdView adView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mdpl);

        recyclerView = findViewById(R.id.rcl_viewmdpl);

        txt_mdpl = findViewById(R.id.txt_setmdpl);

        munculdialog = new Munculdialog();

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        value = getIntent().getExtras().getString("value");
        switch (value){
            case "100":
                txt_mdpl.setText("100 - 1000 Mdpl");
                Getmdpl("101");
                break;
            case "1000":
                txt_mdpl.setText("1000 - 2000 Mdpl");
                Getmdpl("1001");
                break;
            case "2000":
                txt_mdpl.setText("2000 - 3000 Mdpl");
                Getmdpl("2001");
                break;
            case "3000":
                txt_mdpl.setText("3000 - 4000 Mdpl");
                Getmdpl("3001");
                break;
            case "4000":
                txt_mdpl.setText("4000 - 5000 Mdpl");
                Getmdpl("4001");
                break;
        }

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });
        adView = findViewById(R.id.adViewmdpl);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }

    public void Getmdpl(String nilai){
        progressDialog = new ProgressDialog(MdplActivity.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_bar);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        apiinterface = Apiclient.getApiclient().create(Apiinterface.class);
        Call<List<Modelpro>> get = apiinterface.getMdpl(nilai);
        get.enqueue(new Callback<List<Modelpro>>() {
            @Override
            public void onResponse(Call<List<Modelpro>> call, final Response<List<Modelpro>> response) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        modelprovs = response.body();
                        provadapter = new Provadapter(modelprovs,MdplActivity.this);
                        recyclerView.setAdapter(provadapter);
                        provadapter.notifyDataSetChanged();
                    }
                },700);

            }

            @Override
            public void onFailure(Call<List<Modelpro>> call, Throwable t) {
                progressDialog.dismiss();
                munculdialog.mDialog("The server is being maintained\nplease come back next time",MdplActivity.this);
            }
        });
    }
}