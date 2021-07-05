package com.shegi.idpendaki;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

/**
 * created by shegi-developer on 22/11/20
 */
public class Munculdialog {
    Dialog dialog;
    Button btn_klik;
    TextView txt_ket,txtfailure;
    ImageView imagemuncul;

    public void mDialog(String text,Context context){
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_loginerror);
        btn_klik = dialog.findViewById(R.id.btn_dialogerror);
        txt_ket = dialog.findViewById(R.id.txt_pesanerror);

        txt_ket.setText(text);
        btn_klik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }
    public void Dialogsukses(String text, final Context context){
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_loginerror);
        btn_klik = dialog.findViewById(R.id.btn_dialogerror);
        txt_ket = dialog.findViewById(R.id.txt_pesanerror);
        txtfailure = dialog.findViewById(R.id.failure);

        txt_ket.setText(text);
        txtfailure.setText("YEAHHH");
        btn_klik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,Loginactivity.class);
                context.startActivity(intent);
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }
    public void Munculgambar(String img,Context context){
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_loginerror);
        btn_klik = dialog.findViewById(R.id.btn_dialogerror);
        txt_ket = dialog.findViewById(R.id.txt_pesanerror);
        txtfailure = dialog.findViewById(R.id.failure);
        imagemuncul = dialog.findViewById(R.id.imagemucul);
        txtfailure.setVisibility(View.GONE);
        txt_ket.setVisibility(View.GONE);
        imagemuncul.setVisibility(View.VISIBLE);
        btn_klik.setVisibility(View.GONE);

        Glide.with(context)
                .load("https://shegidev.com/indonesiamountain/img/" + img)
                .into(imagemuncul);


        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

    }


}
