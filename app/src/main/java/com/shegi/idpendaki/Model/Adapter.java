package com.shegi.idpendaki.Model;

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
import com.shegi.idpendaki.Munculdialog;
import com.shegi.idpendaki.R;

import java.util.List;

/**
 * created by shegi-developer on 14/09/20
 */
public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    private List<Getmountain> getmountains;
    private Context context;
    Munculdialog munculdialog = new Munculdialog();

    public Adapter(List<Getmountain> getmountains, Context context) {
        this.getmountains = getmountains;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_gunung,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.txt_namagunung.setText(getmountains.get(position).getNama_gunung()+" Mountain");
        Glide.with(context)
                .load("https://shegidev.com/indonesiamountain/img/"+getmountains.get(position).getGambar())
                .into(holder.img_gunung);
        holder.img_gunung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                munculdialog.Munculgambar(getmountains.get(position).getGambar(),context);
            }
        });
        holder.btn_lihatdetail.setOnClickListener(new View.OnClickListener() {
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
                kabgunung = dialogview.findViewById(R.id.kabgunungdialog);
                provgunung = dialogview.findViewById(R.id.provgunungdialog);
                mdpl = dialogview.findViewById(R.id.mdplgunung);
                btn_godialog = dialogview.findViewById(R.id.btn_godialog);
                lat = dialogview.findViewById(R.id.latitude);
                lon = dialogview.findViewById(R.id.longtitude);

                namagunung.setText(getmountains.get(position).getNama_gunung() + " Mountain");
                mdpl.setText(getmountains.get(position).getMdpl() + " Mdpl (Height)");
                kabgunung.setText(getmountains.get(position).getKab_gunung() + " District");
                provgunung.setText(getmountains.get(position).getProv_gunung() + " Province");
                lat.setText(getmountains.get(position).getLatitude());
                lon.setText(getmountains.get(position).getLongtitude());

                btn_godialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String latitude = lat.getText().toString().trim();
                        String longtitude = lon.getText().toString().trim();
                        String nama = namagunung.getText().toString().trim();

                        Intent intent = new Intent(context, MapActivity.class);
                        intent.putExtra("value","adap");
                        intent.putExtra("lati",latitude);
                        intent.putExtra("long",longtitude);
                        intent.putExtra("namagunung",nama);

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
        return getmountains.size();
    }

    public  static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView txt_namagunung;
        ImageView img_gunung;
        Button btn_lihatdetail;

        public MyViewHolder(View itemView){
            super(itemView);
            txt_namagunung = itemView.findViewById(R.id.txt_namagunung);
            img_gunung = itemView.findViewById(R.id.img_gambargunung);
            btn_lihatdetail = itemView.findViewById(R.id.btn_lihatdetail);
        }
    }
}
