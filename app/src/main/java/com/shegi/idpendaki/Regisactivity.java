package com.shegi.idpendaki;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.shegi.idpendaki.API.Apiclient;
import com.shegi.idpendaki.API.Apiinterface;
import com.shegi.idpendaki.Model.Value;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Regisactivity extends AppCompatActivity {
    EditText edt_nama,edt_email,edt_pass;
    Button btn_registration;
    ProgressDialog progressDialog;
    Apiinterface apiinterface;
    Perminternet perminternet;
    Munculdialog munculdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regisactivity);

        edt_nama = findViewById(R.id.edt_registnama);
        edt_email = findViewById(R.id.edt_registemail);
        edt_pass = findViewById(R.id.edt_registpass);

        btn_registration = findViewById(R.id.btn_registration);

        perminternet = new Perminternet();
        munculdialog = new Munculdialog();



        btn_registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nama = edt_nama.getText().toString().trim();
                String email = edt_email.getText().toString().trim();
                String pass = edt_pass.getText().toString().trim();

                if (!perminternet.isConnect(Regisactivity.this)){
                    perminternet.Showdialog(Regisactivity.this);
                }else if (nama.isEmpty()){
                    munculdialog.mDialog("make sure the data is complete",Regisactivity.this);
                }else if (email.isEmpty()){
                    munculdialog.mDialog("make sure the data is complete",Regisactivity.this);
                }else if (pass.isEmpty()){
                    munculdialog.mDialog("make sure the data is complete",Regisactivity.this);
                }else{
                    Save(nama,email,pass);
                }
            }
        });

    }


    private void Save(String nama,String email,String pass){

        progressDialog = new ProgressDialog(Regisactivity.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        apiinterface = Apiclient.getApiclient().create(Apiinterface.class);
        Call<Value> save = apiinterface.Save(nama,email,pass);
        save.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                if (response.isSuccessful() && response.body() != null){

                    String success = response.body().getSuccess();

                    if (success.equals("1")){
                        progressDialog.dismiss();
                        munculdialog.mDialog("email already exists",Regisactivity.this);

                    }else if (success.equals("2")){

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                munculdialog.Dialogsukses("congratulations you have registered",Regisactivity.this);

                            }
                        },4000);

                    }else{
                        progressDialog.dismiss();
                        munculdialog.mDialog("sorry the system is in error\nplease register next time",Regisactivity.this);

                    }


                }else {
                    progressDialog.dismiss();
                    munculdialog.mDialog("sorry the system is in error\nplease register next time",Regisactivity.this);
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                progressDialog.dismiss();
                munculdialog.mDialog("sorry the system is in error\nplease register next time",Regisactivity.this);
            }
        });
    }
}