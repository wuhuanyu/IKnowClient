package com.example.mrdreamer.iknow;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.widget.Toast;

import com.example.mrdreamer.iknow.Social.User;

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
//    public static User getUser(Activity activity){
//

//    }
}
