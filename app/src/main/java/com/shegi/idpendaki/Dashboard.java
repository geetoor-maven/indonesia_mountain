package com.shegi.idpendaki;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.shegi.idpendaki.API.Apiclient;
import com.shegi.idpendaki.API.Apiinterface;
import com.shegi.idpendaki.Model.Adapter;
import com.shegi.idpendaki.Model.Getmountain;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Dashboard extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<Getmountain> getmountains;
    private Adapter adapter;
    private Apiinterface apiinterface;

    private Toolbar m_tolbar;
    BottomNavigationView bottomNavigationView;
    Munculdialog munculdialog;
    Perminternet perminternet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        recyclerView = findViewById(R.id.rcl_viewdashboard);

        bottomNavigationView = findViewById(R.id.bottomnavigationview);

        munculdialog = new Munculdialog();
        perminternet = new Perminternet();

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        if (!perminternet.isConnect(Dashboard.this)){
            perminternet.Showdialog(Dashboard.this);
        }else {
            Getmountain("");
        }

        bottomNavigationView.setSelectedItemId(R.id.dashboard);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()){
                    case R.id.dashboard:
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                        overridePendingTransition(0,0);
                        finish();
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

        // toolbar
        m_tolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(m_tolbar);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu,menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Getmountain(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Getmountain(newText);
                return false;
            }
        });
        return true;
    }



    public void Getmountain(String key){
        apiinterface = Apiclient.getApiclient().create(Apiinterface.class);
        Call<List<Getmountain>> getmountain = apiinterface.getMountain(key);
        getmountain.enqueue(new Callback<List<Getmountain>>() {
            @Override
            public void onResponse(Call<List<Getmountain>> call, final Response<List<Getmountain>> response) {
                        getmountains = response.body();
                        adapter = new Adapter(getmountains,Dashboard.this);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
            }

            @Override
                public void onFailure(Call<List<Getmountain>> call, Throwable t) {
                    munculdialog.mDialog("The server is being maintained\nplease come back next time",Dashboard.this);
                }
        });
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("click yes if you want to exit");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Dashboard.super.onBackPressed();
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