package com.shegi.idpendaki;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;

/**
 * created by shegi-developer on 22/11/20
 */
public class Perminternet {

    public boolean isConnect(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wificon = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobilecon = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if ((wificon != null && wificon.isConnected()) || (mobilecon != null && mobilecon.isConnected())){
            return  true;
        }else {
            return false;
        }

    }

    public void Showdialog(final Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Activate the internet to access this Apps")
                .setCancelable(false)
                .setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        context.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                });
        builder.show();
    }
}
