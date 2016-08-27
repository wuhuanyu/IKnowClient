package com.example.mrdreamer.iknow.Social.DataSource.DB;

import android.provider.BaseColumns;

/**
 * Created by stack on 8/24/16.
 */
public class DataPersistenceContract {
    public static class DbEntry implements BaseColumns{
        public static final String TABLE_NAME="friends";
        public static final String COLUMN_NAME_TIME="time";
        public static final String COLUMN_NAME_JSON="friends_json";
    }
}
