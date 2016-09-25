package com.example.mrdreamer.iknow;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

/**
 * Created by stack on 8/21/16.
 */
public class User {
    private String name,info,password;
    private boolean isLogin=false;
    private boolean isPKMode=false;


    public User(String name,String info,String password,boolean isLogin){
        this.info=info;
        this.name=name;
        this.isLogin=isLogin;
        this.password=password;
    }

    public User(String name,boolean isLogin){
        this(name,"","",isLogin);
    }
    public User(String name){
              this(name,"","",false);
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

    public String getInfo(){
        return this.info;

    }
    public String getPassword(){
        return this.password;
    }


    public void setIsPKMode(boolean isPKMode){
        this.isPKMode=isPKMode;
    }

    public boolean getIsPKMode(){
        return this.isPKMode;
    }

    @Nullable
    public static User getUser(){
        Context context= IKnowApplication.getAppContext();
          SharedPreferences sharedPreferences=context.getSharedPreferences(
                  context.getString(R.string.preference),Context.MODE_PRIVATE);
        if(!sharedPreferences.contains(context.getString(
                R.string.preferenceName
        ))) return null;
        String name=sharedPreferences.getString(context.getString(R.string.preferenceName),"noName");
       //if(sharedPreferences==null)return null;
        boolean isLogin=sharedPreferences.getBoolean(context.getString(R.string.preferenceisLogin),false);
        String password=sharedPreferences.getString(context.getString(R.string.preferencePassword),"noPassword");
        boolean isPKMode=sharedPreferences.getBoolean(context.getString(R.string.preferenceidPKMode),false);
    //    return new User(name,"",password,isLogin);
        User user=new User(name,"",password,isLogin);
        user.setIsPKMode(isPKMode);

        return user;
    }



    public static void setUser(User user){

        Context context=IKnowApplication.getAppContext();
        SharedPreferences sharedPreferences=IKnowApplication.getAppContext().getSharedPreferences(
                IKnowApplication.getAppContext().getString(R.string.preference),
                Context.MODE_PRIVATE
        );
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(context.getString(R.string.preferenceName),user.getName());
        editor.putString(context.getString(R.string.preferencePassword),user.getName());
        editor.putBoolean(context.getString(R.string.preferenceisLogin),user.getIsLoginBoolean());
        editor.putBoolean(context.getString(R.string.preferenceidPKMode),user.getIsPKMode());
        IKnowApplication.setUser(user);
        editor.commit();
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
