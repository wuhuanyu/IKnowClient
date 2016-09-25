package com.example.mrdreamer.iknow;

import android.app.Application;
import android.content.Context;

/**
 * Created by mrdreamer on 16-9-9.
 */
public class IKnowApplication extends Application {
    private static  Context context;
    private static User user=null;


    public  void onCreate(){
        super.onCreate();
        IKnowApplication.context=getApplicationContext();
    }
    @Override
    public void onTerminate(){
        super.onTerminate();
    }
    public static Context getAppContext(){
        return IKnowApplication.context;
    }

    public static void setUser(User user){
        IKnowApplication.user=user;
    }
}
