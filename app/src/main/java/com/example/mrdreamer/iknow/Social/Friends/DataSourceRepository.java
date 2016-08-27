package com.example.mrdreamer.iknow.Social.Friends;

import com.example.mrdreamer.iknow.Constants;
import com.example.mrdreamer.iknow.Social.DataSource.DataSource;
import com.example.mrdreamer.iknow.Social.User;

import java.util.ArrayList;

/**
 * Created by stack on 8/24/16.
 */
public class DataSourceRepository implements DataSource{
    public static final String GET_FRIENDS_LINK= Constants.PROTOCOL+Constants.SERVER+"/get_friend_list.php";
    public static final String SEARCH_USER_LINK=Constants.PROTOCOL+Constants.SERVER+"/search_user.php";
    private final DataSource localDataSource;
    private  final DataSource remoteDataSource;
    private boolean isCacheDirty=false;
    private static final DataSourceRepository INSTANCE=null;
    private DataSourceRepository(DataSource localDataSource,DataSource remoteDataSource){
        this.localDataSource=localDataSource;
        this.remoteDataSource=remoteDataSource;
    }
    public static DataSourceRepository getInstance(DataSource localDataSource,DataSource remoteDataSource){
        if(INSTANCE==null)
            return new DataSourceRepository(localDataSource,remoteDataSource);
        else
            return INSTANCE;
    }

    @Override
    public void getData(LoadDataCallback callback) {
       if(isCacheDirty)
            remoteDataSource.getData(new LoadDataCallback() {
               @Override
               public void onDataLoaded(ArrayList<User> users) {
                   isCacheDirty=false;
                   callback.onDataLoaded(users);
               }

               @Override
               public void onNoDataLoaded(String info) {
                   callback.onNoDataLoaded(info);
                  // localDataSource.getData(callback);
               }
           });
        else localDataSource.getData(new LoadDataCallback() {
           @Override
           public void onDataLoaded(ArrayList<User> users) {
               callback.onDataLoaded(users);
           }

           @Override
           public void onNoDataLoaded(String info) {
               remoteDataSource.getData(callback);

           }
       });

    }

    @Override
    public void refreshData() {
        this.isCacheDirty=true;
    }


}
