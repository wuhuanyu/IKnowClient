package com.example.mrdreamer.iknow.Social.AddUser;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;

import com.example.mrdreamer.iknow.AccountManage.ConnectionUtils;
import com.example.mrdreamer.iknow.R;
import com.example.mrdreamer.iknow.Social.Friends.DataSourceRepository;
import com.example.mrdreamer.iknow.User;
import com.example.mrdreamer.iknow.Utils;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.util.ArrayList;

/**
 * Created by stack on 8/25/16.
 */
public class AddFriends extends AppCompatActivity implements SearchView.OnQueryTextListener{
    private SearchView searchView;
    private SearchUserFragment searchUserFragment;

    interface DataLoadCallback{
        void onDataLoaded(ArrayList<User> users);
        void onNoDataLoaded(String info);
    }
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_search_user);
        Toolbar toolbar=(Toolbar)findViewById(R.id.search_bar);


        searchUserFragment=new SearchUserFragment();
      Utils.addFragmentToActivity(this,searchUserFragment,R.id.users_listfragment);

          setSupportActionBar(toolbar);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.activity_search_user,menu);
        searchView=(SearchView)menu.findItem(R.id.searchuser_menu_item).getActionView();
        SearchManager searchManager=(SearchManager)getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(new ComponentName(this,this.getClass()))
        );
        searchView.setOnQueryTextListener(this);
        searchView.setIconified(false);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onQueryTextSubmit(String query) {

        new AsyncTask<String, Void, String>(){
            @Override
            protected String doInBackground(String... params) {
                HttpURLConnection connection=null;
                String name=params[0];
                try {
                    connection= ConnectionUtils.getConnection(DataSourceRepository.SEARCH_USER_LINK,"POST");
                    BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
                    String data="name="+name;
                    writer.write(data);
                    writer.flush();
                    writer.close();

                    BufferedReader reader=new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder builder=new StringBuilder();
                    String line=null;
                    while((line=reader.readLine())!=null){
                        builder.append(line);
                    }
                    Log.i(getClass().getSimpleName(),builder.toString());
                    reader.close();
                    connection.disconnect();
                  //  Log.i(getClass().getSimpleName(),builder.toString());
                    return builder.toString();
                    // return null;
                } catch (Exception e) {
                    return "error:"+e.getMessage();
                }


            }
            @Override
            protected void onPostExecute(String result){
                DataLoadCallback  callback=null;
                try{
                    callback=searchUserFragment;
                }catch(ClassCastException e){
                    Log.i(getClass().getSimpleName(),e.getMessage());
                }
                if(callback==null) return;
                if(result==null||result.contains("error")){
                    callback.onNoDataLoaded("Nodata "+result);
                }
                else{
                    ArrayList<User> users=new ArrayList<User>();
                    try {
                        users.addAll(com.example.mrdreamer.iknow.Social.DataSource.Utils.jsonStringToUsers(result));
                        callback.onDataLoaded(users);

                    } catch (JSONException e) {
                        callback.onNoDataLoaded("error:"+e.getMessage());
                        e.printStackTrace();
                    }
                }
            }
        }.execute(query);



        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
