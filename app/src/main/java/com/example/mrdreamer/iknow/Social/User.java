package com.example.mrdreamer.iknow.Social;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.mrdreamer.iknow.R;

/**
 * Created by stack on 8/21/16.
 */
public class User {
    private String name,info;
    private boolean isLogin=false;
    public User(String name,String info,boolean isLogin){
        this.info=info;
        this.name=name;
        this.isLogin=isLogin;
    }

    public User(String name,boolean isLogin){
        this.isLogin=isLogin;
        this.info=null;
        this.name=name;
    }
    public User(String name){
        this.name=name;
        this.info="";
    }

    public String getName(){
        return name;
    }

    public String getIsLogInString(){
        if(isLogin) return "Online";
        else return "Offline";
    }
    public boolean getIsLoginBoolean(){
        return isLogin;
    }

    public String toString(){
        return "User: Name: "+name+" isLogin: "+getIsLogInString()+" Info: "+info+"\n";
    }

    public static User getUser(Activity activity){
          SharedPreferences sharedPreferences=activity.getPreferences(Context.MODE_PRIVATE);
        if(!sharedPreferences.contains(activity.getString(
                R.string.sharedPrefrenceName
        ))) return null;
        String name=sharedPreferences.getString(activity.getString(R.string.sharedPrefrenceName),"noName");
       // if(sharedPreferences==null)return null;
        boolean isLogin=sharedPreferences.getBoolean(activity.getString(R.string.sharedPrefrenceIsLogin),false);
        String password=sharedPreferences.getString(activity.getString(R.string.sharedPrefrencePasswd),"noPassword");
        return new User(name,isLogin);
    }

    public static boolean getUserIsLogin(Activity activity){
   SharedPreferences sharedPreferences=activity.getPreferences(Context.MODE_PRIVATE);
        if(!sharedPreferences.contains(activity.getString(
                R.string.sharedPrefrenceName
        ))) return false;

        return  sharedPreferences.getBoolean(activity.getString(R.string.sharedPrefrenceIsLogin),false);
    }

}
