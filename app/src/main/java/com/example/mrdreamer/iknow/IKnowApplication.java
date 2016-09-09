package com.example.mrdreamer.iknow;

import android.app.Application;
import android.content.ContentProvider;
import android.content.Context;

/**
 * Created by mrdreamer on 16-9-9.
 */
public class IKnowApplication extends Application {
    private static  Context context;
    public  void onCreate(){
        super.onCreate();
        IKnowApplication.context=getApplicationContext();
    }

    public static Context getAppContext(){
        return IKnowApplication.context;
    }
}
