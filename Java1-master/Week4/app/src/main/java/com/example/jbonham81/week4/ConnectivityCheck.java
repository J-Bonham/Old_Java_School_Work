// Jeremiah Bonham
// Java 1 Week 4 1411

// API Key for quick usage without looking up
// http://api.themoviedb.org/3/search/movie?api_key=4ed229fe2e133f61d3815a1d358e111f&query=fight%20club
//
// 4ed229fe2e133f61d3815a1d358e111f

package com.example.jbonham81.week4;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by jbonham81 on 11/16/14.
 */

public class ConnectivityCheck {

    private Context _context;

    public ConnectivityCheck(Context context) {

        this._context = context;

    }

    public boolean isConnected() {
        ConnectivityManager connectivity = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivity != null) {

            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
            {
                return true;
            }

        }
        return false;
    }


}