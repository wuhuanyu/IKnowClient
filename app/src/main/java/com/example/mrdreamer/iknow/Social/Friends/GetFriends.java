package com.example.mrdreamer.iknow.Social.Friends;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuItemImpl;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Interpolator;
import android.widget.Toast;

import com.example.mrdreamer.iknow.IKnowApplication;
import com.example.mrdreamer.iknow.R;
import com.example.mrdreamer.iknow.Social.AddUser.AddFriends;
import com.example.mrdreamer.iknow.Social.Injection;
import com.example.mrdreamer.iknow.Social.User;
import com.example.mrdreamer.iknow.Utils;
import com.mikepenz.actionitembadge.library.ActionItemBadge;
import com.mikepenz.actionitembadge.library.ActionItemBadgeAdder;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;
import com.tencent.android.tpush.service.XGPushService;

/**
 * Created by stack on 8/21/16.
 */
public class GetFriends extends AppCompatActivity{
    private SearchView searchView;
    private GetFriendsFrag friendsFrag;
   // private ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState){




        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_friendslist);
        Toolbar toolbar=(Toolbar)findViewById(R.id.search_bar);
         friendsFrag=new GetFriendsFrag();
        new GetFriendsPresenter(friendsFrag, Injection.provideDataSourceRepoistory(this));
        Utils.addFragmentToActivity(this,friendsFrag,R.id.friends_list_frag_container);
    //    listView=(ListView)findViewById(R.id.searchuser_list);
        setSupportActionBar(toolbar);
        XGPushConfig.enableDebug(getApplicationContext(),true);

        User user=User.getUser();
        String name=null;
        if(!(user==null? false:user.getIsLoginBoolean())){

            name="mike";

        }
        else name=user.getName();
        XGPushManager.registerPush(getApplicationContext(), name,new XGIOperateCallback() {
            @Override
            public void onSuccess(Object o, int i) {
                Log.i("PushService",XGPushConfig.getToken(getApplicationContext()));

                Toast.makeText(getApplicationContext(),"success "+XGPushConfig.getToken(getApplicationContext()),Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFail(Object o, int i, String s) {
            //   Log.i("PushService",o.toString()+" failed");
             //   Toast.makeText(getApplicationContext(),"failed  "+i,Toast.LENGTH_SHORT).show();

            }
        });
        Intent service=new Intent(getApplicationContext(), XGPushService.class);

        getApplicationContext().startService(service);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
       getMenuInflater().inflate(R.menu.activity_friends_list,menu);
       //
        ActionItemBadge.update(this, menu.findItem(R.id.request_info_actionbar_item),
                FontAwesome.Icon.faw_android, ActionItemBadge.BadgeStyles.DARK_GREY, 5);

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch(menuItem.getItemId()){
            case R.id.request_info_actionbar_item:
                Intent startRequestinfoIntent=new Intent(this,RequestInfo.class);
                startActivity(startRequestinfoIntent);

                return true;
            case R.id.start_search_user:
                Intent startSearchIntent=new Intent(this, AddFriends.class);
                startActivity(startSearchIntent);
                return true;
        }
        return true;
    }




}
