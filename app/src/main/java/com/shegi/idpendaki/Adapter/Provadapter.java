package com.shegi.idpendaki.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.shegi.idpendaki.MapActivity;
import com.shegi.idpendaki.Modelprov.Modelpro;
import com.shegi.idpendaki.Munculdialog;
import com.shegi.idpendaki.R;

import java.util.List;

/**
 * created by shegi-developer on 20/11/20
 */
public class Provadapter  extends RecyclerView.Adapter<Provadapter.MyViewHolder> {
    private List<Modelpro> modelprovs;
    private Context context;
    Munculdialog munculdialog = new Munculdialog();

    public Provadapter(List<Modelpro> modelprovs, Context context) {
        this.modelprovs = modelprovs;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_prov,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Provadapter.MyViewHolder holder, final int position) {
        holder.txt_namagunungprov.setText(modelprovs.get(position).getNamaGunung() + " Mountain");
        Glide.with(context)
                .load("https://shegidev.com/indonesiamountain/img/"+modelprovs.get(position).getGambar())
                .into(holder.img_gunungpro);
        holder.img_gunungpro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                munculdialog.Munculgambar(modelprovs.get(position).getGambar(),context);
            }
        });
        holder.btn_lihat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getRootView().getContext());
                View dialogview = LayoutInflater.from(v.getRootView().getContext()).inflate(R.layout.lihat_detail,null);

                final TextView namagunung;
                TextView kabgunung;
                TextView mdpl;
                TextView provgunung;
                final TextView lat;
                final TextView lon;
                Button btn_godialog;

                namagunung = dialogview.findViewById(R.id.namagunungdialog);
                mdpl = dialogview.findViewById(R.id.mdplgunung);
                kabgunung = dialogview.findViewById(R.id.kabgunungdialog);
                provgunung = dialogview.findViewById(R.id.provgunungdialog);
                btn_godialog = dialogview.findViewById(R.id.btn_godialog);
                lat = dialogview.findViewById(R.id.latitude);
                lon = dialogview.findViewById(R.id.longtitude);

                namagunung.setText(modelprovs.get(position).getNamaGunung() + " Mountain");
                mdpl.setText(modelprovs.get(position).getMdpl() + " Mdpl (Height)");
                kabgunung.setText(modelprovs.get(position).getKabGunung() + " District");
                provgunung.setText(modelprovs.get(position).getProvGunung()+" Province");
                lat.setText(modelprovs.get(position).getLatitude());
                lon.setText(modelprovs.get(position).getLongtitude());

                btn_godialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String latitude = lat.getText().toString().trim();
                        String longtitude = lon.getText().toString().trim();
                        String nama = namagunung.getText().toString().trim();

                        Intent intent = new Intent(context, MapActivity.class);
                        intent.putExtra("value","prov");
                        intent.putExtra("lat",latitude);
                        intent.putExtra("lon",longtitude);
                        intent.putExtra("nama",nama);
                        context.startActivity(intent);
                    }
                });

                builder.setView(dialogview);
                builder.setCancelable(true);
                builder.show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return modelprovs.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txt_namagunungprov;
        ImageView img_gunungpro;
        Button btn_lihat;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_namagunungprov = itemView.findViewById(R.id.txt_namagunungprovensi);
            img_gunungpro = itemView.findViewById(R.id.img_gambargunungprovensi);
            btn_lihat = itemView.findViewById(R.id.btn_lihatdetailprovensi);
        }
    }
}
