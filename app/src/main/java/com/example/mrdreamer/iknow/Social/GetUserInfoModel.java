package com.example.mrdreamer.iknow.Social;

import android.os.AsyncTask;
import android.util.Log;

import com.example.mrdreamer.iknow.AccountManage.ConnectionUtils;
import com.example.mrdreamer.iknow.GetQuestion.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
//import com.example.mrdreamer.iknow.GetQuestion.Contract;

/**
 * Created by stack on 8/21/16.
 */
public class GetUserInfoModel implements com.example.mrdreamer.iknow.Social.Contract.Model{
    private Contract.Presenter presenter;
    private String query;
    public static final  String  METHOD="POST";
    public static final  String LINK="http://192.168.1.103/search_user_info.php";
    public GetUserInfoModel(String query, Contract.Presenter presenter)
    {
        this.query=query;
        this.presenter=presenter;
        presenter.setModel(this);

    }

    @Override
    public void FetchData() {
      //  new LoadData()



    }

    private class LoadData extends AsyncTask<String,Void,String>{





        @Override
        protected String doInBackground(String... params) {
            String query=params[0];
            try{

                HttpURLConnection connection= ConnectionUtils.getConnection(LINK,METHOD);

                BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
                writer.write("nameToSearch="+query);
                writer.flush();
                writer.close();

                BufferedReader reader=new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder builder=new StringBuilder();
                String line=null;

                while((line=reader.readLine())!=null){
                    builder.append(line);
                }
                Log.i(getClass().getSimpleName(),builder.toString());
                return builder.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }
    }


}
