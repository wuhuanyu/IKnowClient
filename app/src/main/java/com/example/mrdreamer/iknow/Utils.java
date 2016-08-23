package com.example.mrdreamer.iknow;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

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
    public static class Pair{

    }
}
