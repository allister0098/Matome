package com.example.syo.listfragment.Manager;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by syo on 2015/07/20.
 */
public class Util {
    public static boolean netWorkCheck(Context context){
        ConnectivityManager cm =  (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if(info != null ){
            return info.isConnected();
        } else {
            return false;
        }
    }
}
