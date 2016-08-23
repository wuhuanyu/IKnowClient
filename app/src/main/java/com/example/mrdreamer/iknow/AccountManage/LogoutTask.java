package com.example.mrdreamer.iknow.AccountManage;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.mrdreamer.iknow.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

/**
 * Created by stack on 16-8-16.
 */
public class LogoutTask extends AccountAbstract{

    public LogoutTask(Context context){
        super(context);
    }


    @Override
    protected String doInBackground(String... args) {






        try{
            HttpURLConnection connection=ConnectionUtils.getConnection(link,"POST");
            name=args[0];
            password=args[1];
            writer=new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
            String data="name="+name+"&password="+password+"&todo="+"logout";
            writer.write(data);
            writer.flush();
            writer.close();

            reader=new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder builder=new StringBuilder();
            String line=null;
            while((line=reader.readLine())!=null){
                builder.append(line);
            }
            reader.close();
            return builder.toString();



        }catch(IOException e){
            return e.getMessage();
        }



    }

    @Override
    protected void onPostExecute(String result) {
        boolean success=false;
        String info="";
        String toast_str="";
        try{
            JSONObject jsonRootObject=new JSONObject(result);
            JSONArray jsonArray=jsonRootObject.optJSONArray("result");
            JSONObject jsonObject=jsonArray.getJSONObject(0);
            info=jsonObject.getString("info").toString();
            success=jsonObject.getBoolean("result_bool");

        }catch (JSONException e){

            toast_str=e.getMessage();

        }
        if(success){
            toast_str="Logout Successfully";
            SharedPreferences sharedPreferences=((Activity)context).getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean(context.getString(R.string.is_login),false);
            editor.commit();
        }else{
            toast_str+=" LogOut failed";

        }

        Toast.makeText(context,info,Toast.LENGTH_SHORT).show();









    }
}
