package com.example.mrdreamer.iknow.AccountManage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.mrdreamer.iknow.IKnowApplication;
import com.example.mrdreamer.iknow.R;
import com.example.mrdreamer.iknow.Social.User;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;
import com.tencent.android.tpush.service.XGPushService;

import org.json.JSONException;
import org.json.JSONObject;
//import org.json.simple.JSONArray;
//import org.json.simple.parser.JSONParser;

import org.json.JSONArray;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/*
 * Created by mrdreamer on 05/08/16.
 */
public class LoginTask extends AccountAbstract{
        HttpURLConnection connection;
    public LoginTask(Context context) throws IOException {
        super(context);
        this.connection=ConnectionUtils.getConnection(link,"POST");

    }

    public void onPreExecute(){

    }
    public String doInBackground(String... args)
    {  try{


        //  connection.connect();
        name=args[0];
        password=args[1];
        //  Log.i(TAG,name);

        writer=new BufferedWriter(
                new OutputStreamWriter(connection.getOutputStream())
        );
        String data="name="+name+"&password="+password+"&todo="+"login";

        writer.write(data);
        writer.flush();
        writer.close();

        reader=new BufferedReader(
                new InputStreamReader(connection.getInputStream())
        );
        StringBuilder builer=new StringBuilder();
        String line=null;
        while((line=reader.readLine())!=null){
            builer.append(line);

        }
        reader.close();
        return builer.toString();
    }catch(MalformedURLException e){
        return e.getMessage();
    }catch(IOException e){
        return e.getMessage();
    }
        finally {

    }
    }
    public void onPostExecute(String result){
        boolean success=false;
        String info="";
        try{
            JSONObject jsonRootObject=new JSONObject(result);
            JSONArray jsonArray=jsonRootObject.optJSONArray("result");
            JSONObject jsonObject=jsonArray.getJSONObject(0);
            success=Boolean.parseBoolean(jsonObject.optString("result_bool").toString());
            info=jsonObject.optString("info").toString();

        }catch (JSONException e){
            Toast.makeText(context,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
        if(success)
        {
            User user=new User(name,true);
            User.setUser(user);
            registerUser(user);

            Toast.makeText(context,info,Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context,info,Toast.LENGTH_SHORT).show();
        }


    }
    private void registerUser(User user){
        if(user==null){
            return;
        }
           XGPushConfig.enableDebug(IKnowApplication.getAppContext(),true);

        XGPushManager.registerPush(IKnowApplication.getAppContext(), user.getName(),new XGIOperateCallback() {
            @Override
            public void onSuccess(Object o, int i) {
                Log.i("PushService",XGPushConfig.getToken(IKnowApplication.getAppContext()));

                Toast.makeText(IKnowApplication.getAppContext(),"success "+ XGPushConfig.getToken(IKnowApplication.getAppContext()),Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFail(Object o, int i, String s) {
            //   Log.i("PushService",o.toString()+" failed");
             //   Toast.makeText(getApplicationContext(),"failed  "+i,Toast.LENGTH_SHORT).show();

            }
        });
        Intent service=new Intent(IKnowApplication.getAppContext(), XGPushService.class);

        IKnowApplication.getAppContext().startService(service);
    }

}

