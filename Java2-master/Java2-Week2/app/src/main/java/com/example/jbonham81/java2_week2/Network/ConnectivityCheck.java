package com.example.jbonham81.java2_week2.Network;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectivityCheck {

    Context context;

    public ConnectivityCheck() {
    }

    public String Connected(Context context) {

        this.context = context;

        ConnectivityManager mgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = mgr.getActiveNetworkInfo();

        if (mgr != null && info != null) {

            if (info.getType() == ConnectivityManager.TYPE_WIFI) {

                return "wifi";

            } else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {

                return "mobile";
            }
        }
        return "offline";
    }

}
