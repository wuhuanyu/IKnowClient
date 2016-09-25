package com.example.mrdreamer.iknow.Social.DataSource.Remote;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import com.example.mrdreamer.iknow.AccountManage.ConnectionUtils;
import com.example.mrdreamer.iknow.IKnowApplication;
import com.example.mrdreamer.iknow.Social.Friends.DataSourceRepository;
import com.example.mrdreamer.iknow.Social.DataSource.DB.DBHelper;
import com.example.mrdreamer.iknow.Social.DataSource.DB.DataPersistenceContract;
import com.example.mrdreamer.iknow.Social.DataSource.DataSource;
import com.example.mrdreamer.iknow.Social.DataSource.Utils;
import com.example.mrdreamer.iknow.User;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.util.ArrayList;

/**
 * Created by stack on 8/24/16.
 */
public class RemoteDataSource implements DataSource{
    private Context context;
    private static RemoteDataSource INSTANCE;
    private  RemoteDataSource(Context context){
        this.context=context;


    }
    public static RemoteDataSource getInstance(Context context){
        if(INSTANCE==null)
            return new RemoteDataSource(context);
        else
            return INSTANCE;


    }
    @Override
    public void getData(LoadDataCallback callback) {
        User user=User.getUser();
        if(user==null? true:!user.getIsLoginBoolean()){
            com.example.mrdreamer.iknow.Utils.makeToast(IKnowApplication.getAppContext(),"Login first please");
            return;
        }

        ArrayList<User> users=new ArrayList<>();

        new AsyncTask<String, Void, String>(){
            @Override
            protected String doInBackground(String... params) {
                HttpURLConnection connection=null;
                String name=params[0];
                String password=params[1];
                try {
                    connection= ConnectionUtils.getConnection(DataSourceRepository.GET_FRIENDS_LINK,"POST");
                    BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
                    String data="name="+name+"&password="+password;
                    writer.write(data);
                    writer.flush();
                    writer.close();

                    BufferedReader reader=new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder builder=new StringBuilder();
                    String line=null;
                    while((line=reader.readLine())!=null){
                        builder.append(line);
                    }
                    Log.i(getClass().getSimpleName(),builder.toString());
                    reader.close();
                    connection.disconnect();
                    return builder.toString();
                } catch (Exception e) {
                //   callback.onNoDataLoaded(e.getMessage());
                                        Log.i("error:",e.getMessage());
                    return "error"+e.getMessage();


                } finally {
                    if(connection!=null)
                        connection.disconnect();

                }

              //  return null;
            }

            @Override

            public void onPostExecute(String result){
                if(result.contains("error")){
                    callback.onNoDataLoaded(result);

                }
                else{
                try {

                    users.addAll(Utils.jsonStringToUsers(result));
                     DBHelper dbhelper=new DBHelper(context);
                    SQLiteDatabase db=dbhelper.getWritableDatabase();
                    db.execSQL("delete from "+ DataPersistenceContract.DbEntry.TABLE_NAME);
                    ContentValues values=new ContentValues();
                    values.put(DataPersistenceContract.DbEntry.COLUMN_NAME_JSON,result);
                    db.insert(DataPersistenceContract.DbEntry.TABLE_NAME,null,values);
                    db.close();
                    callback.onDataLoaded(users);
                } catch (JSONException e) {
                    callback.onNoDataLoaded("error:"+e.getMessage());
                    Log.i("RemoteDataSource",e.getMessage());
                    //e.printStackTrace();
                }

                }
            }
        }.execute(user.getName(),user.getPassword());
    }
    @Override
    public void refreshData() {

    }
}
