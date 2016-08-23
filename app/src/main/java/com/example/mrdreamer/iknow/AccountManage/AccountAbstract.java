package com.example.mrdreamer.iknow.AccountManage;

import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;

import com.example.mrdreamer.iknow.Constants;
import com.example.mrdreamer.iknow.Constants.*;

/**
 * Created by stack on 16-8-16.
 */


public abstract class AccountAbstract extends AsyncTask<String,Void,String> {
    protected Context context;
    protected String link;
    protected String name;
    protected  String password;
    protected BufferedWriter writer=null;
    protected BufferedReader reader=null;


    public AccountAbstract(Context context){
        this.context=context;
        this.link= Constants.PROTOCOL+Constants.SERVER+"/account_manage.php";
    }

    protected abstract String doInBackground(String... args);
    protected abstract void onPostExecute(String result);


}
