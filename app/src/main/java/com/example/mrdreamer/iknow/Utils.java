package com.example.mrdreamer.iknow;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.widget.Toast;

/**
 * Created by stack on 8/19/16.
 */
public class Utils {
    public static void addFragmentToActivity(Activity activity, Fragment fragment,int id){
        // FragmentTransaction transaction=
        FragmentManager manager=activity.getFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        transaction.add(id,fragment);
        transaction.commit();
    }
    public static void replaceFragment(Activity activity, Fragment newFragment,int id){
        FragmentTransaction transaction=activity.getFragmentManager().beginTransaction();
        transaction.replace(id,newFragment);
        // transaction.addToBackStack(null);
        transaction.commit();
    }


    public static class Pair{

    }


    public static void makeToast(Context context,String string){
        Toast.makeText(context,string,Toast.LENGTH_SHORT).show();
    }


    public static class Constants{
        public static final String[] PERMISSIONS=new String[]{
                Manifest.permission.INTERNET,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.RECEIVE_BOOT_COMPLETED,
                Manifest.permission.RESTART_PACKAGES,
                Manifest.permission.BROADCAST_STICKY,
                Manifest.permission.WRITE_SETTINGS,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.WAKE_LOCK,
                Manifest.permission.KILL_BACKGROUND_PROCESSES,
                Manifest.permission.GET_TASKS,
                Manifest.permission.READ_LOGS,
                Manifest.permission.VIBRATE
        };
    }


    public static Object checkNotNull(Object o){
        return null;
    }
//    public static User getUser(Activity activity){
//

//    }
}
