package com.example.mrdreamer.iknow.Social;

import com.example.mrdreamer.iknow.Utils;

import java.util.ArrayList;

/**
 * Created by stack on 8/21/16.
 */
public interface Contract  {
    interface View{
        void setPresenter(Presenter p);
        void setDataList(ArrayList<User> friendsList);
        void onErrorHappened(String errorInfo);
        void onNoData();
        void FetchData();



    }
    interface Presenter{
        void onDataListLoadedCallback(ArrayList<User> friendsList);
        void onNoDataLoadedCallback(String errorInfo);
        void startQuery();
        void setModel(Model model);

    }
    interface Model{
        void FetchData();



    }
}
