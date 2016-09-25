package com.example.mrdreamer.iknow.Social.Friends;

import android.util.Log;

import com.example.mrdreamer.iknow.Social.Contract;
import com.example.mrdreamer.iknow.Social.DataSource.DataSource;
import com.example.mrdreamer.iknow.User;

import java.util.ArrayList;

/**
 * Created by stack on 8/24/16.
 */
public class GetFriendsPresenter implements Contract.Presenter {

    private Contract.View view;
    private DataSource dataSourceRepository;
    public GetFriendsPresenter(Contract.View view, DataSource dataSource){
        this.view=view;
        this.dataSourceRepository=dataSource;
        view.setPresenter(this);
    }

    @Override
    public void startQuery(boolean forceFriendsUpdate) {
        if(forceFriendsUpdate){
            dataSourceRepository.refreshData();
            dataSourceRepository.getData(new DataSource.LoadDataCallback() {
                @Override
                public void onDataLoaded(ArrayList<User> users) {
                    view.setDataList(users);
                }

                @Override
                public void onNoDataLoaded(String info) {
                    Log.i("NoData",info);
                    view.onNoData();
                }
            });
        }
        else dataSourceRepository.getData(new DataSource.LoadDataCallback() {
            @Override
            public void onDataLoaded(ArrayList<User> users) {
                view.setDataList(users);
            }

            @Override
            public void onNoDataLoaded(String info) {

                view.onNoData();
            }
        });

    }
}
