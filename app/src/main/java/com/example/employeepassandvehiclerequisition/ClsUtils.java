package com.example.employeepassandvehiclerequisition;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ClsUtils {
    public Boolean HasConnection(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {

            android.net.NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

            android.net.NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if (mobile != null && mobile.isConnectedOrConnecting() || wifi != null && wifi.isConnectedOrConnecting()) {
                return true;
            } else {
                return false;
            }

        } else {
            return false;
        }
    }

    public AlertDialog.Builder NoInternetBuilDialog(Context c) {

        AlertDialog.Builder altdial = new AlertDialog.Builder(c);

        altdial.setTitle("No Internet Connection");

        altdial.setMessage("Connect To A WIFI Network or Open Your Mobile Data.").setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //finish();
                        System.exit(0);
                    }
                });

        return altdial;
    }
}
