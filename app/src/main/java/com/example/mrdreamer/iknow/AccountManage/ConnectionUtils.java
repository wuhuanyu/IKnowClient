package com.example.mrdreamer.iknow.AccountManage;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by stack on 16-8-16.
 */
public class ConnectionUtils{
        public static  HttpURLConnection getConnection(String link,String method) throws MalformedURLException,IOException {
            URL url=new URL(link);
            HttpURLConnection connection=(HttpURLConnection)url.openConnection();
            connection.setRequestMethod(method);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestProperty("charset","utf-8");
            return connection;
        }
   }
