package com.example.mrdreamer.iknow.Social;

import android.util.Log;

import com.example.mrdreamer.iknow.AccountManage.LogoutTask;

import java.net.ContentHandler;
import java.util.ArrayList;

public class GetFriendsPresenter implements Contract.Presenter{


    private Contract.View view;
    private Contract.Model model;
    private boolean isSearch=false;
    public GetFriendsPresenter(Contract.View view,boolean isSearch){
        this.view=view;
        view.setPresenter(this);
        this.isSearch=isSearch;

        if(isSearch){
            model=new GetFriendsModel(this,"m");

        }
        else
        model=new GetFriendsModel(this,"mike","why");

    }


    @Override
    public void onDataListLoadedCallback(ArrayList<User> friendsList) {
        Log.i(getClass().getSimpleName(),"ArrayListFetched");
        view.setDataList(friendsList);

    }

    @Override
    public void onNoDataLoadedCallback(String errorInfo) {
        Log.i(getClass().getSimpleName(),errorInfo);
            if(errorInfo.contains("error")){
                view.onErrorHappened(errorInfo);

            }
        else
                view.onNoData();

    }

    @Override
    public void startQuery() {
      //  Log.i(getClass().getSimpleName(),query);
        model.FetchData();


    }

    @Override
    public void setModel(Contract.Model model) {
        this.model=model;

    }
}