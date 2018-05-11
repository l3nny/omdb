package com.example.yotis.omdb.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class tools {


    public static boolean isConnect(Context ctx) {
        ConnectivityManager connectivity = (ConnectivityManager) ctx.getSystemService(ctx.CONNECTIVITY_SERVICE);
        try {
            NetworkInfo activeNetwork = connectivity.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}