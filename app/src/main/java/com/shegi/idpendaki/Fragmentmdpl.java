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

/**
 * created by shegi-developer on 20/11/20
 */
public class Fragmentmdpl extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final String[] mdpl = getActivity().getResources().getStringArray(R.array.mdpl);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setItems(mdpl, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (mdpl[which]){
                    case "100 - 1000":
                        Pindah("100");
                        break;
                    case "1000 - 2000":
                        Pindah("1000");
                        break;
                    case "2000 - 3000":
                        Pindah("2000");
                        break;
                    case "3000 - 4000":
                        Pindah("3000");
                        break;
                    case "4000 - 5000":
                        Pindah("4000");
                        break;

                }
            }
        });
        return builder.create();
    }

    public void Pindah(String value){
        Intent intent = new Intent(getActivity(),MdplActivity.class);
        intent.putExtra("value",value);
        startActivity(intent);
    }
}
