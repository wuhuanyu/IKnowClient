package com.example.mrdreamer.iknow.AccountManage;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.mrdreamer.iknow.R;

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
    // private Context context;
    // private String link;
    // private static String TAG="log";
    // private String name;
    // private String password;
    public LoginTask(Context context){
        // this.context=context;
        // this.link="http://192.168.1.102/account_manage.php";
        super(context);

    }

    public void onPreExecute(){

    }
    public String doInBackground(String... args)
    {  try{

        HttpURLConnection connection=ConnectionUtils.getConnection(link,"POST");
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
        // reader.close();
        // Log.i("result",name+" "+password+" "+builer.toString());
        // connection.disconnect();
        reader.close();
        return builer.toString();
    }catch(MalformedURLException e){
        return e.getMessage();
    }catch(IOException e){
        return e.getMessage();
    }
    }




    public void onPostExecute(String result){
        boolean success=false;
        String info="";
        //JSONObject jsonObject=new JSONObject(result);

        try{

            JSONObject jsonRootObject=new JSONObject(result);
            //JSONArray jsonArray=jsonObject.getJSONArray("result");
            JSONArray jsonArray=jsonRootObject.optJSONArray("result");

            JSONObject jsonObject=jsonArray.getJSONObject(0);

            success=Boolean.parseBoolean(jsonObject.optString("result_bool").toString());
            info=jsonObject.optString("info").toString();

        }catch (JSONException e){
            Toast.makeText(context,e.getMessage(),Toast.LENGTH_SHORT).show();
        }


        //JSONParser parser=new JSONParser();

        if(success)
        {
            SharedPreferences sharedPreferences=((Activity)context).getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putString(context.getString(R.string.username),name);
            editor.putString(context.getString(R.string.password),password);
            editor.putBoolean(context.getString(R.string.is_login),success);
            editor.commit();

            Toast.makeText(context,info,Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context,info,Toast.LENGTH_SHORT).show();
        }


    }





}

