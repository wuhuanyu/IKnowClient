package com.example.mrdreamer.iknow.Social.Friends;

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

import com.example.mrdreamer.iknow.R;
import com.example.mrdreamer.iknow.Social.Contract;
import com.example.mrdreamer.iknow.Social.DataListAdapter;
import com.example.mrdreamer.iknow.Social.DataSource.DataSource;
import com.example.mrdreamer.iknow.Social.User;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by stack on 8/22/16.
 */
public class GetFriendsFrag extends ListFragment implements Contract.View,SwipeRefreshLayout.OnRefreshListener{
    private Contract.Presenter presenter;
    private ListView friendsListView;
    private TextView infoTextView;
    private ArrayList<User> friends=null;
    private SwipeRefreshLayout swipe;
    private DataListAdapter arrayAdapter;
    private boolean isFirst=true;
    private static int count=0;

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

        presenter.startQuery(true);
        return view;
    }

    @Override
    public void setPresenter(Contract.Presenter p) {
        this.presenter=p;
    }

    @Override
    public void setDataList(ArrayList<User> friendsList) {
        Log.i("Count",Integer.toString(++count));
        this.friends=friendsList;
        arrayAdapter=new DataListAdapter(getActivity(),R.layout.userinfo_list_config,friendsList);
        friendsListView.setAdapter(arrayAdapter);
        friendsListView.setTextFilterEnabled(true);
        friendsListView.setOnItemClickListener((parent,itemView,position,id)->{

            PKInvitationDialog dialog=PKInvitationDialog.newInstance(friendsList.get(position).getName());
            dialog.show(getFragmentManager(),"SendPKInvitation");
        });
        Log.i(getClass().getSimpleName(),friendsList.toString());
    }

    @Override
    public void onErrorHappened(String errorInfo) {
        infoTextView.setText(errorInfo);
    }

    @Override
    public void onNoData() {

        // infoTextView.setText("No Friends Yet");
    }



    @Override
    public void onRefresh() {
        // Toast.makeText(getActivity(),"swipe",Toast.LENGTH_SHORT).show();
        presenter.startQuery(true);
        swipe.setRefreshing(false);

        // Log.i("onRefresh","refresh");
    }
}
