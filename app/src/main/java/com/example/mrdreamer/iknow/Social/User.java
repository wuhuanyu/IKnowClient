package com.example.mrdreamer.iknow.Social;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.mrdreamer.iknow.IKnowApplication;
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

    public static User getUser(){
        Context context= IKnowApplication.getAppContext();
          SharedPreferences sharedPreferences=context.getSharedPreferences(
                  context.getString(R.string.preference),Context.MODE_PRIVATE);
        if(!sharedPreferences.contains(context.getString(
                R.string.preferenceName
        ))) return null;
        String name=sharedPreferences.getString(context.getString(R.string.preferenceName),"noName");
       // if(sharedPreferences==null)return null;
        boolean isLogin=sharedPreferences.getBoolean(context.getString(R.string.preferenceisLogin),false);
        String password=sharedPreferences.getString(context.getString(R.string.preferencePassword),"noPassword");
        return new User(name,isLogin);
    }

    public static boolean getUserIsLogin(){
        Context context=IKnowApplication.getAppContext();
   SharedPreferences sharedPreferences=context.getSharedPreferences(
           context.getString(R.string.preference),Context.MODE_PRIVATE);
        if(!sharedPreferences.contains(context.getString(
                R.string.preferenceName
        ))) return false;
        return  sharedPreferences.getBoolean(context.getString(R.string.preferenceisLogin),false);
    }

}
