package com.example.mrdreamer.iknow.Social;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.example.mrdreamer.iknow.R;
import com.example.mrdreamer.iknow.Utils;

/**
 * Created by stack on 8/21/16.
 */
public class SearchUserGetFriends extends AppCompatActivity implements SearchView.OnQueryTextListener{
    private SearchView searchView;
   // private ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchuser_getfriends);
        Toolbar toolbar=(Toolbar)findViewById(R.id.search_bar);
        GetFriendsFrag friendsFrag=new GetFriendsFrag();
        new GetFriendsPresenter(friendsFrag,true);
        Utils.addFragmentToActivity(this,friendsFrag,R.id.friends_list_frag_container);
    //    listView=(ListView)findViewById(R.id.searchuser_list);
        setSupportActionBar(toolbar);





    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
       getMenuInflater().inflate(R.menu.searchuser_bar_menu,menu);
        searchView=(SearchView)menu.findItem(R.id.searchuser_menu_item).getActionView();
        searchView.setOnQueryTextListener(this);
        SearchManager searchManager=(SearchManager)getSystemService(Context.SEARCH_SERVICE);

        searchView.setSearchableInfo(searchManager.getSearchableInfo(new ComponentName(this,this.getClass())));
        searchView.setIconified(false);

        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
