package com.example.mrdreamer.iknow.AccountManage;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;

/**
 * Created by stack on 16-8-16.
 */
public class SignUpTask extends AccountAbstract{
  //  interface {
  //      void onSignUp();
  //  }

  //  private onSignUpSuccessfully


    public SignUpTask(Context context){
       super(context) ;
    }
    @Override
    protected String doInBackground(String... args) {
        try{
            HttpURLConnection connection=ConnectionUtils.getConnection(link,"POST");
            name=args[0];
            password=args[1];
            writer=new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
            String data="name="+name+"&password="+password+"&todo="+"sign_up";
            //writer(data);
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

        } catch (MalformedURLException e) {
            return e.getMessage();

        } catch (IOException e) {
        //    e.printStackTrace();
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        boolean success=false;
        String info=null;
        try{
            JSONObject jsonRootObject=new JSONObject(result);
            JSONArray jsonArray=jsonRootObject.optJSONArray("result");
            JSONObject jsonObject=jsonArray.getJSONObject(0);
            info=jsonObject.getString("info");
            success=jsonObject.getBoolean("result_bool");
            if(success){

            }




        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}

