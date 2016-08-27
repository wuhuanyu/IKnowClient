package com.example.mrdreamer.iknow.Social.DataSource.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.mrdreamer.iknow.Constants;

/**
 * Created by stack on 8/24/16.
 */
public class DBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION=1;
    public static final String DATABASE_NAME="Friends.db";
    private static final String TEXT_TYPE=" TEXT";
    private static final String COMMA_SEP=",";
    private static final String TIME_TYPE=" DATETIME";
    private static final String DEFAULT_TIME=" DEFAULT CURRENT_TIMESTAMP";

    private static final String  SQL_CREATE_ENTRIES=
            "create table "+DataPersistenceContract.DbEntry.TABLE_NAME+" ("+
                    DataPersistenceContract.DbEntry._ID+TEXT_TYPE+" primary key,"+
                    DataPersistenceContract.DbEntry.COLUMN_NAME_JSON+TEXT_TYPE+COMMA_SEP+
                    DataPersistenceContract.DbEntry.COLUMN_NAME_TIME+TIME_TYPE+DEFAULT_TIME+" )";
    public DBHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
