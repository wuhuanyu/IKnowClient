package com.example.mrdreamer.iknow;

import android.app.Application;
import android.content.ContentProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.mrdreamer.iknow.AccountManage.Account;
import com.example.mrdreamer.iknow.AccountManage.AccountManage;
import com.example.mrdreamer.iknow.AccountManage.LogoutTask;
import com.example.mrdreamer.iknow.GetQuestion.Contract;
import com.example.mrdreamer.iknow.Social.User;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;
import com.tencent.android.tpush.service.XGPushService;
/**
 * Created by mrdreamer on 16-9-9.
 */
public class IKnowApplication extends Application {
    private static  Context context;
    public  void onCreate(){
        super.onCreate();
        IKnowApplication.context=getApplicationContext();


    }


    @Override
    public void onTerminate(){
        super.onTerminate();
     //   AccountManage.performTask(AccountManage.Task.LOG_OUT,User.getUser());
      //  Log.i(getClass().getSimpleName(),User.getUser().toString());

    }




    public static Context getAppContext(){
        return IKnowApplication.context;
    }
}
