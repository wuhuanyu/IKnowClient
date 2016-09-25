package com.example.mrdreamer.iknow.Social.DataSource.DB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.util.Log;

import com.example.mrdreamer.iknow.Social.DataSource.DataSource;
import com.example.mrdreamer.iknow.Social.DataSource.Utils;
import com.example.mrdreamer.iknow.User;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by stack on 8/24/16.
 */
public class LocalData implements DataSource {
    private static LocalData INSTANCE=null;
    private SQLiteOpenHelper dbhelper;
    private LocalData(Context context){
        this.dbhelper=new DBHelper(context);
    }
    public static LocalData getInstance(Context context){
        if(INSTANCE==null){
            return new LocalData(context);

        }
        return INSTANCE;
    }
    @Override
    public void getData(LoadDataCallback callback) {

        ArrayList<User> users=new ArrayList<>();

        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {

                SQLiteDatabase database=dbhelper.getReadableDatabase();
                String[] projection={DataPersistenceContract.DbEntry.COLUMN_NAME_JSON};

                Cursor c=database.query(DataPersistenceContract.DbEntry.TABLE_NAME,projection,null,null,null,null,null);
                if(c!=null&&c.getCount()>0){
                    c.moveToFirst();
                    String json=c.getString(c.getColumnIndexOrThrow(DataPersistenceContract.DbEntry.COLUMN_NAME_JSON));
                    Log.i("fromLocalee",json);
                    return  json;
                    // return json;

                }

                // database.close();
                else return null;
                }
            @Override
            protected void onPostExecute(String result){
                if(result==null)return;
                try{
                    users.addAll(Utils.jsonStringToUsers(result));
                    callback.onDataLoaded(users);
                } catch (JSONException e) {
                    Log.i(getClass().getSimpleName(),e.getMessage());
                    callback.onNoDataLoaded("error:"+e.getMessage());
                    //    e.printStackTrace();
                }

//                callback.onDataLoaded(users);

                // else
                //        callback.onNoDataLoaded("NoData");

            }
        }.execute();

    }

    @Override
    public void refreshData() {

    }

}
