package com.shegi.idpendaki;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.shegi.idpendaki.API.Apiinterface;

/**
 * created by shegi-developer on 19/11/20
 */
public class Fragmentdialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final String[] prov = getActivity().getResources().getStringArray(R.array.prov);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setItems(prov, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch (prov[which]){
                    case "Sulawesi Selatan":
                        Pindah("sulsel");
                        break;
                    case "Sulawesi Tengah":
                        Pindah("sulteng");
                        break;
                    case "Aceh":
                        Pindah("aceh");
                        break;
                    case "Lampung":
                        Pindah("lampung");
                        break;
                    case "Riau":
                        Pindah("riau");
                        break;
                    case "Jawa Timur":
                        Pindah("jawatimur");
                        break;
                    case "Jawa Barat":
                        Pindah("jawabarat");
                        break;
                    case "Jawa Tengah":
                        Pindah("jawatengah");
                        break;
                    case "Sumatra Utara":
                        Pindah("sumut");
                        break;
                    case "Sumatra Selatan":
                        Pindah("sumsel");
                        break;
                    case "Sulawesi Barat":
                        Pindah("sulbar");
                        break;
                    case "Sulawesi Utara":
                        Pindah("sulut");
                        break;
                    case "Papua":
                        Pindah("papua");
                        break;
                    case "Bali":
                        Pindah("bali");
                        break;
                    case "Kalimantan Timur":
                        Pindah("kaltim");
                        break;
                    case "Kalimantan Barat":
                        Pindah("kalbar");
                        break;
                    case "Kalimantan Selatan":
                        Pindah("kalsel");
                        break;
                    case "Kalimantan Tengah":
                        Pindah("kalteng");
                        break;
                    case "Kalimantan Utara":
                        Pindah("kalut");
                        break;
                }

            }
        });

        return builder.create();
    }

    public void Pindah(String value){
        Intent intent = new Intent(getActivity(),ProvActivity.class);
        intent.putExtra("value",value);
        startActivity(intent);
    }

}
