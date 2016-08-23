package com.example.mrdreamer.iknow.Social;

import android.app.ListFragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mrdreamer.iknow.AccountManage.LogoutTask;
import com.example.mrdreamer.iknow.R;

import java.util.ArrayList;

/**
 * Created by stack on 8/22/16.
 */
public class GetFriendsFrag extends ListFragment implements Contract.View,SwipeRefreshLayout.OnRefreshListener{
    private Contract.Presenter presenter;
    private ListView friendsListView;
    private TextView infoTextView;
    private ArrayList<User> friends=null;
    private SwipeRefreshLayout swipe;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view= inflater.inflate(R.layout.frag_getfriends,container,false);
         friendsListView=(ListView)view.findViewById(android.R.id.list);

        infoTextView=(TextView)view.findViewById(android.R.id.empty);
        swipe=(SwipeRefreshLayout)view.findViewById(R.id.swipe_refresh);
        swipe.setOnRefreshListener(this);


       presenter.startQuery();
        return view;
    }

    @Override
    public void setPresenter(Contract.Presenter p) {
        this.presenter=p;
    }

    @Override
    public void setDataList(ArrayList<User> friendsList) {
        this.friends=friendsList;

        ArrayAdapter<User> arrayAdapter=new FriendsListAdapter(getActivity(),R.layout.userinfo_list_config,friendsList);

        friendsListView.setAdapter(arrayAdapter);
          friendsListView.setOnItemClickListener((parent,itemView,position,id)->{
           // Toast.makeText(getActivity(),"ItemClicked",Toast.LENGTH_SHORT).show();
           // Log.i("listview","ItemClicked");
            if(friends!=null){
                     User friend=friends.get(position);
                Toast.makeText(getActivity(),friend.getName(),Toast.LENGTH_SHORT).show();
            }
           // else Log.i(getClass().getSimpleName(),"friends is null");
        });


    }

    @Override
    public void onErrorHappened(String errorInfo) {

        infoTextView.setText(errorInfo);

    }

    @Override
    public void onNoData() {

        infoTextView.setText("No Friends Yet");
    }

    @Override
    public void FetchData() {
        presenter.startQuery();
    }

    @Override
    public void onRefresh() {
       // Toast.makeText(getActivity(),"swipe",Toast.LENGTH_SHORT).show();
        presenter.startQuery();
        swipe.setRefreshing(false);

       // Log.i("onRefresh","refresh");
    }
}
