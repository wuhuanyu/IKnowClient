package com.example.mrdreamer.iknow.Social;

import android.content.Context;

import com.example.mrdreamer.iknow.Social.DataSource.DB.LocalData;
import com.example.mrdreamer.iknow.Social.Friends.DataSourceRepository;
import com.example.mrdreamer.iknow.Social.DataSource.Remote.RemoteDataSource;

/**
 * Created by stack on 8/24/16.
 */
public class Injection {
    public static DataSourceRepository provideDataSourceRepoistory(Context context){
        return DataSourceRepository.getInstance(LocalData.getInstance(context), RemoteDataSource.getInstance(context));
    }

}
