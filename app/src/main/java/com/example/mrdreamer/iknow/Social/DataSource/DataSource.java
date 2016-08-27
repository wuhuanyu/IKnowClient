package com.example.mrdreamer.iknow.Social.DataSource;

import com.example.mrdreamer.iknow.Social.User;

import java.util.ArrayList;

/**
 * Created by stack on 8/24/16.
 */
public interface DataSource {
   //  onDataLoadedCallback(ArrayList<User> users);
    interface LoadDataCallback{
       void onDataLoaded(ArrayList<User> users);
       void onNoDataLoaded(String info);
   }

    void getData(LoadDataCallback callback);
    void refreshData();


}
