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
import android.widget.TextView;
import android.widget.Toast;

import com.shegi.idpendaki.API.Apiclient;
import com.shegi.idpendaki.API.Apiinterface;
import com.shegi.idpendaki.Model.Login.Login;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Loginactivity extends AppCompatActivity {

    EditText edt_email,edt_pass;
    Button btn_login,btn_dialogerror;
    TextView txt_register,txt_pesanerror;
    ProgressDialog progressDialog;
    private long backpressed;
    private Toast show;
    Apiinterface apiinterface;
    Dialog dialog;

    SessionManager sessionManager;
    Perminternet perminternet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginactivity);

        sessionManager = new SessionManager(this);
        perminternet = new Perminternet();

        edt_email = findViewById(R.id.edt_email);
        edt_pass = findViewById(R.id.edt_pass);

        btn_login = findViewById(R.id.btn_login);

        txt_register = findViewById(R.id.txt_register);

        txt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!perminternet.isConnect(Loginactivity.this)){
                    perminternet.Showdialog(Loginactivity.this);
                }else {
                    Intent intent = new Intent(Loginactivity.this, Regisactivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edt_email.getText().toString().trim();
                String pass = edt_pass.getText().toString().trim();

                if (!perminternet.isConnect(Loginactivity.this)){
                    perminternet.Showdialog(Loginactivity.this);
                }else if (email.isEmpty()){
                    loginError("fill in the email first");
                }else if (pass.isEmpty()){
                    loginError("fill in the password first");
                }else {
                    Login(email,pass);
                }


            }
        });
    }

    private void Login(String email, String pass){
        progressDialog = new ProgressDialog(Loginactivity.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        apiinterface = Apiclient.getApiclient().create(Apiinterface.class);
        final Call<Login> login = apiinterface.Login(email,pass);
        login.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                if (response.isSuccessful() && response.body() != null){
                    // menangkap nilai http json
                    String success = response.body().getSuccess();

                    if (success.equals("200")){
                        // response success
                        final String ambilnama = response.body().getData().getNama();
                        final String ambilemail = response.body().getData().getEmail();
                        final String totalgunung = response.body().getData().getTotalgunung();

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                sessionManager.CreateSession(ambilnama,ambilemail,totalgunung);
                                Intent intent = new Intent(Loginactivity.this, HomeActivity.class);
                                startActivity(intent);
                            }
                        },4000);

                    }else if (success.equals("300")){
                        // response failed
                        progressDialog.dismiss();
                        loginError("Yeah Login failed\nMake sure you have registered");
                    }else if (success.equals("400")){
                        // failed post
                        progressDialog.dismiss();
                        loginError("Yeah Login failed\nMake sure the email and pass are valid");
                    }else{
                        // server error
                        progressDialog.dismiss();
                        loginError("Yeah Login failed\nOur server was overbooked");
                    }

                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                    progressDialog.dismiss();
                    loginError("Yeah Login failed\nServer kami sedang padat");
            }
        });

    }

    public void loginError(String pesan){
        dialog = new Dialog(Loginactivity.this);
        dialog.setContentView(R.layout.dialog_loginerror);
        btn_dialogerror = dialog.findViewById(R.id.btn_dialogerror);
        txt_pesanerror = dialog.findViewById(R.id.txt_pesanerror);

        txt_pesanerror.setText(pesan);

        btn_dialogerror.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

    }

    @Override
    public void onBackPressed() {
        if (backpressed + 2000 > System.currentTimeMillis()){
            show.cancel();
            super.onBackPressed();
            return;
        }else {
          show =  Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT);
            show.show();
        }

        backpressed = System.currentTimeMillis();
    }
}